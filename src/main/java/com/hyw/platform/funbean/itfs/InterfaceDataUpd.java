package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.ServiceInterface;
import com.hyw.platform.web.model.ServiceInterfaceData;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("interfaceDataUpd")
@Slf4j
public class InterfaceDataUpd extends RequestFunUnit<String, InterfaceDataUpd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceDataUpd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getServiceInterfaceDataId()),"id不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfaceName()),"接口名称不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDataType()),"数据类型dataType不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getFieldName()),"字段名称fieldName不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getFieldSource()),"字段来源fieldSource不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getFieldType()),"字段类型fieldType不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getFieldDesc()),"字段描述fieldDesc不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, InterfaceDataUpd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceInterfaceData serviceInterfaceData = dataService.getOne(new NQueryWrapper<ServiceInterfaceData>()
                .eq(ServiceInterfaceData::getServiceInterfaceDataId, dto.getServiceInterfaceDataId()));
        Assert.isTrue(serviceInterfaceData!=null,"接口字段不存在！");

        serviceInterfaceData.setInterfaceName(dto.getInterfaceName());
        serviceInterfaceData.setDataType(dto.getDataType());
        serviceInterfaceData.setFieldSeq(dto.getFieldSeq());
        serviceInterfaceData.setFieldName(dto.getFieldName());
        serviceInterfaceData.setFieldSource(dto.getFieldSource());
        serviceInterfaceData.setFieldType(dto.getFieldType());
        serviceInterfaceData.setFieldDesc(dto.getFieldDesc());

        dataService.updateById(serviceInterfaceData);
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String serviceInterfaceDataId;
        private String interfaceName;
        private String dataType;
        private Integer fieldSeq;
        private String fieldName;
        private String fieldSource;
        private String fieldType;
        private String fieldDesc;

        private String  defaultInterfaceName;
        private String  defaultDataType;
        private Integer defaultFieldSeq;
        private String  defaultFieldName;
        private String  defaultFieldSource;
        private String  defaultFieldType;
        private String  defaultFieldDesc;
    }
}
