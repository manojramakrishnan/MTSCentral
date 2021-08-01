package com.multiplicandin.mts.util.controller;


import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
