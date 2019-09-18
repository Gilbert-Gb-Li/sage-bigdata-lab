package controllers.data

import java.sql.Timestamp

import controllers.CommonController
import modules.data.{App, AppRepository}
import javax.inject._
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class AppController @Inject()(repo: AppRepository,
                              cc: MessagesControllerComponents)
                             (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with CommonController {

  def getAll: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.getAll.map(app => Ok(toJson(app)))
  }

  def getById(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.getById(id).map(app => Ok(toJson(app)))
  }

  // query="{}"
  def getAllBy(jsonQuery: Option[String]): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    jsonQuery match {
      case Some(x) if x != null && x.nonEmpty =>
        val query = jsonMapper.readValue(x, classOf[Map[String, Any]])
        repo.getAllBy(query).map(app => Ok(toJson(app)))
      case _ => repo.getAll.map(app => Ok(toJson(app)))
    }
  }

  // 创建
  def post(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[App])
      if (entity == null) {
         throw new Exception("body empty")
      } else {
        val date = new Timestamp(System.currentTimeMillis())
        val app = entity.copy(createTime = date, modifyTime = date)
        repo.create(app).map(data => Ok(toJson(data)))
      }

  }

  def put(id: Long): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[App])
      repo.getById(id).map({
        case Some(x: App) =>
          val modifyTime = new Timestamp(System.currentTimeMillis())
          val createTime = if (x.createTime != null) x.createTime else modifyTime
          val entityNew = App(
            Some(id),
            entity.tipsName,
            entity.saveName,
            entity.description match {
              case Some(_) => entity.description
              case None => x.description
            },
            createTime,
            modifyTime
          )
          val res = Await.result(repo.update(entityNew), Duration.Inf)
          Ok(toJson(res))
        case None =>
          NotFound("there is no available record found!")
      })
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.delete(id).map(data => Ok(toJson(data)))
  }


}
