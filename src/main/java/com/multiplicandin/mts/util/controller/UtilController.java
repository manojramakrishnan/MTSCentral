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
	

	@RequestMapping(value="/admin/createPdf",method= RequestMethod.GET)
	public void createPdf(@Valid List<Object> object, HttpServletRequest request,HttpServletResponse response) {
		boolean isFlag=false;
		Customer customer=new Customer();
		List<Customer> custome =new ArrayList<Customer>();
		for(Object obj : object) {
			customer=(Customer) obj;
			custome.add(customer);
		}
			List<Customer> createPdf=customerService.findAll();
			 isFlag=utilService.createPdf(createPdf,context);
			 if(isFlag) {
					String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"customers"+".pdf");
					filedownload(fullPath,response,".pdf");
				}
		
		
	}
	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		// TODO Auto-generated method stub
		File file=new File(fullPath);
		final int BUFFER_SIZE=4096;
		if(file.exists()) {
			try {
				FileInputStream inputStream=new FileInputStream(file);
				String mimeType=context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition","attachment;fileName="+ fileName);
				OutputStream outputStream=response.getOutputStream();
				byte[] buffer=new byte[BUFFER_SIZE];
				int bytesRead= -1;
				while((bytesRead=inputStream.read(buffer))!= -1){
					outputStream.write(buffer,0,bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
