package edu.cmu.cs214.hw6.displayPlugin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.dataPlugin.Journal;
import edu.cmu.cs214.hw6.framework.core.DataPlugin;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

public class TimePluginTest {
    private TimePlugin timeplugin;
    private DataPlugin dataplugin;
    private String key;
    private List<Journal> list;

    @Before
    public void setUp() {
        timeplugin = new TimePlugin();
        dataplugin = mock(DataPlugin.class);
        key = "2017-2021";
        list = new ArrayList<>();
        list.add(new Journal("title","time", 0));
    }

    @Test
    public void testGetDataPluginName() {
        assertEquals("LineChart", timeplugin.getDisplayPluginName());
    }

    @Test
    public void testGetRetrievedJounrals() {
        when(dataplugin.getRetrievedJournals(key)).thenReturn(list);
        List<Journal> result = dataplugin.getRetrievedJournals(key);
        JSONObject displayResult = timeplugin.getVisualizedData(result);
        assertEquals(4, displayResult.length());
    }
}
