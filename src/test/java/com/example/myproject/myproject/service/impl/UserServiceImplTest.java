package com.example.myproject.myproject.service.impl;

import com.example.myproject.myproject.exceptions.UserServiceException;
import com.example.myproject.myproject.io.entity.AddressEntity;
import com.example.myproject.myproject.io.entity.UserEntity;
import com.example.myproject.myproject.io.repositories.UserRepository;
import com.example.myproject.myproject.shared.Utils;
import com.example.myproject.myproject.shared.dto.AddressDto;
import com.example.myproject.myproject.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserEntity userEntity;

    String userId = "dsf40d4f0ds40f";
    String encryptedPassword = "4808048s40d8d40sf8d0f4";


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Tauane");
        userEntity.setLastName("Costa");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail("tes@sdf.com");
        userEntity.setEmailVerificationToken("dfddf");
        userEntity.setEmailVerificationStatus(false);
        userEntity.setAddresses(getAddressesEntity());
    }

    @Test
    final void shouldGetUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("Tauane", userDto.getFirstName());
    }

    @Test
    final void shouldGetUsernameNotFoundException(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                ()->{
                    userService.getUser("test@test.com");
                }
                );
    }

    @Test
    final void shouldThrowAnUserServiceException(){
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Tauane");
        userDto.setLastName("Costa");
        userDto.setPassword("545454");
        userDto.setEmail("teste@oskosd.com");

        assertThrows(UserServiceException.class,
                ()->{
                    userService.createUser(userDto);
                }
        );
    }

    @Test
    final void shouldCreateUser(){

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("dfg56df0g15fd1g");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Tauane");
        userDto.setLastName("Costa");
        userDto.setPassword("545454");
        userDto.setEmail("teste@oskosd.com");

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());
        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils, times(storedUserDetails.getAddresses().size())).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("545454");
        verify(userRepository, times(1)).save(any(UserEntity.class));
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

    private List<AddressEntity> getAddressesEntity(){
        List<AddressDto> addresses = getAddressesDto();

        Type listType = new TypeToken<List<AddressEntity>>() {}.getType();

        return new ModelMapper().map(addresses, listType);
    }

}