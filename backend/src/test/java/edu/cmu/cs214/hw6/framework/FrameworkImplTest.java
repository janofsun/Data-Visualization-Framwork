package edu.cmu.cs214.hw6.framework;

import static org.junit.Assert.assertEquals;


import java.util.List;


import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.dataPlugin.CSVPlugin;
import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;
import edu.cmu.cs214.hw6.dataPlugin.Journal;

public class FrameworkImplTest {
    private DataPlugin dataPlugin;
    FrameworkImpl framework;
    private String key, key2017;

    @Before
    public void setUp() {
        dataPlugin = new CSVPlugin();
        framework = new FrameworkImpl();
        key = "2017-2018";
        key2017 = "2017";
        // key2019 = "src/main/resources/data/8-source-results-2019.csv";
        // key2020 = "src/main/resources/data/8-source-results-2020.csv";
        // key2021 = "src/main/resources/data/8-source-results-2021.csv";
    }

    @Test
    public void testSorter() {
        List<Journal> allJournals = dataPlugin.getRetrievedJournals(key);
        List<Journal> journals = dataPlugin.getRetrievedJournals(key2017);
        assertEquals(16, allJournals.size());

        List<Journal> sortedJournals = framework.analyzeImpact(allJournals);
        for (int i = 0; i < journals.size(); i++) {
            assertEquals("2017", sortedJournals.get(i*2).getTime());
            assertEquals("2018", sortedJournals.get(i*2+1).getTime());
            i += 1;
        }
    }

    @Test
    public void testAvg() {
        framework.setShowAverage(true);
        List<Journal> allJournals = dataPlugin.getRetrievedJournals("2018-2020");

        List<Journal> sortedJournals = framework.analyzeImpact(allJournals);
        assertEquals(sortedJournals.size(), 8);
        String title1 = allJournals.get(0).getTitle();
        float sum = 0;
        int size = 0;
        for (Journal journal: allJournals) {
            if (journal.getTitle().equals(title1)) {
                sum += journal.getCscore();
                size += 1;
            }
        }
        for (Journal journal: allJournals) {
            if (journal.getTitle().equals(title1)) {
                assert(journal.getCscoreAvg()*size == sum);
            }
        }
    }

}
