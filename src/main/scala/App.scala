import adapters.KafkaAdapter
import adapters.TwitterAdapter
import domain.entities.Tweet
import domain.value_objects.{MessageFactory, MessageTopics, PlaylistMessage, TweetMessage, TwitterKeys}
import java.time.Instant
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** App Object. */
object App {
  val consumerKey = scala.util.Properties.envOrElse("consumerKey", "")
  val consumerSecret = scala.util.Properties.envOrElse("consumerSecret", "")
  val accessTokenKey = scala.util.Properties.envOrElse("accessTokenKey", "")
  val accessTokenSecret = scala.util.Properties.envOrElse("accessTokenSecret", "")
  val hashtags = scala.util.Properties.envOrElse("hashtags", "").split(",").toList
  val twitterKeys = TwitterKeys(consumerKey, consumerSecret, accessTokenKey, accessTokenSecret)

  /** Main Method.
   */
  def main(args: Array[String]): Unit = {
    println("spotifyMeTweets online")
    val tweetsToPost = KafkaAdapter.readMessages(MessageTopics.tweetsToPost, tweetsToPostHandler)
    val playlists = KafkaAdapter.readMessages(MessageTopics.playlists, playlistsHandler)
    val twitterStream = TwitterAdapter(twitterKeys).openStream(hashtags, twitterStreamHandler)

    Await.result(twitterStream, Duration.Inf)
    Await.result(tweetsToPost, Duration.Inf)
    Await.result(playlists, Duration.Inf)

  }

  /** Handler for tweets from Twitter Stream API.
   *  @param tweet The data in JSON format.
   */
  def twitterStreamHandler(tweet: Tweet): Unit = {
    val playlistMessage = PlaylistMessage(MessageTopics.playlists.toString, tweet.content)
    val tweetMessage = TweetMessage(MessageTopics.tweetsToPost.toString, tweet)
    KafkaAdapter.writeMessage(MessageTopics.playlists, playlistMessage)
    KafkaAdapter.writeMessage(MessageTopics.tweetsToPost, tweetMessage)
  }

  /** Handler messages from the playlist topic
   *  @param message The data in JSON format.
   */
  def playlistsHandler(message: String): Unit = {
    val messageFactory = MessageFactory[PlaylistMessage](PlaylistMessage.getEncoder(), PlaylistMessage.getDecoder())
    println("New tweet received from Kafka playlists topics! Tweet =", messageFactory.convertStringToMessage(message).getOrElse(PlaylistMessage("", "")))
  }

  /** Handler messages from the tweetsToPost topic
   *  @param message The data in JSON format.
   */
  def tweetsToPostHandler(message: String): Unit = {
    println("New tweet to post received from Kafka! Tweet =", message)
    val messageFactory = MessageFactory[TweetMessage](TweetMessage.getEncoder(), TweetMessage.getDecoder())

    messageFactory.convertStringToMessage(message) match {
      case Some(tweetMessage) => TwitterAdapter(twitterKeys).post(tweetMessage.value, Option.empty)
      case None => println("Invalid tweet message")
    }
  }
}
