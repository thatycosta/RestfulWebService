package com.example.myproject.myproject.ui.controller;

import com.example.myproject.myproject.service.UserService;
import com.example.myproject.myproject.service.impl.UserServiceImpl;
import com.example.myproject.myproject.shared.dto.AddressDto;
import com.example.myproject.myproject.shared.dto.UserDto;
import com.example.myproject.myproject.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    final String USER_ID = "s1d5s1dsd4sd5s4d";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDto = new UserDto();
        userDto.setFirstName("Tauane");
        userDto.setLastName("Costa");
        userDto.setEmail("tauane@costa.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setUserId(USER_ID);
        userDto.setAddresses(getAddressesDto());
        userDto.setEncryptedPassword("djfdjfiodjf5f4d5f4");
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
    }

    private List<AddressDto> getAddressesDto(){
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("vancouver");
        addressDto.setCountry("canada");
        addressDto.setPostalCode("9095090");
        addressDto.setStreetName("jsoidj");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("shipping");
        billingAddressDto.setCity("canada");
        billingAddressDto.setCountry("canada");
        billingAddressDto.setPostalCode("54154");
        billingAddressDto.setStreetName("dfsdfd");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;
    }
}
