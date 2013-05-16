package com.tlorrain.ssu.rss

case class RssCategory(category:String, domain:Option[String] = None) extends Xmllizable {
  lazy val toXml = domain match {
    case None => <category>{ category }</category>
    case Some(domain) => <category domain={domain}>{ category }</category>
  }
}