package org.codedecode.restaurantlisting.cotroller;

import org.codedecode.restaurantlisting.dto.RestaurantDTO;
import org.codedecode.restaurantlisting.entity.Restaurant;
import org.codedecode.restaurantlisting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin 
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/fetchAllRestaurants")
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants() {
        List<RestaurantDTO> allRestaurants= restaurantService.findAllRestaurants();
        return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDTO> saveRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurantAdded=restaurantService.addRestaurantInDB(restaurantDTO);
        return new ResponseEntity<>(restaurantAdded,HttpStatus.CREATED);
    }

    @GetMapping("fetchByID/{id}")
    public ResponseEntity<RestaurantDTO> fetchRestaurantById(@PathVariable int id) {
       return restaurantService.fetchRestaurantByID(id);
    }
}
