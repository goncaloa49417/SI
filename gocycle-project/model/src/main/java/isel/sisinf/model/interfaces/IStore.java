package isel.sisinf.model.interfaces;

public interface IStore {
    public int getCode();
    public void setCode(int code);
    public String getEmail();
    public void setEmail(String email);
    public String getAddress();
    public void setAddress(String address);
    public String getLocation();
    public void setLocation(String location);
    public IPerson getManager();
    public void setManager(IPerson manager);
}
