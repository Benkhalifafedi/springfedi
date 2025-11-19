package tn.esprit.twin.twin2demo.service;

import tn.esprit.twin.twin2demo.entities.Restaurant;

import java.util.Date;
import java.util.List;

public interface IRestaurantService {
    Restaurant addRestaurant(Restaurant restaurant);
    Restaurant retrieveRestaurantById(Long idRestaurant);
    List<Restaurant> retrieveAllRestaurants();
    Restaurant updateRestaurant(Restaurant restaurant);
    void removeRestaurant(Long idRestaurant);

    // Recherches
    List<Restaurant> findByNomContaining(String keyword);
    List<Restaurant> findByNbPlacesMaxGreaterThan(Long nbPlaces);
    List<Restaurant> findByChaineRestaurationIdChaine(Long idChaineRestauration);
    List<Restaurant> findByDatePremiereVitesseAfter(Date date);
    List<Restaurant> findByNomAndNbPlacesMax(String nom, Long nbPlacesMax);
}
