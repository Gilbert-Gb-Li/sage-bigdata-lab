package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.concurrent.{ExecutionContext, Future}

case class App(id: Option[Long],
               tipsName: String,
               saveName: String,
               description: Option[String],
               createTime: Timestamp,
               modifyTime: Timestamp)

@Singleton
class AppRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class Apps(tag: Tag) extends Table[App](tag, "dmp_app") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def tipsName = column[String]("tips_name")

    def saveName = column[String]("save_name")

    def description = column[String]("description")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, tipsName, saveName, description.?, createTime, modifyTime) <>
      ((App.apply _).tupled, App.unapply)
  }

  private val apps = TableQuery[Apps]

  /**
    * @param app
    * create new app
    */
  def create(app: App): Future[Int] = db.run {
    apps += app
  }

  /**
    * @param app
    * update existing app
    */
  def update(app: App): Future[Int] = db.run {
    apps.filter(_.id === app.id.get && app.id.get!= 1).update(app)
  }

  /**
    * @param id
    * Get app by id
    */
  def getById(id: Long): Future[Option[App]] = db.run {
    apps.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all apps
    */
  def getAll: Future[Seq[App]] = db.run {
    apps.result
  }

  /**
    * @return
    * Get all apps
    */
  def getAllBy(map: Map[String, Any]): Future[Seq[App]] = db.run {
    apps.filter(v =>
      map.get("id") match {
        case Some(x: Int) => v.id === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    ).withFilter(v => {
      map.get("tipsName") match {
        case Some(x: String) => v.tipsName.like(s"%$x%")
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).withFilter(v => {
      map.get("description") match {
        case Some(x: String) => v.description.like(s"%$x%")
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).withFilter(v => {
      map.get("saveName") match {
        case Some(x: String) => v.saveName.like(s"%$x%")
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result
  }

  /**
    * @param id
    * delete app by id
    */
  def delete(id: Long): Future[Int] = db.run {
    apps.filter(_.id === id).delete
  }
}
