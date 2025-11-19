package tn.esprit.twin.twin2demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.twin2demo.entities.Client;
import tn.esprit.twin.twin2demo.repository.ClientRepository;

import java.util.Date;
import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> retrieveAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public Client addClient(Client client) {
        // Si l'id n'est pas fourni, on le génère (MAX+1) pour éviter l'AI côté DB
        if (client.getIdClient() == null) {
            Long next = clientRepository.nextId();
            client.setIdClient(next);
        }
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        if (client.getIdClient() == null) return null;
        return clientRepository.save(client);
    }

    @Override
    public Client retrieveClient(Long idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public void removeClient(Long idClient) {
        clientRepository.deleteById(idClient);
    }

    @Override
    public List<Client> searchByNom(String keyword) {
        return clientRepository.findByNomContainingIgnoreCase(keyword);
    }

    @Override
    public List<Client> searchRegisteredAfter(Date d) {
        return List.of();
    }

    // @Override
    //public List<Client> searchRegisteredAfter(Date d) {
       // return clientRepository.findRegisteredAfter(d);
   // }

    @Override
    public List<Client> searchByEmailDomain(String domainLike) {
        return clientRepository.findByEmailLike(domainLike);
    }
}
