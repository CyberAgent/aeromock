package jp.co.cyberagent.aeromock.core.http

import io.netty.handler.codec.http.HttpMethod
import org.apache.commons.lang3.StringUtils

case class Endpoint(raw: String) {

  def value: String = raw.replace("\\", "/")

  def last: String = value.split("/").last

}


class AeromockHttpRequest(
  val url: String,
  val queryParameters: Map[String, String],
  val formData: Map[String, String],
  val routeParameters: Map[String, String],
  val method: HttpMethod = HttpMethod.GET
)

object AeromockHttpRequest {

  def apply(url: String, queryParameters: Map[String, String],
        formData: Map[String, String],
        routeParameters: Map[String, String] = Map.empty,
        method: HttpMethod = HttpMethod.GET): AeromockHttpRequest = {
    require(url != null)

    val s = url.trim
    val formatted = if (StringUtils.isEmpty(s) || s == "/") {
      "/index"
    } else if (s.endsWith("/")) {
      s"${s}index"
    } else {
      s
    }

    new AeromockHttpRequest(formatted, queryParameters, formData, routeParameters, method)
  }

  def unapply(request: AeromockHttpRequest) = {
    Some((request.url, request.queryParameters, request.formData, request.routeParameters, request.method))
  }

}
