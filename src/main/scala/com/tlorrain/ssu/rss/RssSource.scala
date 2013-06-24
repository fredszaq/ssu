package com.tlorrain.ssu.rss

import java.net.URL

case class RssSource(url:URL, title:String) extends Xmllizable {
	lazy val toXml = <source url={ url.toString() }>{ title }</source>
}