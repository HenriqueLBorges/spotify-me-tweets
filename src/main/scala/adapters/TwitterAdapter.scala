package adapters

import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.TwitterStreamingClient
import domain.entities.Tweet
import domain.value_objects.TwitterKeys
import ports.TwitterPort
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Twitter adapter.
 */
case class TwitterAdapter (twitterKeys: TwitterKeys) extends TwitterPort {
  private val consumerToken = ConsumerToken(key = twitterKeys.consumerKey, secret = twitterKeys.consumerSecret)
  private val accessToken = AccessToken(key = twitterKeys.accessTokenKey, secret = twitterKeys.accessTokenSecret)
  private val streamClient = TwitterStreamingClient(consumerToken, accessToken)
  private val restClient = TwitterRestClient(consumerToken, accessToken)

  /** Opens a stream of tweets from Twitter API.
   *  @param hashtags tweets hashtags to search for.
   *  @param handler callback function to handle the tweets.
   */
  override def openStream(hashtags: List[String], handler: Tweet => Unit): Future[Unit] = Future {
    streamClient.filterStatuses(tracks = hashtags) {
      case tweet: com.danielasfregola.twitter4s.entities.Tweet => handler(toTweetEntity(tweet))
    }
  }

  /** Posts a tweet.
   *  @param tweet the tweet that will be posted.
   *  @param replyTo optional param used to reply another tweet.
   */
  override def post(tweet: Tweet, replyTo: Option[Long]): Unit = {
    println("Will post the tweet! Tweet =", tweet.content)
    restClient.createTweet(tweet.content, Option.empty)
  }

  /** Converts a tweet to tweet entity.
   *  @param fromStream the tweet tha came from stream.
   */
  def toTweetEntity(fromStream: com.danielasfregola.twitter4s.entities.Tweet): Tweet ={
    val user = fromStream.user.getOrElse("").toString
    Tweet(fromStream.id, fromStream.text, user, fromStream.favorite_count,
      fromStream.retweet_count, fromStream.retweeted, fromStream.created_at)
  }

}
