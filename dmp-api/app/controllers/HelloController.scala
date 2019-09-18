package controllers

import common.JsonMapper
import javax.inject._
import modules.user.UserRepository
import play.api._
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class HelloController @Inject()(userRepo: UserRepository,
                                config: Configuration,
                                cc: MessagesControllerComponents)
                               (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) with JsonMapper {
  val foo = config.get[String]("foo")

  def hello(name: String) = Action.async { implicit request: Request[AnyContent] =>
    println(foo)
    userRepo.getAll.map(users => Ok(toJson(users)))
  }
}
