package com.ofss.bankapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.Customer;
import com.ofss.bankapp.beans.CustomerDocument;
import com.ofss.bankapp.beans.CustomerLoginHistory;
import com.ofss.bankapp.beans.CustomerSupportTicket;
import com.ofss.bankapp.services.CustomerService;

@Controller
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseBody
	public Customer register(@RequestBody Customer c) {
		return service.register(c);
	}

	@GetMapping
	@ResponseBody
	public List<Customer> getAllCustomers() {
		return service.getAllCustomers();
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Customer get(@PathVariable Long id) {
		return service.get(id);
	}

	@PutMapping("/{id}")
	@ResponseBody
	public Customer update(@PathVariable Long id, @RequestBody Customer c) {
		return service.update(id, c);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "Customer with ID " + id + " deleted successfully.";
	}

	@PostMapping("/login")
	@ResponseBody
	public Customer login(@RequestParam String email, @RequestParam String passwordHash) {
	    return service.login(email, passwordHash);
	}

	@PostMapping("/{id}/documents")
	@ResponseBody
	public CustomerDocument uploadDoc(@PathVariable Long id, @RequestBody CustomerDocument d) {
		return service.uploadDoc(id, d);
	}

	@PostMapping("/{id}/tickets")
	@ResponseBody
	public CustomerSupportTicket ticket(@PathVariable Long id, @RequestBody CustomerSupportTicket t) {
		return service.raiseTicket(id, t);
	}

	@GetMapping("/logins")
	@ResponseBody
	public List<CustomerLoginHistory> logins() {
		return service.loginHistory();
	}
	
	@GetMapping("/{id}/documents")
	@ResponseBody
	public List<CustomerDocument> getAllDocuments(@PathVariable Long id) {
	    return service.getAllDocuments(id);
	}
	@GetMapping("/{id}/tickets")
	@ResponseBody
	public List<CustomerSupportTicket> getAllTickets(@PathVariable Long id) {
	    return service.getAllTickets(id);
	}
	
	@PutMapping("/{customerId}/tickets/{ticketId}")
	@ResponseBody
	public CustomerSupportTicket updateTicketStatus(@PathVariable Long customerId,
	                                                @PathVariable Long ticketId,
	                                                @RequestParam String status) {
	    return service.updateTicketStatus(customerId, ticketId, status);
	}

	
	


	

}
