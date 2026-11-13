package org.codedecode.restaurantlisting.cotroller;

import org.codedecode.restaurantlisting.dto.RestaurantDTO;
import org.codedecode.restaurantlisting.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;
    /*controller depends on RestaurantService, So Mockito creates a fake version of RestaurantService.*/

    @InjectMocks
    private RestaurantController restaurantController;
    /*It creates the controller instance and injects the mocked service into it.(Test class Controller)*/

    @Test
    void testFetchAllRestaurants(){
        //Arrange
        RestaurantDTO restaurant1=new RestaurantDTO(1,"Food Palace","MG Road","Pune","Family Restaurant");
        RestaurantDTO restaurant2 = new RestaurantDTO(
                2, "Spice Garden", "FC Road", "Pune", "Indian Restaurant");

    List<RestaurantDTO> mockRestaurants=Arrays.asList(restaurant1,restaurant2);

    when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);
    /* when().then()   This is called stubbing.*/
    //Act
        ResponseEntity<List<RestaurantDTO>>response = restaurantController.fetchAllRestaurants();

        //Asserts
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(2,response.getBody().size());

        assertEquals("Food Palace",response.getBody().get(0).getName());

        verify(restaurantService).findAllRestaurants();

    }

    /*Second api Test*/
    @Test
    void testSaveRestaurant(){
        //Arrange
        RestaurantDTO inputRestaurant=new RestaurantDTO(
                null,"Food Palace","MG Road","Pune","Indian Restaurant"
        );
        RestaurantDTO savedRestaurant = new RestaurantDTO(
                1,
                "Food Palace",
                "MG Road",
                "Pune",
                "Family Restaurant"
        );
    when(restaurantService.addRestaurantInDB(any(RestaurantDTO.class))).thenReturn(savedRestaurant);
    //Act
        ResponseEntity<RestaurantDTO>response = restaurantController.saveRestaurant(inputRestaurant);

        //Assert
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(1, response.getBody().getId());

        assertEquals("Food Palace", response.getBody().getName());

        verify(restaurantService).addRestaurantInDB(inputRestaurant);
    }
    @Test  /*fetchRestaurantById*/
    void testFetchRestaurantById(){
        //Arrange
        int id=1;

        RestaurantDTO restaurant1=new RestaurantDTO(1,"Food Palace","MG Road","Pune","Indian Restaurant");

        ResponseEntity<RestaurantDTO>mockResponse=new ResponseEntity<>(restaurant1,HttpStatus.OK);

        when(restaurantService.fetchRestaurantByID(id)).thenReturn(mockResponse);

        //Act
        ResponseEntity<RestaurantDTO>response = restaurantController.fetchRestaurantById(id);

        //Assert
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1,response.getBody().getId());
        assertEquals("Food Palace", response.getBody().getName());

    }

}
