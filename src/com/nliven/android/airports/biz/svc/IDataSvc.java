package com.nliven.android.airports.biz.svc;

import java.util.List;

/**
 * 
 * @author matthew.woolley
 *
 * @param <T>
 */
public interface IDataSvc<T> {

    public void insert(T item);
    public void deleteAll();
    public void delete(T item);
    public void deleteById(long id);
    public List<T> getAll();
    public T getById(long id);
    public void update(T item);
    public long count();
    
}
