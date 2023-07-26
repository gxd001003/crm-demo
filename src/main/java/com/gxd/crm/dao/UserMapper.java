package com.gxd.crm.dao;

import com.gxd.crm.base.BaseMapper;
import com.gxd.crm.pojo.User;

public interface UserMapper extends BaseMapper<User,Integer> {
    User queryUserByName(String userName);
}