package ports

import domain.value_objects.{IMessage, MessageTopics}
import scala.concurrent.Future

/** Kafka interface */
trait KafkaPort {
  /** Write messages to Kafka.
   *  @param messageTopic kafka topic to where messages will be written.
   *  @param message message that will be writen.
   */
  def writeMessage(messageTopic: MessageTopics.Value, message: IMessage): Unit

  /** Read messages from Kafka.
   *  @param messageTopic kafka topic from where messages will be read.
   *  @param handler callback function to handle the message.
   */
  def readMessages(messageTopic: MessageTopics.Value, handler: String => Unit): Future[Unit]
}
