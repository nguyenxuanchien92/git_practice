package com.codegym.cms.controller;


import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView showListProvince() {
        ModelAndView mav = new ModelAndView("/province/list");

        Iterable<Province> provinces = provinceService.findAll();
        mav.addObject("provinces", provinces);

        return mav;
    }

    @PostMapping("/delete")
    public ModelAndView deleteProvinceById(@RequestParam(name = "id") Long id) {
        provinceService.remove(id);
        ModelAndView mav = new ModelAndView("redirect:/provinces");

        return mav;
    }

    @GetMapping("/edit")
    public ModelAndView showEditForm(@RequestParam(name = "id") Long id) {
        Province province = provinceService.findById(id);
        ModelAndView mav = new ModelAndView("/province/edit", "province", province);

        return mav;
    }

    @PostMapping("/edit")
    public ModelAndView editProvinceById(@ModelAttribute Province province) {
        provinceService.save(province);

        return new ModelAndView("redirect:/provinces");
    }

    @GetMapping("/create")
    public ModelAndView createShowForm() {
        return new ModelAndView("/province/create");
    }

    @PostMapping("/create")
    public ModelAndView createProvince(@ModelAttribute Province province) {
        provinceService.save(province);

        return new ModelAndView("redirect:/provinces");
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Province province = provinceService.findById(id);
        ModelAndView mav = null;

        if(province == null){
            mav = new ModelAndView("/404");
        }

        Iterable<Customer> customers = customerService.findAllByProvince(province);
        mav = new ModelAndView("/province/view");
        mav.addObject("province", province);
        mav.addObject("customers", customers);

        return mav;
    }
}
