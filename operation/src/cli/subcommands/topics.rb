require_relative '../../domain/topics/read_topic'
require_relative '../../domain/topics/write_topic'

class Topics < Thor
  desc 'read <topic>', 'Listen to the given kafka topic'
  def read(topic)
    ReadTopic.execute(topic)
  end

  desc 'write <topic>', 'Write to the given kafka topic'
  def write(topic)
    WriteTopic.execute(topic)
  end
end