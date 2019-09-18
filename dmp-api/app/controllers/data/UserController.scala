package controllers.data

import java.sql.Timestamp
import java.util

import common.JWTUtils
import controllers.{AllInOneController, CommonController}
import javax.inject.{Inject, Singleton}
import modules.user.{AuthLog, AuthLogRepository, User, UserRepository}
import play.api.Configuration
import play.api.mvc._

import scala.collection.mutable
import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(repo: UserRepository,
                               loginRepo: AuthLogRepository,
                               config: Configuration,
                               cc: MessagesControllerComponents,
                               jwt: SessionCookieBaker)
                              (implicit ec: ExecutionContext)
  extends AllInOneController(config, cc) with CommonController {

  def post(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[User])
      if (entity == null) {
        BadRequest("User's content must not be empty!")
        throw new Exception("body empty")
      } else {
        val date = new Timestamp(System.currentTimeMillis())
        val app = entity.copy(createTime = date, modifyTime = date)
        repo.create(app).map(data => Ok(toJson(data))).map(x =>
          x.withSession(
            ("username", entity.userName),
            ("nickname", entity.nickName)
          )
        )
      }
  }

  def get(): Action[AnyContent] = Action {
    request =>
      val tokenOption = request.headers.get("token")
      val infos = jwt.decode(tokenOption.getOrElse(""))
      if (infos.isEmpty) {
        BadRequest(toJson(Map("code" -> "failed", "msg" -> "用户失效")))
      } else {
        val authorId = request.session.get("authorId")
        BadRequest(toJson(Map("code" -> "failed", "msg" -> "用户失效")))
      }
  }


  def login(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[mutable.Map[String, Any]])
      if (entity.isEmpty) {
        BadRequest("body content must not be empty!")
        throw new Exception("body must not empty!")
      } else {
        repo.getAllBy(entity).map(u => {
          val res = if (u.nonEmpty) {
            // 记录登陆事件
            entity ++= Map("passWord" ->  "******", "state" -> 1)
            Ok(toJson(Map("id" -> u(0).id,
              "username" -> u(0).userName,
              "nickName" -> u(0).nickName,
              "token" -> generateJwt(u(0))
            )))
          } else {
            entity += (("state", 0))
            BadRequest("username and password not match!")
          }
          authLog(entity)
          res
        })
      }

  }

  def authLog(query: mutable.Map[String, Any]): Unit = {
    val date = new Timestamp(System.currentTimeMillis())
    loginRepo.create(
      AuthLog(None,
        query.getOrElse("userName", "").toString,
        query.getOrElse("passWord", "").toString,
        query.getOrElse("state", 0).toString.toShort,
        date
      ))
  }

  def generateJwt (u: User): String = {
    val hm = new util.HashMap[String, AnyRef]()
    hm.put("id", u.id)
    hm.put("username", u.userName)
    hm.put("nickName", u.nickName)
    JWTUtils.createJWT(hm)
  }


}

