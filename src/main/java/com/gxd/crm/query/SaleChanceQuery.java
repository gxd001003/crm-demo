package com.gxd.crm.query;

import com.gxd.crm.base.BaseQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleChanceQuery extends BaseQuery {

    //营销机会管理
    private String customerName;// 客户名
    private String  createMan;// 创建人
    private Integer state;// 分配状态
    //客户开发计划
    private Integer devResult;
    private Integer assignMan;
}
