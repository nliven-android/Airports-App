
package com.nliven.android.airports.request;


import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.util.Log;

import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.biz.model.Airport;
import com.nliven.android.airports.eventbus.GetAirportsCompletedEvent;
import com.nliven.android.airports.request.dto.AirportDTO;

/**
 *  Uses the "Retrofit" library to make a REST request:
 *  <br/>  
 *  <a href="http://square.github.io/retrofit/">http://square.github.io/retrofit</a>
 *  <p>
 *  If a successful response is received, this class stores the 
 *  data returned from the server in the CacheDb using a DataSvc, which
 *  is a wrapper around the GreenDao ORM relational database tool.
 * 
 * @author matthew.woolley
 */
public class GetAirportsByState {

    private static final String TAG = GetAirportsByState.class.getSimpleName();
    
    private static AirportsWebServiceApi airportsRequestClient;
    
    /**
     * Performs the GetAirports request for the given State
     */
    public static void execute(String state){
    	
    	if (airportsRequestClient == null){

    		Log.d(TAG, "Initializing Retrofit RestAdapter and RESTfull API Client...");
    		
    		//Initialize the RestAdapter
    		//TODO: Might want to move this to Application and make a singleton, as we
    		//      could use this for other Requests i.e. getting an Airport by its CODE string
    		RestAdapter restAdapter = new RestAdapter.Builder()
    			.setEndpoint("http://airports.pidgets.com")
    			.build();
    		
    		//Create the Request Client, which will be called below with different
    		//URL Path or Query parameters
    		//TODO: Move this to a Base class or Application class and make a Singleton(??)
    		airportsRequestClient = restAdapter.create(AirportsWebServiceApi.class);

    	}

    	/*
    	 * Performs the REST Request. 
    	 * 
    	 * Note that Retrofit has the GSON converter automatically built in.  So, we 
    	 * don't need to manually convert the JSON String to the list of AirportDTO object...
    	 * Retrofit will do all that for us!
    	 * 
    	 * TODO: 'State' should be URL-encoded i.e. handle spaces for places like 'New York', etc
    	 */
    	airportsRequestClient.getAirportsByState(state, new Callback<List<AirportDTO>>() {

    		@Override
    		public void success(final List<AirportDTO> data, Response response) {			
    			Log.i(TAG, "Retrofit Request success!");
    			
    			//1. Clear the Airport Db Table
                AirportApplication.getAirportSvc().deleteAll();

                //2.  Loop thru DTO, create Entities, and save to Db.  Since we are inserting
                //    MULTIPLE entries into the Db, we want to be sure to use a transaction.
                //    Hence, GreenDao's "runInTx" API call.
                AirportApplication.getDaoSession().runInTx(new Runnable() {

                    @Override
                    public void run() {
                        for(AirportDTO dto : data){

                            //Create an instance of the Airport Entity
                            Airport ap = new Airport();
                            ap.setCode(dto.Code);
                            ap.setIcao(dto.Icao);
                            ap.setName(dto.Name);
                            ap.setCity(dto.City);
                            ap.setState(dto.State);
                            ap.setLatitude(dto.Latitude);
                            ap.setLongitude(dto.Longitude);
                            ap.setRunwayLength(dto.RunwayLength);                            
                            ap.setUrl(dto.Url);

                            //Save the Airport entity to the database
                            AirportApplication.getAirportSvc().insert(ap);
                        }     
                        Log.d(TAG, "runInTx complete...");
                    }
                });

                //3. Publish an Otto Event. Any Subscriber (i.e. a View or Activity) 
                //   will listen for this event and will update accordingly.
                AirportApplication.getEventBus().post(new GetAirportsCompletedEvent(true, response.getStatus()));
    			
    		}

    		@Override
    		public void failure(RetrofitError error) {			
    			Log.e(TAG, "Retrofit Request failure!");

    			// Publish an Otto Event. Any Subscriber will listen for this
                // Event and will update accordingly.  Notice we set the success boolean 
                // to 'false' since this request was a FAILURE.
                AirportApplication.getEventBus().post(new GetAirportsCompletedEvent(false, error.getResponse().getStatus()));
    			
    		}
    	}); 		
    }
}
