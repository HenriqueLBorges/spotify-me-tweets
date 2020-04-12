package domain.value_objects
import io.circe.Json

case class NewPlaylist(to: String, key: String, content: Array[Byte]) extends IMessage {
  override def toByteArray(): Array[Byte] = {

  }

  override def toJson(): Json = {
    implicit val encoderLog = new Encoder[NewPlaylist]() {
      final def apply(obj: Log): Json = Json.obj(
        ("endpoint", Json.fromString(obj.endpoint)),
        ("ip", Json.fromString(obj.ip)),
        ("method", Json.fromString(obj.method)),
        ("reply_time", Json.fromBigDecimal(obj.replyTime.inMilliseconds)),
        ("response", Some(obj.reply).asJson),
        ("error", Some(obj.exception).asJson)
      )
    }
  }
}
