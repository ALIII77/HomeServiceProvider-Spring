package com.example.ProjectV2.controller;

import com.example.ProjectV2.dto.Customer.SaveCustomerDto;
import com.example.ProjectV2.dto.Expert.ExpertSaveDto;
import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.service.CustomerService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.utils.SendEmail;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/save-user")
@RequiredArgsConstructor
public class SaveController {

    private final ExpertService expertService;

    private final CustomerService customerService;

    private final SendEmail sendEmail;


    @Transactional
    @PostMapping("/expert")
    public String save(@RequestBody ExpertSaveDto expertSaveDto)
            throws MessagingException, UnsupportedEncodingException {
        Expert expert = expertService.save(expertSaveDto.getExpert());
        sendEmail.sendSimpleMessage(expert);
        return "Please check your email to verify your account";
    }


    @GetMapping("expert-verify")
    public String verifyExpert(@RequestParam String code) {
        expertService.verify(code);
        return " expert verifying  and  status in AWAITING_CONFIRM";
    }




    @Transactional
    @PostMapping("/customer")
    public String save(@RequestBody SaveCustomerDto saveCustomerDto)
            throws MessagingException, UnsupportedEncodingException {
        Customer customer = customerService.save(saveCustomerDto.getCustomer());
        sendEmail.sendSimpleMessage(customer);
        return "Please check your email to verify your account";
    }




    @GetMapping("customer-verify")
    public String verifyCustomer(@RequestParam String code) {
        customerService.verify(code);
        return "customer  are  verifying!";
    }



}
