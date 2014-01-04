package tabloide.crawlers;

import tabloide.datamodel.Document;
import java.util.List;

public interface Crawler {

    public List<Document> getDocuments();
}
