package com.tlorrain.ssu.rss

abstract class ItemValidity

abstract class WithTitleOrDescription extends ItemValidity
abstract class WithoutTitleOrDescription extends ItemValidity

object RssItem extends RssItem[WithoutTitleOrDescription](None, None, None, None, None, None, None, None, None, None) {
  def apply(title: Option[String] = None,
    description: Option[String] = None,
    link: Option[String] = None,
    author: Option[String] = None,
    category: Option[RssCategory] = None,
    comments: Option[String] = None,
    enclosure: Option[Tuple3[String, Long, String]] = None,
    guid: Option[String] = None,
    pubDate: Option[Long] = None,
    source: Option[Tuple2[String, String]] = None
    ) = (title, description) match {
    case (None, None) => new RssItem[WithoutTitleOrDescription](title, description, link, author, category, comments, enclosure, guid, pubDate, source)
    case _ => new RssItem[WithTitleOrDescription](title, description, link, author, category, comments, enclosure, guid, pubDate, source)
  }
}

class RssItem[Validity <: ItemValidity] private (val title: Option[String] = None,
  val description: Option[String] = None,
  val link: Option[String] = None, //TODO URI
  val author: Option[String] = None, // TODO email, is it possible to have multiple authors ?
  val category: Option[RssCategory] = None, // TODO list
  val comments: Option[String] = None, // TODO URI
  val enclosure: Option[Tuple3[String, Long, String]] = None, // TODO proper type, is it possible to have multiple enclosures
  val guid: Option[String] = None,
  val pubDate: Option[Long] = None,
  val source: Option[Tuple2[String, String]] = None // TODO proper type
  ) {

  private def copy[V <: ItemValidity](title: Option[String] = title,
    description: Option[String] = description,
    link: Option[String] = link,
    author: Option[String] = author,
    category: Option[RssCategory] = category,
    comments: Option[String] = comments,
    enclosure: Option[Tuple3[String, Long, String]] = enclosure,
    guid: Option[String] = guid,
    pubDate: Option[Long] = pubDate,
    source: Option[Tuple2[String, String]] = source
    ) = new RssItem[V](title, description, link, author, category, comments, enclosure, guid, pubDate, source)
  
  def withTitle(title: String) = copy[WithTitleOrDescription](title = Some(title))

  def withDescription(description: String) = copy[WithTitleOrDescription](description = Some(description))

  def withLink(link: Option[String]) = copy[Validity](link = link)
  def withLink(link: String) = copy[Validity](link = Some(link))

  def withAuthor(author: Option[String]) = copy[Validity](author = author)
  def withAuthor(author: String) = copy[Validity](author = Some(author))

  // TODO type erasure problem if uncommented
  // def withCategory(category: Option[String]) = copy[Validity](category = category map ( s => RssCategory(s)))
  def withCategory(category: String) = copy[Validity](category = Some(RssCategory(category)))
  def withCategory(category: Option[RssCategory]) = copy[Validity](category = category )
  def withCategory(category: RssCategory) = copy[Validity](category = Some(category))

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