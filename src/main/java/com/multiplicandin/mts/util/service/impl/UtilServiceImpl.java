package com.multiplicandin.mts.util.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.StoreProduct;
import com.multiplicandin.mts.util.service.UtilService;

@Service("utilService")
public class UtilServiceImpl implements UtilService {
	
	@Autowired
	private ServletContext context;
	

	 public void filedownload(String fullPath, HttpServletResponse response, String fileName) {
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
	@Override
	public boolean createPdf(Modules modules, ServletContext context) {
		if(modules.getCustomer() != null) {
			return createCustomerPdf(modules.getCustomer(),context);

		}
		else if (modules.getProduct() != null) {
			return createProductPdf(modules.getProduct(),context);
		}
		else {
			return createStoreProductPdf(modules.getStoreProduct(),context);
		}
				
		
		
	}
	
			
	
	private boolean createCustomerPdf(List<Customer> customer, ServletContext context) {
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		System.out.println("Customer List " + customer.size());
		
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
			PdfPTable table=new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);
			Font tableHeader=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Font tableBody=FontFactory.getFont("Arial",9,BaseColor.BLACK);
			float[] columnWidths= {2f,2f,2f};
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
	private boolean createStoreProductPdf(List<StoreProduct> storeProduct, ServletContext context) {
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		try {
			String filePath=context.getRealPath("/resources/reports");
			File file=new File(filePath);
			boolean exists=new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"storeproducts"+".pdf"));
			document.open();
			Font mainFont=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Paragraph paragraph=new Paragraph("All StoreProducts",mainFont);
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
			PdfPCell storeProductId=new PdfPCell(new Paragraph("StoreProduct Id",tableHeader));
			storeProductId.setBorderColor(BaseColor.BLACK);
			storeProductId.setPaddingLeft(10);
			storeProductId.setHorizontalAlignment(Element.ALIGN_CENTER);
			storeProductId.setVerticalAlignment(Element.ALIGN_CENTER);
			storeProductId.setBackgroundColor(BaseColor.GRAY);
			storeProductId.setExtraParagraphSpace(5f);
			table.addCell(storeProductId);
			PdfPCell name=new PdfPCell(new Paragraph("Name",tableHeader));
			name.setBorderColor(BaseColor.BLACK);
			name.setPaddingLeft(10);
			name.setHorizontalAlignment(Element.ALIGN_CENTER);
			name.setVerticalAlignment(Element.ALIGN_CENTER);
			name.setBackgroundColor(BaseColor.GRAY);
			name.setExtraParagraphSpace(5f);
			table.addCell(name);
			PdfPCell price=new PdfPCell(new Paragraph("Price",tableHeader));
			price.setBorderColor(BaseColor.BLACK);
			price.setPaddingLeft(10);
			price.setHorizontalAlignment(Element.ALIGN_CENTER);
			price.setVerticalAlignment(Element.ALIGN_CENTER);
			price.setBackgroundColor(BaseColor.GRAY);
			price.setExtraParagraphSpace(5f);
			table.addCell(price);
			PdfPCell stock=new PdfPCell(new Paragraph("Stock",tableHeader));
			stock.setBorderColor(BaseColor.BLACK);
			stock.setPaddingLeft(10);
			stock.setHorizontalAlignment(Element.ALIGN_CENTER);
			stock.setVerticalAlignment(Element.ALIGN_CENTER);
			stock.setBackgroundColor(BaseColor.GRAY);
			stock.setExtraParagraphSpace(5f);
			table.addCell(stock);
			
			for(StoreProduct storeProducts: storeProduct) {
				PdfPCell storeProductIdValue=new PdfPCell(new Paragraph(String.valueOf(storeProducts.getId()),tableBody));
				storeProductIdValue.setBorderColor(BaseColor.BLACK);
				storeProductIdValue.setPaddingLeft(10);
				storeProductIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				storeProductIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
				storeProductIdValue.setBackgroundColor(BaseColor.WHITE);
				storeProductIdValue.setExtraParagraphSpace(5f);
				table.addCell(storeProductIdValue);
				PdfPCell nameValue=new PdfPCell(new Paragraph(storeProducts.getProduct().getProduct_name(),tableBody));
				nameValue.setBorderColor(BaseColor.BLACK);
				nameValue.setPaddingLeft(10);
				nameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				nameValue.setVerticalAlignment(Element.ALIGN_CENTER);
				nameValue.setBackgroundColor(BaseColor.WHITE);
				nameValue.setExtraParagraphSpace(5f);
				table.addCell(nameValue);
				PdfPCell priceValue=new PdfPCell(new Paragraph(String.valueOf(storeProducts.getPrice()),tableBody));
				priceValue.setBorderColor(BaseColor.BLACK);
				priceValue.setPaddingLeft(10);
				priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
				priceValue.setBackgroundColor(BaseColor.WHITE);
				priceValue.setExtraParagraphSpace(5f);
				table.addCell(priceValue);
				PdfPCell stockValue=new PdfPCell(new Paragraph(String.valueOf(storeProducts.getQuantity()),tableBody));
				stockValue.setBorderColor(BaseColor.BLACK);
				stockValue.setPaddingLeft(10);
				stockValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				stockValue.setVerticalAlignment(Element.ALIGN_CENTER);
				stockValue.setBackgroundColor(BaseColor.WHITE);
				stockValue.setExtraParagraphSpace(5f);
				table.addCell(stockValue);

			}
			document.add(table);
			document.close();
			writer.close();
			return true;
		}catch(Exception e) {
			return false;

	}


	}
	
	private boolean createProductPdf(List<Product> product, ServletContext context2) {
		// TODO Auto-generated method stub
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		
		try {
			String filePath=context.getRealPath("/resources/reports");
			File file=new File(filePath);
			boolean exists=new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"products"+".pdf"));
			document.open();
			Font mainFont=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Paragraph paragraph=new Paragraph("All Products",mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			PdfPTable table=new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);
			Font tableHeader=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Font tableBody=FontFactory.getFont("Arial",9,BaseColor.BLACK);
			float[] columnWidths= {2f,2f,2f,2f,2f};
			table.setWidths(columnWidths);
			PdfPCell productId=new PdfPCell(new Paragraph("Product Id",tableHeader));
			productId.setBorderColor(BaseColor.BLACK);
			productId.setPaddingLeft(10);
			productId.setHorizontalAlignment(Element.ALIGN_CENTER);
			productId.setVerticalAlignment(Element.ALIGN_CENTER);
			productId.setBackgroundColor(BaseColor.GRAY);
			productId.setExtraParagraphSpace(5f);
			table.addCell(productId);
			PdfPCell category=new PdfPCell(new Paragraph("Category",tableHeader));
			category.setBorderColor(BaseColor.BLACK);
			category.setPaddingLeft(10);
			category.setHorizontalAlignment(Element.ALIGN_CENTER);
			category.setVerticalAlignment(Element.ALIGN_CENTER);
			category.setBackgroundColor(BaseColor.GRAY);
			category.setExtraParagraphSpace(5f);
			table.addCell(category);
			PdfPCell productCode=new PdfPCell(new Paragraph("Product Code",tableHeader));
			productCode.setBorderColor(BaseColor.BLACK);
			productCode.setPaddingLeft(10);
			productCode.setHorizontalAlignment(Element.ALIGN_CENTER);
			productCode.setVerticalAlignment(Element.ALIGN_CENTER);
			productCode.setBackgroundColor(BaseColor.GRAY);
			productCode.setExtraParagraphSpace(5f);
			table.addCell(productCode);
			PdfPCell productName=new PdfPCell(new Paragraph("Product Name",tableHeader));
			productName.setBorderColor(BaseColor.BLACK);
			productName.setPaddingLeft(10);
			productName.setHorizontalAlignment(Element.ALIGN_CENTER);
			productName.setVerticalAlignment(Element.ALIGN_CENTER);
			productName.setBackgroundColor(BaseColor.GRAY);
			productName.setExtraParagraphSpace(5f);
			table.addCell(productName);
			PdfPCell quantity=new PdfPCell(new Paragraph("Quantity",tableHeader));
			quantity.setBorderColor(BaseColor.BLACK);
			quantity.setPaddingLeft(10);
			quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
			quantity.setVerticalAlignment(Element.ALIGN_CENTER);
			quantity.setBackgroundColor(BaseColor.GRAY);
			quantity.setExtraParagraphSpace(5f);
			table.addCell(quantity);
			
			for(Product products: product) {
				PdfPCell productIdValue=new PdfPCell(new Paragraph(String.valueOf(products.getId()),tableBody));
				productIdValue.setBorderColor(BaseColor.BLACK);
				productIdValue.setPaddingLeft(10);
				productIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				productIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
				productIdValue.setBackgroundColor(BaseColor.WHITE);
				productIdValue.setExtraParagraphSpace(5f);
				table.addCell(productIdValue);
				PdfPCell categoryValue=new PdfPCell(new Paragraph(products.getCategory(),tableBody));
				categoryValue.setBorderColor(BaseColor.BLACK);
				categoryValue.setPaddingLeft(10);
				categoryValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				categoryValue.setVerticalAlignment(Element.ALIGN_CENTER);
				categoryValue.setBackgroundColor(BaseColor.WHITE);
				categoryValue.setExtraParagraphSpace(5f);
				table.addCell(categoryValue);
				PdfPCell productCodeValue=new PdfPCell(new Paragraph(products.getProduct_code(),tableBody));
				productCodeValue.setBorderColor(BaseColor.BLACK);
				productCodeValue.setPaddingLeft(10);
				productCodeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				productCodeValue.setVerticalAlignment(Element.ALIGN_CENTER);
				productCodeValue.setBackgroundColor(BaseColor.WHITE);
				productCodeValue.setExtraParagraphSpace(5f);
				table.addCell(productCodeValue);
				PdfPCell productNameValue=new PdfPCell(new Paragraph(products.getProduct_name(),tableBody));
				productNameValue.setBorderColor(BaseColor.BLACK);
				productNameValue.setPaddingLeft(10);
				productNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				productNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
				productNameValue.setBackgroundColor(BaseColor.WHITE);
				productNameValue.setExtraParagraphSpace(5f);
				table.addCell(productNameValue);
				PdfPCell quantityValue=new PdfPCell(new Paragraph(String.valueOf(products.getQuantity()),tableBody));
				quantityValue.setBorderColor(BaseColor.BLACK);
				quantityValue.setPaddingLeft(10);
				quantityValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				quantityValue.setVerticalAlignment(Element.ALIGN_CENTER);
				quantityValue.setBackgroundColor(BaseColor.WHITE);
				quantityValue.setExtraParagraphSpace(5f);
				table.addCell(quantityValue);
					
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

	



