package com.pandax.litemall.service;

import com.pandax.litemall.bean.Address;
import com.pandax.litemall.bean.User;

import java.util.List;

public interface AddressService {
    List<Address> addressList(User user);

}
