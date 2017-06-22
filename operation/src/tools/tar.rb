class Tar
  def self.untar(tarball_path, destination)
    System.execute "tar -xf #{tarball_path} -C #{destination}"
  end
end