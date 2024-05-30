package isel.sisinf.jpa;


import isel.sisinf.model.Device;
import java.util.Collection;

public interface IDeviceRepository extends IRepository<Device, Collection<Device>, Long>, IDeviceDataMapper {
    public Device findByKey(Long key);
    public Collection<Device> find(String jpql, Object... params);
    public Device create(Device device);
    public Device update(Device device);
    public Device delete(Device device);
}
