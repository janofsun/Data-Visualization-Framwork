# HW6b Academic Journal Impact Factor Analysis and Visualization Framework

**Team name: SevenJobs**

_Team member: Jiahui Zhu_, _Jie Sun_, _Sitong Shang_

This file describes the idea of the framework and how to start and extend it.

![image](https://github.com/CMU-17-214/hw6-analytics-framework-sevenjobs/blob/main/visualization.gif)
___
## Idea of the framework
### Functionality

Our team decided to perform the academic journal impact factor analysis and visualization on different journal types (e.g., Applied Physics Reviews, Nature, Physical Review Letters, etc). 
The impact factor data is sourced from multiple outlets including local files and web APIs. The raw data will be extracted from diverse data plugins and undergo preprocessing according to specific patterns for parsing and preparation.

The processed data will be sent to different display plugins for HTML generation. The result HTML can be rendered to show various charts including histogram charts, bar charts, and time series charts.

### Domain model

The domain model presented below illustrates that our framework consists of two sets of plugins: one for data and one for display. The data plugins are responsible for extracting and parsing data from the source, while the display plugins generate HTML code to create various charts for visualization.
![Alt text](https://github.com/CMU-17-214/hw6-analytics-framework-sevenjobs/blob/main/domain_model.png)


### Flowchart
The flowchart presented below illustrates that our framework follows a fixed data format, accepting input from data plugins and performing data sorting to generate formatted data for display plugins. The framework does not assume responsibility for data preprocessing from various sources or HTML generation for display plugins. This approach ensures that the framework's functionality remains generic and reusable, as it is focused only on input and output types. Moreover, the framework is designed to be versatile enough to accommodate multiple data sources and visualizations.
![Alt text](https://github.com/CMU-17-214/hw6-analytics-framework-sevenjobs/blob/main/flowchart.png)

___
## How to start 

### 1. Start Framework Web GUI
```
cd backend
mvn clean
mvn install
mvn exec:exec
```

### 2. Browser to Web GUI
Enter the following url in a browser, our data visualiztion framework will show up.
`http://localhost:8000`
___
## How to extend 
### Data Plugin

Data plugin should have :
- The unique name of this data plugin.
- A description of this data plugin.
- A string of its input.

For each data plugin, we have a text input which can be a certain year or a range of years and the api key of the Web API. Data plugin needs to implements the interface to provide the data input for user's reference.

Data plugin implements the `getRetrievedJournals()` method to parse and convert the selected data source to a list of `Journal`, which is a formatted data used in the whole framework.

Our sample data plugins are csv file data plugin, txt file data plugin and Web API data plugin.

### Display Plugin
Display plugin should have :
- The unique name of this display plugin.
- A description of this display plugin.

Display plugin implements the `getVisualizedData()` method to transfer the list of `Journal` data into JSONObject that will be used to display the chart.

Our sample data plugins are horizontal bar chart display plugin, time series chart display plugin and Histogram chart display plugin.

