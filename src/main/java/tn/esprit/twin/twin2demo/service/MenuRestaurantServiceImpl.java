// src/main/java/tn/esprit/twin/twin2demo/service/MenuRestaurantServiceImpl.java
package tn.esprit.twin.twin2demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.twin2demo.entities.*;
import tn.esprit.twin.twin2demo.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class MenuRestaurantServiceImpl implements IMenuRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ChaineRestaurationRepository chaineRestaurationRepository;
    private final MenuRepository menuRepository;
    private final ChefCuisinierRepository chefCuisinierRepository;
    private final ComposantRepository composantRepository;
    private final ClientRepository clientRepository;
    private final CommandeRepository commandeRepository;

    // ✅ Constructeur explicite pour initialiser les champs final (remplace @RequiredArgsConstructor)
    public MenuRestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                     ChaineRestaurationRepository chaineRestaurationRepository,
                                     MenuRepository menuRepository,
                                     ChefCuisinierRepository chefCuisinierRepository,
                                     ComposantRepository composantRepository,
                                     ClientRepository clientRepository,
                                     CommandeRepository commandeRepository) {
        this.restaurantRepository = restaurantRepository;
        this.chaineRestaurationRepository = chaineRestaurationRepository;
        this.menuRepository = menuRepository;
        this.chefCuisinierRepository = chefCuisinierRepository;
        this.composantRepository = composantRepository;
        this.clientRepository = clientRepository;
        this.commandeRepository = commandeRepository;
    }

    // 1) Affecter un restaurant à une chaîne de restauration
    @Override
    public Restaurant affecterRestaurantAChaineRestauration(String nomRestaurant, String libelleChaine) {

        // 🔧 ICI : on utilise findByNom (et plus findByNomRestaurant)
        Restaurant restaurant = restaurantRepository.findByNom(nomRestaurant)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant introuvable : " + nomRestaurant));

        ChaineRestauration chaine = chaineRestaurationRepository.findByLibelle(libelleChaine)
                .orElseThrow(() -> new IllegalArgumentException("Chaine de restauration introuvable : " + libelleChaine));

        restaurant.setChaineRestauration(chaine);

        return restaurantRepository.save(restaurant);
    }

    // 2) Ajouter un restaurant avec ses menus associés
    @Override
    public Restaurant ajoutRestaurantEtMenuAssocies(Restaurant restaurant) {

        Set<Menu> menus = restaurant.getMenus();
        if (menus == null) {
            menus = new HashSet<>();
            restaurant.setMenus(menus);
        }

        for (Menu m : menus) {
            m.setRestaurant(restaurant);
            // prixTotal initialisé à 0
            m.setPrixTotal(BigDecimal.ZERO);
        }

        return restaurantRepository.save(restaurant);
    }

    // 3) Affecter un chef cuisinier à un menu
    @Override
    public ChefCuisinier affecterChefCuisinierAMenu(Long idChefCuisinier, Long idMenu) {

        ChefCuisinier chef = chefCuisinierRepository.findById(idChefCuisinier)
                .orElseThrow(() -> new IllegalArgumentException("Chef introuvable : " + idChefCuisinier));

        Menu menu = menuRepository.findById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable : " + idMenu));

        menu.setChefCuisinier(chef);

        menuRepository.save(menu);
        return chef;
    }

    // 4) Désaffecter un chef cuisinier d’un menu
    @Override
    public ChefCuisinier desaffecterChefCuisinierDuMenu(Long idMenu, Long idChefCuisinier) {

        ChefCuisinier chef = chefCuisinierRepository.findById(idChefCuisinier)
                .orElseThrow(() -> new IllegalArgumentException("Chef introuvable : " + idChefCuisinier));

        Menu menu = menuRepository.findById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable : " + idMenu));

        if (menu.getChefCuisinier() != null
                && menu.getChefCuisinier().getIdChefCuisinier().equals(idChefCuisinier)) {
            menu.setChefCuisinier(null);
        }

        menuRepository.save(menu);
        return chef;
    }

    // 5) Ajouter composants + mise à jour du prix du menu
    @Override
    public Menu ajoutComposantsEtMiseAjourPrixMenu(Set<Composant> composants, Long idMenu) {

        Menu menu = menuRepository.findById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable : " + idMenu));

        if (menu.getComposants() == null) {
            menu.setComposants(new HashSet<>());
        }

        // Ajouter les composants au menu
        for (Composant c : composants) {
            // si ton entité Composant n’a pas de setMenu(), on laisse juste la collection côté Menu
            menu.getComposants().add(c);
        }

        // persister les composants (si pas de cascade)
        composantRepository.saveAll(composants);

        // Calcul du prix total (en BigDecimal) = somme des prix des composants
        BigDecimal total = BigDecimal.ZERO;
        for (Composant c : menu.getComposants()) {
            if (c.getPrix() != null) {
                total = total.add(BigDecimal.valueOf(c.getPrix()));
            }
        }

        // Le prix total du menu ne doit pas dépasser 20 dinars
        if (total.compareTo(BigDecimal.valueOf(20)) > 0) {
            throw new IllegalArgumentException("Le prix total du menu (" + total + " dt) dépasse 20 dt.");
        }

        menu.setPrixTotal(total);

        return menuRepository.save(menu);
    }

    // 6) Ajouter une commande + l’associer au client et au menu
    @Override
    public void ajouterCommandeEtAffecterAClientEtMenu(Commande commande, String identifiant, String libelleMenu) {

        Client client = clientRepository.findByIdentifiant(identifiant)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable : " + identifiant));

        Menu menu = menuRepository.findByLibelle(libelleMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable : " + libelleMenu));

        commande.setClient(client);
        commande.setMenu(menu);

        // Prix du menu
        BigDecimal prixMenu = menu.getPrixTotal();
        if (prixMenu == null) {
            prixMenu = BigDecimal.ZERO;
        }

        // Pourcentage de remise
        BigDecimal remisePourcentage = commande.getRemisePourcentage();
        if (remisePourcentage == null) {
            remisePourcentage = BigDecimal.ZERO;
        }

        // total = prix du menu
        BigDecimal totalCommande = prixMenu;

        // remise = total * (remisePourcentage / 100)
        BigDecimal montantRemise = totalCommande
                .multiply(remisePourcentage)
                .divide(BigDecimal.valueOf(100));

        // alimenter la commande
        commande.setTotal(totalCommande);
        commande.setTotalRemise(montantRemise);

        // si la date n’est pas déjà mise, on met aujourd’hui
        if (commande.getDateCommande() == null) {
            commande.setDateCommande(LocalDate.now());
        }

        commandeRepository.save(commande);
    }
}
