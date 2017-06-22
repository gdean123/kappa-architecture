module System
  def self.execute(command)
    puts "Running: #{command}"
    system command
  end
end