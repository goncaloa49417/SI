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

import isel.sisinf.model.interfaces.IReservation;
import jakarta.persistence.*;

/*
@NamedQuery(name="Student.findByKey",
			query="SELECT s FROM Student s WHERE s.studentNumber =:key")
@NamedQuery(name="Student.EnrolledInCourse",
	query="SELECT s FROM Student s join s.courses c WHERE c.courseId =:key")
@NamedQuery(name="Student.CourseEnrolledInStudents",
		query="SELECT s FROM Student s join s.courses c WHERE s.studentNumber =:key")

@NamedStoredProcedureQuery(
	    name = "namedrand_fx", 
	    procedureName = "rand_fx", 
	    parameters = { 
	        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class), 
	        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Number.class)
	    }
)
@NamedStoredProcedureQuery(
	    name = "altnamedfromCountry", 
	    procedureName = "fromCountry", 
	    		resultSetMappings = {"namedfromCountryResult"},
	    parameters = { 
	        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class)//, 
	        //@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class)
	    }
	)
@SqlResultSetMapping(name="namedfromCountryResult",
classes={
    @ConstructorResult(targetClass=Student.class,
        columns={
            @ColumnResult(name="studentNumber"),
            @ColumnResult(name="name"),
            @ColumnResult(name="dateBirth"),
            @ColumnResult(name="sex", type=Character.class),
            @ColumnResult(name="country")})
})

 */

@Entity
@NamedQuery(name="Reservation.findByKey",
		query="SELECT r FROM Reservation r WHERE r.noReservation =:key")
public class Reservation implements IReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noReservation;

	public Reservation() {}

	public Reservation(int noReservation, Store store, Date initialDate, Date endDate, Integer value, Bike bike)
	{
		this.noReservation = noReservation;
		this.store = store;
		this.startDate = new java.sql.Date(initialDate.getTime());
		this.endDate = new java.sql.Date(endDate.getTime());
		this.value = value;
		this.bike = bike;
		//shop.getReserves().add(this);
	}

	
	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="bike",referencedColumnName="bikeId")
	private Bike bike;

	@Id
	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="store",referencedColumnName="storeId")
	private Store store;

	private Date startDate;

	private Date endDate;

	private float value;

	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="client",referencedColumnName="clientId")
	private Client client;

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}
	@Override
	public void setEndDate(Date endDate) {
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
	
}
