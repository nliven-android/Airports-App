package com.nliven.android.airports.biz.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.nliven.android.airports.biz.model.Airport;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table AIRPORT.
*/
public class AirportDao extends AbstractDao<Airport, Long> {

    public static final String TABLENAME = "AIRPORT";

    /**
     * Properties of entity Airport.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Code = new Property(1, String.class, "code", false, "CODE");
        public final static Property Icao = new Property(2, String.class, "icao", false, "ICAO");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property City = new Property(4, String.class, "city", false, "CITY");
        public final static Property State = new Property(5, String.class, "state", false, "STATE");
        public final static Property Latitude = new Property(6, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(7, Double.class, "longitude", false, "LONGITUDE");
        public final static Property RunwayLength = new Property(8, Integer.class, "runwayLength", false, "RUNWAY_LENGTH");
        public final static Property Url = new Property(9, String.class, "url", false, "URL");
    };


    public AirportDao(DaoConfig config) {
        super(config);
    }
    
    public AirportDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'AIRPORT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'CODE' TEXT," + // 1: code
                "'ICAO' TEXT," + // 2: icao
                "'NAME' TEXT," + // 3: name
                "'CITY' TEXT," + // 4: city
                "'STATE' TEXT," + // 5: state
                "'LATITUDE' REAL," + // 6: latitude
                "'LONGITUDE' REAL," + // 7: longitude
                "'RUNWAY_LENGTH' INTEGER," + // 8: runwayLength
                "'URL' TEXT);"); // 9: url
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'AIRPORT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Airport entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(2, code);
        }
 
        String icao = entity.getIcao();
        if (icao != null) {
            stmt.bindString(3, icao);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(5, city);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(6, state);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(7, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(8, longitude);
        }
 
        Integer runwayLength = entity.getRunwayLength();
        if (runwayLength != null) {
            stmt.bindLong(9, runwayLength);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(10, url);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Airport readEntity(Cursor cursor, int offset) {
        Airport entity = new Airport( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // icao
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // city
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // state
            cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6), // latitude
            cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7), // longitude
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // runwayLength
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // url
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Airport entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIcao(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCity(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setState(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLatitude(cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6));
        entity.setLongitude(cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7));
        entity.setRunwayLength(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setUrl(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Airport entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Airport entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
