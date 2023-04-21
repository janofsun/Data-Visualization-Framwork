package edu.cmu.cs214.hw6;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;
import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;
import edu.cmu.cs214.hw6.framework.GUI.FrameworkState;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private static final int PORT = 8000;
    private Framework framework;
    private List<DataPlugin> dataPlugins;
    private List<DisplayPlugin> displayPlugins;
    private Template template;

    public App() throws IOException {
        super(PORT);

        this.framework = new FrameworkImpl();
        this.dataPlugins = loadDataPlugins();
        this.displayPlugins = loadDisplayPlugins();
        framework.registerDataPlugins(dataPlugins);
        framework.registerDisplayPlugins(displayPlugins);

        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("eq", ConditionalHelpers.eq);

        this.template = handlebars.compile("index");

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Running!");
    }

    @Override
    public  Response serve(IHTTPSession session) {
        try {
            String uri = session.getUri();
            Map<String, String> params = session.getParms();
            if (uri.equals("/")) {
                framework.reset();
            } else if (uri.equals("/dataplugin")) {
                this.framework.setCurrentDataPlugin(dataPlugins.get(Integer.parseInt(params.get("i"))));
            } else if (uri.equals("/visualplugin")) {
                this.framework.setCurrentDisplayPlugin(displayPlugins.get(Integer.parseInt(params.get("display_type"))));
            } else if (uri.equals("/getparams")) {
                String key = params.get("from")+"-"+params.get("to");
                framework.setDataPluginKey(key);
                if (params.get("summary0") != null) {
                    framework.setShowAverage(true);
                } else {
                    framework.setShowAverage(false);
                }
                if (params.get("summary1") != null) {
                    framework.setShowStd(true);
                } else {
                    framework.setShowStd(false);
                }
                framework.displayImpact();
            }
            FrameworkState frameworkState = FrameworkState.forFramework(framework);
    
            String HTML = this.template.apply(frameworkState);
            return newFixedLengthResponse(HTML);
    
            // return newFixedLengthResponse(frameworkState.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse("Internal server error");
        }
    }

    /**
     * Load data plugins listed in META-INF/services/...
     * @return List of instantiated data plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin p: plugins) {
            System.out.println("Loaded data plugin " + p.getDataPluginName());
            result.add(p);
        }
        return result;
    }

    /**
     * Load display plugins listed in META-INF/services/...
     * @return List of instantiated display plugins
     */
    private static List<DisplayPlugin> loadDisplayPlugins() {
        ServiceLoader<DisplayPlugin> plugins = ServiceLoader.load(DisplayPlugin.class);
        List<DisplayPlugin> result = new ArrayList<>();
        for (DisplayPlugin p: plugins) {
            System.out.println("Loaded display plugin " + p.getDisplayPluginName());
            result.add(p);
        }
        return result;
    }
}