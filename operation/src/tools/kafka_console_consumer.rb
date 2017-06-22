require_relative '../domain/file_system/system'

module KafkaConsoleConsumer
  def self.read(server, topic)
    System.execute("kafka-console-consumer --bootstrap-server #{server} --topic #{topic} --from-beginning")
  end
end