package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile


import scala.concurrent.{ExecutionContext, Future}
case class Storage(id: Option[Long],
                   name: String,
                   description: Option[String],
                   componentId: Long,
                   appId: Long,
                   schemaId: Long,
                   config: String,
                   stage: String,
                   createTime: Timestamp,
                   modifyTime: Timestamp)

@Singleton
class StorageRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class Storages(tag: Tag) extends Table[Storage](tag, "dmp_storage") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def componentId = column[Long]("component_id")

    def appId = column[Long]("app_id")

    def schemaId = column[Long]("schema_id")

    def description = column[String]("description")

    def config = column[String]("config")

    def stage = column[String]("stage")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, name, description.?, componentId, appId, schemaId, config, stage, createTime, modifyTime) <>
      ((Storage.apply _).tupled, Storage.unapply)
  }

  private val storages = TableQuery[Storages]

  /**
    * @param storage
    * create new storage
    */
  def create(storage: Storage): Future[Int] = db.run {
    storages += storage
  }

  /**
    * @param storage
    * update existing storage
    */
  def update(storage: Storage): Future[Int] = db.run {
    storages.filter(_.id === storage.id.get).update(storage)
  }

  /**
    * @param id
    * Get storage by id
    */
  def getById(id: Long): Future[Option[Storage]] = db.run {
    storages.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all storages
    */
  def getAll: Future[Seq[Storage]] = db.run {
    storages.result
  }

  /**
    * @return
    * Get all apps
    */
  def getAllBy(map: Map[String, Any]): Future[Seq[Storage]] = db.run {
    import slick.lifted.CanBeQueryCondition
    storages.filter(v =>
      map.get("id") match {
        case Some(x: Int) => v.id === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }).withFilter(v =>
      map.get("name") match {
        case Some(x: String) => v.name.like(s"%$x%")
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
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
    }).withFilter(v => {
      map.get("componentId") match {
        case Some(x: Int) => v.componentId === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).withFilter(v => {
      map.get("appId") match {
        case Some(x: Int) => v.appId === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).withFilter(v => {
      map.get("schemaId") match {
        case Some(x: Int) => v.schemaId === x.toLong
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
