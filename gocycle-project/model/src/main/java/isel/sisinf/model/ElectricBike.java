/*
MIT License

Copyright (c) 2022-2024, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.model;

import isel.sisinf.model.interfaces.IElectricBike;
import jakarta.persistence.*;

@Entity
@NamedQuery(name="ElectricBike.findByKey",
        query="SELECT e FROM ElectricBike e WHERE e.bikeId =:key")
public class ElectricBike implements IElectricBike {

    @Override
    public String toString() {
        return "ElectricBike = [bikeId=" + bikeId + ", velocity=" + velocity + "autonomy=" + autonomy + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ElectricBike other = (ElectricBike) obj;
        return bikeId == other.bikeId;
    }

    public ElectricBike() {}

    public ElectricBike(Bike bikeId, Integer velocity, Integer autonomy)
    {
        this.bikeId= bikeId;
        this.velocity=velocity;
        this.autonomy=autonomy;
    }

    @Id
    @ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="bike",referencedColumnName="bikeId")
    private Bike bikeId;

    private Integer velocity;

    private Integer autonomy;

    @Override
    public Bike getBikeId() {
        return bikeId;
    }

    @Override
    public void setBikeId(Bike bikeId) {
        this.bikeId = bikeId;
    }

    @Override
    public Integer getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    @Override
    public Integer getAutonomy() {
        return autonomy;
    }

    @Override
    public void setAutonomy(Integer autonomy) {
        this.autonomy = autonomy;
    }

}
