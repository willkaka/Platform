package com.hyw.platform.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyw.platform.mapper.CommonQueryConfigMapper;
import com.hyw.platform.model.CommonQueryConfig;
import org.springframework.stereotype.Service;

/**
 * 通用查询配置表 服务类
 */
@Service
public class CommonQueryConfigService extends ServiceImpl<CommonQueryConfigMapper, CommonQueryConfig> implements IService<CommonQueryConfig> {



}
