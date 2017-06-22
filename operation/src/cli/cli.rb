require 'thor'

require_relative 'subcommands/topics'
require_relative 'subcommands/database'
require_relative 'subcommands/runtime'

require_relative '../domain/test/run_all_tests'
require_relative '../domain/web_server/launch_web_server'

class Cli < Thor
  desc 'test', 'Run java tests'
  def test
    RunAllTests.execute
  end

  desc 'launch', 'Run server'
  def launch
    LaunchWebServer.execute
  end

  desc 'topics', 'Manage kafka topics'
  subcommand 'topics', Topics

  desc 'database', 'Manage the database'
  subcommand 'database', Database

  desc 'runtime', 'Manage the runtime'
  subcommand 'runtime', Runtime
end