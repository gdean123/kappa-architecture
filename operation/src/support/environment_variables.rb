require 'dotenv'
require_relative 'paths'

module EnvironmentVariables
  def self.load(environment)
    Dotenv.load "#{Paths.root}/.env.#{environment}"
  end
end