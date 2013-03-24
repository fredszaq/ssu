package com.tlorrain.ssu.rss

case class RssItem(title: String,
  description: String,
  link: Option[String] = None, //TODO URI
  author: Option[String] = None, // TODO email
  category: Option[String] = None, // TODO optional attribute "domain"
  comments: Option[String] = None, // TODO url
  enclosure: Option[Tuple3[String, Long, String]] = None, // TODO proper type ?
  guid: Option[String] = None,
  pubDate: Option[Long] = None, // TODO date
  source: Option[Tuple2[String, String]] = None // TODO proper type
  ) {

  def withLink(link: Option[String]) = copy(link = link)
  def withLink(link: String) = copy(link = Some(link))

  def withAuthor(author: Option[String]) = copy(author = author)
  def withAuthor(author: String) = copy(author = Some(author))

  def withCategory(category: Option[String]) = copy(category = category)
  def withCategory(category: String) = copy(category = Some(category))

  def withComments(comments: Option[String]) = copy(comments = comments)
  def withComments(comments: String) = copy(comments = Some(comments))

  def withEnclosure(enclosure: Option[Tuple3[String, Long, String]]) = copy(enclosure = enclosure)
  def withEnclosure(enclosure: Tuple3[String, Long, String]) = copy(enclosure = Some(enclosure))

  def withGuid(guid: Option[String]) = copy(guid = guid)
  def withGuid(guid: String) = copy(guid = Some(guid))

  def withPubDate(pubDate: Option[Long]) = copy(pubDate = pubDate)
  def withPubDate(pubDate: Long) = copy(pubDate = Some(pubDate))

  def withSource(source: Option[Tuple2[String, String]]) = copy(source = source)
  def withSource(source: Tuple2[String, String]) = copy(source = Some(source))

  def toXml = <item>
                <title>{ title }</title>
                <description>{ description }</description>
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