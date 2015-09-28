package com.qinyuan.lib.mvc.tag;

import com.qinyuan.lib.lang.time.DateUtils;

public class BirthdaySelect extends DateSelect {
    public BirthdaySelect(){
        setStartYear(1896);
        setEndYear(DateUtils.currentYear());
    }
}
