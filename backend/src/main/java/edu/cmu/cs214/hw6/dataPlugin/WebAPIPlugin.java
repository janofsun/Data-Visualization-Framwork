package edu.cmu.cs214.hw6.dataPlugin;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WebAPIPlugin implements DataPlugin {
    // Variable to store the data plugin type name
    public static final String PLUGIN_NAME = "WebAPIPlugin";
    // Variable to store the API key of Web api
    public static final String apiKey = "e31bf7b8fed1ba370bd24433aa7f3c52";
    // Variable to store the description of Web API data plugin
    private static final String API_DESC = "This is the data plugin for Scopus Web API data source. You can enter a range of years of the data you want to add, like 2017-2021. " + 
    "It accept the data source from user input and parse the raw data";
    // Variable to store the framework object
    private Framework framework;

    /**
     * Getter for the type name of the CSV data plugin
     * @return the type name of CSV data plugin
     */
    @Override
    public String getDataPluginName() { return PLUGIN_NAME; }
    
    /**
     * Called when the plugin is first registered with the framework, 
     * giving the plugin a chance to perform any initial set up.
     * @param framework the framework instance with which the plugin is registered.
     */
    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }

    /**
     * Getter for retrieving the data from the Web API data source
     * @param yearKey the year in the Web API data source
     * @return the list of Jounral object data
     */
    @Override
    public List<Journal> getRetrievedJournals(String yearKey) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Journal> journals = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.elsevier.com/content/serial/title?" +
                    "issn=1070-664X,0031-9007,2469-9934,0028-0836,0021-8979,1931-9401,0003-6951,1882-0778&field=title," +
                    "citeScoreYearInfo&date="+yearKey+"&view=STANDARD&apiKey=" + apiKey)
                    .addHeader("Accept", "application/xml")
                    .addHeader("X-ELS-APIKey", apiKey)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("Unexpected code " + response);
            }

            String xmlData = response.body().string();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList entries = document.getElementsByTagName("entry");

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);

                String title = entry.getElementsByTagName("dc:title").item(0).getTextContent();
                //System.out.println("Title: " + title);
                Set<String> years = new HashSet<>();
                Map<String, Float> cScoreMap = new HashMap<>();

                NodeList citeScoreList = entry.getElementsByTagName("citeScoreYearInfo");
                for (int j = 0; j < citeScoreList.getLength(); j++) {
                    Element citeScore = (Element) citeScoreList.item(j);
                    String year = citeScore.getAttribute("year");
                    years.add(year);
                    NodeList scoreList = citeScore.getElementsByTagName("citeScore");
                    for (int k = 0; k < scoreList.getLength(); k++) {
                        Element scoreElement = (Element) scoreList.item(k);
                        Float value = Float.parseFloat(scoreElement.getTextContent());
                        cScoreMap.put(year, value);
                        //System.out.println("CiteScore: " + year + ", CiteScore: " + value);
                    }
                }

                for (String year: years) {
                    if (cScoreMap.get(year) != null) {
                        journals.add(new Journal(title, year, cScoreMap.get(year)));
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        System.out.println(journals);
        return journals;
    }

    /**
     * Getter for the description of this Web API data plugin
     * @return the string of this Web API data plugin description
     */
    @Override
    public String getDataPluginDescription() {
        return API_DESC;
    }
}
