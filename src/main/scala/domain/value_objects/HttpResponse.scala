package domain.value_objects

case class HttpResponse(headers: Map[String, String], response: String)
