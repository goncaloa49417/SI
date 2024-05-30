package isel.sisinf.jpa;

import isel.sisinf.model.Client;
import isel.sisinf.model.ElectricBike;

import java.util.Collection;

public interface IElectricBikeRepository extends IRepository<ElectricBike, Collection<ElectricBike>, Long>, IElectricBikeDataMapper {
    public ElectricBike findByKey(Long key);
    public Collection<ElectricBike> find(String jpql, Object... params);
    public ElectricBike create(ElectricBike bike);
    public ElectricBike update(ElectricBike bike);
    public ElectricBike delete(ElectricBike bike);
}
