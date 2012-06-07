require 'aruba/cucumber'
require 'aruba/api'

World(Aruba::Api)

After do |s|
  Cucumber.wants_to_quit = true if s.failed?

  begin
    terminate_processes!
  rescue ChildProcess::Error => ex
    puts "IGNORED: #{ex}"
  end
end
