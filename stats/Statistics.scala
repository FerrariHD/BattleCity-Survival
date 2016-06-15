package stats

import game.GameInfo
import scala.collection.mutable.MutableList
import java.io.RandomAccessFile
import java.io.File
import game.ReplaysController


class Statistics() {

  var bot : Long = 0
  var left : Long = 0
  var right: Long = 0
  var up: Long = 0
  var down: Long = 0
  var shot: Long = 0
  var ch : Char = ' '
  var chars: String = ""
   
  def collectInformations(gameInfo: Array[GameInfo]) {
    for (current <- gameInfo) {
      var file = new RandomAccessFile(new File(current.getFileName), "rw")
      while (file.getFilePointer != file.length()) {
        ch = file.readChar()
        chars += ch
      }
      file.close()
      bot = chars.filter { x => x == 'n'}.length() 
      left = chars.filter { x => x == 'L'}.length() 
      right = chars.filter { x => x == 'R'}.length() 
      up = chars.filter { x => x == 'U'}.length() 
      down = chars.filter { x => x == 'D'}.length() 
      shot = chars.filter { x => x == 'S'}.length() 
    }
  }
}