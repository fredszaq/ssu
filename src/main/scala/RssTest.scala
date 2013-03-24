import com.tlorrain.ssu.rss.RssFeed
import java.util.Locale
import com.tlorrain.ssu.rss.RssItem
import scala.xml.PrettyPrinter
import scala.xml.XML
import scala.language.postfixOps

object RssTest extends App {

  override def main(args: Array[String]) {
    val myItems = ("hello", "world") :: ("You are", "a bitch") :: Nil

    val rss = RssFeed("some title<hello>world</hello>", "http://someli.nk", "some description<heloo>world</heloo>")

    val rss2 = rss.copy(copyright = Some("toto"))
    val feed = (rss2
      withCopyright "Fredszaq"
      withItems (myItems map (item => RssItem(item._1, item._2)
        withLink "http://some.other.li.nk"
        withEnclosure ("http://u.rl", 666, "application/doom")
        withAuthor ("Fredszaq")
        withComments ("http://yetanother.url")
        withCategory ("super category"))) toXml)

    feed.toString
    XML.save("/tmp/test", feed , xmlDecl=true )
    println(new PrettyPrinter(100, 4).format(XML.loadString(feed.toString)))
  }

}