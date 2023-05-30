package com.hyw.platform.web.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors( chain = true )
public class NextOprDto {

    private boolean showSw;
    private List<EventInfo> eventInfoList = new ArrayList<>();

}
