package com.tlorrain.ssu.rss

import java.net.URL

sealed trait ItemValidity

abstract class WithTitleOrDescription extends ItemValidity
abstract class WithoutTitleOrDescription extends ItemValidity

object RssItem extends RssItem[WithoutTitleOrDescription](None, None, None, None, None, None, None, None, None, None) {
  def apply(title: Option[String] = None,
    description: Option[String] = None,
    link: Option[URL] = None,
    author: Option[String] = None,
    category: Option[RssCategory] = None,
    comments: Option[URL] = None,
    enclosure: Option[RssEnclosure] = None,
    guid: Option[String] = None,
    pubDate: Option[Long] = None,
    source: Option[RssSource] = None) = (title, description) match {
    case (None, None) => new RssItem[WithoutTitleOrDescription](title, description, link, author, category, comments, enclosure, guid, pubDate, source)
    case _ => new RssItem[WithTitleOrDescription](title, description, link, author, category, comments, enclosure, guid, pubDate, source)
  }
}

class RssItem[Validity <: ItemValidity] private (val title: Option[String] = None,
  val description: Option[String] = None,
  val link: Option[URL] = None,
  val author: Option[String] = None, // TODO email, is it possible to have multiple authors ?
  val category: Option[RssCategory] = None, // TODO list
  val comments: Option[URL] = None,
  val enclosure: Option[RssEnclosure] = None, // TODO is it possible to have multiple enclosures
  val guid: Option[String] = None,
  val pubDate: Option[Long] = None,
  val source: Option[RssSource] = None) extends Xmllizable {

  private def copy[V <: ItemValidity](title: Option[String] = title,
    description: Option[String] = description,
    link: Option[URL] = link,
    author: Option[String] = author,
    category: Option[RssCategory] = category,
    comments: Option[URL] = comments,
    enclosure: Option[RssEnclosure] = enclosure,
    guid: Option[String] = guid,
    pubDate: Option[Long] = pubDate,
    source: Option[RssSource] = source) = new RssItem[V](title, description, link, author, category, comments, enclosure, guid, pubDate, source)

  def withTitle(title: String) = copy[WithTitleOrDescription](title = Some(title))

  def withDescription(description: String) = copy[WithTitleOrDescription](description = Some(description))

  def withLink(link: Option[URL]) = copy[Validity](link = link)
  def withLink(link: URL) = copy[Validity](link = Some(link))

  def withAuthor(author: Option[String]) = copy[Validity](author = author)
  def withAuthor(author: String) = copy[Validity](author = Some(author))

  // TODO type erasure problem if uncommented
  // def withCategory(category: Option[String]) = copy[Validity](category = category map ( s => RssCategory(s)))
  def withCategory(category: String) = copy[Validity](category = Some(RssCategory(category)))
  def withCategory(category: Option[RssCategory]) = copy[Validity](category = category)
  def withCategory(category: RssCategory) = copy[Validity](category = Some(category))

  def withComments(comments: Option[URL]) = copy[Validity](comments = comments)
  def withComments(comments: URL) = copy[Validity](comments = Some(comments))

  def withEnclosure(enclosure: Option[RssEnclosure]) = copy[Validity](enclosure = enclosure)
  def withEnclosure(enclosure: RssEnclosure) = copy[Validity](enclosure = Some(enclosure))

  def withGuid(guid: Option[String]) = copy[Validity](guid = guid)
  def withGuid(guid: String) = copy[Validity](guid = Some(guid))

  def withPubDate(pubDate: Option[Long]) = copy[Validity](pubDate = pubDate)
  def withPubDate(pubDate: Long) = copy[Validity](pubDate = Some(pubDate))

  def withSource(source: Option[RssSource]) = copy[Validity](source = source)
  def withSource(source: RssSource) = copy[Validity](source = Some(source))

  lazy val formatedPubDate = formatDateRFC822(pubDate)

  lazy val toXml = <item>
                     {
                       (List(
                         (title map (title => <title>{ title }</title>)),
                         (description map (description => <description>{ description }</description>)),
                         (link map (link => <link>{ link }</link>)),
                         (author map (author => <author>{ author }</author>)),
                         (category map (category => category.toXml)),
                         (comments map (comments => <comments>{ comments }</comments>)),
                         (enclosure map (enclosure => enclosure.toXml)),
                         (guid map (guid => <guid>{ guid }</guid>)),
                         (formatedPubDate map (pubDate => <pubDate>{ pubDate }</pubDate>)),
                         (source map (source => source.toXml)))
                         map (elem => elem.getOrElse("")))
                     }
                   </item>

}