package com.tlorrain.ssu.rss

case class RssEnclosure(url: String, length: Long, mimeType:String) extends Xmllizable { // TODO, URI, mimetype
  lazy val toXml = <enclosure url={ url } length={ length.toString } type={ mimeType }/>
  
} 