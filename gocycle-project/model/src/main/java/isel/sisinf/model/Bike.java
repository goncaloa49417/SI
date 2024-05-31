
package isel.sisinf.model;


import java.util.Objects;

import isel.sisinf.model.interfaces.IBike;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Bike.getAll", query = "select b from Bike b")
@NamedQuery(name="Bike.getAllFreeBikes", query = "select b from Bike b where b.state = 'free'")
@NamedQuery(name="Bike.findByKey",
        query="SELECT b FROM Bike b WHERE b.bikeId =:key")
public class Bike implements IBike {
    @Override
    public String toString() {
        return "Bike [bikeId=" + bikeId + ", model="+ model + ", brand =" + brand
                + ", shift=" + shift + ", state=" + state + ", atrDisc=" + atrDisc
                + ", device=" + device.getDeviceNumber() + "]";
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bikeId;
    @Override
    public int hashCode() {
        return Objects.hash(bikeId);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bike other = (Bike) obj;
        return bikeId == other.bikeId;
    }
    public long getBikeId() {
        return bikeId;
    }
    public void setBikeId(long bikeId) {
        this.bikeId = bikeId;
    }
    private String model;
    private String brand;
    private Integer shift;
    private String state;
    private Character atrDisc;
    @ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="device",referencedColumnName="deviceNumber")
    private Device device;
    //private Integer spoke;


    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String getBrand() {
    return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public Integer getShift() {
        return shift;
    }

    @Override
    public void setShift(Integer spoke) {
        this.shift = spoke;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Character getAtrDisc() {
        return atrDisc;
    }

    @Override
    public void setAtrDisc(Character atrDisc) {
        this.atrDisc = atrDisc;
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public void setDevice(Device device) {
        this.device = device;
    }
    /**
    @OneToMany(cascade = CascadeType.ALL,mappedBy="homeCountry",fetch = FetchType.LAZY)
    private Set<Reservation> students;

    public Set<Reservation> getStudents() {
        return students;
    }
    public void setStudents(Set<Reservation> students) {
        this.students = students;
    }
    */
}
