package modules.asset

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class ByzSystem(id: Option[Long],
                    name: String,
                    code: String,
                    parentId: String,
                    createTime: Timestamp,
                    modifyTime: Timestamp)

@Singleton
class ByzSystemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class ByzSystems(tag: Tag) extends Table[ByzSystem](tag, "dmp_user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def code = column[String]("code")

    def parentId = column[String]("parent_id")


    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, name, code, parentId, createTime, modifyTime) <> ((ByzSystem.apply _).tupled, ByzSystem.unapply)
  }

  private val users = TableQuery[ByzSystems]

  /**
    * @param user
    * create new user
    */
  def create(user: ByzSystem): Future[Int] = db.run {
    users += user
  }

  /**
    * @param user
    * update existing user
    */
  def update(user: ByzSystem): Future[Int] = db.run {
    users.filter(_.id === user.id.get).update(user)
  }

  /**
    * @param id
    * Get user by id
    */
  def getById(id: Long): Future[Option[ByzSystem]] = db.run {
    users.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all users
    */
  def getAll: Future[Seq[ByzSystem]] = db.run {
    users.result
  }

  /**
    * @param id
    * delete user by id
    */
  def delete(id: Long): Future[Int] = db.run {
    users.filter(_.id === id).delete
  }
}
