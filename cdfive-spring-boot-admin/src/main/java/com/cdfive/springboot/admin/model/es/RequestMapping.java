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
@Document(index = "request_mapping")
public class RequestMapping implements Serializable {

    private static final long serialVersionUID = 402675455487410868L;

    private String appName;

    private String mappingUri;

    private String httpMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updateTime;
}
