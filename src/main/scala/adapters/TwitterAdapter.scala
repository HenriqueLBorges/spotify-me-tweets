package adapters

import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.TwitterStreamingClient
import domain.entities.Tweet
import domain.value_objects.TwitterKeys
import ports.TwitterPort

case class TwitterAdapter (twitterKeys: TwitterKeys)  extends TwitterPort {
  val consumerToken = ConsumerToken(key = twitterKeys.consumerKey, secret = twitterKeys.consumerSecret)
  val accessToken = AccessToken(key = twitterKeys.accessTokenKey, secret = twitterKeys.accessTokenSecret)
  val streamClient = TwitterStreamingClient(consumerToken, accessToken)
  val restClient = TwitterRestClient(consumerToken, accessToken)

  override def openStream(hashtags: List[String], handler: Tweet => Unit): Unit = {
    streamClient.filterStatuses(tracks = hashtags) {
      case tweet: com.danielasfregola.twitter4s.entities.Tweet => handler(toTweetEntity(tweet))
    }
  }

  override def post(tweet: Tweet, replyTo: Option[Long]): Unit = {
    restClient.createTweet(tweet.content, replyTo)
  }

  def toTweetEntity(fromStream: com.danielasfregola.twitter4s.entities.Tweet): Tweet ={
    val user = fromStream.user.getOrElse("").toString
    Tweet(fromStream.id, fromStream.text, user, fromStream.favorite_count,
      fromStream.retweet_count, fromStream.retweeted, fromStream.created_at)
  }

}
