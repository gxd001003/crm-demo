package com.gxd.crm.controller;

import com.gxd.crm.base.BaseController;
import com.gxd.crm.base.ResultInfo;
import com.gxd.crm.enums.StateStatus;
import com.gxd.crm.pojo.SaleChance;
import com.gxd.crm.query.SaleChanceQuery;
import com.gxd.crm.service.SaleChanceService;
import com.gxd.crm.utils.CookieUtil;
import com.gxd.crm.utils.LoginUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @RequestMapping("/index")
    public String toSaleChance(){
        return "saleChance/sale_chance";
    }
    @RequestMapping("/toSaleChancePage")
    public String toSaleChancePage(){
        return "saleChance/add_update";
    }
    /**
     * 营销机会数据查询
     * @param saleChanceQuery
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest request){
        if(flag != null && flag == 1){
            saleChanceQuery.setState(StateStatus.STATED.getType());
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            saleChanceQuery.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request){
        String userName = CookieUtil.getCookieValue(request,"userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("success");
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChance(ids);
        return success("营销机会删除成功");
    }
    @RequestMapping("/updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return  success("更新成功");
    }
}
