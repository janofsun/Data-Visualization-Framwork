package edu.cmu.cs214.hw6.displayPlugin;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.cs214.hw6.dataPlugin.Journal;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;

public class BarPlugin implements DisplayPlugin {
    // Variable to store the display plugin type name
    private static final String PLUGIN_NAME = "BarChart";
    private Framework framework;
    // Variable to store the JSONObject of the data results
    private JSONObject result;
    // Variable to store the description of Bar display plugin
    private static final String BAR_DESC = "This is the display plugin for horizontal bar chart. " + 
    "The y axis represents the different published years of the same journal, and the x axis represents the corresponding impact factor.";

    /**
     * Generate the JSONObject of data for displaying
     * @param journals the list of data
     * @return JSONObject will be displayed on the frontend
     */
    @Override
    public JSONObject getVisualizedData(List<Journal> journals) {
        result = new JSONObject();
        JSONArray xValues = new JSONArray();
        JSONArray yValues = new JSONArray();
        
        if (this.framework == null || (!this.framework.getShowAverage() && !this.framework.getShowStd())) {
            JSONArray titles = new JSONArray();
            for (Journal journal : journals) {
                xValues.put(journal.getTime());
                yValues.put(journal.getCscore());
                titles.put(journal.getTitle());
            }
            result.put("title", titles);

        } else if (this.framework.getShowAverage()) {
            JSONArray titles = new JSONArray();
            for (Journal journal : journals) {
                xValues.put("");
                yValues.put(journal.getCscoreAvg());
                titles.put(journal.getTitle());
            }
            result.put("title", titles);
            
        } else {
            JSONArray titles = new JSONArray();
            for (Journal journal : journals) {
                xValues.put("");
                yValues.put(journal.getCscoreStd());
                titles.put(journal.getTitle());
            }
            result.put("title", titles);
        }
        result.put("x", xValues);
        result.put("y", yValues);
        result.put("type", "bar");
        return result;
    }

    /**
     * Getter for the type name of the display plugin.
     * @return the type name of the display plugin.
     */
    @Override
    public String getDisplayPluginName() {
        return PLUGIN_NAME;
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
     * Getter for the description of this Horizontal Bar display plugin
     * @return the string of this Horizontal Bar display plugin description
     */
    @Override
    public String getDisplayPluginDescription() {
        return BAR_DESC;
    }
    
}

