package com.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.admin.dto.AdminDTO;
import com.admin.exception.AdminNotFoundException;
import com.admin.model.Admin;
import com.admin.service.AdminService;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @GetMapping("/{Id}")
    public ResponseEntity<Admin> findById(@PathVariable int Id) {
        logger.info("Received request to find Admin with ID: {}", Id);
        try {
            Admin admin = adminService.findById(Id);
            logger.debug("Admin found: {}", admin);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Admin with ID {} not found. Error: {}", Id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody AdminDTO s) throws AdminNotFoundException {
        logger.info("Received request to add new Admin: {}", s);
        Admin createdAdmin = adminService.addAdmin(s);
        logger.debug("Admin created: {}", createdAdmin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int Id) {
        logger.info("Received request to delete Admin with ID: {}", Id);
        adminService.deleteById(Id);
        logger.info("Admin with ID {} deleted successfully", Id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/auth/{userName}")
    public ResponseEntity<Admin> authAdmin(@PathVariable String userName) {
        logger.info("Received authentication request for username: {}", userName);
        Admin admin = adminService.authAdmin(userName);
        logger.debug("Authenticated Admin: {}", admin);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }
}
