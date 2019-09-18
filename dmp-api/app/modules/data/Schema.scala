package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.concurrent.{ExecutionContext, Future}

case class Schema(id: Option[Long],
                  tipsName: String,
                  saveName: String,
                  description: Option[String],
                  appId: Long,
                  createTime: Timestamp,
                  modifyTime: Timestamp)

case class SchemaField(schemaId: Long,
                       fieldId: Long)

@Singleton
class SchemaRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class Schemas(tag: Tag) extends Table[Schema](tag, "dmp_schema") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)


    def tipsName = column[String]("tips_name")

    def saveName = column[String]("save_name")

    def description = column[String]("description")

    def appId = column[Long]("app_id")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, tipsName, saveName, description.?, appId, createTime, modifyTime) <>
      ((Schema.apply _).tupled, Schema.unapply)
  }

  private class SchemaFields(tag: Tag) extends Table[SchemaField](tag, "dmp_schema_field") {
    def schemaId = column[Long]("schema_id", O.PrimaryKey)

    def fieldId = column[Long]("field_id", O.PrimaryKey)

    def * = (schemaId, fieldId) <>
      ((SchemaField.apply _).tupled, SchemaField.unapply)
  }

  private val schemaFields = TableQuery[SchemaFields]

  private val schemas = TableQuery[Schemas]

  /**
    * @param schema
    * create new schema
    */
  def create(schema: Schema): Future[Int] = db.run {
    schemas += schema
  }

  /**
    * @param schema
    * update existing schema
    */
  def update(schema: Schema): Future[Int] = db.run {
    schemas.filter(_.id === schema.id.get).update(schema)
  }

  /**
    * @param id
    * Get schema by id
    */
  def getById(id: Long): Future[Option[Schema]] = db.run {
    schemas.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all schemas
    */
  def getAll: Future[Seq[Schema]] = db.run {
    schemas.result
  }

  def getAllBy(map: Map[String, Any]): Future[Seq[Schema]] = db.run {
    schemas.filter(v =>
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
    }).withFilter(v => {
      map.get("appId") match {
        case Some(x: Int) => v.appId === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result
  }

  /**
    * @param id
    * delete schema by id
    */
  def delete(id: Long): Future[Int] = db.run {
    schemas.filter(_.id === id).delete
  }
}
