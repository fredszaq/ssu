package com.tlorrain.ssu.rss

import java.util.Locale
import scala.collection.immutable.Traversable
import scala.xml.Utility

case class RssFeed(title: String,
  link: String, //TODO URI
  description: String,
  language: Option[Locale] = None,
  copyright: Option[String] = None,
  managingEditor: Option[String] = None, // TODO email
  webMaster: Option[String] = None, // TODO email
  pubDate: Option[Long] = None, // TODO date
  lastBuildDate: Option[Long] = None, // TODO date
  category: Option[String] = None, // TODO optional attribute "domain"
  generator: Option[String] = Some("Scala Syndication Utils"),
  docs: Option[String] = Some("http://www.rssboard.org/rss-specification"), // TODO URI
  cloud: Option[Any] = None, //see RSS cloud interface
  ttl: Option[Int] = None,
  image: Option[Any] = None, // TODO RSS Image
  rating: Option[String] = None, //TODO see PICS Rating rules
  textInput: Option[Any] = None, //TODO see how that works, low priority
  skipHours: Option[Traversable[Int]] = None,
  skipDays: Option[Traversable[Int]] = None,
  items: Option[Traversable[RssItem[ValidItem]]] = None) {

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

  def withCategory(category: Option[String]) = copy(category = category)
  def withCategory(category: String) = copy(category = Some(category))

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

  def withSkipDays(skipDays: Option[Traversable[Int]]) = copy(skipDays = skipDays)
  def withSkipDays(skipDays: Traversable[Int]) = copy(skipDays = Some(skipDays))

  def withItems(items: Option[Traversable[RssItem[ValidItem]]]) = copy(items = items)
  def withItems(items: Traversable[RssItem[ValidItem]]) = copy(items = Some(items))

  def toXml = Utility.trim(
    <rss version="2.0">
      <channel>
        <title>{ title }</title>
        <link>{ link }</link>
        <description>{ description }</description>
        { (for (language <- language) yield <language>{ language }</language>).getOrElse("") }
        { (for (copyright <- copyright) yield <copyright>{ copyright }</copyright>).getOrElse("") }
        { (for (managingEditor <- managingEditor) yield <managingEditor>{ managingEditor }</managingEditor>).getOrElse("") }
        { (for (webMaster <- webMaster) yield <webMaster>{ webMaster }</webMaster>).getOrElse("") }
        { (for (pubDate <- pubDate) yield <pubDate>{ pubDate }</pubDate>).getOrElse("") }
        { (for (lastBuildDate <- lastBuildDate) yield <lastBuildDate>{ lastBuildDate }</lastBuildDate>).getOrElse("") }
        { (for (category <- category) yield <category>{ category }</category>).getOrElse("") }
        { (for (generator <- generator) yield <generator>{ generator }</generator>).getOrElse("") }
        { (for (docs <- docs) yield <docs>{ docs }</docs>).getOrElse("") }
        { (for (cloud <- cloud) yield <cloud>{ cloud }</cloud>).getOrElse("") }
        { (for (ttl <- ttl) yield <ttl>{ ttl }</ttl>).getOrElse("") }
        { (for (image <- image) yield <image>{ image }</image>).getOrElse("") }
        { (for (rating <- rating) yield <rating>{ rating }</rating>).getOrElse("") }
        { (for (textInput <- textInput) yield <textInput>{ textInput }</textInput>).getOrElse("") }
        { (for (skipHours <- skipHours) yield <skipHours>{ skipHours }</skipHours>).getOrElse("") }
        { (for (skipDays <- skipDays) yield <skipDays>{ skipDays }</skipDays>).getOrElse("") }
        { (for (items <- items) yield for (item <- items) yield item.toXml).getOrElse("") }
      </channel>
    </rss>)
}