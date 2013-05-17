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
  skipDays: Option[Traversable[Int]] = None,
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
  def withCategory(category: Option[RssCategory]) = copy(category = category )
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

  def withSkipDays(skipDays: Option[Traversable[Int]]) = copy(skipDays = skipDays)
  def withSkipDays(skipDays: Traversable[Int]) = copy(skipDays = Some(skipDays))

  // TODO if uncommented the error when providing a Traversable[InvalidItem] is less comprehensible
  //def withItems(items: Option[Traversable[RssItem[ValidItem]]]) = copy(items = items) 
  def withItems(items: Traversable[RssItem[WithTitleOrDescription]]) = copy(items = Some(items))

  lazy val toXml = Utility.trim(
    <rss version="2.0">
      <channel>
        <title>{ title }</title>
        <link>{ link }</link>
        <description>{ description }</description>
        { (language map (language => <language>{ language }</language>)).getOrElse("") }
        { (copyright map (copyright => <copyright>{ copyright }</copyright>)).getOrElse("") }
        { (managingEditor map (managingEditor => <managingEditor>{ managingEditor }</managingEditor>)).getOrElse("") }
        { (webMaster map (webMaster => <webMaster>{ webMaster }</webMaster>)).getOrElse("") }
        { (pubDate map (pubDate => <pubDate>{ pubDate }</pubDate>)).getOrElse("") }
        { (lastBuildDate map (lastBuildDate => <lastBuildDate>{ lastBuildDate }</lastBuildDate>)).getOrElse("") }
        { (category map (category => category.toXml)).getOrElse("") }
        { (generator map (generator => <generator>{ generator }</generator>)).getOrElse("") }
        { (docs map (docs => <docs>{ docs }</docs>)).getOrElse("") }
        { (cloud map (cloud => <cloud>{ cloud }</cloud>)).getOrElse("") }
        { (ttl map (ttl => <ttl>{ ttl }</ttl>)).getOrElse("") }
        { (image map (image => <image>{ image }</image>)).getOrElse("") }
        { (rating map (rating => <rating>{ rating }</rating>)).getOrElse("") }
        { (textInput map (textInput => <textInput>{ textInput }</textInput>)).getOrElse("") }
        { (skipHours map (skipHours => <skipHours>{ skipHours }</skipHours>)).getOrElse("") }
        { (skipDays map (skipDays => <skipDays>{ skipDays }</skipDays>)).getOrElse("") }
        { (items map (items => items map (item => item.toXml))).getOrElse("") }
      </channel>
    </rss>)
}