package controllers.data


import controllers.CommonController
import javax.inject.{Inject, Singleton}
import modules.user.AuthLogRepository
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class AuthLogController @Inject()(repo: AuthLogRepository,
                                  cc: MessagesControllerComponents
                                )
                                (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc)  with CommonController {

  // query="{}"
  def getAllBy(jsonQuery: Option[String]): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    jsonQuery match {
      case Some(x) if x != null && x.nonEmpty =>
        val query = jsonMapper.readValue(x, classOf[Map[String, Any]])
        repo.getAllBy(query).map(data => Ok(toJson(data)))
      case _ => repo.getAll.map(data => Ok(toJson(data)))
    }
  }


}

