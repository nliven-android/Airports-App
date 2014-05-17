
package com.nliven.android.airports.ui;

import java.util.List;

import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.Constants;
import com.nliven.android.airports.R;
import com.nliven.android.airports.biz.model.Airport;
import com.nliven.android.airports.eventbus.GetAirportsCompletedEvent;
import com.nliven.android.airports.request.GetAirports;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * The initial UI of our App i.e. the "Home Page".  
 * 
 * @author matthew.woolley
 *
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    private AirportListAdapter mAirportListAdapter;
    private ProgressDialog mProgressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*
         * Load this activity's XML layout file
         */
        setContentView(R.layout.activity_main);
        
        /*
         * Initialize the 'GetAirports' buttons
         */
        Button btnGetCaliAirports = (Button)findViewById(R.id.btnGetCaliAirports);
        btnGetCaliAirports.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Start our REST Request here.  The @Subscribe below will listen
		        //for the event that the Request has completed, either successfully
		        //or if there was a failure.	
				mProgressDialog.show();
				mAirportListAdapter.clearData();
		        GetAirports.execute("California");  			        
			}
		});   
        
        Button btnGetTexasAirports = (Button)findViewById(R.id.btnGetTexasAirports);
        btnGetTexasAirports.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Start our REST Request here.  The @Subscribe below will listen
		        //for the event that the Request has completed, either successfully
		        //or if there was a failure.	
				mProgressDialog.show();
				mAirportListAdapter.clearData();
		        GetAirports.execute("Texas");  			        
			}
		});  
        
        /*
         * Initialize our ListView and ListAdapter.  Includes the click handling when
         * clicking on an item in the List.
         */
        mAirportListAdapter = new AirportListAdapter(null, this);
        ListView listAirports = (ListView)findViewById(R.id.listAirports);
        listAirports.setAdapter(mAirportListAdapter);
        listAirports.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				
				//Get the Airport
				Airport ap = (Airport)adapterView.getItemAtPosition(position);
				
				//Create an Intent and set its contents to just the CacheDb Id.
				//We *could* put the entire Airport object here in the Intent and pass it
				//to the DetailsActivity, but its prob better for the DetailsActivity
				//to load its own copy of the Airport from the CacheDb.
				Intent i = new Intent(MainActivity.this, AirportDetailsActivity.class);
				i.putExtra(Constants.INTENT_EXTRA_AIRPORT_ID, ap.getId());
				startActivity(i);								
			}        	
		});
        
        /*
         * Initialize ProgressDialog, which is shown during a web request
         */
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setMessage("Wait while loading...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        //Register our EventBus so it'll be ready to Subscribe to events
        AirportApplication.getEventBus().register(this);
        
    }       
    
    @Override
    protected void onPause() {     
        super.onPause();      
        
        //Unregister our EventBus
        AirportApplication.getEventBus().unregister(this);
        
        //Hide the "Loading..." dialog
        if (mProgressDialog.isShowing())
        	mProgressDialog.cancel();
        
    }
    
    /**
     * Will be called when the {@link GetAirports} request has finished, whether successfully
     * or if there was a failure.
     * @param event
     */
    @Subscribe public void onGetAirportsCompleted(GetAirportsCompletedEvent event){
        
    	//Hide the "Loading..." dialog
    	mProgressDialog.cancel();
    	
        if (event != null){            
            if (event.Success){
                Log.i(TAG, "Yay, the GetAirports REST request successfully completed!  Get the Airports from the CacheDb...");
                
                //Use the AirportSvc to Query and retrieve ALL Airports
                List<Airport> airports = AirportApplication.getAirportSvc().getAll();
                
                //Update the ListView and it's ListAdapter with this data.  
                mAirportListAdapter.setData(airports);
                
                //Print to a log
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
