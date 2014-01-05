package tabloide.crawlers.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tabloide.crawlers.Extractor;
import tabloide.datamodel.Document;

/**
 *
 * @author Camilo
 */
public class ExtractorRSSItemInfo implements Extractor {

    private ArrayList<String> propertiesNames = null;

    
    public List<String> getPropertiesNames() {
        if (propertiesNames == null) {

            propertiesNames = new ArrayList<String>();
            propertiesNames.add("title");
            propertiesNames.add("id");
            propertiesNames.add("section");
            propertiesNames.add("link");
            propertiesNames.add("pubDate");
            propertiesNames.add("content:encoded");
        }
        return propertiesNames;
    }

    
    public Map<String, Object> getAllProperties(Document doc) {

        Map<String, Object> allProperties = new HashMap<String,Object>();
        String rawdoc = doc.getContent();
        List<String> properties = getPropertiesNames();

        for (String propertyName : properties) {
            String propertyValue = org.apache.commons.lang3.StringUtils.substringsBetween(rawdoc, "<" + propertyName + ">", "</" + propertyName + ">")[0];
            propertyValue = org.apache.commons.lang3.StringEscapeUtils.escapeXml(propertyValue);
            
            allProperties.put(propertyName, propertyValue);
        }

        return allProperties;
    }

    
    public Object getProperty(String name, Document doc) {
        String rawdoc = doc.getContent();
        String[] titles = org.apache.commons.lang3.StringUtils.substringsBetween(rawdoc, "<" + name + ">", "</" + name + ">");
        return titles[0];
    }
}
