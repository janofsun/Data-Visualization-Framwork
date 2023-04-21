package edu.cmu.cs214.hw6.dataPlugin;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TXTPluginTest {
    private TXTPlugin txtPlugin;
    private String key, keyRange;

    @Before
    public void setUp() {
        txtPlugin = new TXTPlugin();
        key = "2021";
        keyRange = "2017-2021";
    }

    @Test
    public void testGetDataPluginName() {
        assertEquals("TXTPlugin", txtPlugin.getDataPluginName());
    }

    @Test
    public void testGetRetrievedJounrals() {
        List<Journal> result = txtPlugin.getRetrievedJournals(key);
        List<Journal> result1 = txtPlugin.getRetrievedJournals(keyRange);
        assertEquals(8, result.size());
        assertEquals(40, result1.size());
    }

    @Test
    public void testGetRetrievedJounralsInvalid() {
        List<Journal> result = txtPlugin.getRetrievedJournals("2016");
        List<Journal> result1 = txtPlugin.getRetrievedJournals("2021-2015");
        assertEquals(0, result.size());
        assertEquals(0, result1.size());
    }
}
