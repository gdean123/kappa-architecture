require 'thor'

require_relative 'tools/gradle'
require_relative 'support/paths'
require_relative 'subcommands/topics'

class Cli < Thor
  desc 'test', 'Run java tests'
  def test
    Dir.chdir(Paths.web) { Gradle.test }
  end

  desc 'launch', 'Run server'
  def launch
    Dir.chdir(Paths.web) { Gradle.boot_run }
  end

  desc 'topics', 'Manage kafka topics'
  subcommand 'topics', Topics
end