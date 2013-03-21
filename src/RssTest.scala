import com.tlorrain.ssu.rss.RssFeed
import java.util.Locale
import com.tlorrain.ssu.rss.RssItem
import scala.xml.PrettyPrinter
import scala.xml.XML

object RssTest extends App {

  override def main(args: Array[String]) {
    println("Hello, world!")
    
    val myItems= ("hello","world") :: ("You are", "a bitch") :: Nil
    
    val rss= RssFeed("some title<hello>world</hello>", "http://someli.nk", "some description<heloo>world</heloo>")
    
    val rss2=rss.copy(copyright=Some("toto"))
    val extractedLocalValue = rss2 withCopyright "Fredszaq" withItems (myItems map ( item => RssItem(item._1, item._2))) toXml
    
   extractedLocalValue.toString
    XML.save("/tmp/test", extractedLocalValue/*, xmlDecl=true*/)
    println(new PrettyPrinter(100,4).format(extractedLocalValue))
  }

}