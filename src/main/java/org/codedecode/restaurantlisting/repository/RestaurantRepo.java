package org.codedecode.restaurantlisting.repository;

import org.codedecode.restaurantlisting.entity.Restaurant;
import org.codedecode.restaurantlisting.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Integer> {

}
