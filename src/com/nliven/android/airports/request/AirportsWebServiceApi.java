package com.nliven.android.airports.request;

import java.util.List;

import com.nliven.android.airports.request.dto.AirportDTO;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Uses the Retrofit library to turn a REST API into a Java interface.
 * 
 * This interface should define all the RESTful API's that a specific
 * WebService has that this App will need to call.  
 * <p>
 * In this case, we are using a publically-available Web Service:
 * <br>
 * <b>"Airports Web Service"</b>
 * <i><a href="http://airports.pidgets.com/v1/">http://airports.pidgets.com/v1/</a></i>
 * 
 * @author mwoolley59
 *
 */
public interface AirportsWebServiceApi {

	/**
	 * Calls the Server API to return a list of Airports within the provided State.
	 * NOTE: This request is asynchronous.
	 */
	@GET("/v1/airports?format=json")
	void getAirportsByState(@Query("state") String state, Callback<List<AirportDTO>> cb) throws RetrofitError;
	
}
