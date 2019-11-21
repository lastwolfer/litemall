package com.pandax.litemall.service;

import com.pandax.litemall.bean.Address;
import com.pandax.litemall.bean.User;
import com.pandax.litemall.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressMapper addressMapper;
    @Override
    public List<Address> addressList(User user) {
        List<Address> list=addressMapper.selectByUserId(user.getId());
        return list;
    }
}
