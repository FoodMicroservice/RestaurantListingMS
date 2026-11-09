package org.codedecode.restaurantlisting.mapper;

import org.codedecode.restaurantlisting.dto.RestaurantDTO;
import org.codedecode.restaurantlisting.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INSTANCE= Mappers.getMapper(RestaurantMapper.class);


    // restaurant to DTO
   Restaurant mapRestaurantDTOtoRestaurant(RestaurantDTO restaurantDTO);

   RestaurantDTO mapRestauranttoRestaurantDTO(Restaurant restaurant);

}
