package com.cdfive.springboot.admin.repository.es;

import com.cdfive.es.repository.AbstractEsRepository;
import com.cdfive.springboot.admin.model.es.Instance;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class InstanceRepository extends AbstractEsRepository<Instance, String> {

}
