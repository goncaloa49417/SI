package isel.sisinf.jpa;
import isel.sisinf.model.Client;
import isel.sisinf.model.Reservation;

import java.util.Collection;

public interface IClientRepository extends IRepository<Client, Collection<Client>, Long>, IClientDataMapper {
    public Client findByKey(Long key);
    public Collection<Client> find(String jpql, Object... params);
    public Client create(Client client);
    public Client update(Client client);
    public Client delete(Client client);

    public Collection<Client> getAllClients();

}
