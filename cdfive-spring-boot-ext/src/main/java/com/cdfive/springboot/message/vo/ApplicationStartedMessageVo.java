package com.cdfive.springboot.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationStartedMessageVo implements Serializable {

    private static final long serialVersionUID = -3257473270253409310L;

    private String appName;

    private String appIp;

    private Integer appPort;

    private Long startingTime;

    private Long startedTime;

    private Long startTimeCostMs;

    private List<RequestMappingVo> requestMappings;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RequestMappingVo implements Serializable{

        private static final long serialVersionUID = -5943619350494749184L;

        private String mappingUri;

        private String httpMethod;
    }
}
