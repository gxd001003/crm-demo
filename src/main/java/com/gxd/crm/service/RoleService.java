package com.gxd.crm.service;

import com.gxd.crm.base.BaseService;
import com.gxd.crm.dao.RoleMapper;
import com.gxd.crm.pojo.Role;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;

    /**
     * 查询所有角色
     * @return
     */
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }
}
