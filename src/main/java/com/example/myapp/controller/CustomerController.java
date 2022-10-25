package com.example.myapp.controller;

import com.example.myapp.dao.CustomerDao;
import com.example.myapp.ds.BookDto;
import com.example.myapp.ds.Customer;
import com.example.myapp.service.CartService;
import com.example.myapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerDao customerDao;

    private List<Integer> bookQuantityList;

    @GetMapping("/customer/login")
    public String login() {

        return "login";
    }


    @RequestMapping("/customer/register")
    public String register(Model model , BookDto bookDto) {
        this.bookQuantityList=bookDto.getBookNumberList();
        System.out.println("==================" + bookQuantityList);
        model.addAttribute("customer", new Customer());
        return "register";
    }


    @PostMapping("/customer/save-customer")
    public String saveCustomer(Customer customer, BindingResult result) {
        Set<BookDto> bookDtos = cartService.listCart();


        if (result.hasErrors()) {
            return "register";
        }
        Customer existingCustomer = customerService.findCustomerByName(customer.getName());
        if (existingCustomer == null) {
            customerService.register(customer, bookDtos);

        } else {
            customerService.saveCustomerBookOrder(bookDtos, existingCustomer);
        }
        return "redirect:/customer/login";
    }

}
