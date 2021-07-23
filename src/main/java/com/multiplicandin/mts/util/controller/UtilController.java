package com.multiplicandin.mts.util.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.multiplicandin.mts.util.service.UtilService;

@Controller
public class UtilController {
	
	@Autowired 
	private UtilService utilService;
	
	@Autowired
	private ServletContext context;  
	

	@GetMapping(value="/createPdf")
	public void createPdf(@) {
		List<> pdf=utilService.getAll();
		boolean isFlag=utilService.createPdf();
		if(isFlag) {
			String fullPath=request.getServletContext().getRealPath("/resources/reports/"+""+".pdf");
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
