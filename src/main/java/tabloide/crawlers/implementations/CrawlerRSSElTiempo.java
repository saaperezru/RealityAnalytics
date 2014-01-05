package tabloide.crawlers.implementations;

import java.util.ArrayList;
import java.util.List;

import tabloide.crawlers.Crawler;
import tabloide.datamodel.Categories;
import tabloide.datamodel.Document;
import tabloide.datamodel.Source;
import tabloide.helpers.WebHelpers;

/**
 * 
 * @author Camilo
 */
public class CrawlerRSSElTiempo implements Crawler {

	private String urlToCrawl;

	public CrawlerRSSElTiempo(String url) {
		urlToCrawl = url;
	}

	public List<Document> getDocuments() throws Exception {

		String rawXML = WebHelpers.getUrl(urlToCrawl);

		Source source = new Source(
				ExtractorRSSChannelInfo.getSourceName(rawXML),
				Categories.PRENSA);
		String[] xmlItemList = ExtractorRSSChannelInfo.getXMLItemList(rawXML);

		ArrayList<Document> documents = new ArrayList<Document>();

		for (String item : xmlItemList) {
			documents.add(new Document(item, source));
		}

		return documents;
	}
}
