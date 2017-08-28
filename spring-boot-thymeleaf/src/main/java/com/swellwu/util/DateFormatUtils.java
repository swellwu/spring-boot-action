package com.swellwu.util;

import org.springside.modules.utils.time.DateFormatUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>Description:</p>
 *
 * @author xinjian.wu
 * @date 2017-08-28
 */
public class DateFormatUtils {

    public static String formatDate(Date date) {
        return DateFormatUtils.formatDate(date,DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) return null;
        return DateFormatUtil.formatDate(pattern, date);
    }

    public static Date parseDate(String date) {
        Date retVal = null;
        try {
            retVal = DateFormatUtil.pareDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND, date);
        } catch (ParseException e) {
            try {
                retVal = DateFormatUtil.pareDate(DateFormatUtil.PATTERN_ISO_ON_DATE, date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return retVal;
    }
}
