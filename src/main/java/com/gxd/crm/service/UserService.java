package com.gxd.crm.service;

import com.gxd.crm.base.BaseService;
import com.gxd.crm.dao.UserMapper;
import com.gxd.crm.model.UserModel;
import com.gxd.crm.pojo.User;
import com.gxd.crm.utils.AssertUtil;
import com.gxd.crm.utils.Md5Util;
import com.gxd.crm.utils.PhoneUtil;
import com.gxd.crm.utils.UserIDBase64;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService extends BaseService<User,Integer> {
    @Resource
    private UserMapper userMapper;

    /**
     * 登录业务处理
     * @param userName
     * @param userPwd
     */
    public UserModel userLogin(String userName, String userPwd){
        UserModel userModel = new UserModel();
        checkLoginParams(userName,userPwd);
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(null == user,"该用户不存在");
        checkUserPwd(userPwd,user.getUserPwd());
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(userName);
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 加密密码
     * @param userPwd
     * @param pwd
     */
    private void checkUserPwd(String userPwd, String pwd) {
        userPwd = Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(pwd),"密码不正确");
    }

    /**
     * 校验用户名和密码
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空");
    }

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassword(Integer userId,String oldPwd,String newPwd,String repeatPwd){
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == user,"该用户不存在");
        checkPasswordParams(user,oldPwd,newPwd,repeatPwd);
        user.setUserPwd(Md5Util.encode(newPwd));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"修改失败");

        //userMapper.updateByPrimaryKeySelective()
    }

    /**
     * 判断要修改密码的参数是否为空
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    private void checkPasswordParams(User user,String oldPwd, String newPwd, String repeatPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)),"原始密码不正确");
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空");
        AssertUtil.isTrue(user.getUserPwd().equals(Md5Util.encode(newPwd)),"新密码不能与原始密码一致");
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空");
        AssertUtil.isTrue(newPwd.equals(oldPwd),"确认密码要与新密码一致");
    }

    /**
     * 添加用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user){
        checkUserParams(user.getUserName(),user.getEmail(),user.getPhone(), null);
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user)!=1,"添加失败");
    }

    /**
     * 添加用户时的参数校验
     * @param userName
     * @param email
     * @param phone
     * @param id
     */
    private void checkUserParams(String userName, String email, String phone, Integer id) {
        AssertUtil.isTrue(userName == null||userName == "","用户名不能为空");
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user != null && !user.getId().equals(id),"用户名已存在，请重新输入");
        AssertUtil.isTrue(email == null || email == "","邮箱不能为空");
        AssertUtil.isTrue(phone == null || phone == "","手机号不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号格式不正确");
    }

    /**
     * 更新用户信息
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        AssertUtil.isTrue(user.getId() == null,"待更新记录不存在");
        User user1 = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(user == null,"待更新记录不存在");
        checkUserParams(user.getUserName(),user.getEmail(),user.getPhone(),user1.getId());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)!=1,"更新失败");
    }
}
