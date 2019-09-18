package modules.user

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

case class User(id: Option[Long],
                userName: String,
                passWord: String,
                nickName: String,
                createTime: Timestamp,
                modifyTime: Timestamp)


@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class Users(tag: Tag) extends Table[User](tag, "dmp_user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def userName = column[String]("username")

    def passWord = column[String]("password")

    def nickName = column[String]("nickname")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, userName, passWord, nickName, createTime, modifyTime) <> ((User.apply _).tupled, User.unapply)

  }

  private val users = TableQuery[Users]

  /**
    * @param user
    * create new user
    */
  def create(user: User): Future[Int] = db.run {
    users += user
  }

  /**
    * @param user
    * update existing user
    */
  def update(user: User): Future[Int] = db.run {
    users.filter(_.id === user.id.get).update(user)
  }

  /**
    * @param id
    * Get user by id
    */
  def getById(id: Long): Future[Option[User]] = db.run {
    users.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all users
    */
  def getAll: Future[Seq[User]] = db.run {
    users.result
  }

  def getAllBy(map: mutable.Map[String, Any]): Future[Seq[User]] = db.run {
    users.filter(v =>
      map.get("userName") match {
        case Some(x: String) => v.userName === x
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    ).withFilter(v => {
      map.get("passWord") match {
        case Some(x: String) => v.passWord ===x
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result
  }

  /**
    * @param id
    * delete user by id
    */
  def delete(id: Long): Future[Int] = db.run {
    users.filter(_.id === id).delete
  }
}
