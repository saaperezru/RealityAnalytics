package tabloide.crawlers.implementations;

/**
 *
 * @author Camilo
 */
public class ExtractorRSSChannelInfo {

    public static String getSourceName(String rawXML) {
        String[] titles = org.apache.commons.lang3.StringUtils.substringsBetween(rawXML, "<title>", "</title>");
        return titles[1];
    }

    public static String[] getXMLItemList(String rawXML) {
        return org.apache.commons.lang3.StringUtils.substringsBetween(rawXML, "<item>", "</item>");
    }
}
