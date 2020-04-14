package domain.value_objects

import io.circe.{Decoder, Encoder, HCursor, Json}

case class PlaylistMessage(key: String, value: String) extends AbstractMessage[PlaylistMessage] with IMessage {

  override def toJson(): Json = {
    this.convertToJson(this)
  }

  override def toJsonByteArray(): Array[Byte] = {
    this.toJson().noSpaces.getBytes()
  }

  override def getEncoder(): Encoder[PlaylistMessage] = {
    PlaylistMessage.getEncoder()
  }

  override def getDecoder(): Decoder[PlaylistMessage] = {
    PlaylistMessage.getDecoder()
  }
}

object PlaylistMessage extends AbstractMessage [PlaylistMessage]{

  override def getEncoder(): Encoder[PlaylistMessage] = {
    obj: PlaylistMessage =>
      Json.obj(
        ("key", Json.fromString(obj.key)),
        ("value", Json.fromString(obj.value))
      )
  }

  override def getDecoder(): Decoder[PlaylistMessage] = {
    new Decoder[PlaylistMessage] {
      final def apply(obj: HCursor): Decoder.Result[PlaylistMessage] =
        for {
          key <- obj.downField("key").as[String]
          link <- obj.downField("value").as[String]
        } yield {
          PlaylistMessage(key, link)
        }
    }
  }
}
