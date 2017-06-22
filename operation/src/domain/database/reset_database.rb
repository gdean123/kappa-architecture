require_relative '../../tools/drop_db'
require_relative '../../tools/create_db'

class ResetDatabase
  def self.execute(environment)
    database_name = "kappa_architecture_#{environment}"
    DropDb.drop(database_name)
    CreateDb.create(database_name)
  end
end