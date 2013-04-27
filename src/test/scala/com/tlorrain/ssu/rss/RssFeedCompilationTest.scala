package com.tlorrain.ssu.rss

import scala.reflect.runtime.universe
import scala.tools.reflect.ToolBox
import scala.tools.reflect.ToolBoxError

import org.scalatest.FunSuite

class RssFeedCompilationTest extends FunSuite {

  def skeletonItems(extra: String) = s"""
		  val rss = (RssFeed("some title", "http://someli.nk", "some description")
		  	withCopyright "SSU"
		  	withItems (items map (item => RssItem() 
		  		$extra
		  		withLink item._3 
		  		withAuthor item._4)) toXml)"""

  val goodItemTitleDescriptionCode = skeletonItems("withTitle item._1 withDescription item._2")
  val goodItemTitleCode = skeletonItems("withTitle item._1")
  val goodItemDescriptionCode = skeletonItems("withDescription item._2")
  val badItemCode = skeletonItems("")

  def compile(code: String) = {
    val toolBox = scala.reflect.runtime.universe.runtimeMirror(getClass.getClassLoader).mkToolBox()
    toolBox.eval(toolBox.parse(s"""
        object Test extends Application { 
    			import com.tlorrain.ssu.rss._
    			val items = ("title1", "content1", "http://link1.com", "author1") :: ("title2", "content2", "http://link2.com","author2") :: Nil       
    			$code     
        }"""))

  }

  test("compile good item (title description) code") {
    compile(goodItemTitleDescriptionCode)
  }

  test("compile good item (title) code") {
    compile(goodItemTitleCode)
  }

  test("compile good item (description) code") {
    compile(goodItemDescriptionCode)
  }

  test("compile bad item code") {
    intercept[ToolBoxError] {
      compile(badItemCode)
    }
  }

}