package notate

import scala.collection.mutable.MutableList
import java.io.RandomAccessFile
import java.io.File

class Notation {

  var ch: Char = ' '
  var i: Int = 1

  def notationShow(filename: String) {
    var file = new RandomAccessFile(new File(filename), "rw")
    var file2 = new RandomAccessFile(new File("newNotation.newsave"), "rw");
    while (file.getFilePointer != file.length()) {
      ch = file.readChar()
      ch match {
        
        case 'W' => file2.writeChars("Added water\n") 
        case 'V' => file2.writeChars("Added wall\n") 
        case 'B' => file2.writeChars("Added brick\n") 
        case 'G' => file2.writeChars("Added grass\n") 
        case 'R' => file2.writeChars("Player/Bot moving right\n") 
        case 'L' => file2.writeChars("Player/Bot moving left\n") 
        case 'U' => file2.writeChars("Player/Bot moving up\n") 
        case 'D' => file2.writeChars("Player/Bot moving down\n") 
        case 'S' => file2.writeChars("Player shooting\n") 
        case 'n' => file2.writeChars("New enemy\n") 
        case _ => 
      }
    }
    file2.close();
  }
}