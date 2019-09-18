package controllers.data

import java.sql.Timestamp

import controllers.CommonController
import javax.inject._
import modules.data.{Storage, StorageRepository}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class StorageController @Inject()(repo: StorageRepository,
                                  cc: MessagesControllerComponents)
                                 (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with CommonController {

  def getAll: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.getAll.map(data => Ok(toJson(data)))
  }

  def getById(id: Long): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.getById(id).map(data => Ok(toJson(data)))
  }

  def getAllBy(jsonQuery: Option[String]): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    jsonQuery match {
      case Some(x) if x != null && x.nonEmpty =>
        val query = jsonMapper.readValue(x, classOf[Map[String, Any]])
        repo.getAllBy(query).map(app => Ok(toJson(app)))
      case _ => repo.getAll.map(app => Ok(toJson(app)))
    }
  }

  def post(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[Storage])
      val date = new Timestamp(System.currentTimeMillis())
      val data = entity.copy(createTime = date, modifyTime = date)
      repo.create(data).map(data => Ok(toJson(data)))
  }

  def put(id: Long): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[Storage])
      repo.getById(id).map({
        case Some(x) =>
          val modifyTime = new Timestamp(System.currentTimeMillis())
          val createTime = if(x.createTime != null) x.createTime else modifyTime
          val entityNew = entity.copy(
            id = x.id,
            description = entity.description match {
              case Some(_) => entity.description
              case None => x.description
            },
            createTime = createTime,
            modifyTime = modifyTime
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
  implicit def int2Long(x: java.lang.Integer) = java.lang.Integer.toUnsignedLong(x)
}
