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

import isel.sisinf.model.interfaces.IStore;
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
@NamedQuery(name="Store.findByKey",
        query="SELECT s FROM Store s WHERE s.code =:key")
public class Store implements IStore {
    @Override
    public String toString(){return "Store = [code=" + code + ", email=" + email + ", address=" + address +
            ", manager=" + manager.getClientName();}
    @Id
    private long code;

    public Store() {}

    public Store(int code,String email, String address, Client manager)
    {
        this.code = code;
        this.email = email;
        this.address = address;
        this.manager = manager;
        //shop.getReserves().add(this);
    }

    @ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="manager",referencedColumnName="clientId")
    private Client manager;

    private String email;

    private String address;

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Client getManager() {
        return manager;
    }

    @Override
    public void setManager(Client manager) {
        this.manager = manager;
    }

}
