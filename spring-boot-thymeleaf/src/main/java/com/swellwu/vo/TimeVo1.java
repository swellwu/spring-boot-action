package com.swellwu.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * <p>Description:</p>
 *
 * @author xinjian.wu
 * @date 2017-08-28
 */
public class TimeVo1 {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date time1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date time2;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeVo1{");
        sb.append("time1=").append(time1);
        sb.append(", time2=").append(time2);
        sb.append('}');
        return sb.toString();
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }
}
