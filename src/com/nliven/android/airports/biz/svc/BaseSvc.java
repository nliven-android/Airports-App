package com.nliven.android.airports.biz.svc;

import java.util.List;

import com.nliven.android.airports.biz.dao.AirportDao.Properties;

import de.greenrobot.dao.AbstractDao;

/**
 * Creates a DataSvc layer on top of GreenDao-related DAOs.  This is helpful
 * for those who are familiar with the Data Service pattern that was used
 * on the various nliven ASP.NET MVC websites.  
 * 
 * The idea is that a child DataSvc will be implemented from this parent class
 * for each Domain/Model object.  From there, the child DataSvc can implement its
 * own custom queries.  
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
    
    public BaseSvc(DAO d) {
    	mDao = d;
    }

    //TODO: Others??    
        
	@SuppressWarnings("unchecked")
	public void insert(T item){
        ((AbstractDao<T, Long>)mDao).insert(item);
    } 
 
    @SuppressWarnings("unchecked")
	public void deleteAll(){
    	((AbstractDao<T, Long>)mDao).deleteAll();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> getAll(){
    	return ((AbstractDao<T, Long>)mDao).queryBuilder().list();
    }
    
    @SuppressWarnings("unchecked")
	public T getById(long id){
    	return ((AbstractDao<T, Long>)mDao).queryBuilder().where(Properties.Id.eq(id)).build().unique();
    }
    
    @SuppressWarnings("unchecked")
	public void update(T item){
        ((AbstractDao<T, Long>)mDao).update(item);
    }
    
}
