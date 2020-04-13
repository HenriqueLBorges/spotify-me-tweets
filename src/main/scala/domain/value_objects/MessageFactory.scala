package domain.value_objects
import io.circe.parser._
import io.circe.{Decoder, Encoder, Json}

case class MessageFactory[A] (enconder: Encoder[A], decoder: Decoder[A]){
  def convertJsonStringToJson(message: String): Json = {
    parse(message).getOrElse(Json.Null)
  }

  def convertJsonToMessage(message: Json): Option[A] = {
    implicit val decoder = this.decoder
    message.as[A].toOption
  }

  def convertStringToMessage(message: String): Option[A] = {
    convertJsonToMessage(convertJsonStringToJson(message))
  }
}
