package edu.cmu.cs214.hw6.dataPlugin;

public class Journal {
    // Variable to store the title of a journal
    private String title;
    // Variable to store the published time of  journal
    private String time;
    // Variable to store the impact factor of journal
    private float Cscore;
    // Variable to store the average impactor factor
    private float CscoreAvg;
    // Variable to store the standard deviation impactor factor
    private float CscoreStd;

    /**
     * Constructor of the Journal object
     * @param title the title of a journal
     * @param time the published time of a journal
     * @param Cscore the impact factor of a journal
     */
    public Journal(String title, String time, float Cscore) {
        this.title = title;
        this.time = time;
        this.Cscore = Cscore;
        this.CscoreAvg = Cscore;
        this.CscoreStd = 0;
    }

    /**
     * Getter for the title of the journal
     * @return the title of the journal
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for the title of the journal
     * @param text the title will be set for the journal
     */
    public void setTitle(String text) {
        this.title = text;
    }

    /**
     * Getter for the published time of the journal
     * @return the published time of the journal
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Setter for the published time of the journal
     * @param time the published time will be set for the journal
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Getter for the impact factor of the journal
     * @return the impact factor of the journal
     */
    public float getCscore() {
        return this.Cscore;
    }

    /**
     * Setter for the impact factor of the journal
     * @param score the impact factor of the journal will be set for the journal
     */
    public void setCscore(float score) {
        this.Cscore = score;
    }

    /**
     * Getter for the average of impact factor
     * @return the average of impact factor
     */
    public float getCscoreAvg() {
        return this.CscoreAvg;
    }

    /**
     * Setter for the average of impact factor
     * @param f the average of impact factor will be set
     */
    public void setCscoreAvg(float f) {
        this.CscoreAvg = f;
    }

    /**
     * Getter for the standard deviation of impact factor
     * @return the standard deviation of impact factor
     */
    public float getCscoreStd() {
        return this.CscoreStd;
    }

    /**
     * Setter for the standard deviation of impact factor
     * @param f the standard deviation of impact factor will be set
     */
    public void setCscoreStd(float f) {
        this.CscoreStd = f;
    }

    /**
     * Return the string representation of the Journal object
     * @return the string representation of the Journal object
     */
    @Override
    public String toString() {
        return "title: " + this.title + "; time: " + this.time + "; CiteScore: " + this.Cscore;
    }
}
