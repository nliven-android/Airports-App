
package com.nliven.android.airports.ui;

import com.nliven.android.airports.AirportApplication;
import com.nliven.android.airports.Constants;
import com.nliven.android.airports.R;
import com.nliven.android.airports.biz.model.Airport;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

/**
 * The 'Details' UI for an Airport.  
 * Will be shown after selecting an Airport from the ListView in the MainActivity
 * 
 * @author mwoolley59
 *
 */
public class AirportDetailsActivity extends Activity {

	private TextView txtName;
	private TextView txtCity;
	private TextView txtCode;
	private TextView txtIcao;
	
	private Airport mAirport;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        /*
         * Load this activity's XML layout file
         */
        setContentView(R.layout.activity_airport_details);
        
        //Find and set the TextViews
        txtName = (TextView)findViewById(R.id.txtName);
        txtCity = (TextView)findViewById(R.id.txtCity);
        txtCode = (TextView)findViewById(R.id.txtCode);
        txtIcao = (TextView)findViewById(R.id.txtIcao);
        
        //Receive the Intent from the initiating MainActivity
        Intent i = getIntent();
        
        //Then get the 'Extra' that we originally put into this Intent in MainActivity,
        //which was the Airport's CacheDb Id
        long airportId = i.getLongExtra(Constants.INTENT_EXTRA_AIRPORT_ID, 0);
        
        //Use the Airport DataSvc to load the Airport using this ID
        if (airportId > 0)
        	 mAirport = AirportApplication.getAirportSvc().getById(airportId);
        
        //Set the various TextViews with Airport data
        if (mAirport != null){
        	txtName.setText(mAirport.getName());
        	txtCity.setText(mAirport.getCity());
        	txtCode.setText(mAirport.getCode());
        	txtIcao.setText(mAirport.getIcao());
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.airport_details, menu);
        return true;
    }

}
