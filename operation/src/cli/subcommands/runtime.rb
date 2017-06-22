require_relative '../../domain/runtime/install_runtime'

class Runtime < Thor
  desc 'install', 'Install zookeeper, kafka, yarn, and samza'
  def install
    InstallRuntime.execute
  end
end