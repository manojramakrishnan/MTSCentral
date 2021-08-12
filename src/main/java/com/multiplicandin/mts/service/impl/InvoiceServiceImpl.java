package com.multiplicandin.mts.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.service.InvoiceService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService{

	@Value("${invoice.logo.path}")
	private String Logo_Path;
	
	@Value("${invoice.template.path}")
	private String Invoice_Template;
	
	
	@Override
	public File generateInvoiceFor(CustomerOrder customerOrder, Locale forLanguageTag) throws IOException {

		File pdfFile = File.createTempFile("invoice", ".pdf");
		try(FileOutputStream pos= new FileOutputStream(pdfFile)){
			final JasperReport report= loadTemplate();
			final Map<String, Object> parameters = parameters(customerOrder,forLanguageTag);
			final JRBeanCollectionDataSource dataSource= new JRBeanCollectionDataSource(Collections.singletonList("invoice"));
			JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos );
			
			return pdfFile;

		}
		catch(final Exception e) {
			throw new RuntimeException(e);
		}
	}


	private Map<String, Object> parameters(CustomerOrder customerOrder, Locale forLanguageTag) {
		// TODO Auto-generated method stub
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("logo", getClass().getResourceAsStream(Logo_Path));
		parameters.put("customerOrder", customerOrder);
		parameters.put("REPORT_LOCALE", forLanguageTag);
		
		return parameters;
	}


	private JasperReport loadTemplate() throws JRException {
		// TODO Auto-generated method stub
		final InputStream reportInputStream =getClass().getResourceAsStream(Invoice_Template);
		final JasperDesign jasperDesign= JRXmlLoader.load(reportInputStream);
		
		return JasperCompileManager.compileReport(jasperDesign);
	}

}
