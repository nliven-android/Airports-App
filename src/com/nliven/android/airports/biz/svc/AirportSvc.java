package com.nliven.android.airports.biz.svc;

import java.util.List;

import com.nliven.android.airports.biz.dao.AirportDao;
import com.nliven.android.airports.biz.dao.AirportDao.Properties;
import com.nliven.android.airports.biz.model.Airport;

import de.greenrobot.dao.Property;


/**
 * Contains custom queries for the Airport table.
 * 
 * @author matthew.woolley
 *
 */
public class AirportSvc extends BaseSvc<AirportDao, Airport>{
       
    public AirportSvc(AirportDao d) {
        super(d);
    }

    @Override
    protected Property getIdProperty() {        
        return Properties.Id;
    }   
    
    /*
     * Start Custom Queries:
     */
    
    /**
     * Searches for an airport who's name contains the provided string (This
     * query would be good for an Autocomplete Textbox!)
     * @param name
     *  Could be a partial string of the airport name
     * @return
     *  List of airports whose full Name contain the provided partial name
     */
    public List<Airport> getByNameContains(String name){                
        return mDao.queryBuilder().where(Properties.Name.like("%" + name + "%")).list();
    }
   
}
