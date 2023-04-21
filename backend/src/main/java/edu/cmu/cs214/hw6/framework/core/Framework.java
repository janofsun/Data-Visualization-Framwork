package edu.cmu.cs214.hw6.framework.core;

import java.util.List;
import org.json.JSONObject;
import edu.cmu.cs214.hw6.dataPlugin.Journal;

// The framework interface that performs journal impact factor performance analysis and display
public interface Framework {

    /**
     * Analyzes the impact factor of a list of journals
     * @param journals a list of journals need to be analyzed
     * @return the list of journals with their impact factor analyzed
     */
    List<Journal> analyzeImpact(List<Journal> journals);

    /**
     * Displays the impact factor of the data provided by the given plugin(using the key)
     */
    void displayImpact();

    // resets the state of this framework
    void reset();
    
    /**
     * Getter for the header text of this framwork
     * @return header text of this framework
     */
    String getFrameworkName();

    /**
     * Register for all the data plugins we will use 
     * @param plugins all the data plugins we will use 
     */
    void registerDataPlugins(Iterable<DataPlugin> plugins);

    /**
     * Register for all the display plugins we will use 
     * @param plugins all the display plugins we will use 
     */
    void registerDisplayPlugins(Iterable<DisplayPlugin> plugins);

    /**
     * Setter for the current data plugin for this framework
     * @param plugin the data plugin will be set for this framework
     */
    void setCurrentDataPlugin(DataPlugin plugin);

    /**
     * Setter for the current display plugin for this framework
     * @param plugin the display plugin will be set for this framework
     */
    void setCurrentDisplayPlugin(DisplayPlugin plugin);

    /**
     * Setter for the year key will pass to the data plugin
     * @param dataKey the year key will pass to the data plugin
     */
    void setDataPluginKey(String dataKey);

    /**
     * Set the state of showing average to true
     * @param b is the boolean value to indicate whether we show average
     */
    void setShowAverage(boolean b);

    /**
     * Set the state of showing standard deviation to true
     * @param b is the boolean value to indicate whether we show standard deviation
     */
    void setShowStd(boolean b);

    /**
     * Getter for boolean value if showing the average
     * @return boolean value if showing the average
     */
    boolean getShowAverage();

    /**
     * Getter for boolean value if showing the standard deviation
     * @return boolean value if showing the standard deviation
     */
    boolean getShowStd();

    /**
     * Getter for the current data plugin name
     * @return the current data plugin name
     */
    String getCurrentDataPlugin();
    
    /**
     * Getter for the current display plugin name
     * @return the current display plugin name
     */
    String getCurrentDisplayPlugin();

    /**
     * Getter for the JSONObject of data for displaying
     * @return the JSONObject of data for displaying
     */
    JSONObject getVisualizedData();

    /**
     * Getter for the current stage of this framework
     * stage 0 means to choose the wanted data plugin
     * stage 1 means to choose the wanted display plugin
     * stage 2 means to choose the wanted data attribute key. eg. year range
     * @return the int value of current stage
     */
    int getCurrentStage();

    /**
     * Getter for the instruction of this framework
     * @return the string instruction of this framework
     */
    String getInstruction();

    /**
     * Setter for the instruction of this framework
     * @param instruction the instruction will be set with this framework
     */
    void setInstruction(String instruction);
}
