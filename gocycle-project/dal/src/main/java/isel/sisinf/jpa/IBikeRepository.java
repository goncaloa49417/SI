package isel.sisinf.jpa;

import isel.sisinf.model.Bike;

import java.util.Collection;


public interface IBikeRepository extends IRepository<Bike, Collection<Bike>, Long>, IBikeDataMapper{
    public Bike findByKey(Long key);
    public Collection<Bike> find(String jpql, Object... params);
    public Bike create(Bike bike);
    public Bike update(Bike bike);
    public Bike delete(Bike bike);

    public Collection<Bike> getAllFreeBikes();

    public Collection<Bike> getAll();
}
