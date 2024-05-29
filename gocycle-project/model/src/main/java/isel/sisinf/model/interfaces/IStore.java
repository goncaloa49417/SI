package isel.sisinf.model.interfaces;

import isel.sisinf.model.Client;

public interface IStore {
    public long getCode();
    public void setCode(long code);
    public String getEmail();
    public void setEmail(String email);
    public String getAddress();
    public void setAddress(String address);
    public String getLocation();
    public void setLocation(String location);
    public Client getManager();
    public void setManager(Client manager);
}
