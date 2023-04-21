package edu.cmu.cs214.hw6.dataPlugin;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;

public class WebAPIPluginTest {
    private DataPlugin plugin;
    @Before
    public void setUp() {
        plugin = new WebAPIPlugin();
    }

    @Test
    public void testGetDataPluginName() {
        assertEquals("WebAPIPlugin", plugin.getDataPluginName());
    }

    @Test
    public void testGetRetrievedData() {
        assertEquals(plugin.getRetrievedJournals("2018").size(),8);
        assertEquals(plugin.getRetrievedJournals("2020-2021").size(),16);
    }
}
