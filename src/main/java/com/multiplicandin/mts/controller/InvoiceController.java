package com.multiplicandin.mts.controller;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_PDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.service.InvoiceService;
import com.multiplicandin.mts.service.OrderService;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class InvoiceController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	
	@RequestMapping(value = { "/invoice/generator" }, method = RequestMethod.POST,produces= "application/pdf")
	public ResponseEntity<InputStreamResource> invoiceGenerate(@RequestParam (name= "orderId") Integer orderId) throws IOException {
		CustomerOrder customerOrder =orderService.getOne(orderId);
		File  invoicePdf = invoiceService.generateInvoiceFor(customerOrder,Locale.forLanguageTag("en"));
		final HttpHeaders httpHeaders= getHttpHeaders(orderId,"en",invoicePdf );
		return new ResponseEntity<>(new InputStreamResource(new FileInputStream(invoicePdf)),httpHeaders,OK);
		
	}


	private HttpHeaders getHttpHeaders(Integer orderId, String lang, File invoicePdf) {
		// TODO Auto-generated method stub
		HttpHeaders responseHeaders= new HttpHeaders();
		responseHeaders.setContentType(APPLICATION_PDF);
		responseHeaders.setContentLength(invoicePdf.length());
		responseHeaders.setContentDispositionFormData("attachment", format("%s-%s.pdf",String.valueOf(orderId),lang));
		return responseHeaders;
	}
	
	@RequestMapping(value = { "/admin/invoice/forms" }, method = RequestMethod.GET)
	public ModelAndView invoiceForms() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/forms.html");
		return modelAndView;
		
	}

}
