package com.hyw.platform.iservice;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyw.platform.mapper.LoanBalanceMapper;
import com.hyw.platform.model.LoanBalance;
import org.springframework.stereotype.Service;

/**
 * 贷款信息表 服务类
 */
@Service
public class LoanBalanceService extends ServiceImpl<LoanBalanceMapper, LoanBalance> implements IService<LoanBalance> {



}
