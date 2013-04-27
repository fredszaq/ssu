package com.tlorrain.ssu.rss


abstract class ItemValidity

abstract class ValidItem extends ItemValidity
abstract class InvalidItem extends ItemValidity

case class RssItem[Validity <: ItemValidity] (title: Option[String]=None,
  description: Option[String]=None,
  link: Option[String] = None, //TODO URI
  author: Option[String] = None, // TODO email
  category: Option[String] = None, // TODO optional attribute "domain"
  comments: Option[String] = None, // TODO url
  enclosure: Option[Tuple3[String, Long, String]] = None, // TODO proper type ?
  guid: Option[String] = None,
  pubDate: Option[Long] = None,
  source: Option[Tuple2[String, String]] = None // TODO proper type
  ) {
  
  def withTitle(title: String) = copy[ValidItem](title = Some(title))

  def withDescription(description: String) = copy[ValidItem](description = Some(description))

  def withLink(link: Option[String]) = copy[Validity](link = link)
  def withLink(link: String) = copy[Validity](link = Some(link))

  def withAuthor(author: Option[String]) = copy[Validity](author = author)
  def withAuthor(author: String) = copy[Validity](author = Some(author))

  def withCategory(category: Option[String]) = copy[Validity](category = category)
  def withCategory(category: String) = copy[Validity](category = Some(category))

  def withComments(comments: Option[String]) = copy[Validity](comments = comments)
  def withComments(comments: String) = copy[Validity](comments = Some(comments))

  def withEnclosure(enclosure: Option[Tuple3[String, Long, String]]) = copy[Validity](enclosure = enclosure)
  def withEnclosure(enclosure: Tuple3[String, Long, String]) = copy[Validity](enclosure = Some(enclosure))

  def withGuid(guid: Option[String]) = copy[Validity](guid = guid)
  def withGuid(guid: String) = copy[Validity](guid = Some(guid))

  def withPubDate(pubDate: Option[Long]) = copy[Validity](pubDate = pubDate)
  def withPubDate(pubDate: Long) = copy[Validity](pubDate = Some(pubDate))

  def withSource(source: Option[Tuple2[String, String]]) = copy[Validity](source = source)
  def withSource(source: Tuple2[String, String]) = copy[Validity](source = Some(source))

  def toXml = <item>
                { (for (title <- title) yield <title>{ title }</title>).getOrElse("") }
                { (for (description <- description) yield <description>{ description }</description>).getOrElse("") }
                { (for (link <- link) yield <link>{ link }</link>).getOrElse("") }
                { (for (author <- author) yield <author>{ author }</author>).getOrElse("") }
                { (for (category <- category) yield <category>{ category }</category>).getOrElse("") }
                { (for (comments <- comments) yield <comments>{ comments }</comments>).getOrElse("") }
                { (for ((url, length, typ) <- enclosure) yield <enclosure url={ url } length={ length.toString } type={ typ }/>).getOrElse("") }
                { (for (guid <- guid) yield <guid>{ guid }</guid>).getOrElse("") }
                { (for (pubDate <- pubDate) yield <pubDate>{ pubDate }</pubDate>).getOrElse("") }
                { (for ((url, title) <- source) yield <source url={ url }>{ title }</source>).getOrElse("") }
              </item>

}