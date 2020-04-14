package domain.value_objects
import io.circe.{Encoder, Json}

/** Message interface
 */
trait IMessage {
  /** Converts the message to JSON byte array.
   */
  def toJsonByteArray(): Array[Byte]

  /** Converts the message to JSON.
   */
  def toJson(): Json
}
