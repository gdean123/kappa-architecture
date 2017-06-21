require_relative '../tools/kafka_console_consumer'
require_relative '../tools/kafka_console_producer'

class Topics < Thor
  desc 'read <topic>', 'Listen to the given kafka topic'
  def read(topic)
    KafkaConsoleConsumer.read('localhost:9092', topic)
  end

  desc 'write <topic>', 'Write to the given kafka topic'
  def write(topic)
    KafkaConsoleProducer.write('localhost:9092', topic)
  end
end