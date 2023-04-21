# HW6

**Team name: SevenJobs**

_Team member: Jiahui Zhu_, _Jie Sun_, _Sitong Shang_

## Domain

The purpose of this framework is to analyze and visualize the impact factors of academic journal papers over time and the type of different journals. It processes data related to journal papers and their impact factors from various sources (provided by data plugins) and presents the results using multiple visualization techniques (enabled by visualization plugins). The framework offers reusability, as it handles the data analysis and visualization components, allowing the integration of existing visualization or data plugins.

Data plugins supply a list of journal paper title information, including impact factors, publication years, and any additional metadata, which may include:
1. CSV plugin: Input a CSV file and extract data, including journal paper title information, impact factors, publication years, and any additional metadata.
2. TXT plugin:  This plugin can parse plain text files containing structured information, like journal paper title information, impact factors, publication years and so on.
3. Web API plugin: This plugin connects to external Web APIs, such as academic databases, to fetch journal paper information. It can be customized to work with various APIs by implementing specific API requests and response parsing methods.

The framework can also provide a map with given String topics as keys and a list of `Journal` object about paper title, impact factors, and publication years as values to visualization plugins. It can also offer aggregated data, such as averaging impact factors for journal titles or calculating the impact factor standard deviation by journal titles. Visualization plugins could include:
1. Time-series graph: Display the change in impact factors of journal papers over the different published time, revealing trends in a given research domain or publisher.
2. Histogram: Visualize the distribution of impact factors for different journal types, with the y-axis representing the impact factor and the x-axis representing various journal titles. This visualization provides insights into the relative performance of different research domains based on their impact factors.
3. Horizontal Bar Chart: Illustrate the impact factors across different publication years, offering a high-level overview of the academic landscape and enabling comparisons between different time periods.

Researchers can gain valuable insights into the performance and impact of academic journal papers, informing decision-making and strategic planning.
___
## Generality vs specificity

The framework is designed to be highly adaptable, accommodating a wide range of data and visualization plugins. 

Domain Engineering:

The generality of the framework lies in its ability to interface with numerous data plugins, such as sourcing information from CSV files. These data plugins pass information in `Journal` object format to the framework, which in turn is compatible with various visualization plugins, offering support for time-series graphs, bar charts, and histograms.

The specificity of the framework is its primary focus on the analysis of content, utilizing side information provided by the plugins to support the visualization of processed results. The framework extracts the text information from the data received from data plugins, performs analysis, and subsequently provides the necessary data components for visualization based on frontend requirements or commands.

Key Abstractions:
+ Data Plugins: Handle data retrieval and preprocessing, providing the framework with structured information on journal paper titles, their impact factors, their published years and associated metadata.
+ Visualization Plugins: Enable various visualization techniques to display the processed data in a meaningful and insightful manner.

Reusable Functionality:

​​The reusability of the framework is evident in its ability to handle content analysis independently. It processes a list of Strings in a specific format from different plugins. The visualization of results is left to the user's discretion. 

Potential Flexibility of Plugins:
+ Data Plugins: Can be extended to support a variety of data sources, such as APIs, databases, or other file formats, ensuring compatibility with diverse data types and structures.
+ Visualization Plugins: Can be expanded to include new visualization techniques, allowing users to explore and discover insights from the analyzed data in innovative ways.
___
## Project structure 

### *Planned project structure*
We will have a `hw6` package, which contains the following folders and files.

```java
/framework
    /core
        Framework.java
        FrameworkImpl.java
        DataPlugin.java
    /gui
        DisplayPlugin.java
/plugin
    /dataplugin
        /CSVPlugin.java
        /TXTPlugin.java
        /WebAPIPlugin.java
    /dsiplayplugin
        /TimePlugin.java
        /HistogramPlugin.java
        /BarPlugin.java 
App.java
```

### *Implemented project structure*
We will have a `hw6` package, which contains the following folders and files.

```java
/framework
    /core
        Framework.java
        FrameworkImpl.java
        DataPlugin.java
        DisplayPlugin.java
    /gui
        FrameworkState.java
/plugin
    /dataplugin
        /Journal.java
        /CSVPlugin.java
        /TXTPlugin.java
        /WebAPIPlugin.java
    /dsiplayplugin
        /TimePlugin.java
        /HistogramPlugin.java
        /BarPlugin.java 
App.java
```

### *Key data structures*
DataEntry`Journal`: A class representing a single journal paper's information, including source titles, impact factors, publication years, average and standard deviation. The structure is organized as follows: `SourceTitle#~#ImpactFactor#~#PublicationYear#~#Average#~#StandardDeviation`

### *Plugin loading mechanism*
The plugins are loaded using the ServiceLoader mechanism, which loads the plugins listed in the file under the META-INF/services folder in resources. This is done within the `loadDataPlugins` `loadDisplayPlugins` method in the App class, which returns a list of loaded plugins. The resulting list is then used to register the plugins with the framework. By utilizing the ServiceLoader, our framework project can dynamically discover and load available plugins at runtime, enhancing its modularity and extensibility.
___
## Plugin interfaces
![Alt text](https://github.com/CMU-17-214/hw6-analytics-framework-sevenjobs/blob/main/flowchart.png)

### *Data Plugin*
1. String getDataPluginName(): Get the name of the data plugin;
2. String getDataPluginDescription(): Get the description of the data plugin;
3. void onRegister(Framework framework): Called when the plugin is first registered with the framework, giving the plugin a chance to perform any initial set up.
4. List<Journal> getRetrievedJournals(String key): Retrieve the journal data based on specified search parameters, like the year range of the journal published year.

### *Display Plugin*
1. String getDisplayPluginName(): Get the name of the display plugin;
2. String getDisplayPluginDescription(): Get the description of the display plugin;
3. void onRegister(Framework f): Called when the plugin is first registered with the framework, giving the plugin a chance to perform any initial set up.
4. `JSONObject` getVisualizedData(List<Journal> journal): Generate a JSONObject for visualization based on the provided `Journal` data.


### *Data Structures exchanged between plugins and framework*

1. DataEntry`Journal`: A `Journal` class that represents a single data entry containing source titles, impact factors, publication years, average and standard deviation.
2. List<Journal>: A list of DataEntry objects, representing the data loaded and processed by data plugins, which is then passed to framework and visualization plugins for generating visualizations.
3. Map<String, List<Journal>>: A map containing journal source title and the list of `Journal` objects , used by framework to group the journals by their source title and then do the sorting or calculating the average or standard deviation on specific criteria.
4. Map<String, Double>: A map containing journal source title and the average or standard deviation in the double format, allowing display the average or standard deviation during visualizations.