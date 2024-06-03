package isel.sisinf.jpa;

import isel.sisinf.model.Store;
import java.util.Collection;

public interface IStoreRepository extends IRepository<Store, Collection<Store>, Long>, IStoreDataMapper {
    Collection<Store> getAllStores();
}
