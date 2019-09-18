package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.concurrent.{ExecutionContext, Future}

case class Component(id: Option[Long],
                     name: String,
                     description: Option[String],
                     config: String,
                     createTime: Timestamp,
                     modifyTime: Timestamp)



@Singleton
class ComponentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class Components(tag: Tag) extends Table[Component](tag, "dmp_component") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")


    def description = column[String]("description")

    def config = column[String]("config")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, name, description.?, config, createTime, modifyTime) <>
      ((Component.apply _).tupled, Component.unapply)
  }

  private val storages = TableQuery[Components]

  /**
    * @param storage
    * create new storage
    */
  def create(storage: Component): Future[Int] = db.run {
    storages += storage
  }

  /**
    * @param storage
    * update existing storage
    */
  def update(storage: Component): Future[Int] = db.run {
    storages.filter(_.id === storage.id.get).update(storage)
  }

  /**
    * @param id
    * Get storage by id
    */
  def getById(id: Long): Future[Option[Component]] = db.run {

    storages.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all storages
    */
  def getAll: Future[Seq[Component]] = db.run {
    storages.result
  }

  /**
    * @return
    * Get all storages
    */
  def getAllBy(map: Map[String, Any]): Future[Seq[Component]] = db.run {
    storages.filter(v =>
      map.get("id") match {
        case Some(x: Int) => v.id === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    ).withFilter(v => {
      map.get("name") match {
        case Some(x: String) => v.name.like(s"%$x%")
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
      map.get("config") match {
        case Some(x: String) => v.config === x
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result

  }


  /**
    * @param id
    * delete storage by id
    */
  def delete(id: Long): Future[Int] = db.run {
    storages.filter(_.id === id).delete
  }



}
