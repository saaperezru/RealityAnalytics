package tabloide.crawlers.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tabloide.crawlers.Crawler;
import tabloide.datamodel.Categories;
import tabloide.datamodel.Document;
import tabloide.datamodel.Source;
import tabloide.helpers.WebHelpers;

public class CrawlerCorteConstitucional implements Crawler {

	private static String GET_URL = "http://www.corteconstitucional.gov.co/relatoria/tematico.php?sql=";
	private static String BASE_URL = "http://www.corteconstitucional.gov.co/";

	private String query;

	static Source CorteConstitucionalSource = new Source("CorteConstitucional",
			Categories.JUDICIAL);

	public CrawlerCorteConstitucional(String query) {
		this.query = query;
	}

	private String getQuery(String query, int page) throws Exception {
		String finalUrl = GET_URL + StringEscapeUtils.escapeEcmaScript(query);
		finalUrl = finalUrl.replace("\\u00", "%");
		return WebHelpers.getUrl(finalUrl + "&pg=" + Integer.toString(page));
	}

	private String getQuery(String query) throws Exception {
		String finalUrl = GET_URL + StringEscapeUtils.escapeEcmaScript(query);
		finalUrl = finalUrl.replace("\\u00", "%");
		return WebHelpers.getUrl(finalUrl);
	}

	private List<Document> extractDocuments(String content) throws Exception {
		ArrayList<Document> docs = new ArrayList<Document>();
		org.jsoup.nodes.Document doc = Jsoup.parse(content);
		Elements elems = doc.select("#left tr");
		for (Element e : elems) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("summary", e.html());
			for(Element link : e.select("a")){
				map.put(link.text(), WebHelpers.getUrl(BASE_URL + link.attr("href")));
			}
			docs.add(new Document(map, CorteConstitucionalSource));
		}

		return docs;
	}

	private static final String REGISTERS_REGEX = ".*Total de Registros --> (\\d+)<br.*";
	private static final String TABLE_REGEX = ".*<table width='100%'   >.*</table>.*";

	// private static final String TABLE_REGEX =
	// ".*<table width='100%'   >(<tr\\s+id='over'\\s*><td\\s+class='filacolor\\d'\\s*><font\\s+face='Arial'\\s+size='\\d+'>[^<]+<A\\s+HREF=[^>]+>[^<]+</A>[^<]+</font></td></tr>)+</table>.*";

	public List<Document> getDocuments() throws Exception {
		String content = getQuery(query);
		Matcher m = Pattern.compile(REGISTERS_REGEX, Pattern.DOTALL).matcher(
				content);

		int results;
		if (m.matches()) {
			results = Integer.parseInt(m.group(1));
			System.out.println(results);
		} else {
			throw new Exception(
					"There was a problem matching the regexes on the query "
							+ query + " on the CorteConstitucional");
		}

		List<Document> docs = new ArrayList<Document>();
		int page = 0;
		while (docs.size() < results) {
			content = getQuery(query, page);

			m = Pattern.compile(TABLE_REGEX, Pattern.DOTALL).matcher(content);
			if (m.matches()) {
				docs.addAll(this.extractDocuments(content));
				System.out.println("Parsed " + docs.size() + "/" + results
						+ " so far.");
			} else {
				throw new Exception(
						"There was a problem matching the regexes on the query "
								+ query + " on the CorteConstitucional");
			}
			page++;
		}

		return docs;
	}

	public static void main(String[] args) throws Exception {

		CrawlerCorteConstitucional crawler = new CrawlerCorteConstitucional(
				"corrupción");
		for(Document d : crawler.getDocuments()){
			System.out.println(d.getContent());
		};
	}

}
