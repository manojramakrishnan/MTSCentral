package com.multiplicandin.mts.util.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UtilService {

	boolean createPdf(List pdf, ServletContext context, HttpServletRequest request, HttpServletResponse response);




}
