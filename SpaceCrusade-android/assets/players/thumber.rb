#! /usr/bin/env ruby

MAX=10


def get_list
  # flist = Dir.glob("./raw/**")
  prefix=ARGV[0]
  puts "prefix=#{prefix}"
  flist = Dir.glob("*png")  
  cmds = []
  rem  = 0
  ctr = 0
  html = ""
  flist.each_with_index {|fp,i|
    fn = fp.gsub("./raw/", "")
    outf = "img#{i}.png"
    html += "<img src='img/#{prefix}/#{outf}' /> <br/><br/>\n"
    
    if fn.include?("img") || fn.include?('Thumbs.db')
      puts "skipping #{fn}"
    else
      puts "#{i+1}:#{fp}"
      cmds << "mv '#{fp}' #{outf}"
    # %w[40x40 60x60 80x80].each {|sz|
      # cmds << "/usr/local/bin/convert #{fp} -undercolor white -colors 256 -depth 8 -resize #{sz} #{sz}/#{fn}"
      #ftmp = fn.gsub('.gif','.png')
      #cmds << "/usr/local/bin/convert #{fp} -trim -undercolor white -colors 256 -depth 8 -resize #{sz} #{sz}/#{ftmp}"
      #ctr+=1      
      #if ctr>MAX then break end
    # }
    end
  }
  puts "please wait while processing...#{flist.size-rem} items"
  puts html
  cmds
end

# make sure to get fresh images
# system("rm -Rf 40x40 60x60 80x80")
# system("mkdir 40x40 60x60 80x80")

get_list.each {|cm|
  puts "doing: #{cm}"
  system(cm)
}
  
puts "done"
