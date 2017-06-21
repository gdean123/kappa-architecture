require_relative '../support/system'

module KafkaConsoleProducer
  def self.write(server, topic)
    System.execute("kafka-console-producer --broker-list #{server} --topic #{topic}")
  end
end