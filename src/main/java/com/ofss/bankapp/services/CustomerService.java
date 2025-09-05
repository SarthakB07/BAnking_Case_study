package com.ofss.bankapp.services;

import java.io.InputStream;
import java.nio.file.*;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

  // allowed types & max size
  private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
          "application/pdf", "image/png", "image/jpeg"
  );
  private static final long MAX_FILE_BYTES = 10 * 1024 * 1024; // 10MB

  @Value("${file.storage.base-dir}")
  private String baseDir;

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
    if (c.getCustomerNumber() == null || c.getCustomerNumber().isBlank()) {
      Long next = customerRepo.nextCustomerSeq();
      c.setCustomerNumber("CUST" + next);
    }
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
    c.setEmail(upd.getEmail());
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
//    h.setIpAddress(ip);
//    h.setDeviceInfo(device);
    loginRepo.save(h);
  }

  public Customer login(String email, String passwordHash) {
    Customer c = customerRepo.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Invalid email or password"));

    if (!c.getPasswordHash().equals(passwordHash)) {
      throw new NotFoundException("Invalid email or password");
    }

    // record successful login
    recordLogin(c.getCustomerId(), null, null);
    return c;
  }

  /**
   * Save uploaded file on disk and metadata in DB.
   */
  public CustomerDocument uploadDocFile(Long customerId, MultipartFile file,
                                        String documentType, String documentNumber) throws Exception {
    Customer customer = customerRepo.findById(customerId)
        .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));

    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Empty file");
    }

    if (file.getSize() > MAX_FILE_BYTES) {
      throw new IllegalArgumentException("File too large. Max " + (MAX_FILE_BYTES / (1024 * 1024)) + "MB");
    }

    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
      throw new IllegalArgumentException("Unsupported file type: " + contentType);
    }

    // create customer folder
    Path customerDir = Paths.get(baseDir, "customer_" + customerId).toAbsolutePath().normalize();
    Files.createDirectories(customerDir);

    // generate a safe filename
    String original = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
    String ext = "";
    int i = original.lastIndexOf('.');
    if (i > 0) ext = original.substring(i); // includes dot

    String fileName = UUID.randomUUID().toString() + ext;
    Path target = customerDir.resolve(fileName);

    try (InputStream in = file.getInputStream()) {
      Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
    }

    // create metadata and save
    CustomerDocument doc = new CustomerDocument();
    doc.setCustomer(customer);
    doc.setDocumentType(documentType);
    doc.setDocumentNumber(documentNumber);
    doc.setDocumentPath(Paths.get("customer_" + customerId).resolve(fileName).toString());
    doc.setUploadedAt(clock.now());
    doc.setVerifiedStatus("PENDING");
    doc.setOriginalFileName(original);
    doc.setContentType(contentType);
    doc.setFileSize(file.getSize());

    return docRepo.save(doc);
  }

  public CustomerDocument getCustomerDocument(Long docId) {
    return docRepo.findById(docId)
        .orElseThrow(() -> new NotFoundException("Document not found: " + docId));
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
    Customer c = get(customerId);
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

  public List<CustomerSupportTicket> getAllTicketsForAdmin() {
	    return ticketRepo.findAll();
	}
  
  public Customer findByEmail(String email) {
    return customerRepo.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Customer not found with email: " + email));
  }
  public CustomerSupportTicket updateAnyTicketStatus(Long ticketId, String status) {
	    CustomerSupportTicket t = ticketRepo.findById(ticketId)
	        .orElseThrow(() -> new NotFoundException("Ticket not found: " + ticketId));

	    t.setStatus(status.toUpperCase());
	    t.setUpdatedAt(clock.now());
	    return ticketRepo.save(t);
	}
  public List<CustomerDocument> getAllDocumentsForAdmin() {
	    return docRepo.findAll(); // return every document regardless of customer
	}
}
