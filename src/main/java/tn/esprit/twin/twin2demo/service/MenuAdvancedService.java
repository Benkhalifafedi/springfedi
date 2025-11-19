// src/main/java/tn/esprit/twin/twin2demo/service/MenuAdvancedService.java
package tn.esprit.twin.twin2demo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.twin.twin2demo.entities.*;
import tn.esprit.twin.twin2demo.repository.*; // ✅ bon package: repositories

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class MenuAdvancedService implements IMenuAdvancedService {

    private final RestaurantRepository restaurantRepo;
    private final ChaineRestaurationRepository chaineRepo;
    private final MenuRepository menuRepo;
    private final ChefCuisinierRepository chefRepo;
    private final ComposantRepository composantRepo;
    private final ClientRepository clientRepo;
    private final CommandeRepository commandeRepo;

    // ✅ Injection par constructeur EXPLICITE (pas de Lombok requis)
    @Autowired
    public MenuAdvancedService(RestaurantRepository restaurantRepo,
                               ChaineRestaurationRepository chaineRepo,
                               MenuRepository menuRepo,
                               ChefCuisinierRepository chefRepo,
                               ComposantRepository composantRepo,
                               ClientRepository clientRepo,
                               CommandeRepository commandeRepo) {
        this.restaurantRepo = restaurantRepo;
        this.chaineRepo = chaineRepo;
        this.menuRepo = menuRepo;
        this.chefRepo = chefRepo;
        this.composantRepo = composantRepo;
        this.clientRepo = clientRepo;
        this.commandeRepo = commandeRepo;
    }

    // ---------- 1) Affecter restaurant à chaine ----------
    @Override
    public Restaurant affecterRestaurantAChaineRestauration(String nomRestaurant, String libelleChaine) {
        // 🔧 ICI AUSSI : findByNom (et plus findByNomRestaurant)
        Restaurant r = restaurantRepo.findByNom(nomRestaurant)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant introuvable: " + nomRestaurant));

        ChaineRestauration ch = chaineRepo.findByLibelle(libelleChaine)
                .orElseThrow(() -> new IllegalArgumentException("Chaîne introuvable: " + libelleChaine));

        r.setChaineRestauration(ch);
        return restaurantRepo.save(r);
    }

    // ---------- 2) Ajouter restaurant + menus associés (prix menus = 0) ----------
    @Override
    public Restaurant ajoutRestaurantEtMenuAssocies(Restaurant restaurant) {
        Set<Menu> menus = Optional.ofNullable(restaurant.getMenus()).orElseGet(HashSet::new);
        for (Menu m : menus) {
            m.setRestaurant(restaurant);
            if (m.getComposants() == null) m.setComposants(new HashSet<>());
            if (m.getPrixTotal() == null) m.setPrixTotal(BigDecimal.ZERO);
        }
        restaurant.setMenus(menus);
        return restaurantRepo.save(restaurant);
    }

    // ---------- 3) Affecter chef à menu ----------
    @Override
    public ChefCuisinier affecterChefCuisinierAMenu(Long idChefCuisinier, Long idMenu) {
        ChefCuisinier chef = chefRepo.findById(idChefCuisinier)
                .orElseThrow(() -> new IllegalArgumentException("Chef introuvable: " + idChefCuisinier));
        Menu menu = menuRepo.findById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable: " + idMenu));
        menu.setChefCuisinier(chef);
        menuRepo.save(menu);
        return chef;
    }

    // ---------- 4) Désaffecter chef du menu ----------
    @Override
    public ChefCuisinier desaffecterChefCuisinierDuMenu(Long idMenu, Long idChefCuisinier) {
        Menu menu = menuRepo.findById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable: " + idMenu));
        if (menu.getChefCuisinier() == null || !menu.getChefCuisinier().getIdChefCuisinier().equals(idChefCuisinier)) {
            throw new IllegalStateException("Ce chef n'est pas affecté à ce menu.");
        }
        ChefCuisinier ancien = menu.getChefCuisinier();
        menu.setChefCuisinier(null);
        menuRepo.save(menu);
        return ancien;
    }

    // ---------- 5) Ajouter composants + MAJ prix (cap 20000) ----------
    @Override
    public Menu ajoutComposantsEtMiseAjourPrixMenu(Set<Composant> composants, Long idMenu) {
        Menu menu = menuRepo.findById(idMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable: " + idMenu));

        if (menu.getComposants() == null) {
            menu.setComposants(new HashSet<>());
        }

        // Persister/charger les composants
        Set<Composant> persisted = new HashSet<>();
        for (Composant c : composants) {
            if (c.getIdComposant() == null) {
                persisted.add(composantRepo.save(c));
            } else {
                persisted.add(composantRepo.findById(c.getIdComposant())
                        .orElseThrow(() -> new IllegalArgumentException("Composant inconnu: " + c.getIdComposant())));
            }
        }
        menu.getComposants().addAll(persisted);

        // Somme sans ambiguïté (adapte selon le type réel de prix dans Composant)
        BigDecimal total = BigDecimal.ZERO;
        for (Composant comp : menu.getComposants()) {
            Float pf = comp.getPrix();
            if (pf != null) {
                total = total.add(BigDecimal.valueOf(pf.doubleValue()));
            }
        }

        if (total.compareTo(new BigDecimal("20000")) > 0) {
            throw new IllegalStateException("Prix total du menu dépasse 20000");
        }

        menu.setPrixTotal(total);
        return menuRepo.save(menu);
    }

    // ---------- 6) Ajouter commande + lier client & menu (calcul totaux) ----------
    @Override
    public void ajouterCommandeEtAffecterAClientEtMenu(Commande commande, String identifiantClient, String libelleMenu) {
        Client client = clientRepo.findByIdentifiant(identifiantClient)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable: " + identifiantClient));
        Menu menu = menuRepo.findByLibelle(libelleMenu)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable: " + libelleMenu));

        BigDecimal total = (menu.getPrixTotal() == null) ? BigDecimal.ZERO : menu.getPrixTotal();

        BigDecimal remisePct = (commande.getRemisePourcentage() == null)
                ? BigDecimal.ZERO
                : commande.getRemisePourcentage();

        BigDecimal totalRemise = total.multiply(remisePct)
                .divide(new BigDecimal("100"), 3, RoundingMode.HALF_UP);

        commande.setMenu(menu);
        commande.setClient(client);
        commande.setTotal(total);
        commande.setTotalRemise(totalRemise);

        commandeRepo.save(commande);
    }
}
