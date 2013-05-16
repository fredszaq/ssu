package com.tlorrain.ssu.rss

case class RssSource(url:String, title:String) extends Xmllizable {// TODO url
	lazy val toXml = <source url={ url }>{ title }</source>
}