package com.pandax.litemall.service;

import com.pandax.litemall.bean.Groupon;
import com.pandax.litemall.bean.GrouponExample;
import com.pandax.litemall.bean.User;
import com.pandax.litemall.mapper.GrouponMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GrouponServiceImpl implements GrouponService{
    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public List<Groupon> selectGrouponMy(Integer showType) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Groupon> groupons = null;
        //判断是否是发起的团购
        if(showType == 0) {//发起的团购
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andCreatorUserIdEqualTo(user.getId());
            groupons = grouponMapper.selectByExample(grouponExample);
        }else if(showType == 1){//参与的团购
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andUserIdEqualTo(user.getId());
            groupons = grouponMapper.selectByExample(grouponExample);
        }
        for (Groupon groupon : groupons) {//只保留父团购
            if(groupon.getId() != groupon.getGrouponId()){
                groupons.remove(groupon);
            }
        }
        return groupons;
    }
}
