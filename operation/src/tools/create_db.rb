require_relative '../support/system'

module CreateDb
  def self.create(database_name)
    System.execute("createdb #{database_name}")
  end
end