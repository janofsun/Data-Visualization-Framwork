package edu.cmu.cs214.hw6.displayPlugin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.dataPlugin.Journal;
import edu.cmu.cs214.hw6.dataPlugin.TXTPlugin;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

public class HistogramPluginTest {
    private HistogramPlugin histogramPlugin;
    private TXTPlugin txtPlugin;
    private String key;
    private List<Journal> list;

    @Before
    public void setUp() {
        histogramPlugin = new HistogramPlugin();
        txtPlugin = mock(TXTPlugin.class);
        key = "2021";
        list = new ArrayList<>();
        list.add(new Journal("title","time", 0));
    }

    @Test
    public void testGetDataPluginName() {
        assertEquals("HistogramChart", histogramPlugin.getDisplayPluginName());
    }

    @Test
    public void testGetRetrievedJounrals() {
        when(txtPlugin.getRetrievedJournals(key)).thenReturn(list);
        List<Journal> result = txtPlugin.getRetrievedJournals(key);
        JSONObject displayResult = histogramPlugin.getVisualizedData(result);
        assertEquals(5, displayResult.length());
    }
}
