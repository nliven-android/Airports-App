
package com.nliven.android.airports.ui;

import java.util.List;

import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.R;
import com.nliven.android.airports.biz.model.Airport;
import com.nliven.android.airports.events.GetAirportsCompletedEvent;
import com.nliven.android.airports.request.GetAirports;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

/**
 * The initial UI of our App.    
 * 
 * @author matthew.woolley
 *
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
                
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        //Register our EventBus so it'll be ready to Subscribe to events
        AirportApplication.getEventBus().register(this);
        
        //Start our REST Request here.  The @Subscribe below will listen
        //for the event that the Request has completed, either successfully
        //or if there was a failure.
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
                
                //Use the AirportDao to Query and retrieve ALL Airports
                List<Airport> airports = AirportApplication.getAirportDao().queryBuilder().list();
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
