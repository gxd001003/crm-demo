package com.gxd.crm.query;

import com.gxd.crm.base.BaseQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQuery extends BaseQuery {
    private String userName;
    private String email;
    private String phone;
}
