package domain.value_objects

import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.syntax._

abstract class AbstractMessage[A]() {

  def convertToJson(obj: A): Json = {
    obj.asJson(this.getEncoder())
  }

  def getEncoder(): Encoder[A]

  def getDecoder(): Decoder[A]

}
