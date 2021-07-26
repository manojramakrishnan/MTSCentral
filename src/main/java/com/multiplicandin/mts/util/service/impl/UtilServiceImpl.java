package com.multiplicandin.mts.util.service.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Modules;
import com.multiplicandin.mts.util.service.UtilService;

@Service("utilService")
public class UtilServiceImpl implements UtilService {

	@Override
	public boolean createPdf(Modules modules, ServletContext context) {
		
		return createCustomerPdf(modules.getCustomer(),context);
		
	}
	private boolean createCustomerPdf(List<Customer> customer, ServletContext context) {
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		try {
			String filePath=context.getRealPath("/resources/reports");
			File file=new File(filePath);
			boolean exists=new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"customers"+".pdf"));
			document.open();
			Font mainFont=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Paragraph paragraph=new Paragraph("All Customers",mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			PdfPTable table=new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);
			Font tableHeader=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Font tableBody=FontFactory.getFont("Arial",9,BaseColor.BLACK);
			float[] columnWidths= {2f,2f,2f,2f};
			table.setWidths(columnWidths);
			PdfPCell customerId=new PdfPCell(new Paragraph("Customer Id",tableHeader));
			customerId.setBorderColor(BaseColor.BLACK);
			customerId.setPaddingLeft(10);
			customerId.setHorizontalAlignment(Element.ALIGN_CENTER);
			customerId.setVerticalAlignment(Element.ALIGN_CENTER);
			customerId.setBackgroundColor(BaseColor.GRAY);
			customerId.setExtraParagraphSpace(5f);
			table.addCell(customerId);
			PdfPCell name=new PdfPCell(new Paragraph("Name",tableHeader));
			name.setBorderColor(BaseColor.BLACK);
			name.setPaddingLeft(10);
			name.setHorizontalAlignment(Element.ALIGN_CENTER);
			name.setVerticalAlignment(Element.ALIGN_CENTER);
			name.setBackgroundColor(BaseColor.GRAY);
			name.setExtraParagraphSpace(5f);
			table.addCell(name);
			PdfPCell email=new PdfPCell(new Paragraph("Email",tableHeader));
			email.setBorderColor(BaseColor.BLACK);
			email.setPaddingLeft(10);
			email.setHorizontalAlignment(Element.ALIGN_CENTER);
			email.setVerticalAlignment(Element.ALIGN_CENTER);
			email.setBackgroundColor(BaseColor.GRAY);
			email.setExtraParagraphSpace(5f);
			table.addCell(email);
			
			for(Customer customers: customer) {
				PdfPCell customerIdValue=new PdfPCell(new Paragraph(String.valueOf(customers.getId()),tableBody));
				customerIdValue.setBorderColor(BaseColor.BLACK);
				customerIdValue.setPaddingLeft(10);
				customerIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				customerIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
				customerIdValue.setBackgroundColor(BaseColor.WHITE);
				customerIdValue.setExtraParagraphSpace(5f);
				table.addCell(customerIdValue);
				PdfPCell nameValue=new PdfPCell(new Paragraph(customers.getName(),tableBody));
				nameValue.setBorderColor(BaseColor.BLACK);
				nameValue.setPaddingLeft(10);
				nameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				nameValue.setVerticalAlignment(Element.ALIGN_CENTER);
				nameValue.setBackgroundColor(BaseColor.WHITE);
				nameValue.setExtraParagraphSpace(5f);
				table.addCell(nameValue);
				PdfPCell emailValue=new PdfPCell(new Paragraph(customers.getEmail(),tableBody));
				emailValue.setBorderColor(BaseColor.BLACK);
				emailValue.setPaddingLeft(10);
				emailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				emailValue.setVerticalAlignment(Element.ALIGN_CENTER);
				emailValue.setBackgroundColor(BaseColor.WHITE);
				emailValue.setExtraParagraphSpace(5f);
				table.addCell(emailValue);
					}
			document.add(table);
			document.close();
			writer.close();
			return true;
		}catch(Exception e) {
			return false;
		}

	}

	}

	



