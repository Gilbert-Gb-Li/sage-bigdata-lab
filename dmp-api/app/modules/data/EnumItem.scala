package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class EnumItem(id: Option[Long],
                    tipsName: String,
                    saveName: String,
                    description: Option[String],
                    typeId: Long,
                    createTime: Timestamp,
                    modifyTime: Timestamp)

@Singleton
class EnumItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class EnumItems(tag: Tag) extends Table[EnumItem](tag, "dmp_enum_item") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def tipsName = column[String]("tipsName")

    def saveName = column[String]("saveName")

    def description = column[String]("description")

    def typeId = column[Long]("enum_type_id")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, tipsName, saveName, description.?, typeId, createTime, modifyTime) <>
      ((EnumItem.apply _).tupled, EnumItem.unapply)
  }

  private val enumItems = TableQuery[EnumItems]

  /**
    * @param enumItem
    * create new enumItem
    */
  def create(enumItem: EnumItem): Future[Int] = db.run {
    enumItems += enumItem
  }

  /**
    * @param enumItem
    * update existing enumItem
    */
  def update(enumItem: EnumItem): Future[Int] = db.run {
    enumItems.filter(_.id === enumItem.id.get).update(enumItem)
  }

  /**
    * @param id
    * Get enumItem by id
    */
  def getById(id: Long): Future[Option[EnumItem]] = db.run {
    enumItems.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all enumItems
    */
  def getAll: Future[Seq[EnumItem]] = db.run {
    enumItems.result
  }

  /**
    * @param id
    * delete enumItem by id
    */
  def delete(id: Long): Future[Int] = db.run {
    enumItems.filter(_.id === id).delete
  }
}
