package domain.value_objects

import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.syntax._

/** Abstract message.
 */
abstract class AbstractMessage[A]() {

  /** Converts the message to JSON
   *  @param message message to convert.
   */
  def convertToJson(message: A): Json = {
    message.asJson(this.getEncoder())
  }

  /** Gets the JSON encoder for this message.
   */
  def getEncoder(): Encoder[A]

  /** Gets the JSON decoder for this message.
   */
  def getDecoder(): Decoder[A]

}
