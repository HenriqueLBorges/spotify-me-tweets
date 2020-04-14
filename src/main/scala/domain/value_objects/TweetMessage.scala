package domain.value_objects

import java.time.Instant
import domain.entities.Tweet
import io.circe.syntax._
import io.circe.{Decoder, Encoder, HCursor, Json}

case class TweetMessage(key: String, value: Tweet) extends AbstractMessage[TweetMessage] with IMessage {

  override def getEncoder(): Encoder[TweetMessage] = {
    TweetMessage.getEncoder()
  }

  override def getDecoder(): Decoder[TweetMessage] = {
    TweetMessage.getDecoder()
  }

  override def toJsonByteArray(): Array[Byte] = {
    this.toJson().noSpaces.getBytes()
  }

  override def toJson(): Json = {
    this.convertToJson(this)
  }

}

object TweetMessage extends AbstractMessage [TweetMessage]{
  def getTweetEncoder(): Encoder[Tweet] = {
    obj: Tweet =>
      Json.obj(
        ("id", Json.fromBigDecimal(obj.id)),
        ("content", Json.fromString(obj.content)),
        ("from", Json.fromString(obj.from)),
        ("likes", Json.fromInt(obj.likes)),
        ("rts", Json.fromBigDecimal(obj.likes)),
        ("isRT", Json.fromBoolean(obj.isRT)),
        ("when", Json.fromBigDecimal(obj.when.getEpochSecond)),
      )
  }

  def getTweetDecoder(): Decoder[Tweet] = {
    new Decoder[Tweet] {
      final def apply(obj: HCursor): Decoder.Result[Tweet] =
        for {
          id <- obj.downField("id").as[Long]
          content <- obj.downField("content").as[String]
          from <- obj.downField("from").as[String]
          likes <- obj.downField("likes").as[Int]
          rts <- obj.downField("rts").as[Long]
          isRT <- obj.downField("isRT").as[Boolean]
          when <- obj.downField("when").as[Long]
        } yield {
          Tweet(id, content, from, likes, rts, isRT, Instant.ofEpochSecond(when))
        }
    }
  }

  override def getEncoder(): Encoder[TweetMessage] = {
    obj: TweetMessage =>
      Json.obj(
        ("key", Json.fromString(obj.key)),
        ("value", obj.value.asJson(getTweetEncoder()))
      )
  }

  override def getDecoder(): Decoder[TweetMessage] = {
    implicit val tweetDecoder = getTweetDecoder()
    new Decoder[TweetMessage] {
      final def apply(obj: HCursor): Decoder.Result[TweetMessage] =
        for {
          key <- obj.downField("key").as[String]
          value <- obj.downField("value").as[Tweet]
        } yield {
          TweetMessage(key, value)
        }
    }
  }
}
