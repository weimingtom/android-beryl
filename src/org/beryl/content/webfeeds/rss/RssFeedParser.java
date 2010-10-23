package org.beryl.content.webfeeds.rss;

import java.io.InputStream;

import org.beryl.content.webfeeds.AbstractXmlFeedConsumer;
import org.beryl.content.webfeeds.WebFeedException;
import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

public class RssFeedParser extends AbstractXmlFeedConsumer {
	static class RssHolder
	{
		final RssChannel Channel = new RssChannel();
		RssItem CurrentItem = new RssItem();
		
		public RssImage getImage()
		{
			return Channel.image;
		}
		public void sealImage()
		{
		}
		public void sealItem()
		{
			Channel.items.add(CurrentItem);
			CurrentItem = new RssItem();
		}
	}
	
	private RootElement assembleHandler(final RssHolder holder)
	{
		RootElement root = new RootElement("rss");
		
		Element channel = root.getChild("channel");
		Element item = channel.getChild("item");
		Element image = channel.getChild("image");
		
		
		channel.getChild("title").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.title = body; }
        });
		channel.getChild("link").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.link = body; }
        });
		channel.getChild("description").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.description = body; }
        });
		channel.getChild("language").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.language = body; }
        });
		channel.getChild("copyright").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.copyright = body; }
        });
		channel.getChild("managingEditor").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.managingEditor = body; }
        });
		channel.getChild("webMaster").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.webMaster = body; }
        });
		channel.getChild("pubDate").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.pubDate = body; }
        });
		channel.getChild("category").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.category = body; }
        });
		channel.getChild("generator").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.generator = body; }
        });
		channel.getChild("docs").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.docs = body; }
        });
		channel.getChild("ttl").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { try { holder.Channel.ttl = Integer.parseInt(body); } catch (Exception e) {} }
        });
		channel.getChild("rating").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.rating = body; }
        });
		channel.getChild("lastBuildDate").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.Channel.lastBuildDate = body; }
        });
		
		item.setEndElementListener(new EndElementListener(){
            public void end() {
                holder.sealItem();
            }
        });
		item.getChild("title").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.title = body; }
        });
		item.getChild("link").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.link = body; }
        });
		item.getChild("description").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.description = body; }
        });
		item.getChild("author").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.author = body; }
        });
		item.getChild("category").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.category = body; }
        });
		item.getChild("comments").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.comments = body; }
        });
		item.getChild("enclosure").setStartElementListener(new StartElementListener(){
			public void start(Attributes attributes) {
				holder.CurrentItem.enclosure_url = attributes.getValue("url");
				try { holder.CurrentItem.enclosure_length = Integer.parseInt(attributes.getValue("length")); } catch (Exception e) {}
				holder.CurrentItem.enclosure_type = attributes.getValue("type");
			}
        });
		item.getChild("guid").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.guid = body; }
        });
		item.getChild("guid").setStartElementListener(new StartElementListener(){
			public void start(Attributes attributes) {
				try { holder.CurrentItem.guid_isPermaLink = Boolean.parseBoolean(attributes.getValue("isPermaLink")); } catch (Exception e) {}
			}
        });
		item.getChild("pubDate").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.pubDate = body; }
        });
		item.getChild("source").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.CurrentItem.source = body; }
        });
		
		
		image.setEndElementListener(new EndElementListener(){
            public void end() {
                holder.sealImage();
            }
        });
		image.getChild("url").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.getImage().url = body; }
        });
		image.getChild("title").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.getImage().title = body; }
        });
		image.getChild("link").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.getImage().link = body; }
        });
		image.getChild("width").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { try { holder.getImage().width = Integer.parseInt(body); } catch (Exception e) {} }
        });
		image.getChild("height").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { try { holder.getImage().height = Integer.parseInt(body); } catch (Exception e) {} }
        });
		image.getChild("description").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) { holder.getImage().description = body; }
        });
		
		return root;
	}

	public boolean parseStream(InputStream is) throws WebFeedException
	{
		final RssHolder holder = new RssHolder();
		RootElement root = assembleHandler(holder);
		
		try {
			Xml.parse(is, Xml.Encoding.UTF_8, root.getContentHandler());
		}
		catch(Exception e) {
			throw new WebFeedException(e);
		}
		
		if(holder.Channel.title != null) {
			return true;
		}
		return false;
	}
}
