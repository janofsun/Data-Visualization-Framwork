package edu.cmu.cs214.hw6.dataPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;

public class TXTPlugin implements DataPlugin {
    // Variable to store the data plugin type name
    private static final String PLUGIN_NAME = "TXTPlugin";
    // Variable to store the description of TXT data plugin
    private static final String TXT_DESC = "This is the data plugin for txt file. You can enter a range of years, like 2017-2021. " + 
    "It accept the data source from user input and parse the raw data";
    // Variable to store the framework object
    private Framework framework;

    /**
     * Getter for the type name of the TXT data plugin
     * @return the type name of TXT data plugin
     */
    @Override
    public String getDataPluginName() { return PLUGIN_NAME; }

    /**
     * Getter for retrieving the data from the TXT data source
     * @param yearKey the year in the TXT data source file name 
     * @return the list of Jounral object data
     */
    @Override
    public List<Journal> getRetrievedJournals(String yearKey) {
        List<String> journalPaths = parseYearKey(yearKey);
        List<Journal> allJournals = new ArrayList<>();
        for (String key : journalPaths) {
            try {
                List<Journal> journals = new ArrayList<>();
                File file = new File(key);
                Scanner scanner = new Scanner(file);
                String date = extractYearFromFilePath(key);
                boolean firstLine = true;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (firstLine) { 
                        firstLine = false;
                        continue;
                    }
                    String[] infos = line.split(",");
                    String sourceTitle = infos[0].trim();
                    float citeScore = Float.valueOf(infos[1].trim());
                    journals.add(new Journal(sourceTitle, date, citeScore));    
                } 
                scanner.close();
                allJournals.addAll(journals);
            } catch(FileNotFoundException e) {
                System.out.println("no such file or invalid input");
                e.printStackTrace();
            }
        }
        return allJournals;
    }
    
    /**
     * Parse the yearkey to get the path of the data source file.
     * @param yearkey the yearkey of the data source file
     * @return a list of the path of the data source file.
     */
    private List<String> parseYearKey(String yearkey) {
        List<String> journalPaths = new ArrayList<>();
        String path = "src/main/resources/data/8-source-results-";

        if (yearkey.contains("-")) {
            String[] years = yearkey.split("-");
            int startYear = Integer.parseInt(years[0]);
            int endYear = Integer.parseInt(years[1]);

            for (int year = startYear; year <= endYear; year++) {
                String key = path + year + ".txt";
                journalPaths.add(key);
            }
        } else {
            int year = Integer.parseInt(yearkey);
            String key = path + year + ".txt";
            journalPaths.add(key);
        }

        return journalPaths;
    }

    /**
     * Extract date(year) from file path (e.g. 2021).
     * @param filePath the path of data source file
     * @return date(year) the published time of this data source file
     */
    private String extractYearFromFilePath(String filePath) {
        int yearStartIndex = filePath.lastIndexOf("-") + 1;
        int yearEndIndex = filePath.lastIndexOf(".");
        
        if (yearStartIndex != -1 && yearEndIndex != -1 && yearEndIndex > yearStartIndex) {
            return filePath.substring(yearStartIndex, yearEndIndex);
        } else {
            return "Unknown";
        }
    }

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
     * Getter for the description of this TXT data plugin
     * @return the string of this TXT data plugin description
     */
    @Override
    public String getDataPluginDescription() {
        return TXT_DESC;
    }
}