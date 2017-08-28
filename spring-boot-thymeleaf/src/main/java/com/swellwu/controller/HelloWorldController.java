package com.swellwu.controller;

import com.swellwu.vo.TimeVo1;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.utils.time.DateFormatUtil;
import org.springside.modules.utils.time.DateUtil;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author swellwu
 * @create 2017-08-12-17:15
 */
@Controller
public class HelloWorldController {

    @RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView helloWorld(@Valid TimeVo1 vo, BindingResult result) {
        System.out.println("time1:" + formatDate(vo.getTime1()));
        System.out.println("time2:" + formatDate(vo.getTime2()));
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("name", "world!");
        modelAndView.addObject("now", new Date());
        modelAndView.addObject("vo", vo);
        return modelAndView;
    }

    String formatDate(Date date) {
        if(date==null) return null;
        String dateStr = "";
        try {
            dateStr = DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND, date);
        } catch (Exception e) {
            dateStr = DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND, date);
        }
        return dateStr;
    }
}
