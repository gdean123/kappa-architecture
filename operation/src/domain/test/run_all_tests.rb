require_relative '../../tools/gradle'
require_relative '../file_system/paths'
require_relative '../file_system/environment_variables'

class RunAllTests
  def self.execute
    EnvironmentVariables.load('test')
    Dir.chdir(Paths.web) { Gradle.test }
  end
end