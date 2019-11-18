package com.pandax.litemall.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 21:14
 */

@Component
public class Date2String implements Converter<Date, String> {

    @Override
    public String convert(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(date);
        return format;
    }
}
