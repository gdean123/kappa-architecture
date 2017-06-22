require_relative '../../tools/kafka_console_consumer'

class ReadTopic
  def self.execute(topic)
    KafkaConsoleConsumer.read('localhost:9092', topic)
  end
end