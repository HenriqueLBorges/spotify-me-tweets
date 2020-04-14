package domain.value_objects

import io.circe.parser._
import io.circe.{Decoder, Encoder, Json}

/** Message Factory
 */
case class MessageFactory[A] (enconder: Encoder[A], decoder: Decoder[A]){

  /** Converts a String formated as JSON to JSON.
   *  @param message message to convert.
   */
  def convertJsonStringToJson(message: String): Json = {
    parse(message).getOrElse(Json.Null)
  }

  /** Converts a JSON to message.
   *  @param message JSON to convert.
   */
  def convertJsonToMessage(message: Json): Option[A] = {
    implicit val decoder = this.decoder
    message.as[A].toOption
  }

  /** Converts a String formated as JSON to message.
   *  @param message JSON to convert.
   */
  def convertStringToMessage(message: String): Option[A] = {
    println("convertJsonToMessage(convertJsonStringToJson(message)) =", convertJsonToMessage(convertJsonStringToJson(message)))
    convertJsonToMessage(convertJsonStringToJson(message))
  }

}
