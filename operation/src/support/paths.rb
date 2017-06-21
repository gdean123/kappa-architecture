module Paths
  def self.root
    File.expand_path(File.dirname(__FILE__) + '/../../..')
  end

  def self.web
    "#{root}/web"
  end
end