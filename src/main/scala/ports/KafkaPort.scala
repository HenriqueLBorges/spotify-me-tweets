package ports

import domain.value_objects.{IMessage, MessageTopics}
import scala.concurrent.Future

trait KafkaPort {
  def writeMessage(messageTopic: MessageTopics.Value, message: IMessage): Unit
  def readMessages(messageTopic: MessageTopics.Value, handler: String => Unit): Future[Unit]
}
