package com.cdfive.springboot.util;


import com.cdfive.springboot.common.PageRespVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class PageUtil {

    public static <VO, PO> PageRespVo<VO> buildPage(Page<PO> page, Function<PO, VO> transformer) {
        List<PO> list = page.getContent();
        List<VO> data = list.stream().map(o -> transformer.apply(o)).collect(Collectors.toList());
        PageRespVo<VO> respVo = new PageRespVo<VO>(page.getNumber() + 1, page.getSize(), (int) page.getTotalElements(), data);
        return respVo;
    }
}
