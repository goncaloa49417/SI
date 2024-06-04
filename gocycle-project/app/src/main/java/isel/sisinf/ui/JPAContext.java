package isel.sisinf.ui;

import isel.sisinf.jpa.*;

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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import isel.sisinf.model.Bike;
import isel.sisinf.model.Client;
import isel.sisinf.model.Reservation;
import isel.sisinf.model.Store;
import jakarta.persistence.*;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;

public class JPAContext implements IContext{


    private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;
    private IReservationRepository _reservationRepository;
    private IBikeRepository _bikeRepository;

    private IClientRepository _clientRepository;

    private IStoreRepository _storeRepository;

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
        System.out.println("helperCreateImpl: " + entity.toString());

        beginTransaction();
        _em.persist(entity);
        _em.flush(); //To assure all changes in memory go into the database
        System.out.println("helperCreateImpl: " + entity.toString());
        _tx.commit();
        //commit(); //c) Why can we commit a transaction here, if other commands can be sent to the database?

        return entity;
    }

    protected Object helperUpdateImpl(Object entity)
    {
        beginTransaction();
        _em.merge(entity);
        commit();
        beginTransaction(); //Each write can have multiple inserts on the DB. See the relations mapping.
        _em.merge(entity); //d) What does merge do?
        _em.flush(); //To assure all changes in memory go into the database
        _tx.commit();
        return entity;
    }

    protected Object helperDeleteImpl(Object entity)
    {
        beginTransaction();
        _em.remove(entity);
        //_em.flush(); //To assure all changes in memory go into the database
        _tx.commit();
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
    protected class StoreRepository implements IStoreRepository{

        @Override
        public Store create(Store entity) {
            return (Store) helperCreateImpl(entity);
        }

        @Override
        public Store update(Store entity) {
            return (Store) helperUpdateImpl(entity);
        }

        @Override
        public Store delete(Store entity) {
            return (Store) helperDeleteImpl(entity);
        }

        @Override
        public Store findByKey(Long key) {
            return _em.createNamedQuery("Store.findByKey", Store.class).setParameter("key", key).getSingleResult();
        }

        @Override
        public Collection<Store> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Collection<Store> getAllStores(){
            return _em.createNamedQuery("Store.getAll").getResultList();
        }
    }
    protected class ClientRepository implements IClientRepository {
        public Collection<Client> getAll() {
            Query query = _em.createNamedQuery("Client.getAll");
            List resultList = query.getResultList();
            return resultList;
        }

        @Override
        public Client findByKey(Long key) {
            return  _em.createNamedQuery("Client.findByKey", Client.class).setParameter("key", key).getSingleResult();
        }

        @Override
        public Collection<Client> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
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
    public void lock(Object entity) {
        _em.lock(entity, LockModeType.OPTIMISTIC);
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
            System.out.println("Commiting transaction");
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
    public Bike updateBike(Bike bike){
        return _bikeRepository.update(bike);
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
        return _clientRepository.getAll();
    }

    @Override
    public Client getClient(Long clientId) {
        return _clientRepository.findByKey(clientId);
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
    public Reservation getReservation(Long resId){
        return _reservationRepository.findByKey(resId);
    }

    @Override
    public Reservation deleteReservation(Reservation reservation){
        return _reservationRepository.delete(reservation);
    }

    @Override
    public Collection<Store> getAllStores(){
        return _storeRepository.getAllStores();
    }

    @Override
    public Store getStore(Long noStore) {
        return _storeRepository.findByKey(noStore);
    }


    @Override
    public boolean checkReservationIntegrity(Integer client, Integer bike, Timestamp startDate){
        StoredProcedureQuery query = _em.createNamedStoredProcedureQuery("check_reservation_integrity");
        query.setParameter(1, client);
        query.setParameter(2, startDate);
        query.setParameter(3, bike);

        query.execute();
        return (Boolean) query.getOutputParameterValue(4);

    }

    public IClientRepository getClients() {
        return _clientRepository;
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
        this._storeRepository = new StoreRepository();
    }



    @Override
    public void close() throws Exception {

        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }



    public Boolean checkAvailability(int bikeId, Date checkTime) {

        StoredProcedureQuery name_makeReservation =
                _em.createNamedStoredProcedureQuery("Name_checkAvailability");
        name_makeReservation.setParameter(1, bikeId);
        name_makeReservation.setParameter(2, checkTime);
//2024-02-20 17:35
        name_makeReservation.execute();
        Object returnType=name_makeReservation.getSingleResult();
        Boolean availability = null;
        for (Object o : (Object[]) returnType) {
            availability = (Boolean) o;
        }

        return availability;
    }

}
