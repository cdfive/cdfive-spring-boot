package com.cdfive.springboot.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DemoRespVo implements Serializable {

    private static final long serialVersionUID = 7516148521432649233L;

    private String data;
}
