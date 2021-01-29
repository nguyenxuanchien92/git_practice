package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView mav = new ModelAndView("customer/create");
        mav.addObject("customer", new Customer());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView listCustomer() {
        Iterable<Customer> customerList = customerService.findAll();
        ModelAndView mav = new ModelAndView("customer/list");
        mav.addObject("customerList", customerList);

        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView mav = null;
        if (customer != null) {
            mav = new ModelAndView("customer/edit");
            mav.addObject("customer", customer);
        } else {
            mav = new ModelAndView("/404");
        }

        return mav;
    }

    @PostMapping("/edit")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView mav = new ModelAndView("customer/edit");
        mav.addObject("customer", customer);
        mav.addObject("message", "Customer updated successfully");

        return mav;
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam(name = "id") Long id) {
        customerService.remove(id);

        return "redirect:/customers";
    }

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }
}
