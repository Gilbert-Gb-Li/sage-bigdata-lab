import java.util.Date

import common.JWTUtils
import org.junit.Test




class JWTTest () {

  @Test
  def timeTest(): Unit = {
    val nowMillis = System.currentTimeMillis
    val now = new Date(nowMillis)
    println(now)

  }

  @Test
  def tokenTest(): Unit = {
    val str = "eyJhbGciOiJIUzI1NiJ9.eyJuaWNrTmFtZSI6IuW8oOS4iSIsImlkIjp7ImVtcHR5IjpmYWxzZSwiZGVmaW5lZCI6dHJ1ZX0sImV4cCI6MTU2ODc4NzYxNywiaWF0IjoxNTY4Nzg3NTU3LCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.eZ5IMhGTUG9unxe0xRHJBpXITNPQaowsEW8Q2bRz8xA"
    val r = JWTUtils.validateJWT(str)
    println(r.success)
  }



}