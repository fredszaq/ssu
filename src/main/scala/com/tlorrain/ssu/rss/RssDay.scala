package com.tlorrain.ssu.rss

sealed trait RssDay {
	val name=getClass().getSimpleName().replace("$", "") //remove extra $ at the end of object name
}

object Monday extends RssDay
object Tuesday extends RssDay
object Wednesday extends RssDay
object Thursday extends RssDay
object Friday extends RssDay
object Saturday extends RssDay
object Sunday extends RssDay
