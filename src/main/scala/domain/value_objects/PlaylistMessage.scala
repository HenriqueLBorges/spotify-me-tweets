package domain.value_objects

import io.circe.{Decoder, Encoder, HCursor, Json}

case class PlaylistMessage(key: String, link: String) extends AbstractMessage[PlaylistMessage] with IMessage {

  override def getKey(): String = {
    this.key
  }

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
        ("link", Json.fromString(obj.link))
      )
  }

  override def getDecoder(): Decoder[PlaylistMessage] = {
    new Decoder[PlaylistMessage] {
      final def apply(obj: HCursor): Decoder.Result[PlaylistMessage] =
        for {
          key <- obj.downField("key").as[String]
          link <- obj.downField("link").as[String]
        } yield {
          PlaylistMessage(key, link)
        }
    }
  }
}
