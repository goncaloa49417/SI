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
/**
@Entity

public class ClientReservation {



    public ClientReservation() {}

    public ClientReservation(Person person,Store reservation_store)
    {
        this.person= person;
        this.reservation_store= reservation_store;

    }

    @Id
    @ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="person",referencedColumnName="personId")
    private Person person;

    @Id
    @ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name="reservation",referencedColumnName="storeId")
    private Store reservation_store;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Store getReservation_store() {
        return reservation_store;
    }

    public void setReservation_store(Store reservation_store) {
        this.reservation_store = reservation_store;
    }





}
 */
