package com.nliven.android.airports.eventbus;

/**
 * Used by the Otto EventBus.  It contains information
 * that should be posted by a Publisher to it's Subscribers.
 * 
 * @author matthew.woolley
 *
 */
public class GetAirportsCompletedEvent {

    /*
     * For simplicity, make these PUBLIC properties.
     */
    public final boolean Success;
    public final int Status;
    
    /**
     * 
     * @param success
     *  Was this a successful REST request?
     * @param status
     *  HTTP Response status code
     */
    public GetAirportsCompletedEvent(boolean success, int status){
        Success = success;
        Status = status;
    }
}
