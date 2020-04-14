package ports

import domain.entities.Tweet

import scala.concurrent.Future

trait TwitterPort {
  def openStream(hashtags: List[String], handler: Tweet => Unit): Future[Unit]
  def post(tweet: Tweet, replyTo: Option[Long]): Unit
}
