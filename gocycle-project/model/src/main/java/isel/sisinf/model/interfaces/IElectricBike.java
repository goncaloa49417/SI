package isel.sisinf.model.interfaces;

import isel.sisinf.model.Bike;

public interface IElectricBike {
    public Bike getBikeId();
    public void setBikeId(Bike bikeId);
    public Integer getVelocity();
    public void setVelocity(Integer velocity);
    public Integer getAutonomy();
    public void setAutonomy(Integer autonomy);
}
