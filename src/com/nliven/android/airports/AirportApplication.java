package com.nliven.android.airports;

import retrofit.RestAdapter;

import com.nliven.android.airports.biz.dao.AirportDao;
import com.nliven.android.airports.biz.dao.DaoMaster;
import com.nliven.android.airports.biz.dao.DaoSession;
import com.nliven.android.airports.biz.dao.ProdDbOpenHelper;
import com.nliven.android.airports.biz.svc.AirportSvc;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Declare any application-wide singletons, etc here.  Eventually, it would
 * be a good idea to set up Dependency Injection here...
 * 
 * @author matthew.woolley
 *
 */
public class AirportApplication extends Application{

    private static final String TAG = AirportApplication.class.getSimpleName();
    
    private static Bus sBus;    
    private static AirportDao sAirportDao;
    private static DaoSession sDaoSession;
    private static AirportSvc sAirportSvc; 
    private static RestAdapter sAirportApiRestAdapter;
    
    @Override
    public void onCreate() {     
        super.onCreate();
        
        Log.d(TAG, "onCreate");
        
        //Initialize the Otto Event Bus
        sBus = new Bus(ThreadEnforcer.ANY);
        
        //Initialize the Retrofit RestAdapter(s)     
        sAirportApiRestAdapter = new RestAdapter.Builder()
            .setEndpoint(Constants.AIRPORTS_API_ENDPOINT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();        
        
        //Initialize Database DAOs, etc
        initializeDatabase(this);
        
    }
    
    /**
     * Returns the Otto EventBus instance for Publishing events
     * to Subscribers.
     */
    public static Bus getEventBus(){
        return sBus;
    }

    /**
     * Returns the Retrofit RestAdapter for making REST requests to the
     * Airports REST Service.
     */
    public static RestAdapter getAirportApiRestAdapter(){
        return sAirportApiRestAdapter;
    }    
    
    /**
     * Returns the Airport DataSvc.  This has the CRUD and Query
     * functionality for the Airport model object.
     */
    public static AirportSvc getAirportSvc(){
    	return sAirportSvc;
    }    
    
    /**
     * Returns a DaoSession object.  The session cache is not just a plain 
     * data cache to improve performance, but also manages object identities. 
     * For example, if you load the same entity twice in a query, you will 
     * get a single Java object instead of two when using a session cache.
     * This is particular useful for relations pointing to a common set of entities.
     */
    public static DaoSession getDaoSession(){
        return sDaoSession;
    }    
   
    /**
     * Sets all GreenDao-Orm related objects i.e. Database, Sessions, Daos, etc...
     */
    private static void initializeDatabase(Context ctx){
        
        Log.d(TAG, "initializeDatabase..." + ctx.toString());
        
        //Boilerplate for creating a GreenDao database
        ProdDbOpenHelper helper = new ProdDbOpenHelper(ctx, "airport_app.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);        
        
        //Initialize DaoSession
        sDaoSession = daoMaster.newSession();
    
        //Setup your individual table DAOs here...
        sAirportDao = sDaoSession.getAirportDao();      
        
        //Create your DataSvc's here...
        sAirportSvc = new AirportSvc(sAirportDao);       
        
    }    
}
