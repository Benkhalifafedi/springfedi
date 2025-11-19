package tn.esprit.twin.twin2demo.service;

import tn.esprit.twin.twin2demo.entities.Client;

import java.util.Date;
import java.util.List;

public interface IClientService {
    List<Client> retrieveAllClients();
    Client addClient(Client client);
    Client updateClient(Client client);
    Client retrieveClient(Long idClient);
    void removeClient(Long idClient);

    // Recherches
    List<Client> searchByNom(String keyword);
    List<Client> searchRegisteredAfter(Date d);
    List<Client> searchByEmailDomain(String domainLike);
}
