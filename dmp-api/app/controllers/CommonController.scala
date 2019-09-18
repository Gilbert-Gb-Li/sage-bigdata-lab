package controllers

import common.{JWTUtils, JsonMapper}
import javax.inject.Inject
import play.api.mvc.{AnyContent, MessagesAbstractController, MessagesControllerComponents, Request}

import scala.concurrent.ExecutionContext

trait CommonController extends JsonMapper {
  def getRawText(implicit request: Request[AnyContent]): String = {
    if (!request.hasBody) {
      return ""
    }
    val body = request.body

    body.asRaw match {
      case Some(value) =>
        value.asBytes() match {
          case Some(data) =>
            return new String(data.toArray)
          case _ =>
        }
      case _ =>

    }
    body.asText match {
      case Some(x) =>
        return x;
      case _ =>
    }
    return ""
  }

  def fromBody[T](t: Class[T])(implicit request: Request[AnyContent]): T = {
    val body = getRawText
    jsonMapper.readValue(body, t)
  }
}

import play.api.Configuration
class AllInOneController @Inject()(config: Configuration,
                                   cc: MessagesControllerComponents)
                                  (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  val ttl = config.getOptional[Int]("play.jwt.expire.min").getOrElse(3)
  val secret = config.getOptional[String]("play.http.secret.key").getOrElse("dmpapi")


  JWTUtils.jwtConfig(ttl, secret)


}


case class a(a:String)