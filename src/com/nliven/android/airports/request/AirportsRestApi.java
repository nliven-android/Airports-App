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
 * http://square.github.io/retrofit/
 * 
 * @author mwoolley59
 *
 */
public interface AirportsRestApi {

	/**
	 * Calls the Server API to return a list of Airports within the provided State.
	 * NOTE: This request is asynchronous.
	 */
	@GET("/v1/airports?format=json")
	void getAirports(@Query("state") String state, Callback<List<AirportDTO>> cb) throws RetrofitError;
	
}
