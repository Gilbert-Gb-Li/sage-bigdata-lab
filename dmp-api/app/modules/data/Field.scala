package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.concurrent.{ExecutionContext, Future}

case class Field(id: Option[Long],
                 tipsName: String,
                 saveName: String,
                 description: Option[String],
                 dataType: String,
                 enumTypeId: Option[Long],
                 createTime: Timestamp,
                 modifyTime: Timestamp)


@Singleton
class FieldRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import dbConfig.profile.api._

  private class Fields(tag: Tag) extends Table[Field](tag, "dmp_field") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def tipsName = column[String]("tips_name")

    def saveName = column[String]("save_name")

    def description = column[String]("description")

    def dataType = column[String]("data_type")

    def enumTypeId = column[Long]("enum_type_id")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, tipsName, saveName, description.?,
      dataType, enumTypeId.?, createTime, modifyTime) <>
      ((Field.apply _).tupled, Field.unapply)
  }

  private val fields = TableQuery[Fields]

  /**
    * @param field
    * create new field
    */
  def create(field: Field): Future[Int] = db.run {
    fields += field
  }

  /**
    * @param field
    * update existing field
    */
  def update(field: Field): Future[Int] = db.run {
    fields.filter(_.id === field.id.get).update(field)
  }

  /**
    * @param id
    * Get field by id
    */
  def getById(id: Long): Future[Option[Field]] = db.run {
    fields.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all fields
    */
  def getAll: Future[Seq[Field]] = db.run {
    fields.result
  }

  /**
    * @return
    * Get all apps
    */
  def getAllBy(map: Map[String, Any]): Future[Seq[Field]] = db.run {
    fields.filter(v =>
      map.get("id") match {
        case Some(x: Int) => v.id === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }).withFilter(v =>
      map.get("tipsName") match {
        case Some(x: String) => v.tipsName.like(s"%$x%")
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
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
      map.get("dataType") match {
        case Some(x: String) => v.dataType === x
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).withFilter(v => {
      map.get("enumTypeId") match {
        case Some(x: Int) => v.enumTypeId === x.toLong
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result
  }

  /**
    * @param id
    * delete field by id
    */
  def delete(id: Long): Future[Int] = db.run {
    fields.filter(_.id === id).delete
  }
}
