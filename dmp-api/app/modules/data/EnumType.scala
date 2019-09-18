package modules.data

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.CanBeQueryCondition

import scala.concurrent.{ExecutionContext, Future}

case class EnumType(id: Option[Long],
                    tipsName: String,
                    saveName: String,
                    description: Option[String],
                    config: String,
                    createTime: Timestamp,
                    modifyTime: Timestamp)

@Singleton
class EnumTypeRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope,
  // which will let you do the actual db operations.
  import dbConfig._
  // The second one brings the Slick DSL into scope,
  // which lets you define the table and other queries.
  import profile.api._

  private class EnumTypes(tag: Tag) extends Table[EnumType](tag, "dmp_enum_type") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def tipsName = column[String]("tips_name")

    def saveName = column[String]("save_name")

    def description = column[String]("description")

    def config = column[String]("config")

    def createTime = column[Timestamp]("create_time")

    def modifyTime = column[Timestamp]("modify_time")

    def * = (id.?, tipsName, saveName, description.?, config, createTime, modifyTime) <>
      ((EnumType.apply _).tupled, EnumType.unapply)
  }



  private val enumTypes = TableQuery[EnumTypes]

  /**
    * @param enumType
    * create new enumType
    */
  def create(enumType: EnumType): Future[Int] = db.run {
    enumTypes += enumType
  }

  /**
    * @param enumType
    * update existing enumType
    */
  def update(enumType: EnumType): Future[Int] = db.run {
    enumTypes.filter(_.id === enumType.id.get).update(enumType)
  }

  /**
    * @param id
    * Get enumType by id
    */
  def getById(id: Long): Future[Option[EnumType]] = db.run {
    enumTypes.filter(_.id === id).result.headOption
  }

  /**
    * @return
    * Get all enumTypes
    */
  def getAll: Future[Seq[EnumType]] = db.run {
    enumTypes.result
  }

  def getAllBy(map: Map[String, Any]): Future[Seq[EnumType]] = db.run {
    checkField(map) match {
      case Some(x: Set[String]) => x.reduce((x:String, y:String) => s"$x,$y")
    }
    enumTypes.filter(v =>
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
      map.get("config") match {
        case Some(x: String) => v.config === x
        case None => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(true).asInstanceOf[Rep[Boolean]]
        case _ => CanBeQueryCondition.BooleanCanBeQueryCondition.apply(false).asInstanceOf[Rep[Boolean]]
      }
    }).result
  }

  /**
    * @param id
    * delete enumType by id
    */
  def delete(id: Long): Future[Int] = db.run {
    enumTypes.filter(_.id === id).delete
  }

  def checkField(map: Map[String, Any]): Option[Set[String]] = {
    val fields = classOf[EnumType].getDeclaredFields.map(f=> f.getName).toSet
    val sets = map.keySet
    Some(sets -- fields)
  }
}
