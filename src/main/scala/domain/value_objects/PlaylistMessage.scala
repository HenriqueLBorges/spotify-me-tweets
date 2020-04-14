package domain.value_objects

import io.circe.{Decoder, Encoder, HCursor, Json}

/** Playlist Message
 */
case class PlaylistMessage(key: String, value: String) extends AbstractMessage[PlaylistMessage] with IMessage {

  /** Converts the playlist message to JSON
   */
  override def toJson(): Json = {
    this.convertToJson(this)
  }

  /** Converts the playlist message to JSON byte array.
   */
  override def toJsonByteArray(): Array[Byte] = {
    this.toJson().noSpaces.getBytes()
  }

  /** Get the playlist message encoder.
   */
  override def getEncoder(): Encoder[PlaylistMessage] = {
    PlaylistMessage.getEncoder()
  }

  /** Get the playlist message decoder.
   */
  override def getDecoder(): Decoder[PlaylistMessage] = {
    PlaylistMessage.getDecoder()
  }
}

/** Playlist Message Object
 */
object PlaylistMessage extends AbstractMessage [PlaylistMessage]{

  /** Get the playlist message encoder.
   */
  override def getEncoder(): Encoder[PlaylistMessage] = {
    obj: PlaylistMessage =>
      Json.obj(
        ("key", Json.fromString(obj.key)),
        ("value", Json.fromString(obj.value))
      )
  }

  /** Get the playlist message decoder.
   */
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
