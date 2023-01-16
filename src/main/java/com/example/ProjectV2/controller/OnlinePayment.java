package com.example.ProjectV2.controller;


import com.example.ProjectV2.utils.SendEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class OnlinePayment {


    @RequestMapping("/online-payment")
    public ModelAndView paymentOnline (){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }
}
