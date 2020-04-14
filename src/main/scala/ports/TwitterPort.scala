package ports

import domain.entities.Tweet
import scala.concurrent.Future

/** Twitter interface
 */
trait TwitterPort {

  /** Opens a stream of tweets from Twitter API.
   *  @param hashtags tweets hashtags to search for.
   *  @param handler callback function to handle the tweets.
   */
  def openStream(hashtags: List[String], handler: Tweet => Unit): Future[Unit]

  /** Posts a tweet.
   *  @param tweet the tweet that will be posted.
   *  @param replyTo optional param used to reply another tweet.
   */
  def post(tweet: Tweet, replyTo: Option[Long]): Unit
}
