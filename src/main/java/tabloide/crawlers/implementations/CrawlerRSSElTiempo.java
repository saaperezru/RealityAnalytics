package tabloide.crawlers.implementations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tabloide.crawlers.Crawler;
import tabloide.datamodel.Categories;
import tabloide.datamodel.Document;
import tabloide.datamodel.Source;
import org.w3c.dom.NodeList;

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
        try {
            String xmlRaw = getXML();
            
            InputSource is = new InputSource(new StringReader(xmlRaw));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document d = db.parse(is);
            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            
            String sourceName = xpath.evaluate("/rss/channel/title", d);
            Source source=new Source(sourceName, Categories.PRENSA);
            
            XPathExpression expression=xpath.compile("/rss/channel/item");
            NodeList items = (NodeList) expression.evaluate(d,XPathConstants.NODESET);
            
            for(int i=0;i<items.getLength();i++){
                System.out.println("Item "+(i+1)+" = " + items.item(i).getTextContent());
            }
            
            System.out.println("Title= " + sourceName + ";");
            
            
            return null;
        } catch (SAXException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CrawlerRSSElTiempo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
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
