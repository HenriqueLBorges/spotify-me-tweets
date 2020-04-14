package adapters

import java.time.Duration
import java.util.{Collections, Properties}

import domain.value_objects.{IMessage, MessageTopics}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer._
import ports.KafkaPort

import scala.collection.JavaConversions._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case object KafkaAdapter extends KafkaPort {
  private val bootstrapServers: String = scala.util.Properties.envOrElse("KafkaBootstrapServers", "localhost:9092")
  private val keySerializer: String = scala.util.Properties.envOrElse("KafkaKeySerializer", "org.apache.kafka.common.serialization.StringSerializer")
  private val keyDeserializer: String = scala.util.Properties.envOrElse("KafkaKeyDeserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  private val valueSerializer: String = scala.util.Properties.envOrElse("KafkaValueSerializer", "org.apache.kafka.common.serialization.StringSerializer")
  private val valueDeserializer: String = scala.util.Properties.envOrElse("KafkaValueDeserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  private val groupId: String = scala.util.Properties.envOrElse("kafkaGroupID", "test-consumer-group")
  private val props = new Properties()
  props.put("bootstrap.servers", this.bootstrapServers)
  props.put("key.serializer", this.keySerializer)
  props.put("key.deserializer", this.keyDeserializer)
  props.put("value.serializer", this.valueSerializer)
  props.put("value.deserializer", this.valueDeserializer)
  props.put("group.id", this.groupId)

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)

  override def writeMessage(topic: MessageTopics.Value, message: IMessage): Unit = {
    val record = new ProducerRecord[String, String](topic.toString, topic.toString, message.toJson().toString())
    this.producer.send(record)
    this.producer.close()
  }

  override def readMessages(topic: MessageTopics.Value, handler: String => Unit): Future[Unit] = Future {
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(Collections.singletonList(topic.toString))

    while(true){
      val records = consumer.poll(Duration.ofMillis(100))
      for (record <- records){
        handler(record.value())
      }
    }
  }
}
