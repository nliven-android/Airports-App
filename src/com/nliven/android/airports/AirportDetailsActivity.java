
package com.nliven.android.airports;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class AirportDetailsActivity extends Activity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_details);
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.airport_details, menu);
        return true;
    }

}
