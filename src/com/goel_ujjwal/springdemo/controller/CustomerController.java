package com.goel_ujjwal.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goel_ujjwal.springdemo.entity.Customer;
import com.goel_ujjwal.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject the customer service
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String ListCustomers(Model model) {

		List<Customer> theCustomers = customerService.getCustomers();

		model.addAttribute("customers", theCustomers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		Customer theCustomer = new Customer();

		model.addAttribute("customer", theCustomer);

		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model model) {

		// get customer from db using theId
		Customer theCustomer = customerService.getCustomer(theId);

		// save in model attribute to pre-populate the form
		model.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {

		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model model) {

		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

		model.addAttribute("customers", theCustomers);

		return "list-customers";
	}
}
