package isel.sisinf.model.interfaces;

public interface IDevice {
    public int getDeviceNumber();
    public void setDeviceNumber(int deviceNumber);
    public Integer getLatitude();
    public void setLatitude(Integer latitude);
    public Integer getLongitude();
    public void setLongitude(Integer longitude);
    public Integer getBattery();
    public void setBattery(Integer battery);
}
