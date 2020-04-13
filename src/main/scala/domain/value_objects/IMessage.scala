package domain.value_objects
import io.circe.{Encoder, Json}

trait IMessage {
  def getKey(): String
  def toJsonByteArray(): Array[Byte]
  def toJson(): Json
}
