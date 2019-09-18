package common

import java.util
import java.util.{Base64, Date}

import javax.crypto.spec.SecretKeySpec
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import javax.crypto.SecretKey

object JWTUtils {

  var ttlmin = 0
  var secret = "d"

  def createJWT(map: util.Map[String, AnyRef], ttlMin: Long = ttlmin): String = {
    val signatureAlgorithm = SignatureAlgorithm.HS256
    val nowMillis = System.currentTimeMillis
    val now = new Date(nowMillis)
    val secretKey = generalKey
    val builder = Jwts.builder
        .setClaims(map)
      .setIssuedAt(now)
      .signWith(signatureAlgorithm, secretKey)
    if (ttlMin >= 0) {
      val expMillis = nowMillis + (ttlMin * 1000 * 60)
      val expDate = new Date(expMillis)
      builder.setExpiration(expDate) // 过期时间
    }
    builder.compact
  }

  def validateJWT(jwtStr: String): CheckResult = {
    try {
      val claims = parseJWT(jwtStr)
      CheckResult(true, Some(claims), None)
    } catch {
      case ex: Exception =>
      CheckResult(false, None, Some(ex))
    }
  }

  def jwtConfig(ttl: Int, sec: String): Unit = {
    ttlmin = ttl
    secret = sec
  }

  private def parseJWT(jwt: String): Claims = {
    val secretKey = generalKey
    Jwts.parser.setSigningKey(secretKey).parseClaimsJws(jwt).getBody
  }

  private def generalKey: SecretKey = {
    val encodedKey = Base64.getDecoder.decode(secret)
    new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES")
  }

}


case class CheckResult (success: Boolean,
                        claims: Option[Claims],
                        ex: Option[Exception])