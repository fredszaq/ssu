package com.tlorrain.ssu.rss;

public class JavaRssUtils {
	
	public static void main(String args[]) {
		RssItem<WithTitleOrDescription> item= rssItem().withAuthor("toto").withTitle("titi");
		System.out.println(item.author());
	}
	
	@SuppressWarnings("unchecked")
	public static RssItem<WithoutTitleOrDescription> rssItem() {
		return RssItem$.MODULE$;
	}

}
