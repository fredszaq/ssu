import com.tlorrain.ssu.rss.RssFeed
import java.util.Locale
import com.tlorrain.ssu.rss.RssItem
import scala.xml.PrettyPrinter
import scala.xml.XML
import scala.language.postfixOps
import scala.xml.Utility

object RssTest extends App {
  

  override def main(args: Array[String]) {
    val myItems = ("hello", "world") :: ("You are", "a bitch") :: Nil

    val rss = RssFeed("some title<hello>world</hello>", "http://someli.nk", "some description<heloo>world</heloo>")

    val rss2 = rss.copy(copyright = Some("toto"))
    val feed = (rss2
      withCopyright "Fredszaq"
      withItems (myItems map (item => RssItem
        withTitle item._1
        withDescription item._2
        withLink "http://some.other.li.nk"
        withEnclosure ("http://u.rl", 666, "application/doom")
        withAuthor ("Fredszaq")
        withComments ("http://yetanother.url")
        withCategory ("super category"))) toXml)
        
    XML.save("/tmp/test", feed , xmlDecl=true )
    val xml=new PrettyPrinter(100, 4).format(XML.loadString(feed.toString))
    println(xml)
    
    val expected=Utility.trim(
<rss version="2.0">
    <channel>
        <title>some title&lt;hello&gt;world&lt;/hello&gt;</title>
        <link>http://someli.nk</link>
        <description>some description&lt;heloo&gt;world&lt;/heloo&gt;</description>
        <copyright>Fredszaq</copyright>
        <generator>Scala Syndication Utils</generator>
        <docs>http://www.rssboard.org/rss-specification</docs>
        <item>
            <title>hello</title>
            <description>world</description>
            <link>http://some.other.li.nk</link>
            <author>Fredszaq</author>
            <category>super category</category>
            <comments>http://yetanother.url</comments>
            <enclosure url="http://u.rl" length="666" type="application/doom"/>
        </item>
        <item>
            <title>You are</title>
            <description>a bitch</description>
            <link>http://some.other.li.nk</link>
            <author>Fredszaq</author>
            <category>super category</category>
            <comments>http://yetanother.url</comments>
            <enclosure url="http://u.rl" length="666" type="application/doom"/>
        </item>
    </channel>
</rss>)
    
    println(feed)
    println(expected)
    println(feed.toString.equals(expected.toString))

  }

}