package adapters

import domain.value_objects.HttpResponse
import org.apache.http.Header
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import ports.HttpPort

/** HttpAdapter. */
object HttpAdapter extends HttpPort{
  private val httpClient = HttpClientBuilder.create().disableRedirectHandling().disableCookieManagement().build()

  /** Executes a GET HTTP request.
   * @param url link that will be called.
   */
  override def get(url: String): HttpResponse = {
    val httpResponse = this.httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity()
    val headers = this.convertHeaders(httpResponse.getAllHeaders)
    val body = EntityUtils.toString(entity)
    httpResponse.close()
    if (entity != null) {
      HttpResponse(headers, body)
    } else {
      HttpResponse(Map[String, String](), "")
    }
  }

  /** Gets headers from the HTTP response.
   * @param headers HTTP response headers
   */
  private def convertHeaders(headers: Array[Header]): Map[String, String] = {
    headers.map(header => header.getName -> header.getValue).toMap
  }
}
