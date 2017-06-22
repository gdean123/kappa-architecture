require_relative '../../tools/kafka_console_producer'

class WriteTopic
  def self.execute(topic)
    KafkaConsoleProducer.write('localhost:9092', topic)
  end
end