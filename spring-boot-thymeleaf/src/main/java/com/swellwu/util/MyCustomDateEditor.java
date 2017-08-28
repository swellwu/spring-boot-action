package com.swellwu.util;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * <p>Description:</p>
 *
 * @author xinjian.wu
 * @date 2017-08-28
 */
public class MyCustomDateEditor extends PropertyEditorSupport {

    public void setAsText(String text) throws IllegalArgumentException {
        Date date = DateFormatUtils.parseDate(text);
        this.setValue(date);
    }

    public String getAsText() {
        Date value = (Date) this.getValue();
        return DateFormatUtils.formatDate(value);
    }
}
