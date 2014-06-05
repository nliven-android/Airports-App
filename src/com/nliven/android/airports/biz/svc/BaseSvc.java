package com.nliven.android.airports.biz.svc;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;

/**
 * Creates a DataSvc layer on top of GreenDao-related DAOs.  This is helpful
 * for those who are familiar with the Data Service pattern that was used
 * on the various nliven ASP.NET MVC websites.  
 * 
 * The idea is that a child Svc will implement this parent class
 * for each Domain/Model object.  At that point, the child DataSvc can implement its
 * own custom queries.  
 * 
 * @author matthew.woolley
 *
 * @param <DAO>
 *  The GreenDao auto-generated DAO class
 * @param <T>
 *  The GreenDao auto-generated Entity (Model) class
 */
public abstract class BaseSvc<DAO extends AbstractDao<T, Long>, T> implements IDataSvc<T> {

    protected DAO mDao;
        
    public BaseSvc(){
        
    }
    
    public BaseSvc(DAO d) {
    	mDao = d;
    }
        
    abstract protected Property getIdProperty();       
	
	public void insert(T item){
        ((AbstractDao<T, Long>)mDao).insert(item);
    } 
 
	public void deleteAll(){
    	((AbstractDao<T, Long>)mDao).deleteAll();
    }
    
	public List<T> getAll(){
    	return ((AbstractDao<T, Long>)mDao).queryBuilder().list();
    }
        
	public void update(T item){
        ((AbstractDao<T, Long>)mDao).update(item);
    }
    
    public void delete(T item) {
        ((AbstractDao<T, Long>)mDao).delete(item);        
    }

    public void deleteById(long id) {
        ((AbstractDao<T, Long>)mDao).deleteByKey(id);        
    }

    public long count() {        
        return ((AbstractDao<T, Long>)mDao).count();
    }
    
    public T getById(long id) {     
        return ((AbstractDao<T, Long>)mDao).queryBuilder().where(getIdProperty().eq(id)).build().unique();
    }
    
}
