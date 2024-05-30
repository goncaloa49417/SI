package isel.sisinf.jpa;

import isel.sisinf.model.Reservation;
import java.util.Collection;

public interface IReservationRepository extends IRepository<Reservation, Collection<Reservation>, Long>, IReservationDataMapper {
    public Reservation findByKey(Long key);
    public Collection<Reservation> find(String jpql, Object... params);
    public Reservation create(Reservation reservation);
    public Reservation update(Reservation reservation);
    public Reservation delete(Reservation reservation);

    public Collection<Reservation> getAll();
}
