package modules.user

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.concurrent.{ExecutionContext, Future}

case class AuthLog(id: Option[Long],
                   userName: String,
                   passWord: String,
                   state: Short,
                   logInTime: Timestamp)


@Singleton
class AuthLogRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class AuthLogs(tag: Tag) extends Table[AuthLog](tag, "dmp_login") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def userName = column[String]("username")

    def passWord = column[String]("password")

    def state = column[Short]("state")

    def logInTime = column[Timestamp]("log_in_time")


    def * = (id.?, userName, passWord, state, logInTime) <> ((AuthLog.apply _).tupled, AuthLog.unapply)

  }

  private val logIns = TableQuery[AuthLogs]

  /**
    * @param login
    * create new user
    */
  def create(login: AuthLog): Future[Int] = db.run {
    logIns += login
  }


  /**
    * @return
    * Get all users
    */
  def getAll: Future[Seq[AuthLog]] = db.run {
    logIns.result
  }

  def getAllBy(map: Map[String, Any]): Future[Seq[AuthLog]] = db.run {
    logIns.filter(v =>
      map.get("userName") match {
        case Some(x: String) => v.userName === x
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    ).withFilter(v => {
      map.get("logInTime") match {
        case Some(x: Timestamp) => v.logInTime === x
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result
  }


}
