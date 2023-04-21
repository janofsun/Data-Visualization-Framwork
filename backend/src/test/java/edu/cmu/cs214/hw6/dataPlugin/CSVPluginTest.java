package edu.cmu.cs214.hw6.dataPlugin;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class CSVPluginTest {
    private DataPlugin dataPlugin;
    private String key2017, key2018, key2019, key2020, key2021, keyRange0, keyRange1;

    @Before
    public void setUp() {
        dataPlugin = new CSVPlugin();
        key2017 = "2017";
        key2018 = "2018";
        key2019 = "2019";
        key2020 = "2020";
        key2021 = "2021";
        keyRange0 = "2017-2018";
        keyRange1 = "2019-2021";
    }

    @Test
    public void testGetDataPluginName() {
        assertEquals("CSVPlugin", dataPlugin.getDataPluginName());
    }

    @Test
    public void testGetRetrievedData() {
        List<Journal> result2017 = dataPlugin.getRetrievedJournals(key2017);
        List<Journal> result2018 = dataPlugin.getRetrievedJournals(key2018);
        List<Journal> result2019 = dataPlugin.getRetrievedJournals(key2019);
        List<Journal> result2020 = dataPlugin.getRetrievedJournals(key2020);
        List<Journal> result2021 = dataPlugin.getRetrievedJournals(key2021);
        // System.out.println(result.get(0).toString());
        assertEquals(8, result2017.size());
        assertEquals(8, result2018.size());
        assertEquals(8, result2019.size());
        assertEquals(8, result2020.size());
        assertEquals(8, result2021.size());
    }

    @Test
    public void testGetRetrievedDataRange() {
        List<Journal> result = dataPlugin.getRetrievedJournals(keyRange0);
        List<Journal> result1 = dataPlugin.getRetrievedJournals(keyRange1);
        assertEquals(16, result.size());
        assertEquals(24, result1.size());
    }

    @Test
    public void testGetRetrievedDataInvalid() {
        List<Journal> result = dataPlugin.getRetrievedJournals("2016");
        List<Journal> result1 = dataPlugin.getRetrievedJournals("2021-2015");
        List<Journal> result2 = dataPlugin.getRetrievedJournals("2015-2021");
        assertEquals(0, result.size());
        assertEquals(0, result1.size());
        assertEquals(40, result2.size());
    }

}