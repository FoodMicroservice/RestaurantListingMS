package org.codedecode.restaurantlisting.service;

import org.codedecode.restaurantlisting.dto.RestaurantDTO;
import org.codedecode.restaurantlisting.entity.Restaurant;
import org.codedecode.restaurantlisting.mapper.RestaurantMapper;
import org.codedecode.restaurantlisting.repository.RestaurantRepo;
import org.codedecode.restaurantlisting.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.codedecode.restaurantlisting.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepo restaurantRepo;

    public List<RestaurantDTO> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        List<RestaurantDTO> restaurantDTOList=restaurants.stream().map(restaurant ->RestaurantMapper.INSTANCE.mapRestauranttoRestaurantDTO(restaurant)).collect(Collectors.toList());
        return restaurantDTOList;
    }
public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
        Restaurant savedRestaurant= restaurantRepo.save(RestaurantMapper.INSTANCE.mapRestaurantDTOtoRestaurant(restaurantDTO));
        //it save in db as Restaurant
    //but to return here we need DTO
    RestaurantDTO restaurantDTO1=RestaurantMapper.INSTANCE.mapRestauranttoRestaurantDTO(savedRestaurant);
    return restaurantDTO1;
}

public ResponseEntity<RestaurantDTO> fetchRestaurantByID(int id) {
        Optional <Restaurant> restaurant=restaurantRepo.findById(id);
        if (restaurant.isPresent()) {
            return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestauranttoRestaurantDTO(restaurant.get()), HttpStatus.OK);
        }
        return  ResponseEntity.notFound().build();

}
}
