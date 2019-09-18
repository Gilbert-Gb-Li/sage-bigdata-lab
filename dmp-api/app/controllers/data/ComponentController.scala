package controllers.data

import java.sql.Timestamp

import common.JsonMapper
import controllers.CommonController
import javax.inject._
import modules.data.{Component, ComponentRepository}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class ComponentController @Inject()(repo: ComponentRepository,
                                    cc: MessagesControllerComponents)
                                   (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with JsonMapper with CommonController {

  def getAll: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.getAll.map(component => Ok(toJson(component)))
  }

  def getById(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.getById(id).map(component => Ok(toJson(component)))
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

  def post(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val entity = fromBody(classOf[Component])
    val date = new Timestamp(System.currentTimeMillis())
    val component = entity.copy(createTime = date, modifyTime = date)
    repo.create(component).map(data => Ok(toJson(data)))
  }

  def put(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val entity = fromBody(classOf[Component])

    repo.getById(id).map(tmp => tmp match {
      case Some(rawEntity) =>
        // 设置修改时间为当前
        val modifyTime = new Timestamp(System.currentTimeMillis())
        // 如果创建时间为空，置为当前
        val createTime = if (rawEntity.createTime == null) {
          modifyTime
        } else {
          rawEntity.createTime
        }
        // 生成修改内容后的数据
        val component = rawEntity.copy(
          name = entity.name,
          description = entity.description match {
            case Some(_) => entity.description
            case None => rawEntity.description
          },
          config = entity.config,
          createTime = createTime,
          modifyTime = modifyTime
        )
        // 修改数据库
        val res = Await.result(repo.update(component), Duration.Inf)
        Ok(toJson(res))
      case _ =>
        NotFound("there is no available record found!")
    })
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.delete(id).map(data => Ok(toJson(data)))
  }
}
