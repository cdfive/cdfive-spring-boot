package com.cdfive.springboot.admin.repository.es;

import com.cdfive.es.repository.AbstractEsRepository;
import com.cdfive.springboot.admin.model.es.RequestLog;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class RequestLogRepository extends AbstractEsRepository<RequestLog, String> {


}
