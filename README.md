# Scala Syndication Utils (SSU)
SSU is a simple and fluent Scala library to generate RSS feeds.
## Creating a simple RSS feed

```scala
val items = ("title1", "content1", new URL("http://link1.com"), "author1@site1.com") ::
            ("title2", "content2", new URL("http://link2.com"), "author2@site2.com") ::
            Nil

val rss = (
        RssFeed("some title", new URL("http://someli.nk"), "some description")
            withWebMaster "somebody@somewhere.com"
            withItems (items map (item => RssItem
                withTitle item._1 
                withDescription item._2
                withLink item._3
                withAuthor item._4))).toXml
```
## Validation at compilation
Code with no enough information to produce a valid RSS feed will not compile, for example the following code will not compile because an item must have a title or a description

```scala
val rss = (
        RssFeed("some title", new URL("http://someli.nk"), "some description")
            withItems (items map (item => RssItem // error
                withLink item._3
                withAuthor item._4))).toXml
```
