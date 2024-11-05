package com.cdfive.springboot.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DemoReqVo implements Serializable {

    private static final long serialVersionUID = 7177288567592077959L;

    private Long id;

    private String name;

    private ClientType clientType;

    @AllArgsConstructor
    @Getter
    private static enum ClientType {

        WX("微信"),
        ZFB("支付宝"),
        ;

        private String desc;
    }
}
