package com.tlorrain.ssu.rss

import java.text.SimpleDateFormat
import java.util.Date

trait Xmllizable {
  def toXml:scala.xml.Node
  
  def formatDateRFC822(date:Option[Long]) = date map (date => new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", java.util.Locale.US).format(new Date(date)))

}