import java.util

import akka.actor.Status.Success
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

import scala.concurrent.{Await, Future}
import scala.util.Try



class ScalaTest {

  val a = 0
  private val b = "a"

  def JsonTest(): Unit = {
    val a = "{\"id\":1,name:\"ES\"}"
    val mapper = new ObjectMapper()
    val m = mapper.readValue(a, classOf[util.HashMap[String, Any]])
    print(m.get("name"))
  }

  @Test
  def optionTest(): Unit = {
    val map = Map("id" -> 1001,
    "name" -> "xiaoming"
    )

  }

  @Test
  def getFields(): Unit ={
    val s = classOf[ScalaTest]
    val f = s.getFields
    f.foreach(x=>println(x.getName))
  }

  @Test
  def futureTest(): Unit = {
    val a = "abc"

    val b = Future.fromTry(Try(a))

    println()
  }

}

