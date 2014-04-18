
package com.nliven.android.airports.ui;

import java.util.List;

import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.R;
import com.nliven.android.airports.biz.model.Airport;
import com.nliven.android.airports.biz.svc.AirportSvc;
import com.nliven.android.airports.events.GetAirportsCompletedEvent;
import com.nliven.android.airports.request.GetAirports;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

/**
 * The initial UI of our App i.e. the "Home Page".  When this activity is created,
 * it also will make a web service request to get the data of our choosing.    
 * 
 * @author matthew.woolley
 *
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    private AirportSvc mAirportSvc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*
         * Load this activity's XML layout file
         */
        setContentView(R.layout.activity_main);
        
        /*
         * Initialize our 'GetAirports' button
         */
        
        
        
        /*
         * Initialize our ListView and ListAdapter
         */
        
        
        /*
         * Initialize our data svc for Airports.
         * TODO: If this Svc is used elsewhere in the app, might be better to 
         * make it a singleton in the AirportApplication class...
         */
        mAirportSvc = new AirportSvc(AirportApplication.getAirportDao());
                        
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        //Register our EventBus so it'll be ready to Subscribe to events
        AirportApplication.getEventBus().register(this);
        
        //Start our REST Request here.  The @Subscribe below will listen
        //for the event that the Request has completed, either successfully
        //or if there was a failure.
        //**NOTE**
        //In reality, we probably want to be smarter about when to make this call 
        //(i.e. user-initiated by pressing a 'Get Airports' Button.
        GetAirports.execute();        
        
    }       
    
    @Override
    protected void onPause() {     
        super.onPause();      
        
        //Unregister our EventBus
        AirportApplication.getEventBus().unregister(this);
        
    }
    
    /**
     * Will be called when the {@link GetAirports} request has finished, whether successfully
     * or if there was a failure.
     * @param event
     */
    @Subscribe public void onGetAirportsCompleted(GetAirportsCompletedEvent event){
        
        if (event != null){            
            if (event.Success){
                Log.e(TAG, "Yay, the GetAirports REST request successfully completed!  Get the Airports from the CacheDb...");
                
                //Use the AirportSvc to Query and retrieve ALL Airports
                List<Airport> airports = mAirportSvc.getAll();
                if (airports != null){
                    for (Airport a : airports){
                        Log.d(TAG, a.toString());
                    }
                }
                
            } else {
                Log.e(TAG, "Oops, the GetAirports REST request failed with this status: " + event.Status);                
            }            
        } else {            
            Log.e(TAG, "Oops, we got a NULL GetAirportsCompletedEvent for some reason...");            
        }        
    }    
}
