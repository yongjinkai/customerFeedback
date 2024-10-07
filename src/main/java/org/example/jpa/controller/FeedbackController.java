package org.example.jpa.controller;

import org.example.jpa.exception.ResourceNotFoundException;
import org.example.jpa.model.Customer;
import org.example.jpa.model.Feedback;
import org.example.jpa.repository.CustomerRepository;
import org.example.jpa.service.CustomerService;
import org.example.jpa.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Object> allFeedback() {
        System.out.println("command triggered");
        return new ResponseEntity<>(feedbackService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> saveFeedback(@PathVariable("id") Long customerId, @RequestBody Feedback feedback) {
        Customer customer = customerService.findById(customerId).orElseThrow(() -> new ResourceNotFoundException());
        Feedback _feedback = new Feedback(customer, feedback.getDescription());


        return new ResponseEntity<>(feedbackService.save(_feedback), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFeedback(@PathVariable("id") Long feedbackId, @RequestBody Feedback feedback) {
        Feedback updatedFeedback = feedbackService.findById(feedbackId).orElseThrow(() -> new ResourceNotFoundException());

        updatedFeedback.setDescription(feedback.getDescription());
        return new ResponseEntity<>(feedbackService.save(updatedFeedback), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable("id") Long feedbackId) {
        Feedback checkFeedback = feedbackService.findById(feedbackId).orElseThrow(() -> new ResourceNotFoundException());
        feedbackService.deleteById(feedbackId);
        String response = String.format("Feedback id %s deleted successfully", feedbackId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/count")
    public ResponseEntity<Object> countFeedback() {
        long count = feedbackService.count();
        if (count == 0)
            return new ResponseEntity<>("No feedback found", HttpStatus.NOT_FOUND);
        Map<String, Object> totalFeedback = new HashMap<>();
        totalFeedback.put("total", count);
        return new ResponseEntity<>(totalFeedback, HttpStatus.OK);
    }

    @DeleteMapping("/customer/{id}")          //Additional method to delete all feedback by a customer
    public ResponseEntity<Object> deleteCustFeedback(@PathVariable("id") Long customerId) {
        Customer customer = customerService.findById(customerId).orElseThrow(()->new ResourceNotFoundException());

        List<Feedback> listFeedback = feedbackService.findAll();
        for (Feedback feedback : listFeedback) {
            if (feedback.getCustomer().getId() == customerId) {
                feedbackService.deleteById(feedback.getId());
            }
        }
        String response = String.format("All feedback from %s deleted",
                customer.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
