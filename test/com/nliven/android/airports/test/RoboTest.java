package com.nliven.android.airports.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.nliven.android.airports.R;
import com.nliven.android.airports.ui.MainActivity;

import static org.fest.assertions.api.Assertions.assertThat;

// https://github.com/alexruiz/fest-assert-2.x/wiki/One-minute-starting-guide

@RunWith(RobolectricTestRunner.class)
public class RoboTest {

    @Test
    public void verifyButtonText() throws Exception{
        
        String btnCaliText = new MainActivity().getResources().getString(R.string.button_get_cali_airports);
        assertThat(btnCaliText).isEqualTo("Get California Airports");
        
        String btnTexasText = new MainActivity().getResources().getString(R.string.button_get_texas_airports);
        assertThat(btnTexasText).isEqualTo("Get Texas Airports");
        
        
    }
    
}
