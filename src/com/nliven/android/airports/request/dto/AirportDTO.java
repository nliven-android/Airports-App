package com.nliven.android.airports.request.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Matches the schema of the JSON string that is returned from the 
 * http://airports.pidgets.com/ request.  The GSON Parser will
 * convert the JSON String into this AirportDTO object (and visa-versa,
 * if applicable.)  Although the JSON String contains more properties, this DTO
 * allows us to gather only the properties we care about.
 * <p>
 * DTOs are simple objects that should not contain any business 
 * logic that would require testing.
 * <p>
 * If you want this Object's public property Name to be different than the
 * JSON Key Property, use the {@link SerializedName} annotation, like so:
 * <p>
 * <code>
 * @Serializable("code")
 * <br>
 * public String AirportCode;
 * </code>
 * 
 * 
 * @author matthew.woolley
 *
 */
public class AirportDTO {
    
    /*
     * For simplicity, make all these PUBLIC properties, rather than
     * having getters/setters, since this really is a simple 'Bean'-like
     * object.
     */    
    
    @SerializedName("code") //yep, Gson parsing is CASE-SENSITIVE!!
    public String Code;
    
    @SerializedName("icao")
    public String Icao;
    
    @SerializedName("name")
    public String Name;
    
    @SerializedName("city")
    public String City;
    
    @SerializedName("state")
    public String State;
    
    @SerializedName("lat")
    public Double Latitude;
    
    @SerializedName("lon")
    public Double Longitude;
    
    @SerializedName("runway_length")
    public Integer RunwayLength;
    
    @SerializedName("url")
    public String Url;
    
    public AirportDTO(){
        //No-Args constructor required for GSON
    }
    
}
