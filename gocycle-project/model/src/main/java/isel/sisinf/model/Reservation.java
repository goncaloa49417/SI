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

import jakarta.persistence.*;

@Entity
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
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reserveNumber;

	
	public Reservation() {}

	public Reservation(int reserveNumber, Store store, Date initialDate, Date endDate, Integer value, Bike bike)
	{
		this.reserveNumber = reserveNumber;
		this.store = store;
		this.initialDate = new java.sql.Date(initialDate.getTime());
		this.endDate = new java.sql.Date(endDate.getTime());
		this.value = value;
		this.bike = bike;
		//shop.getReserves().add(this);
	}

	
	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="bike",referencedColumnName="bikeId")
	private Bike bike;

	@ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="store",referencedColumnName="storeId")
	private Store store;
	
	/*
	@ManyToMany
    @JoinTable(name="StudentCourse",
          joinColumns=@JoinColumn(name="studentId"),
          inverseJoinColumns=@JoinColumn(name="courseId"))
	private Set<Course> courses;

	 */

	private Date initialDate;

	private Date endDate;

	private Integer value;



	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	public void setDateBirth(Date endDate) {
		this.endDate = endDate;
	}

		public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}

	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
}
