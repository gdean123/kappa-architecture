require_relative '../tools/drop_db'
require_relative '../tools/create_db'

class Database < Thor
  desc 'reset <environment>', 'Drop and recreate the database for the given environment'
  def reset(environment)
    database_name = "kappa_architecture_#{environment}"
    DropDb.drop(database_name)
    CreateDb.create(database_name)
  end
end