package com.swellwu.util;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.utils.time.DateFormatUtil;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * <p>Description:</p>
 *
 * @author xinjian.wu
 * @date 2017-08-28
 */
public class DateFormatUtilsTest {
    @Test
    public void parseDateTest() throws Exception {
        String str1 = "2017-08-28 15:22:11";
        String str2 = "2017-08-25";
        String str3 = "2017-08-25-1-1-1";

        Date date1 = DateFormatUtils.parseDate(str1);
        Date date2 = DateFormatUtils.parseDate(str2);
        Date date3 = DateFormatUtils.parseDate(str3);

        Assert.assertTrue(date1 != null);
        Assert.assertTrue(date2 != null);
        Assert.assertTrue(date3 != null);
    }

}