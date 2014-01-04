package tabloide.crawlers.implementations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tabloide.crawlers.Crawler;
import tabloide.datamodel.Categories;
import tabloide.datamodel.Document;
import tabloide.datamodel.Source;

/**
 *
 * @author Camilo
 */
public class CrawlerRSSElTiempo implements Crawler {

    private String urlToCrawl;

    public CrawlerRSSElTiempo(String url) {
        urlToCrawl = url;
    }

    public List<Document> getDocuments() {

        String rawXML = getXML();

        Source source = new Source(ExtractorRSSChannelInfo.getSourceName(rawXML), Categories.PRENSA);
        String[] xmlItemList = ExtractorRSSChannelInfo.getXMLItemList(rawXML);
        
        ArrayList<Document> documents=new ArrayList<Document>();
        
        for(String item:xmlItemList){
            documents.add(new Document(item, source));
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

        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
