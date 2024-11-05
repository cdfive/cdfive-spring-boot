package com.cdfive.springboot.admin.model.es;

import com.cdfive.es.annotation.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(index = "instance")
public class Instance implements Serializable {

    private static final long serialVersionUID = -59699672075422768L;

    private String appName;

    private String appIp;

    private Integer appPort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startedTime;

    private Long startTimeCostMs;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime heartBeatTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updateTime;
}
