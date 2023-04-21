package edu.cmu.cs214.hw6.framework.core;

import edu.cmu.cs214.hw6.dataPlugin.Journal;
import org.json.JSONObject;

import java.util.List;

/**
 * The display plugin interface that plugins use to implement and register visualization
 * with the framework.
 */
public interface DisplayPlugin {

    /**
     * Getter for the type name of the display plugin.
     * @return the type name of the display plugin.
     */
    String getDisplayPluginName();

    /**
     * Getter for the description of the display plugin
     * @return the description of the display plugin
     */
    String getDisplayPluginDescription();
    /**
     * Calculates display data and formats it as an JSONObject.
     * For example, for bar chart, the format is:
     * {"x":["2021","2022"],"y":[5.1,4.9],"type":"bar"}
     * For histogram chart, the format is:
     * {"x":["2021","2022"],"y":[5.1,4.9],"type":"histogram"}
     * @param data A list of data.
     * @return JSONObject will be displayed on the frontend
     */
    JSONObject getVisualizedData(List<Journal> journal);

    /**
     * Called when the plugin is first registered with the framework, 
     * giving the plugin a chance to perform any initial set up.
     * @param framework the framework instance with which the plugin is registered.
     */
    void onRegister(Framework framework);
}