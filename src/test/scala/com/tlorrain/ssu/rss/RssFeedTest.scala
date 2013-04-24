package com.tlorrain.ssu.rss

import scala.reflect.runtime.universe
import scala.tools.reflect.ToolBox
import scala.tools.reflect.ToolBoxError

import org.scalatest.FunSuite

class RssFeedTest extends FunSuite {
  val goodCode = """
    import com.tlorrain.ssu.rss.RssFeed
    import com.tlorrain.ssu.rss.RssItem

        
    val myItems = ("hello", "world") :: ("You are", "a bitch") :: Nil

    val rss = RssFeed("some title<hello>world</hello>", "http://someli.nk", "some description<heloo>world</heloo>")

    val rss2 = rss.copy(copyright = Some("toto"))
    val feed = (rss2
      withCopyright "Fredszaq"
      withItems (myItems map (item => RssItem()
        withTitle item._1 // TODO better compiler error when not a valid Item
        withDescription item._2
        withLink "http://some.other.li.nk"
        withEnclosure ("http://u.rl", 666, "application/doom")
        withAuthor ("Fredszaq")
        withComments ("http://yetanother.url")
        withCategory ("super category"))) toXml)"""
  val badCode = """
	  import com.tlorrain.ssu.rss.RssFeed
	  import com.tlorrain.ssu.rss.RssItem
	  
	  
	  val myItems = ("hello", "world") :: ("You are", "a bitch") :: Nil
	  
	  val rss = RssFeed("some title<hello>world</hello>", "http://someli.nk", "some description<heloo>world</heloo>")
	  
	  val rss2 = rss.copy(copyright = Some("toto"))
	  val feed = (rss2
		withCopyright "Fredszaq"
		withItems (myItems map (item => RssItem()
		  //withTitle item._1 // TODO better compiler error when not a valid Item
		  //withDescription item._2
		  withLink "http://some.other.li.nk"
		  withEnclosure ("http://u.rl", 666, "application/doom")
		  withAuthor ("Fredszaq")
		  withComments ("http://yetanother.url")
		  withCategory ("super category"))) toXml)"""

  def compile(code: String) = {
    val toolBox = scala.reflect.runtime.universe.runtimeMirror(getClass.getClassLoader).mkToolBox()
    toolBox.eval(toolBox.parse("object Test extends Application { " + code + "}"))

  }

  test("compile good code") {
    compile(goodCode)
  }
  test("compile bad code") {
    intercept[ToolBoxError] {
      compile(badCode)
    }
  }

}