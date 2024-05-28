/*MIT License

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

import isel.sisinf.model.interfaces.IPerson;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
/*@NamedQuery(name="Course.findByKey",
query="SELECT c FROM Course c WHERE c.courseId =:key")

 */
public class Person implements IPerson {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long personId;
	
	//@Column(unique=true)
	private String personName;

	private String address;

	private String email;

	private String phone;

	private Character noIdent;

	private Character atrDisc;



/*
	@ManyToMany(mappedBy="courses")
    private Set<Student> students;

 */


	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String name) {
		this.personName = name;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long courseId) {
		this.personId = courseId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Character getNoIdent() {
		return noIdent;
	}

	public void setNoIdent(Character noIdent) {
		this.noIdent = noIdent;
	}

	public Character getAtrDisc() {
		return atrDisc;
	}

	public void setAtrDisc(Character atrDisc) {
		this.atrDisc = atrDisc;
	}

	
}
