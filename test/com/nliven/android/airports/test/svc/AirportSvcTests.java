package com.nliven.android.airports.test.svc;

import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.biz.model.Airport;
import com.nliven.android.airports.biz.svc.AirportSvc;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * An example of how to use Robolectric for UNIT TESTING an Android app.
 * 
 * See the following for setting up Robolectric in Eclipse:<br>
 * <a href="https://github.com/thecodepath/android_guides/wiki/Robolectric-Installation-for-Unit-Testing">
 * https://github.com/thecodepath/android_guides/wiki/Robolectric-Installation-for-Unit-Testing
 * </a>
 * 
 * <p>
 * This Unit Test file also uses 'Fest', a fluent way of doing the assertions to make the test code
 * more readable and maintainable.  See:
 * <br>
 * <a href="https://code.google.com/p/fest/">https://code.google.com/p/fest/</a><br>
 * <a href="https://github.com/alexruiz/fest-assert-2.x/wiki/One-minute-starting-guide">
 * https://github.com/alexruiz/fest-assert-2.x/wiki/One-minute-starting-guide
 * </a>
 * 
 * <p>
 * <b>TODO:</b><br>
 *  - Make an abstract Base class and move the <i>setUp</i> and <i>tearDown</i> methods to it<br>
 *  - Make <i>addMockData</i> and <i>clearMockData</i> abstract methods in this Base Class
 * @author matthew.woolley
 *
 */
@RunWith(RobolectricTestRunner.class)
public class AirportSvcTests {

    private static final String TAG = AirportSvcTests.class.getSimpleName();
    
    private AirportSvc svc;
    
    long ap1Id = 1L;
    String ap1Name = "NAME1";
    
    public AirportSvcTests() {
        
        //Needed for Android Logging to work w/ Robolectric!
        ShadowLog.stream = System.out; 
    }
    
    @Before
    public void setUp(){                
        svc = AirportApplication.getAirportSvc();
        addMockData();        
    }
    
    @After
    public void tearDown(){
        clearMockData();
    }
    
    @Test
    public void testAdd(){
        
        Airport a = new Airport();
        a.setName("Test Airport");
        svc.insert(a);
        
        assertThat(svc.count()).isEqualTo(4);
        assertThat(a.getId()).isGreaterThan(0);        
        
    }
    
    @Test
    public void testDeleteById(){
        
        //Initial MockData count should be 3
        assertThat(svc.count()).isEqualTo(3);
        
        svc.deleteById(ap1Id);
        assertThat(svc.count()).isEqualTo(2);        
        assertThat(svc.getById(ap1Id)).isNull();
        
    }
    
    @Test
    public void testDeleteByObject(){
        
        //Initial MockData count should be 3
        assertThat(svc.count()).isEqualTo(3);
        
        //Get the object by ID
        Airport a = svc.getById(ap1Id);
        assertThat(a).isNotNull();
        assertThat(a.getName()).isEqualTo(ap1Name);
        
        //Delete it
        svc.delete(a);        
        
        //Assert        
        assertThat(svc.count()).isEqualTo(2);        
        assertThat(svc.getById(ap1Id)).isNull();
        
    }
           
    @Test
    public void testUpdate(){
        
        //Get the object by ID
        Airport a = svc.getById(ap1Id);
        assertThat(a).isNotNull();
        assertThat(a.getName()).isEqualTo(ap1Name);
        
        //Update it
        String newName = ap1Name + " EDIT";
        a.setName(newName);
        svc.update(a);
        
        //Assert the updates
        assertThat(svc.count()).isEqualTo(3);
        Airport newAirport = svc.getById(ap1Id);
        assertThat(newAirport).isNotNull();
        assertThat(newAirport.getName()).isEqualTo(newName);       
        
    }
    
    @Test
    public void testGetById(){
        
        Airport a = svc.getById(3L);
        assertThat(a.getId()).isEqualTo(3);
        assertThat(a.getCode()).isEqualTo("CODE3");
        assertThat(a.getIcao()).isEqualTo("ICAO3");
        assertThat(a.getName()).isEqualTo("NAME3");
        assertThat(a.getCity()).isEqualTo("CITY3");
        assertThat(a.getState()).isEqualTo("STATE3");
        assertThat(a.getLatitude()).isEqualTo(3.3);
        assertThat(a.getLongitude()).isEqualTo(3.333);
        assertThat(a.getRunwayLength()).isEqualTo(3003);
        assertThat(a.getUrl()).isNullOrEmpty();
    
        //Test non-existing
        Airport a2 = svc.getById(333L);
        assertThat(a2).isNull();        
    }
    
    @Test
    public void testGetByNameContains(){
                
        svc.insert(new Airport(4L, "CODE4", "ICAO4", "NAM4", "CITY4", "STATE4", 4.4, 4.444, 4004, null));        
        svc.insert(new Airport(5L, "CODE5", "ICAO5", "JUNK5", "CITY5", "STATE5", 5.5, 5.555, 5005, null));
        Airport junk = svc.getById(5L);
        assertThat(junk).isNotNull();
        
        //Search for "NAME"
        List<Airport> matchedAirports = svc.getByNameContains("NAME");
        assertThat(matchedAirports)
            .isNotNull()
            .isNotEmpty()
            .hasSize(3)
            .doesNotContain(junk);
        assertThat(junk).isNotIn(matchedAirports); //This is the same as the "doesNotContain" check above...
        
        //Search for "NAM"
        matchedAirports = svc.getByNameContains("NAM");
        assertThat(matchedAirports)
            .isNotNull()
            .isNotEmpty()
            .hasSize(4)
            .doesNotContain(junk);       
    }
    
    //TODO: Make abstract...
    private void addMockData(){
        
        assertThat(svc).isNotNull();
        
        //Insert new data
        svc.insert(new Airport(ap1Id, "CODE1", "ICAO1", ap1Name, "CITY1", "STATE1", 1.1, 1.111, 1001, null));
        svc.insert(new Airport(2L, "CODE2", "ICAO2", "NAME2", "CITY2", "STATE2", 2.2, 2.222, 2002, null));
        svc.insert(new Airport(3L, "CODE3", "ICAO3", "NAME3", "CITY3", "STATE3", 3.3, 3.333, 3003, null));
        assertThat(svc.count()).isEqualTo(3);
        
    }
    
    //TODO: Make abstract...
    private void clearMockData(){
        //Clear out previous data
        svc.deleteAll();
        assertThat(svc.count()).isEqualTo(0);
    }    
}
