package org.codedecode.restaurantlisting.service;

import org.checkerframework.checker.units.qual.A;
import org.codedecode.restaurantlisting.dto.RestaurantDTO;
import org.codedecode.restaurantlisting.entity.Restaurant;
import org.codedecode.restaurantlisting.mapper.RestaurantMapper;
import org.codedecode.restaurantlisting.repository.RestaurantRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension .class)
public class RestaurantServiceTest {
    @Mock
    private RestaurantRepo restaurantRepo;

    @InjectMocks
    private RestaurantService restaurantService;

    private final RestaurantMapper restaurantMapper=RestaurantMapper.INSTANCE;


    //findAllRestaurants()-	Retrieves all restaurants and converts entities into DTOs

    @Test
    void testFindAllRestaurants() {

        // Arrange
        Restaurant restaurant1 = new Restaurant(
                1, "Food Palace", "MG Road", "Pune", "Family Restaurant");

        Restaurant restaurant2 = new Restaurant(
                2, "Sweet Palace", "MG Road", "Pune", "Family Restaurant");

        List<Restaurant> restaurantList =
                Arrays.asList(restaurant1, restaurant2);

        when(restaurantRepo.findAll()).thenReturn(restaurantList);

        // Act
        List<RestaurantDTO> response =
                restaurantService.findAllRestaurants();

        // Assert
        RestaurantDTO expectedRestaurantDTO=restaurantMapper.mapRestauranttoRestaurantDTO(restaurant1);
        assertEquals(2, response.size());
        assertEquals("Food Palace", response.get(0).getName());
        assertEquals("Sweet Palace", response.get(1).getName());

        assertEquals(expectedRestaurantDTO.getName(), response.get(0).getName());
        assertEquals(expectedRestaurantDTO.getAddress(), response.get(0).getAddress());

        verify(restaurantRepo).findAll();
    }

    //addRestaurantInDB()-	Converts DTO into entity, saves it, and converts it back
    @Test
    void testAddRestaurantInDB() {

        // Arrange
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                null, "Food Palace", "MG Road", "Pune", "Family Restaurant"
        );

        Restaurant savedRestaurant = new Restaurant(
                1, "Food Palace", "MG Road", "Pune", "Family Restaurant"
        );

        when(restaurantRepo.save(any(Restaurant.class)))
                .thenReturn(savedRestaurant);

        // Act
        RestaurantDTO response =
                restaurantService.addRestaurantInDB(restaurantDTO);

        // Assert
        assertEquals(1, response.getId());
        assertEquals("Food Palace", response.getName());
        assertEquals("MG Road", response.getAddress());
        assertEquals("Pune", response.getCity());
        assertEquals("Family Restaurant", response.getRestaurantDescription());

        //use ArgumentCaptor
        ArgumentCaptor<Restaurant>captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(restaurantRepo,times(1)).save(captor.capture());
        Restaurant capturedRestaurant=captor.getValue();
        assertEquals("Food Palace", capturedRestaurant.getName());
        assertEquals("MG Road", capturedRestaurant.getAddress());
        assertEquals("Pune", capturedRestaurant.getCity());
    }

    @Test
    void testFindRestaurantById() {
        //Arrange
        int id=1;
        Restaurant restaurant = new Restaurant(1, "Food Palace", "MG Road", "Pune", "Family Restaurant");
        when(restaurantRepo.findById(id)).thenReturn(Optional.of(restaurant));

        //Act
        ResponseEntity<RestaurantDTO>response = restaurantService.fetchRestaurantByID(id);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Food Palace", response.getBody().getName());

        verify(restaurantRepo).findById(id);
    }
    @Test
    void testFetchRestaurantByIDNotFound() {
        //Arrange
        int id=99;
        Restaurant restaurant = new Restaurant(1, "Food Palace", "MG Road", "Pune", "Family Restaurant");
        when(restaurantRepo.findById(id)).thenReturn(java.util.Optional.empty());

        //Act
        ResponseEntity<RestaurantDTO>response = restaurantService.fetchRestaurantByID(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());


        verify(restaurantRepo).findById(id);
    }


}

//
//Method	          -  Responsibility
//findAllRestaurants()-	Retrieves all restaurants and converts entities into DTOs
//addRestaurantInDB()-	Converts DTO into entity, saves it, and converts it back
//fetchRestaurantByID()-	Finds a restaurant by ID and returns 200 or 404