package com.gxd.crm.dao;

import com.gxd.crm.base.BaseMapper;
import com.gxd.crm.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    public List<Map<String,Object>> queryAllRoles(Integer userId);
}