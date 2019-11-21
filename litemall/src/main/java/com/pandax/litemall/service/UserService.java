package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;
import com.pandax.reponseJson.UserAllAddress;

import java.util.List;
import java.util.Map;

public interface UserService {

   /**
    * 查詢所有的用戶
    *
    * @param page
    * @param limit
    * @param sort
    * @param order
    * @param mobile
    * @param username
    * @return
    */
   Map selectUserList(Integer page, Integer limit, String sort, String order, String mobile, String username);


   Map selectAddressList(Integer page, Integer limit, String sort, String order, String name, Integer userId);

   Map selectCollectList(Integer page, Integer limit, String sort, String order, Integer valueId, Integer userId);

   Map selectFootprintList(Integer page, Integer limit, String sort, String order, Integer goodsId, Integer userId);

   Map selectHistoryList(Integer page, Integer limit, String sort, String order, Integer userId, String keyword);

   Map selectFeedbackList(Integer page, Integer limit, String sort, String order, Integer id, String username);

   int countUsers();

    User selectUserByUsername(String usrname);


    List<Region> selectRegionList(Integer pid);

    List<UserAllAddress> selectAllAddress();

   int insertFeedBack(Feedback feedback);

   List<Footprint> selectFootprintByUserId(Integer id);


    Map selectAddressById(Integer id);

    Integer updateAddressSave(Address address);

    void deleteAddress(Integer id);

   int deleteFootprint(Integer id);

}

