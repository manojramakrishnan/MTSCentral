package com.multiplicandin.mts.util.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.util.service.UtilService;

@Controller
public class UtilController {
	
	@Autowired 
	private UtilService utilService;
	
	@Autowired
	private ServletContext context;  
	
	@Autowired
	private CustomerService customerService;
	

	
}
