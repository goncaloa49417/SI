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

import java.sql.Date;
import java.sql.Timestamp;
import isel.sisinf.model.interfaces.IReservation;
import jakarta.persistence.*;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredFunctionQuery;
import org.eclipse.persistence.platform.database.oracle.annotations.NamedPLSQLStoredFunctionQuery;
import org.eclipse.persistence.platform.database.oracle.annotations.PLSQLParameter;




@Entity
@NamedStoredProcedureQuery(
		name = "Name_checkAvailability",
		procedureName = "checkAvailability",
		parameters = {
				@StoredProcedureParameter(type = Integer.class, mode = ParameterMode.IN),
				@StoredProcedureParameter(type = Date.class, mode = ParameterMode.IN)
		}
)

@NamedQuery(name="Reservation.getAll", query = "select r from Reservation r")
@NamedQuery(name="Reservation.findByKey",
		query="SELECT r FROM Reservation r WHERE r.noReservation =:key")
@NamedStoredProcedureQuery(
		name = "check_reservation_integrity",
		procedureName = "check_reservation_integrity",
		parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, type = Timestamp.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, type = Boolean.class)
		})
public class Reservation implements IReservation {

	@Override
	public String toString(){return "Reservation = [noReservation=" + noReservation + ", store="+ store.getCode() +
			", startDate=" + startDate + ", endDate=" + endDate + ", value=" + value + ", bike=" + bike.getBikeId() + "]";}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noReservation;

	@Version
	private int version;
	public Reservation() {}

	public Reservation(int noReservation, Store store, Timestamp initialDate, Timestamp endDate, Integer value, Bike bike, int version)
	{
		this.noReservation = noReservation;
		this.store = store;
		this.startDate = initialDate;
		this.endDate = endDate;
		this.value = value;
		this.bike = bike;
		this.version = version;
		//shop.getReserves().add(this);
	}

	
	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="bike",referencedColumnName="bikeId")
	private Bike bike;

	@Id
	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="store",referencedColumnName="code")
	private Store store;

	private Timestamp startDate;

	private Timestamp endDate;

	private float value;

	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="client",referencedColumnName="clientId")
	private Client client;

	@Override
	public Timestamp getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Override
	public Timestamp getEndDate() {
		return endDate;
	}
	@Override
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public long getNoReservation() {
		return noReservation;
	}

	public void setNoReservation(long noReserve) {
		this.noReservation = noReserve;
	}

	@Override
	public Store getStore() {
		return store;
	}
	@Override
	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public float getValue() {
		return value;
	}
	@Override
	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public Client getClient(){
		return client;
	}

	@Override
	public void setClient(Client client){this.client = client;}

	@Override
	public Bike getBike(){return this.bike;}

	@Override
	public void setBike(Bike bike){this.bike = bike;}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public void setVersion(int version) {
		this.version = version;
	}
}
