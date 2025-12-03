package tn.esprit.twin.twin2demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.twin.twin2demo.dto.CommandeDto;
import tn.esprit.twin.twin2demo.entities.Commande;
import tn.esprit.twin.twin2demo.repository.CommandeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeService implements ICommandeService {

    // ✅ Logger SLF4J classique (plus besoin de @Slf4j)
    private static final Logger log = LoggerFactory.getLogger(CommandeService.class);

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

    // ✅ MÉTHODE DEMANDÉE PAR LE PROF
    // Signature EXACTE :
    // List<CommandeDto> listeCommandesParClientEtMenu(String identifiant, String libelleMenu);
    @Override
    public List<CommandeDto> listeCommandesParClientEtMenu(String identifiant, String libelleMenu) {

        // 1) Récupérer les commandes correspondant au client + menu
        List<Commande> commandes =
                commandeRepository.chercherParClientEtMenu(identifiant, libelleMenu);

        // (Optionnel mais utile pour debug)
        log.info("➤ Filtre client={} menu={} → {} commandes trouvées",
                identifiant, libelleMenu, commandes.size());

        // 2) Mapper vers le DTO avec uniquement les champs demandés
        return commandes.stream()
                .map(commande -> new CommandeDto(
                        commande.getDateCommande(),              // date commande
                        commande.getTotal(),                     // montantCommande
                        commande.getMenu().getLibelle(),         // libelle menu
                        commande.getClient().getIdentifiant()    // identifiant client
                ))
                .collect(Collectors.toList());
    }

    // ⚠️ Ta méthode avait datedebut = 1/1 et datefin = 1/1 => ça ne prend qu'un seul jour.
    // Ici je corrige pour prendre toute l'année courante.
    @Scheduled(cron = "0/59 * * * * *")
    public void findCurrentYearCommandesOrderByNote() {

        int year = LocalDate.now().getYear();
        LocalDate datedebut = LocalDate.of(year, 1, 1);
        LocalDate datefin = LocalDate.of(year, 12, 31);

        List<Commande> commandes =
                commandeRepository.findByDateCommandeBetweenOrderByNote(datedebut, datefin);

        for (Commande commande : commandes) {
            log.info("Commande de l'année {} : {}", year, commande);
        }
    }

}
