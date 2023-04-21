package edu.cmu.cs214.hw6.framework.GUI;

import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.Framework;

public final class FrameworkState {
    // Variable to store the name of framework
    private final String name;
    // Variable to store the instruction info of framework
    private final String info;
    // Variable to store the current data plugin name 
    private final String currentDataPlugin;
    // Variable to store the current display plugin name 
    private final String currentDisplayPlugin;
    // Variable to store the JSONObject of visualized Data
    private final JSONObject visualizedData;
    // Variable to store the stage of framework
    private final int stage;

    /**
     * Constructor 
     * @param frameWorkName the name of framework
     * @param instruction the instruction info of framework
     * @param currentDataPlugin the current data plugin name 
     * @param currentDisplayPlugins the current display plugin name 
     * @param visualizedData the JSONObject of visualized Data
     * @param stage the stage of framework
     */
    private FrameworkState(String frameWorkName, String instruction, String currentDataPlugin, String currentDisplayPlugins, JSONObject visualizedData, int stage) {
        this.name = frameWorkName;
        this.info = instruction;
        this.currentDataPlugin = currentDataPlugin;
        this.currentDisplayPlugin = currentDisplayPlugins;
        this.visualizedData = visualizedData;
        this.stage = stage;
    }

    /**
     * To create new framework state
     * @param framework the framework
     * @return the new framework state
     */
    public static FrameworkState forFramework(Framework framework) {
        String instruction = framework.getInstruction();
        String frameworkName = framework.getFrameworkName();
        String dataPlugin = framework.getCurrentDataPlugin();
        String displayPlugin = framework.getCurrentDisplayPlugin();
        JSONObject visualizedData = framework.getVisualizedData();
        int stage = framework.getCurrentStage();
        System.out.println("instruction:"+instruction+"name:"+frameworkName+" data:"+dataPlugin+" display:"+displayPlugin+" stage:"+stage+ "visualized "+visualizedData);
        return new FrameworkState(frameworkName, instruction, dataPlugin, displayPlugin, visualizedData, stage);

    }

    /**
     * Getter for the framework name
     * @return the framework name
     */
    public String getFrameWorkName() {
        return this.name;
    }

    /**
     * Getter for the int value of framework current stage
     * @return the int value of framework current stage
     */
    public int getStage() {
        return this.stage;
    }

    /**
     * Getter for the instruction of this framwork
     * @return the string instruction of this framework
     */
    public String getInfo() { return this.info;}

    /**
     * Getter for the current data plugin
     * @return the name of the current data plugin
     */
    public String getCurrentDataPlugin() {
        return this.currentDataPlugin;
    }

    /**
     * Getter fro the key of data plugin
     * @return the key of data plugin
     */

    public String getCurrentDisplayPlugin() {
        return this.currentDisplayPlugin;
    }
    
    /**
     * Getter for the JSONObject of visualized Data
     * @return the JSONObject of visualized Data
     */
    public JSONObject getVisualizedData() {
        return this.visualizedData;
    }
    /**
     * Override the toString method.
     * @return the string of this framework state
     */
    @Override
    public String toString() {
        return "FrameworkState [name=" + this.name + ", info=" + this.info + ", currentDataPlugin=" + this.currentDataPlugin
                + ", currentDisplayPlugins=" + this.currentDisplayPlugin + ", visualizedData=" + this.visualizedData + ", stage="
                + this.stage + "]";
    }
}