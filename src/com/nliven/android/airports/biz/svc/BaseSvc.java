package com.nliven.android.airports.biz.svc;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;

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
 *  The GreenDao auto-generated DAO class
 * @param <T>
 *  The GreenDao auto-generated Entity class
 */
public abstract class BaseSvc<DAO, T> implements IDataSvc<T> {

    protected DAO mDao;
        
    public BaseSvc(){
        
    }
    
    public BaseSvc(DAO d) {
    	mDao = d;
    }
        
    abstract public Property getIdProperty();
        
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
	public void update(T item){
        ((AbstractDao<T, Long>)mDao).update(item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(T item) {
        ((AbstractDao<T, Long>)mDao).delete(item);        
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteById(long id) {
        ((AbstractDao<T, Long>)mDao).deleteByKey(id);        
    }

    @SuppressWarnings("unchecked")
    @Override
    public long count() {        
        return ((AbstractDao<T, Long>)mDao).count();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T getById(long id) {     
        return ((AbstractDao<T, Long>)mDao).queryBuilder().where(getIdProperty().eq(id)).build().unique();
    }
    
}
