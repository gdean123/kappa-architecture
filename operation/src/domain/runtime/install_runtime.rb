require_relative 'install_zookeeper'

class InstallRuntime
  def self.execute
    InstallZookeeper.execute
  end
end