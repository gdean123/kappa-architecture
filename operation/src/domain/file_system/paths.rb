module Paths
  def self.root
    File.expand_path(File.dirname(__FILE__) + '/../../../..')
  end

  def self.web
    "#{root}/web"
  end

  def self.runtime_tools
    "#{root}/runtime/tools"
  end

  def self.runtime_cache
    "#{root}/runtime/cache"
  end
end