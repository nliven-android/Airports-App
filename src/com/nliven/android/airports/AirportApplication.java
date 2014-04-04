package com.nliven.android.airports;

import com.nliven.android.airports.biz.dao.AirportDao;
import com.nliven.android.airports.biz.dao.DaoMaster;
import com.nliven.android.airports.biz.dao.DaoMaster.DevOpenHelper;
import com.nliven.android.airports.biz.dao.DaoSession;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Declare any application-wide singletons, etc here.  Eventually, it would
 * be a good idea to set up Dependency Injection here...
 * 
 * @author matthew.woolley
 *
 */
public class AirportApplication extends Application{

    private static Bus mBus;    
    private static AirportDao mAirportDao;
    
    @Override
    public void onCreate() {     
        super.onCreate();
        
        //Otto Event Bus
        mBus = new Bus(ThreadEnforcer.ANY);
        
        //Initialize Database DAOs, etc
        initializeDatabase(this);
        
    }
    
    /**
     * The Otto EventBus instance for Publishing events
     * to Subscribers.
     * @return
     *  The Otto EventBus instance
     */
    public static Bus getEventBus(){
        return mBus;
    }
    
    /**
     * Returs a DAO object allowing you to Save/Update/Delete
     * Airport entities.
     * @return
     */
    public static AirportDao getAirportDao(){
        return mAirportDao;
    }
    
    /*
     * 
     */
    private static void initializeDatabase(Context ctx){
        
        //Boilerplate for creating a GreenDao database
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "airport_app.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
    
        //Setup your individual table DAOs here...
        mAirportDao = daoSession.getAirportDao();        
    }
    
}
