package isel.sisinf.ui;

import isel.sisinf.jpa.IReservationRepository;
import isel.sisinf.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Collection;
import java.util.List;

public class ReservationRepository implements IReservationRepository {
    private final EntityManagerFactory ef = Persistence.createEntityManagerFactory("dal-lab");
    private final EntityManager em = ef.createEntityManager();
    @Override
    public Reservation findByKey(Long key) {
        return null;
    }

    public Collection<Reservation> getAll(){
        Reservation r = new Reservation();
        Query query = em.createNamedQuery("Reservation.getAll");
        List resultList = query.getResultList();
        return resultList;
    }
    @Override
    public Collection<Reservation> find(String jpql, Object... params) {
        Query query = em.createQuery("select r from Reservation r");
        List resultList = query.getResultList();
        System.out.println(resultList);
        return resultList;
    }

    @Override
    public Reservation create(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation update(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation delete(Reservation reservation) {
        return null;
    }
}

