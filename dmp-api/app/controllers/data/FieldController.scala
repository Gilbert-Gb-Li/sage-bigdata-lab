package controllers.data

import java.sql.Timestamp

import controllers.CommonController
import javax.inject._
import modules.data.{Field, FieldRepository}
import play.api.mvc._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration

@Singleton
class FieldController @Inject()(repo: FieldRepository,
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
      val entity = fromBody(classOf[Field])
      val date = new Timestamp(System.currentTimeMillis())
      val data = entity.copy(createTime = date, modifyTime = date)
      repo.create(data).map(data => Ok(toJson(data)))
  }

  def put(id: Long): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val entity = fromBody(classOf[Field])
      repo.getById(id).map({
        case Some(x) =>
          val modifyTime = new Timestamp(System.currentTimeMillis())
          val createTime = if(x.createTime != null) x.createTime else modifyTime
          val entityNew = Field(
            Some(id),
            entity.tipsName,
            entity.saveName,
            entity.description match {
              case Some(_) => entity.description
              case None => x.description
            },
            entity.dataType,
            entity.enumTypeId match {
              case Some(_) => entity.enumTypeId
              case None => x.enumTypeId
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
