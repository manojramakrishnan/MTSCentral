package com.multiplicandin.mts.util.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multiplicandin.mts.util.service.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;

	public UploadController () {
		
		}
	@RequestMapping(value={"/admin/upload"}, method= RequestMethod.POST)
	 public List<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws Exception {
		
		return uploadService.upload(file);
		 
	 }
}
