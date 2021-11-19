package com.multiplicandin.mts.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.service.CustomerService;

@Controller
public class PasswordController {


	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
    private JavaMailSender mailSender;
	

	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgotPassword");
    }

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {

		Optional<Customer> optional = customerService.finduserByEmail(userEmail);

		if (!optional.isPresent()) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} else {
			
			Customer customer = optional.get();
			customer.setResetToken(UUID.randomUUID().toString());

			
			customerService.update(customer);

			String appUrl = request.getScheme() + "://" + request.getServerName();
			
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("support@demo.com");
			passwordResetEmail.setTo(customer.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
					+ "/reset?token=" + customer.getResetToken());
			
			mailSender.send(passwordResetEmail);

			// Add success message to view
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
		}

		modelAndView.setViewName("forgotPassword");
		return modelAndView;

	}
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		
		Optional<Customer> customer = customerService.findCustomerByResetToken(token);

		if (customer.isPresent()) { // Token found in DB
			modelAndView.addObject("resetToken", token);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

		// Find the user associated with the reset token
		Optional<Customer> customer =customerService.findCustomerByResetToken(requestParams.get("token"));

		// This should always be non-null but we check just in case
		if (customer.isPresent()) {
			
			Customer resetCustomer = customer.get(); 
            
			// Set new password    
			resetCustomer.setPassword(encoder.encode(requestParams.get("password")));
            
			// Set the reset token to null so it cannot be used again
			resetCustomer.setResetToken(null);

			// Save user
			customerService.update(resetCustomer);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;
			
		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");	
		}
		
		return modelAndView;
   }
   
    // Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}
}
