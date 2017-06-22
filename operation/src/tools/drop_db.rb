require_relative '../domain/file_system/system'

module DropDb
  def self.drop(database_name)
    System.execute("dropdb #{database_name}")
  end
end