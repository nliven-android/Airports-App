package com.nliven.android.airports.ui;

import java.util.ArrayList;
import java.util.List;

import com.nliven.android.airports.R;
import com.nliven.android.airports.biz.model.Airport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Simple example of how to set up a List's Adapter.  Read more about how the 
 * "ViewHolder" pattern works for ListViews, and why you should use it here:
 * <p>
 * <a href="http://lucasr.org/2012/04/05/performance-tips-for-androids-listview">
 *  http://lucasr.org/2012/04/05/performance-tips-for-androids-listview
 * </a>
 * 
 * @author mwoolley59
 *
 */
public class AirportListAdapter extends BaseAdapter{

	private List<Airport> mData;
	private LayoutInflater mLayoutInflator;
	
	/**
	 * Sets up the data for the Airport ListView
	 * @param airports
	 * 	the airport data, most likely returned from the Web Service call
	 * @param ctx
	 *  Context, which is needed to get the LayoutInflator
	 */
	public AirportListAdapter(List<Airport> airports, Context ctx){
		
		//At least to initialize to an empty list, otherwise will get 
		//"Null Pointer Exceptions"
		mData = (airports == null) ? new ArrayList<Airport>() : airports;
		
		//Initialize the LayoutInflator here
		mLayoutInflator = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}	
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Airport getItem(int position) {		
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0; //Ok to be 0 for now...
	}
	
	/**
	 * Used to refresh/update the Airports in the list
	 * @param airports
	 * 	List of Airports
	 */
	public void setData(List<Airport> airports){
		
	    // Dont want to do the notifyDataSetChanged 2x i.e. once in 'clearData' and once here
	    clearData(false); 
		
		// Be sure to handle if the airports parameter here is 'null'
		mData = (airports == null) ? new ArrayList<Airport>() : airports;
		
		// Triggers the refresh of the List
		notifyDataSetChanged();
	}
	
	/**
	 * Clears all data from the list
	 */
	public void clearData(boolean triggerNotifyDataSetChange){
		mData.clear();
		if (triggerNotifyDataSetChange)
		    notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// create a ViewHolder reference
        ViewHolder holder;
 
        //check to see if the reused view is null or not, if is not null then reuse it
        if (convertView == null) {
            holder = new ViewHolder();
 
            //Inflate the List Row XML layout file
            convertView = mLayoutInflator.inflate(R.layout.airport_list_row, null);
            
            //Set the Holder fields to the UI Control item
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtCity = (TextView) convertView.findViewById(R.id.txtCity);
            holder.txtCacheDbId = (TextView) convertView.findViewById(R.id.txtCacheDbId);
 
            // the setTag is used to store the data within this view
            convertView.setTag(holder);
            
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
 
        //get the Airport object from the data based on position
        Airport a = mData.get(position);
        
        //If Airport object is not null, set the holder's TextViews with airport text
        if (a != null) {        
        	holder.txtName.setText(a.getName());
        	holder.txtCity.setText(a.getCity()); 
        	holder.txtCacheDbId.setText(a.getId().toString());
        }
        
        return convertView;
	}
	
	/**
	 * The ViewHolder design pattern enables you to access each list item view 
	 * without the need for the look up, saving valuable processor cycles. 
	 * Specifically, it avoids frequent call of findViewById() during ListView 
	 * scrolling, and that will make it smooth.
	 */
	private static class ViewHolder{
		TextView txtName;
		TextView txtCity;
		TextView txtCacheDbId;
	}
}
