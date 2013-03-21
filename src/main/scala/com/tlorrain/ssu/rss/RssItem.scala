package com.tlorrain.ssu.rss

case class RssItem(title: String,
  description: String,
  link: Option[String] = None, //TODO URI
  author: Option[String] = None, // TODO email
  category: Option[String] = None, // TODO optional attribute "domain"
  comments: Option[String] = None, // TODO url
  enclosure: Option[Tuple3[String, Int, String]] = None, // TODO proper type ?
  guid: Option[String] = None,
  pubDate: Option[Long] = None, // TODO date
  source: Option[Tuple2[String, String]] = None // TODO proper type
  ) {

  def toXml = <item>
                <title>{ title }</title>
                <description>{ description }</description>
              </item>

}