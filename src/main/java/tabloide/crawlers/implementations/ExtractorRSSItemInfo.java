package tabloide.crawlers.implementations;

import java.util.ArrayList;
import java.util.Map;
import tabloide.crawlers.Extractor;
import tabloide.datamodel.Document;

/**
 *
 * @author Camilo
 */
public class ExtractorRSSItemInfo implements Extractor{

    @Override
    public ArrayList<String> getPropertiesNames() {
        return null;
    }

    @Override
    public Map<String, Object> getAllProperties(Document doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getProperty(String name, Document doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
