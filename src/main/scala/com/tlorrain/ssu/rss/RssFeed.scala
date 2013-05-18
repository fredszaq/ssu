package com.tlorrain.ssu.rss

import java.util.Locale
import scala.collection.immutable.Traversable
import scala.xml.Utility
import java.text.SimpleDateFormat
import java.util.Date

case class RssFeed(title: String,
  link: String, //TODO URI
  description: String,
  language: Option[Locale] = None,
  copyright: Option[String] = None,
  managingEditor: Option[String] = None, // TODO email
  webMaster: Option[String] = None, // TODO email
  pubDate: Option[Long] = None,
  lastBuildDate: Option[Long] = None,
  category: Option[RssCategory] = None, // TODO list
  generator: Option[String] = Some("Scala Syndication Utils"),
  docs: Option[String] = Some("http://www.rssboard.org/rss-specification"), // TODO URI
  cloud: Option[Any] = None, //see RSS cloud interface
  ttl: Option[Int] = None,
  image: Option[Any] = None, // TODO RSS Image
  rating: Option[String] = None, //TODO see PICS Rating rules
  textInput: Option[Any] = None, //TODO see how that works, low priority
  skipHours: Option[Traversable[Int]] = None,
  skipDays: Option[Traversable[String]] = None, // TODO use sealed trait
  items: Option[Traversable[RssItem[WithTitleOrDescription]]] = None) extends Xmllizable {

  def withLanguage(language: Locale) = copy(language = Some(language))
  def withLanguage(language: Option[Locale]) = copy(language = language)

  def withCopyright(copyright: String) = copy(copyright = Some(copyright))
  def withCopyright(copyright: Option[String]) = copy(copyright = copyright)

  def withManagingEditor(managingEditor: String) = copy(managingEditor = Some(managingEditor))
  def withManagingEditor(managingEditor: Option[String]) = copy(managingEditor = managingEditor)

  def withWebMaster(webMaster: String) = copy(webMaster = Some(webMaster))
  def withWebMaster(webMaster: Option[String]) = copy(webMaster = webMaster)

  def withPubDate(pubDate: Long) = copy(pubDate = Some(pubDate))
  def withPubDate(pubDate: Option[Long]) = copy(pubDate = pubDate)

  def withLastBuildDate(lastBuildDate: Option[Long]) = copy(lastBuildDate = lastBuildDate)
  def withLastBuildDate(lastBuildDate: Long) = copy(lastBuildDate = Some(lastBuildDate))

  // TODO type erasure problem if uncommented
  // def withCategory(category: Option[String]) = copy(category = category map ( s => RssCategory(s)))
  def withCategory(category: String) = copy(category = Some(RssCategory(category)))
  def withCategory(category: Option[RssCategory]) = copy(category = category)
  def withCategory(category: RssCategory) = copy(category = Some(category))

  def withGenerator(generator: Option[String]) = copy(generator = generator)
  def withGenerator(generator: String) = copy(generator = Some(generator))

  def withDocs(docs: Option[String]) = copy(docs = docs)
  def withDocs(docs: String) = copy(docs = Some(docs))

  def withCloud(cloud: Option[Any]) = copy(cloud = cloud)
  def withCloud(cloud: Any) = copy(cloud = Some(cloud))

  def withTtl(ttl: Option[Int]) = copy(ttl = ttl)
  def withTtl(ttl: Int) = copy(ttl = Some(ttl))

  def withImage(image: Option[Any]) = copy(image = image)
  def withImage(image: Any) = copy(image = Some(image))

  def withRating(rating: Option[String]) = copy(rating = rating)
  def withRating(rating: String) = copy(rating = Some(rating))

  def withTextInput(textInput: Option[Any]) = copy(textInput = textInput)
  def withTextInput(textInput: Any) = copy(textInput = Some(textInput))

  def withSkipHours(skipHours: Option[Traversable[Int]]) = copy(skipHours = skipHours)
  def withSkipHours(skipHours: Traversable[Int]) = copy(skipHours = Some(skipHours))

  def withSkipDays(skipDays: Option[Traversable[String]]) = copy(skipDays = skipDays)
  def withSkipDays(skipDays: Traversable[String]) = copy(skipDays = Some(skipDays))

  // TODO if uncommented the error when providing a Traversable[WithoutTitleOrDescription] is less comprehensible
  //def withItems(items: Option[Traversable[RssItem[WithTitleOrDescription]]]) = copy(items = items) 
  def withItems(items: Traversable[RssItem[WithTitleOrDescription]]) = copy(items = Some(items))

  lazy val formatedPubDate = formatDateRFC822(pubDate)
  lazy val formatedLastBuildDate = formatDateRFC822(lastBuildDate)

  lazy val toXml = Utility.trim(
    <rss version="2.0">
      <channel>
        <title>{ title }</title>
        <link>{ link }</link>
        <description>{ description }</description>
        {
          (List(
            (language map (language => <language>{ language }</language>)),
            (copyright map (copyright => <copyright>{ copyright }</copyright>)),
            (managingEditor map (managingEditor => <managingEditor>{ managingEditor }</managingEditor>)),
            (webMaster map (webMaster => <webMaster>{ webMaster }</webMaster>)),
            (formatedPubDate map (pubDate => <pubDate>{ pubDate }</pubDate>)),
            (formatedLastBuildDate map (lastBuildDate => <lastBuildDate>{ lastBuildDate }</lastBuildDate>)),
            (category map (category => category.toXml)),
            (generator map (generator => <generator>{ generator }</generator>)),
            (docs map (docs => <docs>{ docs }</docs>)),
            (cloud map (cloud => <cloud>{ cloud }</cloud>)),
            (ttl map (ttl => <ttl>{ ttl }</ttl>)),
            (image map (image => <image>{ image }</image>)),
            (rating map (rating => <rating>{ rating }</rating>)),
            (textInput map (textInput => <textInput>{ textInput }</textInput>)),
            (skipHours map (skipHours => <skipHours>{ skipHours map (hour => <hour> { hour } </hour>) }</skipHours>)),
            (skipDays map (skipDays => <skipDays>{ skipDays map (day => <day> { day } </day>) }</skipDays>)),
            (items map (items => items map (item => item.toXml))))
            map (elem => elem.getOrElse("")))
        }
      </channel>
    </rss>)
}