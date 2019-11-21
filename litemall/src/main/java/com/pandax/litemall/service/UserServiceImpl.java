package com.pandax.litemall.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.*;
import com.pandax.reponseJson.UserAllAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author bao
 * @version 1.0
 * @date 2019/11/15
 * @time 17:28
 */


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    RegionMapper regionMapper;

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    FootprintMapper footprintMapper;

    @Autowired
    HistoryMapper historyMapper;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public Map selectUserList(Integer page, Integer limit, String sort, String order, String mobile, String username) {
        //分页工具进行分页
        PageHelper.startPage(page, limit);
        //username模糊查詢条件
        if (username != null) {
            username = "%" + username + "%";
        }
        //查询所有的数据
        List<User> users = userMapper.selectUserList(order, sort, mobile, username);
        //分页的信息
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        long total = userPageInfo.getTotal();
        //将数据封装到Map
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", users);
        return map;
    }

    @Override
    public Map selectAddressList(Integer page, Integer limit, String sort, String order, String name, Integer userId) {
        //分页
        PageHelper.startPage(page, limit);
        //username模糊查詢条件
        if (name != null) {
            name = "%" + name + "%";
        }

//        AddressExample addressExample = new AddressExample();
//        AddressExample.Criteria criteria = addressExample.createCriteria();
//        addressExample.setOrderByClause(sort + " " + order);
//        criteria.andNameLike(name);
//        List<Address> addresses1 = addressMapper.selectByExample(addressExample);

        //查询所有数据
        List<Address> addresses = addressMapper.selectAddressList(sort, order, name, userId);
        //查询省市县
        for (Address address : addresses) {
            String province = regionMapper.selectNameById(address.getProvinceId());
            String city = regionMapper.selectNameById(address.getCityId());
            String area = regionMapper.selectNameById(address.getAreaId());
            address.setProvince(province);
            address.setCity(city);
            address.setArea(area);
        }
        //分页的信息
        PageInfo<Address> userPageInfo = new PageInfo<>(addresses);
        long total = userPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", addresses);
        return map;
    }

    @Override
    public Map selectCollectList(Integer page, Integer limit, String sort, String order, Integer valueId, Integer userId) {
        //分页
        PageHelper.startPage(page, limit);
        //查询数据
        CollectExample example = new CollectExample();
        example.setOrderByClause(sort + " " + order);
        CollectExample.Criteria criteria = example.createCriteria();
        if (valueId != null) {//valueId不为空执行
            criteria = criteria.andValueIdEqualTo(valueId);
        }
        if (userId != null) {//userId 不为空执行
            criteria.andUserIdEqualTo(userId);
        }
        List<Collect> collects = collectMapper.selectByExample(example);
        //分页的信息
        PageInfo<Collect> userPageInfo = new PageInfo<>(collects);
        long total = userPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", collects);
        return map;
    }

    @Override
    public Map selectFootprintList(Integer page, Integer limit, String sort, String order, Integer goodsId, Integer userId) {
        //分页
        PageHelper.startPage(page, limit);
        //查询数据
        FootprintExample example = new FootprintExample();
        if (sort != null && order != null) {
            example.setOrderByClause(sort + " " + order);
        }
        FootprintExample.Criteria criteria = example.createCriteria();
        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        List<Footprint> footprints = footprintMapper.selectByExample(example);
        //分页的信息
        PageInfo<Footprint> userPageInfo = new PageInfo<>(footprints);
        long total = userPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", footprints);
        return map;
    }

    @Override
    public Map selectHistoryList(Integer page, Integer limit, String sort, String order, Integer userId, String keyword) {
        //分页
        PageHelper.startPage(page, limit);
        //查询数据
        HistoryExample historyExample = new HistoryExample();
        historyExample.setOrderByClause(sort + " " + order);
        HistoryExample.Criteria criteria = historyExample.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (keyword != null && !"".equals(keyword.trim())) {
            keyword = "%" + keyword + "%";
            criteria.andKeywordLike(keyword);
        }
        List<History> histories = historyMapper.selectByExample(historyExample);
        //查询所有的数量
        PageInfo<History> pageInfo = new PageInfo<>(histories);
        long total = pageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", histories);
        return map;
    }

    @Override
    public Map selectFeedbackList(Integer page, Integer limit, String sort, String order, Integer id, String username) {
        //分页
        PageHelper.startPage(page, limit);
        //查询数据
        FeedbackExample feedbackExample = new FeedbackExample();
        feedbackExample.setOrderByClause(sort + " " + order);
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();
        if (username != null && !"".equals(username.trim())) {
            username = "%" + username + "%";
            criteria.andUsernameLike(username);
        }
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        List<Feedback> feedbacks = feedbackMapper.selectByExample(feedbackExample);
        //查询所有的数量
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
        long total = pageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", feedbacks);
        return map;
    }

    @Override
    public int countUsers() {
        return (int) userMapper.countByExample(null);
    }

    @Override
    public User selectUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }


    @Override
    public List<Region> selectRegionList(Integer pid) {
        RegionExample example = new RegionExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Region> regions = regionMapper.selectByExample(example);
        return regions;
    }

    @Override
    public List<UserAllAddress> selectAllAddress() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Address> addresses = addressMapper.selectByExample(example);
        List<UserAllAddress> userAllAddresses = new ArrayList<>();
        for (Address address : addresses) {
            String province = regionMapper.selectNameById(address.getProvinceId());
            String city = regionMapper.selectNameById(address.getCityId());
            String area = regionMapper.selectNameById(address.getAreaId());
            String detailedAddress = province+city+area+" "+address.getAddress();
            UserAllAddress userAllAddress = new UserAllAddress();
            userAllAddress.setDetailedAddress(detailedAddress);
            userAllAddress.setId(address.getId());
            userAllAddress.setMobile(address.getMobile());
            userAllAddress.setName(address.getName());
            userAllAddress.setIsDefault(address.getIsDefault());
            userAllAddresses.add(userAllAddress);
        }

        return userAllAddresses;
    }


    /**
     * 新增反馈
     *
     * @param feedback
     * @return
     */
    @Override
    public int insertFeedBack(Feedback feedback) {
        return feedbackMapper.insertSelective(feedback);
    }

    /**
     * 根据用户Id获取足迹
     * @param id
     * @return
     */
    @Override
    public List<Footprint> selectFootprintByUserId(Integer id) {
        FootprintExample footprintExample = new FootprintExample();
        footprintExample.createCriteria().andUserIdEqualTo(id).andDeletedNotEqualTo(true);
        return footprintMapper.selectByExample(footprintExample);
    }


    /**
     *宝
     * 按照地址id查询收货地址
     * @param id 收货地址id
     * @return 查询地址数据
     */
    @Override
    public Map selectAddressById(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        String provinceName = regionMapper.selectNameById(address.getProvinceId());
        String cityName = regionMapper.selectNameById(address.getCityId());
        String areaName = regionMapper.selectNameById(address.getAreaId());
        Map<String,Object> map = new HashMap<>();
        map.put("isDefault",address.getIsDefault());
        map.put("areaId",address.getAreaId());
        map.put("id",address.getId());
        map.put("mobile",address.getMobile());
        map.put("name",address.getName());
        map.put("provinceId",address.getProvinceId());
        map.put("provinceName",provinceName);
        map.put("cityId",address.getCityId());
        map.put("cityName",cityName);
        map.put("areaId",address.getAreaId());
        map.put("areaName",areaName);
        map.put("address",address.getAddress());
        return map;
    }

    @Override
    public Integer updateAddressSave(Address address) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        address.setUserId(user.getId());
        //查询id和插入/修改数据
        Integer id = null;
        if(address.getId() == 0) {//新的数据插入，返回id
            addressMapper.insertSelective(address);
            id = address.getId();
        }else{
            addressMapper.updateByPrimaryKey(address);
            id = address.getId();
        }
        return id;
    }

    @Override
    public void deleteAddress(Integer id) {
        addressMapper.deleteByPrimaryKey(id);
    }


    @Override
    public int deleteFootprint(Integer id) {
        Footprint footprint = footprintMapper.selectByGoodsId(id);
        footprint.setDeleted(true);
        footprint.setUpdateTime(new Date());
        return footprintMapper.updateByPrimaryKey(footprint);
    }

}
