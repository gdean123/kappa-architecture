require_relative '../support/system'

module DropDb
  def self.drop(database_name)
    System.execute("dropdb #{database_name}")
  end
end