
package com.nliven.android.airports.test.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import retrofit.RestAdapter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.biz.svc.AirportSvc;
import com.nliven.android.airports.eventbus.GetAirportsCompletedEvent;
import com.nliven.android.airports.request.GetAirportsByStateRequest;
import com.nliven.android.airports.request.dto.AirportDTO;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * This is an example of a Unit Test that is used to test REST Requests and
 * Responses. It uses the <i>MockWebServer</i> library, allowing you to not rely
 * on an actual HTTP network connection and to focus on testing your request and
 * response implementation.
 * <p>
 * http://stackoverflow.com/questions/17544751/square-retrofit-server-mock-for-
 * testing https://gist.github.com/swankjesse/5889518
 * 
 * @author matthew.woolley
 */
@RunWith(RobolectricTestRunner.class)
public class GetAirportsByStateRequestTest {

    private static final String TAG = GetAirportsByStateRequestTest.class.getSimpleName();

    private static final int COUNTDOWN_LATCH_TIMEOUT = 1; // In seconds
    private static final long TEST_TIMEOUT = 15000; // In milliseconds

    private Gson mGson = new GsonBuilder().serializeNulls().create();
    private MockWebServer mMockWebServer;
    private Bus mockBus;
    private AirportSvc svc;
    private CountDownLatch signal;    
    private AtomicBoolean testDone;

    public GetAirportsByStateRequestTest() {
        // Needed for Android Logging to work w/ Robolectric!
        ShadowLog.stream = System.out;         
    }

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "============ setUp called ===============");

        // Initialize various fields. FYI, this will happen every time for every
        // test
        mockBus = new Bus(ThreadEnforcer.ANY);
        mMockWebServer = new MockWebServer();
        mMockWebServer.play();
        signal = new CountDownLatch(1);
        testDone = new AtomicBoolean(false);
        svc = AirportApplication.getAirportSvc();

    }

    @After
    public void tearDown() throws Exception {
        Log.d(TAG, "tearDown called...");

        // Shutdown MockWebServer
        mMockWebServer.shutdown();

        // Clear database
        svc.deleteAll();

    }

    @Test(timeout = TEST_TIMEOUT)
    public void testSuccessRequestWithNonEmptyResponse() throws Exception {

        /*
         * Register an EventBus object to validate the event posted via the Bus
         */
        mockBus.register(new Object() {
            @Subscribe
            public void onGetAirportsCompleted(GetAirportsCompletedEvent event) {
                Log.d(TAG, "MockedBus 'onGetAirportsCompleted' called...");
                assertThat(event).isNotNull();
                assertThat(event.Success).isTrue();
                testDone.set(true);
            }
        });

        /*
         * Create our 'Mocked' Response JSON String here. We can use the DTO to
         * build the object, then use GSON to convert the object to a string.
         */
        List<AirportDTO> airports = new ArrayList<AirportDTO>();
        AirportDTO ap1 = new AirportDTO();
        ap1.Name = "NAME1";
        ap1.City = "CITY1";
        ap1.Code = null;
        ap1.Icao = "";
        airports.add(ap1);

        AirportDTO ap2 = new AirportDTO();
        ap2.Name = "NAME2";
        ap2.City = "CITY2";
        airports.add(ap2);

        /*
         * Prepare and execute the REST Request we are testing
         */
        buildAndSendRequest(mGson.toJson(airports));

        /*
         * Do all the assertions here
         */
        assertThat(svc.count()).isEqualTo(2);
        assertThat(svc.getAll()).isNotEmpty();
        assertThat(svc.getAll().get(0).getName()).isEqualTo("NAME1");
        assertThat(svc.getAll().get(0).getCode()).isNull();
        assertThat(svc.getAll().get(0).getIcao()).isEmpty();

        /*
         * Wait for test to finish or timeout, to allow time for the EventBus
         * callback to happen
         */
        while (!testDone.get());

    }

    @Test(timeout = TEST_TIMEOUT)
    public void testSuccessRequestWithOneAirportResponse() throws Exception {

        // Register an EventBus object to validate the event posted via the Bus
        mockBus.register(new Object() {
            @Subscribe
            public void onGetAirportsCompleted(GetAirportsCompletedEvent event) {
                Log.d(TAG, "MockedBus 'onGetAirportsCompleted' called...");
                assertThat(event).isNotNull();
                assertThat(event.Success).isTrue();
                testDone.set(true);
            }
        });

        // Create our 'Mocked' Response JSON String here
        List<AirportDTO> airports = new ArrayList<AirportDTO>();
        AirportDTO ap1 = new AirportDTO();
        ap1.Name = "NAME3";
        ap1.City = "CITY3";
        ap1.Code = null;
        ap1.Icao = "";
        airports.add(ap1);

        // Prepare and execute the REST Request we are testing
        buildAndSendRequest(mGson.toJson(airports));

        // Verify and assert
        assertThat(svc.count()).isEqualTo(1);
        assertThat(svc.getAll()).isNotEmpty();
        assertThat(svc.getAll().get(0).getName()).isEqualTo("NAME3");
        assertThat(svc.getAll().get(0).getCode()).isNull();
        assertThat(svc.getAll().get(0).getIcao()).isEmpty();

        // Wait for test to finish or timeout, to allow time for the EventBus
        // callback to happen
        while (!testDone.get());

    }

    @Test(timeout = TEST_TIMEOUT)
    public void testSuccessRequestWithEmptyResponse() throws Exception {

        // Register an EventBus object to validate the event posted via the Bus
        mockBus.register(new Object() {
            @Subscribe
            public void onGetAirportsCompleted(GetAirportsCompletedEvent event) {
                Log.d(TAG, "MockedBus 'onGetAirportsCompleted' called...");
                assertThat(event).isNotNull();
                assertThat(event.Success).isTrue();
                testDone.set(true);
            }
        });

        // Create our 'Mocked' Response JSON String here
        List<AirportDTO> airports = new ArrayList<AirportDTO>();

        // Prepare and execute the REST Request we are testing
        buildAndSendRequest(mGson.toJson(airports));

        // Verify and assert
        assertThat(svc.count()).isEqualTo(0);
        assertThat(svc.getAll()).isEmpty();

        // Wait for test to finish or timeout, to allow time for the EventBus
        // callback to happen
        while (!testDone.get());

    }

    @Test(timeout = TEST_TIMEOUT)
    public void testSuccessRequestWithNullFields() throws Exception {

        // Register an EventBus object to validate the event posted via the Bus
        mockBus.register(new Object() {
            @Subscribe
            public void onGetAirportsCompleted(GetAirportsCompletedEvent event) {
                Log.d(TAG, "MockedBus 'onGetAirportsCompleted' called...");
                assertThat(event).isNotNull();
                assertThat(event.Success).isTrue();
                testDone.set(true);
            }
        });

        // Create our 'Mocked' Response JSON String here
        List<AirportDTO> airports = new ArrayList<AirportDTO>();
        AirportDTO ap1 = new AirportDTO();
        airports.add(ap1);

        // Prepare and execute the REST Request we are testing
        buildAndSendRequest(mGson.toJson(airports));

        // Verify and assert
        assertThat(svc.getAll()).isNotEmpty();
        assertThat(svc.count()).isEqualTo(1);
        assertThat(svc.getAll().get(0).getName()).isNullOrEmpty();
        assertThat(svc.getAll().get(0).getCode()).isNull();

        // Wait for test to finish or timeout, to allow time for the EventBus
        // callback to happen
        while (!testDone.get());
    }

    @Test(timeout = TEST_TIMEOUT)
    public void testFailedRequest() throws Exception {

        // Register an EventBus object to validate the event posted via the Bus
        mockBus.register(new Object() {
            @Subscribe
            public void onGetAirportsCompleted(GetAirportsCompletedEvent event) {
                Log.d(TAG, "MockedBus 'onGetAirportsCompleted' called...");
                assertThat(event).isNotNull();
                assertThat(event.Success).isFalse();
                testDone.set(true);
            }
        });

        // Prepare and execute the REST Request we are testing
        buildAndSendRequest("{BadJsonBody:\"true\"}");

        // Verify and assert
        assertThat(svc.count()).isEqualTo(0);
        assertThat(svc.getAll()).isEmpty();

        // Wait for test to finish or timeout, to allow time for the EventBus
        // callback to happen
        while (!testDone.get());

    }

    private void buildAndSendRequest(String mockResponseString) throws Exception {

        /*
         * Enqueue our mocked response in the MockWebServer
         */
        mMockWebServer.enqueue(new MockResponse().setBody(mockResponseString));

        /*
         * Build the Mocked RestAdapter
         */
        Executor executor = Executors.newSingleThreadExecutor();
        RestAdapter mockAdapter = 
                new RestAdapter.Builder()
                        .setExecutors(executor, executor)
                        .setEndpoint(mMockWebServer.getUrl("/").toString())
                        .setLogLevel(RestAdapter.LogLevel.BASIC)
                        .build();

        /*
         * Execute the request we want to test
         */
        GetAirportsByStateRequest request = new GetAirportsByStateRequest(mockAdapter, mockBus);
        request.execute("California");

        /*
         * Wait a second or 2 for our callback. Since we are using a
         * MockWebServer and not connecting to the actual Internet, this
         * theoretically shouldn't take too long.
         */
        try {
            //Wait for Retrofit Request callback
            signal.await(COUNTDOWN_LATCH_TIMEOUT, TimeUnit.SECONDS);                                                                      
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
