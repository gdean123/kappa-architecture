require_relative '../domain/file_system/system'

module CreateDb
  def self.create(database_name)
    System.execute("createdb #{database_name}")
  end
end