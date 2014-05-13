package com.cjb.drivetime.Repositories;

public interface IRepository<TEntity, TKey>
{
    TEntity Get(TKey id);
    void Save(TEntity entity);
    void Delete(TEntity entity);
}
