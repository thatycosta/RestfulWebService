package com.example.myproject.myproject.service.impl;

import com.example.myproject.myproject.io.entity.AddressEntity;
import com.example.myproject.myproject.io.entity.UserEntity;
import com.example.myproject.myproject.io.repositories.AddressRepository;
import com.example.myproject.myproject.io.repositories.UserRepository;
import com.example.myproject.myproject.service.AddressService;
import com.example.myproject.myproject.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity:addresses){
            returnValue.add( modelMapper.map(addressEntity, AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if(addressEntity != null){
            returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
        }

        return returnValue;
    }
}
