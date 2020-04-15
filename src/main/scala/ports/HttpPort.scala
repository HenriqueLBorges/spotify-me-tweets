package ports

import domain.value_objects.HttpResponse

/** HTTP interface */
trait HttpPort {

  /** Executes a GET HTTP request.
   * @param url link that will be called.
   */
  def get(url: String): HttpResponse
}
