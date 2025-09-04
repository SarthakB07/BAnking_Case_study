package com.ofss.bankapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ofss.bankapp.beans.Customer;
import com.ofss.bankapp.beans.CustomerDocument;
import com.ofss.bankapp.beans.CustomerLoginHistory;
import com.ofss.bankapp.beans.CustomerSupportTicket;
import com.ofss.bankapp.beans.LoginRequest;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Customer customer = service.findByEmail(loginRequest.getEmail());

        if (customer != null && customer.getPasswordHash().equals(loginRequest.getPasswordHash())) {
            // Create a response object with customer details
            Map<String, Object> response = new HashMap<>();
            response.put("customerId", customer.getId());
            response.put("email", customer.getEmail());
            response.put("firstName", customer.getFirstName());
            // Add other fields if needed (e.g., token)
            return ResponseEntity.ok(response);
        }
        // Return error message with 401 status
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid email or password");
    }

    // -----------------------
    // OLD JSON-based upload (still works if you want)
    @PostMapping("/{id}/documents")
    @ResponseBody
    public CustomerDocument uploadDoc(@PathVariable Long id,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("documentType") String documentType,
                                      @RequestParam("documentNumber") String documentNumber) throws Exception {
        return service.uploadDocFile(id, file, documentType, documentNumber);
    }

    @GetMapping("/{id}/documents")
    @ResponseBody
    public List<CustomerDocument> getAllDocuments(@PathVariable Long id) {
        return service.getAllDocuments(id);
    }
    

    // File Download
    @GetMapping("/{customerId}/documents/{docId}/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long customerId,
                                                   @PathVariable Long docId) throws IOException {
        CustomerDocument doc = service.getCustomerDocument(docId);

        // build absolute path (your base dir is C:\CaseStudy_Uploads)
        Path filePath = Paths.get("C:\\CaseStudy_Uploads")
                .resolve(doc.getDocumentPath())
                .toAbsolutePath()
                .normalize();

        byte[] data = Files.readAllBytes(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + doc.getOriginalFileName() + "\"")
                .contentType(MediaType.parseMediaType(doc.getContentType()))
                .body(data);
    }

    // -----------------------
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
