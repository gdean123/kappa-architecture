require_relative '../../tools/gradle'
require_relative '../file_system/paths'
require_relative '../file_system/environment_variables'

class LaunchWebServer
  def self.execute
    EnvironmentVariables.load('development')
    Dir.chdir(Paths.web) { Gradle.boot_run }
  end
end