package com.nliven.android.airports.biz.svc;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * Puts a 'query' layer on top of GreenDao-Orm.  (Though, it would be nice if we could
 * do all this in GreenDao's auto-generated DAO classes.  For now, the DAO class will still 
 * take care of the CRUD commands, but doesn't allow us to add custom queries in its generated code.  
 * This is why we need this SVC layer.)
 * 
 * @author matthew.woolley
 *
 * @param <DAO>
 *  The GreenDao-Orm auto-generated DAO class
 * @param <T>
 *  The GreenDao-Orm auto-generated Entity class
 */
public abstract class BaseSvc<DAO, T> {

    protected DAO mDao;
    
    public BaseSvc(){
        
    }
    
    public BaseSvc(DAO d){
        mDao = d;
    }
    
    protected abstract QueryBuilder<T> getQueryBuilder(); 
    public abstract List<T> getAll();
    public abstract T getById(long id);
    
    //TODO: Others??
    
    
}
