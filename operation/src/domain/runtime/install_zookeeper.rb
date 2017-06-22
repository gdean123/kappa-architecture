require 'open-uri'
require 'zlib'
require_relative '../file_system/paths'
require_relative '../../tools/tar'

class InstallZookeeper
  ZOOKEEPER_DISTRIBUTION='http://archive.apache.org/dist/zookeeper/zookeeper-3.4.3/zookeeper-3.4.3.tar.gz'

  def self.execute
    FileUtils.mkdir_p Paths.runtime_tools

    with_runtime_cache do |runtime_cache|
      tarball_path = "#{runtime_cache}/zookeeper-3.4.3.tar.gz"
      untarred_path = "#{runtime_cache}/zookeeper-3.4.3"
      destination_path = "#{Paths.runtime_tools}/zookeeper"

      download_file(ZOOKEEPER_DISTRIBUTION, tarball_path)
      Tar.untar(tarball_path, Paths.runtime_cache)
      FileUtils.mv(untarred_path, destination_path)
    end
  end

  private

  def self.with_runtime_cache(&block)
    FileUtils.mkdir_p Paths.runtime_cache
    block.call(Paths.runtime_cache)
    FileUtils.rm_rf Paths.runtime_cache
  end

  def self.download_file(remote_url, local_path)
    puts "Downloading #{remote_url} to #{local_path}"

    File.open(local_path, 'wb') do |saved_file|
      open(remote_url, 'rb') do |read_file|
        saved_file.write(read_file.read)
      end
    end
  end
end