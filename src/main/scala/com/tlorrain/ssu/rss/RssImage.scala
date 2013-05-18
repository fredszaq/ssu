package com.tlorrain.ssu.rss

sealed trait ImageUrlValidity
abstract class WithUrl extends ImageUrlValidity
abstract class WithoutUrl extends ImageUrlValidity

sealed trait ImageTitleValidity
sealed trait WithTitle extends ImageTitleValidity
sealed trait WithoutTitle extends ImageTitleValidity

object RssImage extends RssImage[WithoutUrl,WithoutTitle] (None,None,None,None,None,None)

class RssImage[UrlValidity <: ImageUrlValidity, TitleValidity <: ImageTitleValidity] private (val url: Option[String] = None,
  val title: Option[String] = None,
  val link: Option[String] = None,
  val width: Option[Int] = None,
  val height: Option[Int] = None,
  val description: Option[String] = None) extends Xmllizable {

  lazy val effectiveLink = link match {
    case None => url
    case _ => link
  }

  private def copy[UrlValidity <: ImageUrlValidity, TitleValidity <: ImageTitleValidity](url: Option[String] = url,
    title: Option[String] = title,
    link: Option[String] = link,
    width: Option[Int] = width,
    height: Option[Int] = height,
    description: Option[String] = description) = new RssImage[UrlValidity, TitleValidity](url, title, link, width, height, description)

  def withUrl(url: String) = copy[WithUrl, TitleValidity](url = Some(url))

  def withTitle(title: String) = copy[UrlValidity, WithTitle](title = Some(title))

  def withLink(link: String) = copy[UrlValidity, TitleValidity](link = Some(link))

  def withWidth(width: Option[Int]) = copy[UrlValidity, TitleValidity](width = width)
  def withWidth(width: Int) = copy[UrlValidity, TitleValidity](width = Some(width))

  def withHeight(height: Option[Int]) = copy[UrlValidity, TitleValidity](height = height)
  def withHeight(height: Int) = copy[UrlValidity, TitleValidity](height = Some(height))

  def withDescription(description: Option[String]) = copy[UrlValidity, TitleValidity](description = description)
  def withDescription(description: String) = copy[UrlValidity, TitleValidity](description = Some(description))

  lazy val toXml = <image>
                     {
                       (List(
                         (title map (title => <title>{ title }</title>)),
                         (url map (url => <url>{ url }</url>)),
                         (effectiveLink map (link => <link>{ link }</link>)),
                         (width map (width => <width>{ width }</width>)),
                         (height map (height => <height>{ height }</height>)),
                         (description map (description => <description>{ description }</description>)))
                         map (elem => elem.getOrElse("")))
                     }
                   </image>

}