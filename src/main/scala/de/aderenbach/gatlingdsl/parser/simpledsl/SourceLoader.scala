package de.aderenbach.gatlingdsl.parser.simpledsl

import java.io.File
import scala.collection.JavaConversions._

// enables foreach

import org.apache.commons.io.FileUtils
import scala.io.Source

/**
 * Created by Alexander Derenbach <alexander.derenbach@gmail.com> on 23.10.15.
 */
object SourceLoader {

  private var sourceLocation = ""

  // TODO at the moment filenames must be unique.
  def sourceLocation(location: String): Unit = {
    val duplicateFiles = FileUtils.listFiles(new File(location.trim), null, true)
      .map(f => f.getName).groupBy(f => f).collect { case (x, xs) if xs.size > 1 => x }
    duplicateFiles match {
      case Nil => sourceLocation = location
      case _ => throw new Error("duplicate filenames: " + (duplicateFiles mkString (",")))
    }

  }

  def getSource(name: String) = {
    Source.fromFile(FileUtils.listFiles(new File(sourceLocation.trim), null, true).toStream
      .filter(f => f.getName().equals(name.trim)).head)
  }

}
