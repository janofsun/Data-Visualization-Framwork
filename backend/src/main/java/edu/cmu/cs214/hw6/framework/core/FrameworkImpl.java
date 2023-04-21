package edu.cmu.cs214.hw6.framework.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.HashMap;

import edu.cmu.cs214.hw6.dataPlugin.Journal;

public class FrameworkImpl implements Framework{
    // Variable to store the used data plugins
    private List<DataPlugin> registeredDataPlugins;
    // Variable to store the used display plugins
    private List<DisplayPlugin> registeredDisplayPlugins;
    // Variable to store the current data plugin
    private DataPlugin currentDataPlugin;
    // Variable to store the data attribute key of data plugin, eg.year
    private String dataPluginKey;
    // Variable to store the current display plugin
    private DisplayPlugin currentDisplayPlugin;
    // Variable to store the header text
    private String instruction;
    // Variable to store the result
    private JSONObject result;
    // Variable to store the boolean value if showing the average
    private boolean showAverage;
    // Variable to store the boolean value if showing the standard deviation
    private boolean showStd;
    // Variable to store the current stage of framework
    private int stage;
    // Variable to store the name of framework
    public static final String FRAMEWORK_NAME = "Journal Impact Factor Analytics Framework";

    /**
     * Constructor
     */
    public FrameworkImpl() {
        this.registeredDataPlugins = new ArrayList<>();
        this.registeredDisplayPlugins = new ArrayList<>();
        this.currentDataPlugin = null;
        this.dataPluginKey = null;
        this.currentDisplayPlugin = null;
        this.instruction = "";
        this.result = null;
        this.stage = 0;
        this.showAverage = false;
        this.showStd = false;
    }

    // resets the state of this framework
    public void reset() {
        this.currentDataPlugin = null;
        this.dataPluginKey = null;
        this.currentDisplayPlugin = null;
        this.instruction = "";
        this.result = null;
        this.stage = 0;
        this.showAverage = false;
        this.showStd = false;
    }

    /**
     * Getter for the framework name
     * @return the framework name
     */
    @Override
    public String getFrameworkName() { return FRAMEWORK_NAME; }

    /**
     * Getter for the int value of framework current stage
     * @return the int value of framework current stage
     */
    @Override
    public int getCurrentStage() { return this.stage; }

    /**
     * Getter for the JSONObject of data for displaying
     * @return the JSONObject of data for displaying
     */
    @Override
    public JSONObject getVisualizedData() { return this.result; }

    /**
     * Register for all the data plugins we will use 
     * @param plugins all the data plugins we will use 
     */
    @Override
    public void registerDataPlugins(Iterable<DataPlugin> dataPlugins) {
        for (DataPlugin dataPlugin: dataPlugins) {
            registerDataPlugin(dataPlugin);
        }
    }

    /**
     * Set the state of showing average to true
     * @param b is the boolean value to indicate whether we show average
     */
    @Override
    public void setShowAverage(boolean b) { this.showAverage = b; }

    /**
     * Set the state of showing standard deviation to true
     * @param b is the boolean value to indicate whether we show standard deviation
     */
    @Override
    public void setShowStd(boolean b) { this.showStd = b; }

    /**
     * Getter for boolean value if showing the average
     * @return boolean value if showing the average
     */
    @Override
    public boolean getShowAverage() { return this.showAverage; }

    /**
     * Getter for boolean value if showing the standard deviation
     * @return boolean value if showing the standard deviation
     */
    @Override
    public boolean getShowStd() { return this.showStd; }

    /**
     * Register for all the display plugins we will use 
     * @param plugins all the display plugins we will use 
     */
    @Override
    public void registerDisplayPlugins(Iterable<DisplayPlugin> displayPlugins) {
        for (DisplayPlugin displayPlugin: displayPlugins) {
            registerDisplayPlugin(displayPlugin);
        }
    }

    /**
     * Register a new data plugin with the framework
     * @param plugin the data plugin will be registered
     */
    public void registerDataPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredDataPlugins.add(plugin);
    }

    /**
     * Registers a new display plugin with the framework
     * @param plugin the display plugin will be registered
     */
    public void registerDisplayPlugin(DisplayPlugin plugin) {
        plugin.onRegister(this);
        registeredDisplayPlugins.add(plugin);
    }

    /**
     * Analyzes the impact factor of a list of journals
     * @param journals a list of journals need to be analyzed
     * @return the list of journals with their impact factor analyzed
     */
    @Override
    public List<Journal> analyzeImpact(List<Journal> journals) {
        // Group the journals by their publishers
        Map<String, List<Journal>> groupedByPublisher = new HashMap<>();
        
        for (Journal journal : journals) {
            String title = journal.getTitle();
            if (!groupedByPublisher.containsKey(title)) {
                groupedByPublisher.put(title, new ArrayList<Journal>());
            }
            groupedByPublisher.get(title).add(journal);
        }
    
        // Sort journals by time within each publisher group
        for (List<Journal> publisherJournals : groupedByPublisher.values()) {
            Collections.sort(publisherJournals, new Comparator<Journal>() {
                @Override
                public int compare(Journal j1, Journal j2) {
                    return j1.getTime().compareTo(j2.getTime());
                }
            });
        }
    
        // Combine the sorted journal groups into a single list
        List<Journal> sortedJournals = new ArrayList<>();
        
        if (this.showAverage || this.showStd) {
            // Group journals by publisher and calculate average Cscore
            Map<String, Double> publisherCscoreAvg = new HashMap<>();
            for (List<Journal> publisherJournals : groupedByPublisher.values()) {
                String publisher = publisherJournals.get(0).getTitle();
                double totalScore = 0;
                for (Journal journal : publisherJournals) {
                    totalScore += journal.getCscore();
                }
                double avgScore = totalScore / publisherJournals.size();
                publisherCscoreAvg.put(publisher, avgScore);
                for (Journal journal : publisherJournals) {
                    journal.setCscoreAvg((float)avgScore);
                }
                sortedJournals.add(publisherJournals.get(0));
            }

            if (this.showStd) {
                // Group journals by publisher and calculate Cscore standard deviation
                Map<String, Double> publisherCscoreStd = new HashMap<>();
                for (List<Journal> publisherJournals : groupedByPublisher.values()) {
                    String publisher = publisherJournals.get(0).getTitle();
                    double sumSquaredDeviations = 0;
                    for (Journal journal : publisherJournals) {
                        double deviation = journal.getCscore() - publisherCscoreAvg.get(publisher);
                        sumSquaredDeviations += deviation * deviation;
                    }
                    double stdDeviation = Math.sqrt(sumSquaredDeviations / publisherJournals.size());
                    publisherCscoreStd.put(publisher, stdDeviation);
                    for (Journal journal : publisherJournals) {
                        journal.setCscoreStd((float)stdDeviation);
                    }
                }
            }
        } else {
            for (List<Journal> publisherJournals : groupedByPublisher.values()) {
                sortedJournals.addAll(publisherJournals);
            }
        }
        return sortedJournals;
    }
    
    /**
     * Process data and calculates display data after client provides necessary keywords.
     */
    @Override
    public void displayImpact() {
        if (this.stage < 2) {
            return;
        }

        List<Journal> journals = this.currentDataPlugin.getRetrievedJournals(this.dataPluginKey);
        if (journals.isEmpty()) {
            return;
        }
        journals = analyzeImpact(journals);
        this.result = this.currentDisplayPlugin.getVisualizedData(journals);
    }

    /**
     * Getter for the instruction of this framwork
     * @return the string instruction of this framework
     */
    @Override
    public String getInstruction() {
        return this.instruction;
    }

    /**
     * Setter for the instruction of this framework
     * @param text the instruction will be set with this framework
     */
    @Override
    public void setInstruction(String text) {
        this.instruction = text;
    }

    /**
     * Setter for the current data plugin
     * @param plugin the data plugin will be set as the current data plugin
     */
    @Override
    public void setCurrentDataPlugin(DataPlugin plugin) {
        this.currentDataPlugin = plugin;
        this.stage = 1;
    }

    /**
     * Setter for the data plugin key
     * @param key the key will be set as the data plugin key
     */
    @Override
    public void setDataPluginKey(String key) {
        this.dataPluginKey = key;
    }

    /**
     * Setter for the current display plugin
     * @param plugin the display plugin will be set as the current display plugin
     */
    @Override
    public void setCurrentDisplayPlugin(DisplayPlugin plugin) {
        this.currentDisplayPlugin = plugin;
        this.stage = 2;
    }

    /**
     * Getter for the current data plugin
     * @return the name of the current data plugin
     */
    @Override
    public String getCurrentDataPlugin() {
        if (this.currentDataPlugin == null) { return ""; }
        return this.currentDataPlugin.getDataPluginName();
    }

    /**
     * Getter fro the key of data plugin
     * @return the key of data plugin
     */
    public String getDataPluginKey() {
        return this.dataPluginKey;
    }

    /**
     * Getter for the current display plugin
     * @return the name of the current display plugin
     */
    @Override
    public String getCurrentDisplayPlugin() {
        if (this.currentDisplayPlugin == null) { return ""; }
        return this.currentDisplayPlugin.getDisplayPluginName();
    }

    /**
     * Getter for the result
     * @return the result
     */
    public JSONObject getResult() {
        return this.result;
    }
}
