package com.multiplicandin.mts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

	@RequestMapping(value= {"/index" }, method=RequestMethod.GET)
	public ModelAndView homeScreen() {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("/index.html");
		return modelAndView;
	}
}
