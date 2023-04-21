package edu.cmu.cs214.hw6.framework.core;

import edu.cmu.cs214.hw6.dataPlugin.Journal;
import java.util.List;

/**
 * The data plugin interface that plugins use to implement and register data source with the framework.
 */
public interface DataPlugin {

    /**
     * Getter for the type name of the data plugin data source
     * @return the type name of the data plugin data source
     */
    String getDataPluginName();

    /**
     * Getter for the description of the data plugin data source
     * @return the description of the data plugin data source
     */
    String getDataPluginDescription();

    /**
     * Getter for retrievings the info, like title, time and impact factor from the given data source.
     * @param key the data source
     * For the CSV file, the key will be the year of CSV file name
     * For the TXT file, the key will be the year of TXT file name
     * For the Web API, the key will be the year in the api url
     * @return A list of Jounral object data
     */
    List<Journal> getRetrievedJournals(String key);

    /**
     * Called when the plugin is first registered with the framework, 
     * giving the plugin a chance to perform any initial set up.
     * @param framework the framework instance with which the plugin is registered.
     */
    void onRegister(Framework framework);
}