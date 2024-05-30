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

import isel.sisinf.model.interfaces.IClient;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name="Client.getAll", query = "select c from Client c")
@NamedQuery(name="Client.findByKey",
query="SELECT c FROM Client c WHERE c.clientId =:key")
public class Client implements IClient {

	@Override
	public String toString(){
		return "Client = [clientId =" + clientId + ", name=" + name + ", address=" + address
				+ ", email=" + email + ", phone=" + phone + ", noIdent=" + noIdent + ", nationality=" + nationality
				+ ", atrDisc=" + atrDisc + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long clientId;

	@Override
	public int hashCode(){return Objects.hash(clientId);}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return clientId == other.clientId;
	}

	/**
	 * clientId serial primary key,
	 * 	name varchar(20) not null,
	 * 	address varchar(100) not null,
	 * 	email varchar(50) unique not null,
	 * 	phone varchar(15) unique not null,
	 * 	noIdent varchar(20) unique not null,
	 * 	nationality varchar(15) not null,
	 * 	atrDisc varchar(2) not null,
	 */



	//@Column(unique=true)
	private String name;

	private String address;

	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String phone;
	@Column(unique = true)
	private String noIdent;

	private String nationality;
	private Character atrDisc;

	public Client(long clientId, String name, String address, String email, String phone, String noIdent, String nationality, Character atrDisc){
		this.clientId = clientId;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.noIdent = noIdent;
		this.nationality = nationality;
		this.atrDisc = atrDisc;
	}

	public Client() {};

	@Override
	public long getClientId() {
		return clientId;
	}

	@Override
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	@Override
	public String getClientName() {
		return name;
	}

	@Override
	public void setClientName(String name) {
		this.name = name;
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
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String getNoIdent() {
		return noIdent;
	}

	@Override
	public void setNoIdent(String noIdent) {
		this.noIdent = noIdent;
	}

	@Override
	public String getNationality() {
		return nationality;
	}

	@Override
	public void SetNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public Character getAtrDisc() {
		return atrDisc;
	}

	@Override
	public void setAtrDisc(Character atrDisc) {
		this.atrDisc = atrDisc;
	}

/*
	@ManyToMany(mappedBy="courses")
    private Set<Student> students;

 */


}