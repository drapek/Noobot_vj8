/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author drapek
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void TestDatabase() {
        String tekst1 = "Jestem gorylem!";
        String tekst2 = "Lubie chodzić po chodniku, ale nie po ulicy!";
        
        Database tstDb = new Database();
        
        tstDb.addSentenceToDb(tekst1, "tekst1");
        tstDb.addSentenceToDb(tekst2, "tekst2");
        
        assertEquals(tstDb.sentencesCollector.get(0).words.get(0), "Jestem");
        assertEquals(tstDb.sentencesCollector.get(0).words.get(1), "gorylem!");
        
        assertEquals(tstDb.sentencesCollector.get(1).words.get(0), "Lubie");
        assertEquals(tstDb.sentencesCollector.get(1).words.get(3), "chodniku,");
        assertEquals(tstDb.sentencesCollector.get(1).words.get(7), "ulicy!");
        
        
         assertEquals(tstDb.sentencesCollector.get(0).words.size(), 2);
         assertEquals(tstDb.sentencesCollector.get(1).words.size(), 8);
        
                
                
        
    }
    

   
    
}
