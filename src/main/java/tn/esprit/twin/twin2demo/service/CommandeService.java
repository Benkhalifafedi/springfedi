package tn.esprit.twin.twin2demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.twin.twin2demo.entities.Commande;
import tn.esprit.twin.twin2demo.repository.CommandeRepository;

import java.util.List;

@Service
public class CommandeService implements ICommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public List<Commande> retrieveAllCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public Commande addCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public Commande updateCommande(Commande commande) {
        if (commandeRepository.existsById(commande.getIdCommande())) {
            return commandeRepository.save(commande);
        }
        return null;
    }

    @Override
    public Commande retrieveCommande(Long idCommande) {
        return commandeRepository.findById(idCommande).orElse(null);
    }

    @Override
    public void removeCommande(Long idCommande) {
        commandeRepository.deleteById(idCommande);
    }
}
