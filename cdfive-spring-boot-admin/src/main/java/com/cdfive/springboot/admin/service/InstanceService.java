package com.cdfive.springboot.admin.service;

import com.cdfive.springboot.admin.vo.PageQueryInstanceListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryInstanceListRespVo;
import com.cdfive.springboot.admin.vo.ReceiveHeartBeatReqVo;
import com.cdfive.springboot.admin.vo.ReceiveHeartBeatRespVo;
import com.cdfive.springboot.common.PageRespVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface InstanceService {

    ReceiveHeartBeatRespVo receiveHeartBeat(ReceiveHeartBeatReqVo reqVo);

    PageRespVo<PageQueryInstanceListRespVo> pageQueryInstanceList(PageQueryInstanceListReqVo reqVo);

    List<String> queryInstanceNameList();

    List<String> queryInstanceIpList(String appName);
}
