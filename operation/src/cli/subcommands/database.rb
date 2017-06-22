require_relative '../../domain/database/reset_database'

class Database < Thor
  desc 'reset <environment>', 'Drop and recreate the database for the given environment'
  def reset(environment)
    ResetDatabase.execute(environment)
  end
end