require 'thor'

require_relative 'tools/gradle'
require_relative 'support/paths'
require_relative 'support/environment_variables'
require_relative 'subcommands/topics'
require_relative 'subcommands/database'

class Cli < Thor
  desc 'test', 'Run java tests'
  def test
    EnvironmentVariables.load('test')
    Dir.chdir(Paths.web) { Gradle.test }
  end

  desc 'launch', 'Run server'
  def launch
    EnvironmentVariables.load('development')
    Dir.chdir(Paths.web) { Gradle.boot_run }
  end

  desc 'topics', 'Manage kafka topics'
  subcommand 'topics', Topics

  desc 'database', 'Manage the database'
  subcommand 'database', Database
end