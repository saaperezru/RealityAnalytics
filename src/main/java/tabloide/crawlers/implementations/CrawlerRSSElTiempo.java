package tabloide.crawlers.implementations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tabloide.crawlers.Crawler;
import tabloide.crawlers.Extractor;
import tabloide.datamodel.Categories;
import tabloide.datamodel.Document;
import tabloide.datamodel.Source;

/**
 *
 * @author Camilo
 */
public class CrawlerRSSElTiempo implements Crawler {

    private String urlToCrawl;
    final private static List<Extractor> extractors = new ArrayList<>();

    static {
        extractors.add(new ExtractorRSSItemInfo());
    }

    public CrawlerRSSElTiempo(String url) {
        urlToCrawl = url;
    }

    public List<Document> getDocuments() {

        String rawXML = getXML();

        Source source = new Source(ExtractorRSSChannelInfo.getSourceName(rawXML), Categories.PRENSA);
        String[] xmlItemList = ExtractorRSSChannelInfo.getXMLItemList(rawXML);

        ArrayList<Document> documents = new ArrayList<Document>();

        for (String item : xmlItemList) {
            Document doc = new Document(item, source);
            doc.addExtractors(extractors);
            documents.add(doc);
        }

        return documents;
    }

    private String getXML() {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";

        try {
            url = new URL(urlToCrawl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
