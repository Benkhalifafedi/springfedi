package tn.esprit.twin.twin2demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.twin.twin2demo.entities.Restaurant;
import tn.esprit.twin.twin2demo.repository.RestaurantRepository;

import java.util.Date;
import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant retrieveRestaurantById(Long idRestaurant) {
        return restaurantRepository.findById(idRestaurant).orElse(null);
    }

    @Override
    public List<Restaurant> retrieveAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        if (restaurant.getIdRestaurant() == null) return null;
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void removeRestaurant(Long idRestaurant) {
        restaurantRepository.deleteById(idRestaurant);
    }

    @Override
    public List<Restaurant> findByNomContaining(String keyword) {
        return restaurantRepository.findByNomContainingIgnoreCase(keyword);
    }

    @Override
    public List<Restaurant> findByNbPlacesMaxGreaterThan(Long nbPlaces) {
        return restaurantRepository.findByNbPlacesMaxGreaterThan(nbPlaces);
    }

    @Override
    public List<Restaurant> findByChaineRestaurationIdChaine(Long idChaineRestauration) {
        // 🔧 méthode correcte (avec _)
        return restaurantRepository.findByChaineRestauration_IdChaine(idChaineRestauration);
    }

    @Override
    public List<Restaurant> findByDatePremiereVitesseAfter(Date date) {
        return restaurantRepository.findByDatePremiereVitesseAfter(date);
    }

    @Override
    public List<Restaurant> findByNomAndNbPlacesMax(String nom, Long nbPlacesMax) {
        return restaurantRepository.findByNomAndNbPlacesMax(nom, nbPlacesMax);
    }
}
