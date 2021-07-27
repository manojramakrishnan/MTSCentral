package com.multiplicandin.mts.util.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Modules;

public interface UtilService {

	boolean createPdf(Modules modules, ServletContext context);

	public void filedownload(String fullPath, HttpServletResponse response, String fileName);




}
