package com.ofss.bankapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ofss.bankapp.beans.Customer;
import com.ofss.bankapp.beans.CustomerDocument;
import com.ofss.bankapp.beans.CustomerLoginHistory;
import com.ofss.bankapp.beans.CustomerSupportTicket;
import com.ofss.bankapp.component.TimeProvider;
import com.ofss.bankapp.dao.CustomerDocumentRepository;
import com.ofss.bankapp.dao.CustomerLoginHistoryRepository;
import com.ofss.bankapp.dao.CustomerRepository;
import com.ofss.bankapp.dao.CustomerSupportTicketRepository;
import com.ofss.bankapp.exception.NotFoundException;

@Service
public class CustomerService {

  private final CustomerRepository customerRepo;
  private final CustomerLoginHistoryRepository loginRepo;
  private final CustomerDocumentRepository docRepo;
  private final CustomerSupportTicketRepository ticketRepo;
  private final TimeProvider clock;

  public CustomerService(CustomerRepository customerRepo,
                         CustomerLoginHistoryRepository loginRepo,
                         CustomerDocumentRepository docRepo,
                         CustomerSupportTicketRepository ticketRepo,
                         TimeProvider clock) {
    this.customerRepo = customerRepo;
    this.loginRepo = loginRepo;
    this.docRepo = docRepo;
    this.ticketRepo = ticketRepo;
    this.clock = clock;
  }

  public Customer register(Customer c) {
    c.setCreatedAt(clock.now());
    if (c.getKycStatus() == null) c.setKycStatus("PENDING");
    return customerRepo.save(c);
  }

  public Customer get(Long id) {
    return customerRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("Customer not found: " + id));
  }

  public Customer update(Long id, Customer upd) {
    Customer c = get(id);
    c.setFirstName(upd.getFirstName());
    c.setLastName(upd.getLastName());
    c.setPhone(upd.getPhone());
    c.setAddressLine1(upd.getAddressLine1());
    c.setAddressLine2(upd.getAddressLine2());
    c.setCity(upd.getCity());
    c.setState(upd.getState());
    c.setPostalCode(upd.getPostalCode());
    c.setCountry(upd.getCountry());
    c.setUpdatedAt(clock.now());
    return customerRepo.save(c);
  }

  public void recordLogin(Long customerId, String ip, String device) {
    CustomerLoginHistory h = new CustomerLoginHistory();
    h.setCustomer(get(customerId));
    h.setLoginTime(clock.now());
    h.setIpAddress(ip);
    h.setDeviceInfo(device);
    loginRepo.save(h);
  }

  public CustomerDocument uploadDoc(Long customerId, CustomerDocument d) {
    d.setCustomer(get(customerId));
    d.setUploadedAt(clock.now());
    if (d.getVerifiedStatus() == null) d.setVerifiedStatus("PENDING");
    return docRepo.save(d);
  }

  public CustomerSupportTicket raiseTicket(Long customerId, CustomerSupportTicket t) {
    t.setCustomer(get(customerId));
    t.setStatus("OPEN");
    t.setCreatedAt(clock.now());
    return ticketRepo.save(t);
  }

  public List<CustomerLoginHistory> loginHistory() {
    return loginRepo.findAll();
  }
  public List<Customer> getAllCustomers() {
	    return customerRepo.findAll();
	}
  public void delete(Long id) {
	    if (!customerRepo.existsById(id)) {
	        throw new NotFoundException("Customer not found with id: " + id);
	    }
	    customerRepo.deleteById(id);
	}
  public List<CustomerDocument> getAllDocuments(Long customerId) {
	    Customer c = get(customerId); // reuse get() to validate customer exists
	    return docRepo.findAll().stream()
	            .filter(d -> d.getCustomer().getCustomerId().equals(c.getCustomerId()))
	            .toList();
	}
  
  public List<CustomerSupportTicket> getAllTickets(Long customerId) {
	    Customer c = get(customerId);
	    return ticketRepo.findAll().stream()
	            .filter(t -> t.getCustomer().getCustomerId().equals(c.getCustomerId()))
	            .toList();
	}
  public CustomerSupportTicket updateTicketStatus(Long customerId, Long ticketId, String status) {
	    Customer c = get(customerId);
	    CustomerSupportTicket t = ticketRepo.findById(ticketId)
	            .orElseThrow(() -> new NotFoundException("Ticket not found: " + ticketId));

	    if (!t.getCustomer().getCustomerId().equals(c.getCustomerId())) {
	        throw new NotFoundException("Ticket does not belong to customer " + customerId);
	    }

	    t.setStatus(status.toUpperCase());
	    t.setUpdatedAt(clock.now());
	    return ticketRepo.save(t);
	}




}
