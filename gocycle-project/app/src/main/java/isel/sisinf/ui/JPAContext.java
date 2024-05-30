package isel.sisinf.ui;

import isel.sisinf.jpa.IBikeRepository;
import isel.sisinf.jpa.IClientRepository;
import isel.sisinf.jpa.IContext;
import isel.sisinf.jpa.IReservationRepository;

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

import java.util.Collection;
import java.util.List;

import isel.sisinf.model.Bike;
import isel.sisinf.model.Client;
import isel.sisinf.model.Reservation;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

//o)Why this code here do not work?
/*@NamedStoredProcedureQuery(
	    name = "namedrand_fx",
	    procedureName = "rand_fx",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Number.class)
	    }
	)
*/
public class JPAContext implements IContext{


    private EntityManagerFactory _emf;
    private EntityManager _em;

    //Simple implementation for flat transaction support
    private EntityTransaction _tx;
    //b) What is the purpose of _txCount?
    private int _txcount;
    private IReservationRepository _reservationRepository;
    private IBikeRepository _bikeRepository;

    private IClientRepository _clientRepository;

    /// HELPER METHODS
    protected List helperQueryImpl(String jpql, Object... params)
    {
        Query q = _em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
    }

    protected Object helperCreateImpl(Object entity)
    {
        beginTransaction(); //Each write can have multiple inserts on the DB. See the relations mapping.
        _em.persist(entity);
        commit(); //c) Why can we commit a transaction here, if other commands can be sent to the database?
        return entity;
    }

    protected Object helperUpdateImpl(Object entity)
    {
        beginTransaction(); //Each write can have multiple inserts on the DB. See the relations mapping.
        _em.merge(entity); //d) What does merge do?
        commit();
        return entity;
    }

    protected Object helperDeleteImpl(Object entity)
    {
        beginTransaction(); //Each write can have multiple inserts on the DB. See the relations.
        _em.remove(entity);
        commit();
        return entity;
    }
    protected class BikeRepository implements IBikeRepository{

        @Override
        public Bike findByKey(Long key) {
            return _em.createNamedQuery("Bike.findByKey", Bike.class).setParameter("key", key).getSingleResult();
        }

        @Override
        public Collection<Bike> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Bike create(Bike bike) {
            return (Bike) helperCreateImpl(bike);
        }

        @Override
        public Bike update(Bike bike) {
            return (Bike) helperUpdateImpl(bike);
        }

        @Override
        public Bike delete(Bike bike) {
            return (Bike) helperDeleteImpl(bike);
        }

        @Override
        public Collection<Bike> getAll(){
            return _em.createNamedQuery("Bike.getAll").getResultList();
        }

        @Override
        public Collection<Bike> getAllFreeBikes(){
            return _em.createNamedQuery("Bike.getAllFreeBikes").getResultList();
        }
    }
    protected class ReservationRepository implements IReservationRepository{

        @Override
        public Reservation findByKey(Long key) {
            return _em.createNamedQuery("Reservation.findByKey", Reservation.class).setParameter("key", key).getSingleResult();
        }

        @Override
        public Collection<Reservation> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Reservation create(Reservation reservation) {
            return (Reservation) helperCreateImpl(reservation);
        }

        @Override
        public Reservation update(Reservation reservation) {
            return (Reservation) helperUpdateImpl(reservation);
        }

        @Override
        public Reservation delete(Reservation reservation) {
            return (Reservation) helperDeleteImpl(reservation);
        }

        @Override
        public Collection<Reservation> getAll() {
            Query query = _em.createNamedQuery("Reservation.getAll");
            List resultList = query.getResultList();
            return resultList;
        }
    }

    protected class ClientRepository implements IClientRepository {
/*
        @Override
        public Reservation findByKey(Long key) {
            return null;
        }

 */
/*
        @Override
        public Collection<Reservation> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

 */

        @Override
        public Client findByKey(Long key) {
            return null;
        }

        @Override
        public Collection<Client> find(String jpql, Object... params) {
            return List.of();
        }

        @Override
        public Client create(Client client) {
            return (Client) helperCreateImpl(client);
        }

        @Override
        public Client update(Client client) {
            return (Client) helperUpdateImpl(client);
        }

        @Override
        public Client delete(Client client) {
            return (Client) helperDeleteImpl(client);
        }



        @Override
        public Collection<Client> getAllClients() {
            Query query = _em.createNamedQuery("Client.getAll");
            List resultList = query.getResultList();
            System.out.println(resultList);
            return resultList;
        }
    }


    @Override
    public void beginTransaction() {
        if(_tx == null)
        {
            _tx = _em.getTransaction();
            _tx.begin();
            _txcount=0;
        }
        ++_txcount;
    }

    @Override
    public void beginTransaction(IsolationLevel isolationLevel)
    {
        beginTransaction();
        Session session =_em.unwrap(Session.class);
        DatabaseLogin databaseLogin = (DatabaseLogin) session.getDatasourceLogin();
        System.out.println(databaseLogin.getTransactionIsolation());

        int isolation = DatabaseLogin.TRANSACTION_READ_COMMITTED;
        if(isolationLevel == IsolationLevel.READ_UNCOMMITTED)
            isolation = DatabaseLogin.TRANSACTION_READ_UNCOMMITTED;
        else if(isolationLevel == IsolationLevel.REPEATABLE_READ)
            isolation = DatabaseLogin.TRANSACTION_REPEATABLE_READ;
        else if(isolationLevel == IsolationLevel.SERIALIZABLE)
            isolation = DatabaseLogin.TRANSACTION_SERIALIZABLE;

        databaseLogin.setTransactionIsolation(isolation);
    }

    @Override
    public void commit() {

        --_txcount;
        if(_txcount==0 && _tx != null)
        {
            _em.flush(); //To assure all changes in memory go into the database
            _tx.commit();
            _tx = null;
        }
    }

    @Override
    public void flush() {
        _em.flush();
    }


    @Override
    public void clear() {
        _em.clear();

    }

    @Override
    public void persist(Object entity) {
        _em.persist(entity);

    }

    @Override
    public Collection<Reservation> getAllReservations() {
        return _reservationRepository.getAll();
    }

    @Override
    public Reservation createReservation(Reservation reservation){
        return _reservationRepository.create(reservation);
    }

    @Override
    public Collection<Bike> getAllFreeBikes(){
        return _bikeRepository.getAllFreeBikes();
    }

    @Override
    public Collection<Bike> getAllBikes(){
        return _bikeRepository.getAll();
    }

    @Override
    public Bike getBike(Long bikeId){
        return _bikeRepository.findByKey(bikeId);
    }



    @Override
    public Client createClient(Client newClient) {
        return _clientRepository.create(newClient);
    }

    @Override
    public Collection<Client> getAllClients() {
        return _clientRepository.getAllClients();
    }

    @Override
    public Client getClient(Long clientId) {
        return _clientRepository.findByKey(clientId);
    }

    public JPAContext() {
        this("dal-lab");
    }

    public JPAContext(String persistentCtx)
    {
        super();

        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        this._reservationRepository = new ReservationRepository();
        this._clientRepository = new ClientRepository();
        this._bikeRepository = new BikeRepository();
    }



    @Override
    public void close() throws Exception {

        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }



    public java.math.BigDecimal rand_fx(int seed) {

        StoredProcedureQuery namedrand_fx =
                _em.createNamedStoredProcedureQuery("namedrand_fx");
        namedrand_fx.setParameter(1, seed);
        namedrand_fx.execute();

        return (java.math.BigDecimal)namedrand_fx.getOutputParameterValue(2);
    }

}
