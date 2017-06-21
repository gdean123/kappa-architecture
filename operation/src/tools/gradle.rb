require_relative '../support/system'

module Gradle
  def self.test
    System.execute('./gradlew clean cleanTest test')
  end

  def self.boot_run
    System.execute('./gradlew clean build bootRun -x test')
  end
end