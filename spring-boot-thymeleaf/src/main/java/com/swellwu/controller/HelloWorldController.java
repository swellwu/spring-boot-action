package com.swellwu.controller;

import com.swellwu.util.DateFormatUtils;
import com.swellwu.util.MyCustomDateEditor;
import com.swellwu.vo.TimeVo1;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author swellwu
 * @create 2017-08-12-17:15
 */
@Controller
public class HelloWorldController {

    @RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView helloWorld(@Valid TimeVo1 vo, BindingResult result) {
        System.out.println("time1:" + DateFormatUtils.formatDate(vo.getTime1()));
        System.out.println("time2:" + DateFormatUtils.formatDate(vo.getTime2()));
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("name", "world!");
        modelAndView.addObject("now", new Date());
        modelAndView.addObject("vo", vo);
        return modelAndView;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //自定义类型转换
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
    }
}
