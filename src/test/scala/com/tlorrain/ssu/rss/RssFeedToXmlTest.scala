package com.tlorrain.ssu.rss

import org.scalatest.FunSuite
import java.util.Locale
import org.scalatest.matchers.ShouldMatchers
import java.net.URL

class RssFeedToXmlTest extends FunSuite with ShouldMatchers {

  val items = ("title1", "content1", new URL("http://link1.com"), "author1@site1.com") :: ("title2", "content2", new URL("http://link2.com"), "author2@site2.com") :: Nil

  test("generate correct XML (simple version)") {

    val rss = (
      RssFeed("some title", new URL("http://someli.nk"), "some description")
      withCopyright "SSU"
      withItems (items map (item => RssItem
        withTitle item._1 withDescription item._2
        withLink item._3
        withAuthor item._4))).toXml

    val expected = <rss version="2.0"><channel><title>some title</title><link>http://someli.nk</link><description>some description</description><copyright>SSU</copyright><generator>Scala Syndication Utils</generator><docs>http://www.rssboard.org/rss-specification</docs><item><title>title1</title><description>content1</description><link>http://link1.com</link><author>author1@site1.com</author></item><item><title>title2</title><description>content2</description><link>http://link2.com</link><author>author2@site2.com</author></item></channel></rss>

    rss toString () should equal(expected toString ())
  }

  test("generate correct XML (full version)") {

    val rss = (
      RssFeed("some title", new URL("http://someli.nk"), "some description")
      withLanguage Locale.ENGLISH
      withCopyright "SSU"
      withManagingEditor "somebody@somewhere.com"
      withWebMaster "somebodyElse@somewhere.com"
      withPubDate 0
      withLastBuildDate 1234567890
      withCategory RssCategory("IT", Some("http://www.yetanotherlink.org"))
      withCloud "TODO"
      withTtl 60
      withImage (RssImage
        withTitle "Some image"
        withUrl new URL("http://imageurl.info/img.png")
        withWidth 144
        withHeight 400
        withDescription "Some description")
        withRating "TODO"
        withTextInput "TODO"
        withSkipHours 0 :: 1 :: 12 :: 15 :: Nil
        withSkipDays Saturday :: Sunday :: Nil
        withItems (items map (item => RssItem
          withTitle item._1
          withDescription item._2
          withLink item._3
          withAuthor item._4
          withCategory RssCategory("IT", Some("http://www.yetanotherlink.org"))
          withComments new URL("http://www.comments-url.net")
          withEnclosure RssEnclosure("http://www.localtion.biz", 7839, "image/png")
          withGuid item._3.toString()
          withPubDate 1357924680
          withSource RssSource(new URL("http://www.sourceurl.eu"), "Very cool site")))).toXml

    val expected = <rss version="2.0"><channel><title>some title</title><link>http://someli.nk</link><description>some description</description><language>en</language><copyright>SSU</copyright><managingEditor>somebody@somewhere.com</managingEditor><webMaster>somebodyElse@somewhere.com</webMaster><pubDate>Thu, 1 Jan 1970 01:00:00 +0100</pubDate><lastBuildDate>Thu, 15 Jan 1970 07:56:07 +0100</lastBuildDate><category domain="http://www.yetanotherlink.org">IT</category><generator>Scala Syndication Utils</generator><docs>http://www.rssboard.org/rss-specification</docs><cloud>TODO</cloud><ttl>60</ttl><image><title>Some image</title><url>http://imageurl.info/img.png</url><link>http://imageurl.info/img.png</link><width>144</width><height>400</height><description>Some description</description></image><rating>TODO</rating><textInput>TODO</textInput><skipHours><hour>0</hour><hour>1</hour><hour>12</hour><hour>15</hour></skipHours><skipDays><day>Saturday</day><day>Sunday</day></skipDays><item><title>title1</title><description>content1</description><link>http://link1.com</link><author>author1@site1.com</author><category domain="http://www.yetanotherlink.org">IT</category><comments>http://www.comments-url.net</comments><enclosure url="http://www.localtion.biz" length="7839" type="image/png"/><guid>http://link1.com</guid><pubDate>Fri, 16 Jan 1970 18:12:04 +0100</pubDate><source url="http://www.sourceurl.eu">Very cool site</source></item><item><title>title2</title><description>content2</description><link>http://link2.com</link><author>author2@site2.com</author><category domain="http://www.yetanotherlink.org">IT</category><comments>http://www.comments-url.net</comments><enclosure url="http://www.localtion.biz" length="7839" type="image/png"/><guid>http://link2.com</guid><pubDate>Fri, 16 Jan 1970 18:12:04 +0100</pubDate><source url="http://www.sourceurl.eu">Very cool site</source></item></channel></rss>

    rss toString () should equal(expected toString ())

  }

}