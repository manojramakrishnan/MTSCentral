package com.multiplicandin.mts.util.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Modules;
import com.multiplicandin.mts.model.PaymentMethod;
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.StoreProduct;
import com.multiplicandin.mts.util.service.UtilService;

@Service("utilService")
public class UtilServiceImpl implements UtilService {
	
	@Autowired
	private ServletContext context;
	
	@Override
	 public void filedownload(String fullPath, HttpServletResponse response, String fileName) {
			File file=new File(fullPath);
			
			System.out.println("inside file before if "+fullPath+"file data "+file.exists()+ "fileName" +fileName);
			final int BUFFER_SIZE=6096;
			if(file.exists()) {
				System.out.println("inside file exists if");
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
		if(modules.getCustomer().size() > 0) {
			System.out.println("inside ");
			return createCustomerPdf(modules.getCustomer(),context);

		}
		else if (modules.getProduct().size() > 0) {
			return createProductPdf(modules.getProduct(),context);
		}
		else if(modules.getCustomerOrder().size() > 0) {
			return createOrderPdf(modules.getCustomerOrder(),context);
		}else if(modules.getEstimate().size() > 0) {
			return createEstimatePdf(modules.getEstimate(),context);
		}else if(modules.getPaymentMethod().size() > 0) {
			return createPaymentPdf(modules.getPaymentMethod(),context);
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
				PdfPCell nameValue=new PdfPCell(new Paragraph(storeProducts.getProduct().getProductName(),tableBody));
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
				PdfPCell productCodeValue=new PdfPCell(new Paragraph(products.getProductCode(),tableBody));
				productCodeValue.setBorderColor(BaseColor.BLACK);
				productCodeValue.setPaddingLeft(10);
				productCodeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				productCodeValue.setVerticalAlignment(Element.ALIGN_CENTER);
				productCodeValue.setBackgroundColor(BaseColor.WHITE);
				productCodeValue.setExtraParagraphSpace(5f);
				table.addCell(productCodeValue);
				PdfPCell productNameValue=new PdfPCell(new Paragraph(products.getProductName(),tableBody));
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

	private boolean createOrderPdf(List<CustomerOrder> customerOrder, ServletContext context2) {
		// TODO Auto-generated method stub
	Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		
		try {
			String filePath=context.getRealPath("/resources/reports");
			File file=new File(filePath);
			boolean exists=new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"orders"+".pdf"));
			document.open();
			Font mainFont=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Paragraph paragraph=new Paragraph("All Orders",mainFont);
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
			PdfPCell orderId=new PdfPCell(new Paragraph("Order Id",tableHeader));
			orderId.setBorderColor(BaseColor.BLACK);
			orderId.setPaddingLeft(10);
			orderId.setHorizontalAlignment(Element.ALIGN_CENTER);
			orderId.setVerticalAlignment(Element.ALIGN_CENTER);
			orderId.setBackgroundColor(BaseColor.GRAY);
			orderId.setExtraParagraphSpace(5f);
			table.addCell(orderId);
			PdfPCell orderDate=new PdfPCell(new Paragraph("Order Date",tableHeader));
			orderDate.setBorderColor(BaseColor.BLACK);
			orderDate.setPaddingLeft(10);
			orderDate.setHorizontalAlignment(Element.ALIGN_CENTER);
			orderDate.setVerticalAlignment(Element.ALIGN_CENTER);
			orderDate.setBackgroundColor(BaseColor.GRAY);
			orderDate.setExtraParagraphSpace(5f);
			table.addCell(orderDate);
			PdfPCell orderStatus=new PdfPCell(new Paragraph("Order Status",tableHeader));
			orderStatus.setBorderColor(BaseColor.BLACK);
			orderStatus.setPaddingLeft(10);
			orderStatus.setHorizontalAlignment(Element.ALIGN_CENTER);
			orderStatus.setVerticalAlignment(Element.ALIGN_CENTER);
			orderStatus.setBackgroundColor(BaseColor.GRAY);
			orderStatus.setExtraParagraphSpace(5f);
			table.addCell(orderStatus);
			PdfPCell orderTotal=new PdfPCell(new Paragraph("Order Total",tableHeader));
			orderTotal.setBorderColor(BaseColor.BLACK);
			orderTotal.setPaddingLeft(10);
			orderTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
			orderTotal.setVerticalAlignment(Element.ALIGN_CENTER);
			orderTotal.setBackgroundColor(BaseColor.GRAY);
			orderTotal.setExtraParagraphSpace(5f);
			table.addCell(orderTotal);
			
			for(CustomerOrder customerOrders: customerOrder) {
				PdfPCell orderIdValue=new PdfPCell(new Paragraph(String.valueOf(customerOrders.getId()),tableBody));
				orderIdValue.setBorderColor(BaseColor.BLACK);
				orderIdValue.setPaddingLeft(10);
				orderIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				orderIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
				orderIdValue.setBackgroundColor(BaseColor.WHITE);
				orderIdValue.setExtraParagraphSpace(5f);
				table.addCell(orderIdValue);
				PdfPCell orderDateValue=new PdfPCell(new Paragraph(String.valueOf(customerOrders.getOrderDate()),tableBody));
				orderDateValue.setBorderColor(BaseColor.BLACK);
				orderDateValue.setPaddingLeft(10);
				orderDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				orderDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
				orderDateValue.setBackgroundColor(BaseColor.WHITE);
				orderDateValue.setExtraParagraphSpace(5f);
				table.addCell(orderDateValue);
				PdfPCell orderStatusValue=new PdfPCell(new Paragraph(customerOrders.getOrderStatus(),tableBody));
				orderStatusValue.setBorderColor(BaseColor.BLACK);
				orderStatusValue.setPaddingLeft(10);
				orderStatusValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				orderStatusValue.setVerticalAlignment(Element.ALIGN_CENTER);
				orderStatusValue.setBackgroundColor(BaseColor.WHITE);
				orderStatusValue.setExtraParagraphSpace(5f);
				table.addCell(orderStatusValue);
				PdfPCell orderTotalValue=new PdfPCell(new Paragraph(String.valueOf(customerOrders.getOrderTotal()),tableBody));
				orderTotalValue.setBorderColor(BaseColor.BLACK);
				orderTotalValue.setPaddingLeft(10);
				orderTotalValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				orderTotalValue.setVerticalAlignment(Element.ALIGN_CENTER);
				orderTotalValue.setBackgroundColor(BaseColor.WHITE);
				orderTotalValue.setExtraParagraphSpace(5f);
				table.addCell(orderTotalValue);
						
			}
			document.add(table);
			document.close();
			writer.close();
			return true;
		}catch(Exception e) {
			return false;

	
	}

	}
	private boolean createEstimatePdf(List<Estimate> estimate, ServletContext context2) {
		// TODO Auto-generated method stub
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		
		try {
			String filePath=context.getRealPath("/resources/reports");
			File file=new File(filePath);
			boolean exists=new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"estimates"+".pdf"));
			document.open();
			Font mainFont=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Paragraph paragraph=new Paragraph("All Estimates",mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			PdfPTable table=new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);
			Font tableHeader=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Font tableBody=FontFactory.getFont("Arial",9,BaseColor.BLACK);
			float[] columnWidths= {2f,2f,2f,2f,2f,2f};
			table.setWidths(columnWidths);
			PdfPCell estimateId=new PdfPCell(new Paragraph("Estimate Id",tableHeader));
			estimateId.setBorderColor(BaseColor.BLACK);
			estimateId.setPaddingLeft(10);
			estimateId.setHorizontalAlignment(Element.ALIGN_CENTER);
			estimateId.setVerticalAlignment(Element.ALIGN_CENTER);
			estimateId.setBackgroundColor(BaseColor.GRAY);
			estimateId.setExtraParagraphSpace(5f);
			table.addCell(estimateId);
			PdfPCell brand=new PdfPCell(new Paragraph("Brand",tableHeader));
			brand.setBorderColor(BaseColor.BLACK);
			brand.setPaddingLeft(10);
			brand.setHorizontalAlignment(Element.ALIGN_CENTER);
			brand.setVerticalAlignment(Element.ALIGN_CENTER);
			brand.setBackgroundColor(BaseColor.GRAY);
			brand.setExtraParagraphSpace(5f);
			table.addCell(brand);
			PdfPCell size=new PdfPCell(new Paragraph("Size",tableHeader));
			size.setBorderColor(BaseColor.BLACK);
			size.setPaddingLeft(10);
			size.setHorizontalAlignment(Element.ALIGN_CENTER);
			size.setVerticalAlignment(Element.ALIGN_CENTER);
			size.setBackgroundColor(BaseColor.GRAY);
			size.setExtraParagraphSpace(5f);
			table.addCell(size);
			PdfPCell sizeUnit=new PdfPCell(new Paragraph("Size Unit",tableHeader));
			sizeUnit.setBorderColor(BaseColor.BLACK);
			sizeUnit.setPaddingLeft(10);
			sizeUnit.setHorizontalAlignment(Element.ALIGN_CENTER);
			sizeUnit.setVerticalAlignment(Element.ALIGN_CENTER);
			sizeUnit.setBackgroundColor(BaseColor.GRAY);
			sizeUnit.setExtraParagraphSpace(5f);
			table.addCell(sizeUnit);
			PdfPCell total=new PdfPCell(new Paragraph("Total",tableHeader));
			total.setBorderColor(BaseColor.BLACK);
			total.setPaddingLeft(10);
			total.setHorizontalAlignment(Element.ALIGN_CENTER);
			total.setVerticalAlignment(Element.ALIGN_CENTER);
			total.setBackgroundColor(BaseColor.GRAY);
			total.setExtraParagraphSpace(5f);
			table.addCell(total);
			PdfPCell price=new PdfPCell(new Paragraph("Price",tableHeader));
			price.setBorderColor(BaseColor.BLACK);
			price.setPaddingLeft(10);
			price.setHorizontalAlignment(Element.ALIGN_CENTER);
			price.setVerticalAlignment(Element.ALIGN_CENTER);
			price.setBackgroundColor(BaseColor.GRAY);
			price.setExtraParagraphSpace(5f);
			table.addCell(price);
		
			
			for(Estimate estimates: estimate) {
				PdfPCell estimateIdValue=new PdfPCell(new Paragraph(String.valueOf(estimates.getId()),tableBody));
				estimateIdValue.setBorderColor(BaseColor.BLACK);
				estimateIdValue.setPaddingLeft(10);
				estimateIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				estimateIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
				estimateIdValue.setBackgroundColor(BaseColor.WHITE);
				estimateIdValue.setExtraParagraphSpace(5f);
				table.addCell(estimateIdValue);
				PdfPCell brandValue=new PdfPCell(new Paragraph(estimates.getBrand(),tableBody));
				brandValue.setBorderColor(BaseColor.BLACK);
				brandValue.setPaddingLeft(10);
				brandValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				brandValue.setVerticalAlignment(Element.ALIGN_CENTER);
				brandValue.setBackgroundColor(BaseColor.WHITE);
				brandValue.setExtraParagraphSpace(5f);
				table.addCell(brandValue);
				PdfPCell sizeValue=new PdfPCell(new Paragraph(String.valueOf(estimates.getSize()),tableBody));
				sizeValue.setBorderColor(BaseColor.BLACK);
				sizeValue.setPaddingLeft(10);
				sizeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				sizeValue.setVerticalAlignment(Element.ALIGN_CENTER);
				sizeValue.setBackgroundColor(BaseColor.WHITE);
				sizeValue.setExtraParagraphSpace(5f);
				table.addCell(sizeValue);
				PdfPCell sizeUnitValue=new PdfPCell(new Paragraph(String.valueOf(estimates.getSizeUnit()),tableBody));
				sizeUnitValue.setBorderColor(BaseColor.BLACK);
				sizeUnitValue.setPaddingLeft(10);
				sizeUnitValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				sizeUnitValue.setVerticalAlignment(Element.ALIGN_CENTER);
				sizeUnitValue.setBackgroundColor(BaseColor.WHITE);
				sizeUnitValue.setExtraParagraphSpace(5f);
				table.addCell(sizeUnitValue);
				PdfPCell totalValue=new PdfPCell(new Paragraph(String.valueOf(estimates.getTotal()),tableBody));
				totalValue.setBorderColor(BaseColor.BLACK);
				totalValue.setPaddingLeft(10);
				totalValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				totalValue.setVerticalAlignment(Element.ALIGN_CENTER);
				totalValue.setBackgroundColor(BaseColor.WHITE);
				totalValue.setExtraParagraphSpace(5f);
				table.addCell(totalValue);
				PdfPCell priceValue=new PdfPCell(new Paragraph(String.valueOf(estimates.getPrice()),tableBody));
				priceValue.setBorderColor(BaseColor.BLACK);
				priceValue.setPaddingLeft(10);
				priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
				priceValue.setBackgroundColor(BaseColor.WHITE);
				priceValue.setExtraParagraphSpace(5f);
				table.addCell(priceValue);
						
			}
			document.add(table);
			document.close();
			writer.close();
			return true;
		}catch(Exception e) {
			return false;

	
	}

	}
	private boolean createPaymentPdf(List<PaymentMethod> paymentMethod, ServletContext context2) {
		// TODO Auto-generated method stub
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		try {
			String filePath=context.getRealPath("/resources/reports");
			File file=new File(filePath);
			boolean exists=new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"payment"+".pdf"));
			document.open();
			Font mainFont=FontFactory.getFont("Arial",10,BaseColor.BLACK);
			Paragraph paragraph=new Paragraph("All Payments",mainFont);
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
			PdfPCell ownerName=new PdfPCell(new Paragraph("Owner Name",tableHeader));
			ownerName.setBorderColor(BaseColor.BLACK);
			ownerName.setPaddingLeft(10);
			ownerName.setHorizontalAlignment(Element.ALIGN_CENTER);
			ownerName.setVerticalAlignment(Element.ALIGN_CENTER);
			ownerName.setBackgroundColor(BaseColor.GRAY);
			ownerName.setExtraParagraphSpace(5f);
			table.addCell(ownerName);
			PdfPCell creditCardNumber=new PdfPCell(new Paragraph("Credit Card Number",tableHeader));
			creditCardNumber.setBorderColor(BaseColor.BLACK);
			creditCardNumber.setPaddingLeft(10);
			creditCardNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
			creditCardNumber.setVerticalAlignment(Element.ALIGN_CENTER);
			creditCardNumber.setBackgroundColor(BaseColor.GRAY);
			creditCardNumber.setExtraParagraphSpace(5f);
			table.addCell(creditCardNumber);
			PdfPCell expirationMonth=new PdfPCell(new Paragraph("Expiration Month",tableHeader));
			expirationMonth.setBorderColor(BaseColor.BLACK);
			expirationMonth.setPaddingLeft(10);
			expirationMonth.setHorizontalAlignment(Element.ALIGN_CENTER);
			expirationMonth.setVerticalAlignment(Element.ALIGN_CENTER);
			expirationMonth.setBackgroundColor(BaseColor.GRAY);
			expirationMonth.setExtraParagraphSpace(5f);
			table.addCell(expirationMonth);
			PdfPCell expirationYear=new PdfPCell(new Paragraph("Expiration Year",tableHeader));
			expirationYear.setBorderColor(BaseColor.BLACK);
			expirationYear.setPaddingLeft(10);
			expirationYear.setHorizontalAlignment(Element.ALIGN_CENTER);
			expirationYear.setVerticalAlignment(Element.ALIGN_CENTER);
			expirationYear.setBackgroundColor(BaseColor.GRAY);
			expirationYear.setExtraParagraphSpace(5f);
			table.addCell(expirationYear);
			
			for(PaymentMethod paymentMethods: paymentMethod) {
				PdfPCell ownerNameValue=new PdfPCell(new Paragraph(paymentMethods.getCardOwner(),tableBody));
				ownerNameValue.setBorderColor(BaseColor.BLACK);
				ownerNameValue.setPaddingLeft(10);
				ownerNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				ownerNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
				ownerNameValue.setBackgroundColor(BaseColor.WHITE);
				ownerNameValue.setExtraParagraphSpace(5f);
				table.addCell(ownerNameValue);
				PdfPCell creditCardNumberValue=new PdfPCell(new Paragraph(paymentMethods.getCreditCardNumber(),tableBody));
				creditCardNumberValue.setBorderColor(BaseColor.BLACK);
				creditCardNumberValue.setPaddingLeft(10);
				creditCardNumberValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				creditCardNumberValue.setVerticalAlignment(Element.ALIGN_CENTER);
				creditCardNumberValue.setBackgroundColor(BaseColor.WHITE);
				creditCardNumberValue.setExtraParagraphSpace(5f);
				table.addCell(creditCardNumberValue);
				PdfPCell expirationMonthValue=new PdfPCell(new Paragraph(String.valueOf(paymentMethods.getExpirationMonth()),tableBody));
				expirationMonthValue.setBorderColor(BaseColor.BLACK);
				expirationMonthValue.setPaddingLeft(10);
				expirationMonthValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				expirationMonthValue.setVerticalAlignment(Element.ALIGN_CENTER);
				expirationMonthValue.setBackgroundColor(BaseColor.WHITE);
				expirationMonthValue.setExtraParagraphSpace(5f);
				table.addCell(expirationMonthValue);
				PdfPCell expirationYearValueValue=new PdfPCell(new Paragraph(String.valueOf(paymentMethods.getExpirationYear()),tableBody));
				expirationYearValueValue.setBorderColor(BaseColor.BLACK);
				expirationYearValueValue.setPaddingLeft(10);
				expirationYearValueValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				expirationYearValueValue.setVerticalAlignment(Element.ALIGN_CENTER);
				expirationYearValueValue.setBackgroundColor(BaseColor.WHITE);
				expirationYearValueValue.setExtraParagraphSpace(5f);
				table.addCell(expirationYearValueValue);

			}
			document.add(table);
			document.close();
			writer.close();
			return true;
		}catch(Exception e) {
			return false;

	}


	}
	@Override
	public boolean createExcel(Modules modules, ServletContext context) {
		if(modules.getCustomer().size() > 0) {
			return createCustomerExcel(modules.getCustomer(),context);

		}
		else if (modules.getProduct().size() > 0) {
			return createProductExcel(modules.getProduct(),context);
		}
		else if(modules.getCustomerOrder().size() > 0) {
			return createOrderExcel(modules.getCustomerOrder(),context);
		}
		else if(modules.getEstimate().size() > 0) {
			return createEstimateExcel(modules.getEstimate(),context);
		}
		else if(modules.getPaymentMethod().size() > 0) {
			return createPaymentExcel(modules.getPaymentMethod(),context);
		}
	else {
			return createStoreProductExcel(modules.getStoreProduct(),context);
		}
			
	
			
		
	}

	
	


		
	
	private boolean createCustomerExcel(List<Customer> customer, ServletContext context) {
		// TODO Auto-generated method stub
		String filePath=context.getRealPath("/resources/reports");
		File file=new File(filePath);
		boolean exists=new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream=new FileOutputStream(file+"/"+"customers"+".xls");
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet workSheet=workbook.createSheet("Customers");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle=workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFRow headerRow = workSheet.createRow(0);
			HSSFCell customerId = headerRow.createCell(0);
			customerId.setCellValue("Customer Id");
			customerId.setCellStyle(headerCellStyle);
			HSSFCell name = headerRow.createCell(1);
			name.setCellValue("Name");
			name.setCellStyle(headerCellStyle);
			HSSFCell email = headerRow.createCell(2);
			email.setCellValue("Email");
			email.setCellStyle(headerCellStyle);
			int i=1;
			for(Customer customers : customer) {
				HSSFRow bodyRow=workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle=workbook.createCellStyle();
				bodyCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
				HSSFCell customerIdValue=bodyRow.createCell(0);
				customerIdValue.setCellValue(customers.getId());
				customerIdValue.setCellStyle(bodyCellStyle);
				HSSFCell nameValue=bodyRow.createCell(1);
				nameValue.setCellValue(customers.getName());
				nameValue.setCellStyle(bodyCellStyle);
				HSSFCell emailValue=bodyRow.createCell(2);
				emailValue.setCellValue(customers.getEmail());
				emailValue.setCellStyle(bodyCellStyle);
				i++;
				
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
		}catch(Exception e) {
			return false;
		}
					
		
	}

	private boolean createStoreProductExcel(List<StoreProduct> storeProduct, ServletContext context) {
		// TODO Auto-generated method stub
		String filePath=context.getRealPath("/resources/reports");
		File file=new File(filePath);
		boolean exists=new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream=new FileOutputStream(file+"/"+"Storeproducts"+".xls");
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet workSheet=workbook.createSheet("Storeproducts");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle=workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFRow headerRow = workSheet.createRow(0);
			HSSFCell storeProductId = headerRow.createCell(0);
			storeProductId.setCellValue("StoreProduct Id ");
			storeProductId.setCellStyle(headerCellStyle);
			HSSFCell Name = headerRow.createCell(1);
			Name.setCellValue("Name");
			Name.setCellStyle(headerCellStyle);
			HSSFCell price = headerRow.createCell(2);
			price.setCellValue("Price");
			price.setCellStyle(headerCellStyle);
			HSSFCell stock = headerRow.createCell(3);
			stock.setCellValue("Stock");
			stock.setCellStyle(headerCellStyle);
			int i=1;
			for(StoreProduct storeProducts : storeProduct) {
				HSSFRow bodyRow=workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle=workbook.createCellStyle();
				bodyCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
				HSSFCell storeProductIdValue=bodyRow.createCell(0);
				storeProductIdValue.setCellValue(storeProducts.getId());
				storeProductIdValue.setCellStyle(bodyCellStyle);
				HSSFCell nameValue=bodyRow.createCell(1);
				nameValue.setCellValue(storeProducts.getProduct().getProductName());
				nameValue.setCellStyle(bodyCellStyle);
				HSSFCell priceValue=bodyRow.createCell(2);
				priceValue.setCellValue(storeProducts.getPrice());
				priceValue.setCellStyle(bodyCellStyle);
				HSSFCell stockValue=bodyRow.createCell(3);
				stockValue.setCellValue(storeProducts.getQuantity());
				stockValue.setCellStyle(bodyCellStyle);
				i++;
				
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	private boolean createProductExcel(List<Product> product, ServletContext context) {
		// TODO Auto-generated method stub
		String filePath=context.getRealPath("/resources/reports");
		File file=new File(filePath);
		boolean exists=new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream=new FileOutputStream(file+"/"+"products"+".xls");
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet workSheet=workbook.createSheet("Products");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle=workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFRow headerRow = workSheet.createRow(0);
			HSSFCell productId = headerRow.createCell(0);
			productId.setCellValue("Product Id");
			productId.setCellStyle(headerCellStyle);
			HSSFCell category = headerRow.createCell(1);
			category.setCellValue("Category");
			category.setCellStyle(headerCellStyle);
			HSSFCell productCode = headerRow.createCell(2);
			productCode.setCellValue("Product Code");
			productCode.setCellStyle(headerCellStyle);
			HSSFCell productName = headerRow.createCell(3);
			productName.setCellValue("Product Name");
			productName.setCellStyle(headerCellStyle);
			HSSFCell quantity = headerRow.createCell(4);
			quantity.setCellValue("Quantity");
			quantity.setCellStyle(headerCellStyle);
			
			int i=1;
			for(Product products : product) {
				HSSFRow bodyRow=workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle=workbook.createCellStyle();
				bodyCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
				HSSFCell customerIdValue=bodyRow.createCell(0);
				customerIdValue.setCellValue(products.getId());
				customerIdValue.setCellStyle(bodyCellStyle);
				HSSFCell categoryValue=bodyRow.createCell(1);
				categoryValue.setCellValue(products.getCategory());
				categoryValue.setCellStyle(bodyCellStyle);
				HSSFCell productCodeValue=bodyRow.createCell(2);
				productCodeValue.setCellValue(products.getProductCode());
				productCodeValue.setCellStyle(bodyCellStyle);
				HSSFCell productNameValue=bodyRow.createCell(3);
				productNameValue.setCellValue(products.getProductName());
				productNameValue.setCellStyle(bodyCellStyle);
				HSSFCell quantityValue=bodyRow.createCell(4);
				quantityValue.setCellValue(products.getQuantity());
				quantityValue.setCellStyle(bodyCellStyle);
				
				i++;
				
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	private boolean createEstimateExcel(List<Estimate> estimate, ServletContext context) {
		// TODO Auto-generated method stub
		String filePath=context.getRealPath("/resources/reports");
		File file=new File(filePath);
		boolean exists=new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream=new FileOutputStream(file+"/"+"estimates"+".xls");
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet workSheet=workbook.createSheet("Estimates");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle=workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFRow headerRow = workSheet.createRow(0);
			HSSFCell estimateId = headerRow.createCell(0);
			estimateId.setCellValue("Estimate Id");
			estimateId.setCellStyle(headerCellStyle);
			HSSFCell brand = headerRow.createCell(1);
			brand.setCellValue("Brand");
			brand.setCellStyle(headerCellStyle);
			HSSFCell size = headerRow.createCell(2);
			size.setCellValue("Size");
			size.setCellStyle(headerCellStyle);
			HSSFCell sizeUnit = headerRow.createCell(3);
			sizeUnit.setCellValue("Size Unit");
			sizeUnit.setCellStyle(headerCellStyle);
			HSSFCell total = headerRow.createCell(4);
			total.setCellValue("Total");
			total.setCellStyle(headerCellStyle);
			HSSFCell price = headerRow.createCell(5);
			price.setCellValue("Price");
			price.setCellStyle(headerCellStyle);
			
			int i=1;
			for(Estimate estimates : estimate) {
				HSSFRow bodyRow=workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle=workbook.createCellStyle();
				bodyCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
				HSSFCell estimateIdValue=bodyRow.createCell(0);
				estimateIdValue.setCellValue(estimates.getId());
				estimateIdValue.setCellStyle(bodyCellStyle);
				HSSFCell brandValue=bodyRow.createCell(1);
				brandValue.setCellValue(estimates.getBrand());
				brandValue.setCellStyle(bodyCellStyle);
				HSSFCell sizeValue=bodyRow.createCell(2);
				sizeValue.setCellValue(estimates.getSize());
				sizeValue.setCellStyle(bodyCellStyle);
				HSSFCell sizeUnitValue=bodyRow.createCell(3);
				sizeUnitValue.setCellValue(estimates.getSizeUnit());
				sizeUnitValue.setCellStyle(bodyCellStyle);
				HSSFCell totalValue=bodyRow.createCell(4);
				totalValue.setCellValue(estimates.getTotal());
				totalValue.setCellStyle(bodyCellStyle);
				HSSFCell priceValue=bodyRow.createCell(5);
				priceValue.setCellValue(estimates.getPrice());
				priceValue.setCellStyle(bodyCellStyle);
				
				i++;
				
			}		
				workbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				return true;
			}catch(Exception e) {
				return false;
			}
						
}
				
			
	private boolean createOrderExcel(List<CustomerOrder> customerOrder, ServletContext context) {
		// TODO Auto-generated method stub
		String filePath=context.getRealPath("/resources/reports");
		File file=new File(filePath);
		boolean exists=new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream=new FileOutputStream(file+"/"+"orders"+".xls");
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet workSheet=workbook.createSheet("Orders");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle=workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFRow headerRow = workSheet.createRow(0);
			HSSFCell orderId = headerRow.createCell(0);
			orderId.setCellValue("Order Id");
			orderId.setCellStyle(headerCellStyle);
			HSSFCell orderDate = headerRow.createCell(1);
			orderDate.setCellValue("Order Date");
			orderDate.setCellStyle(headerCellStyle);
			HSSFCell orderStatus = headerRow.createCell(2);
			orderStatus.setCellValue("Order Status");
			orderStatus.setCellStyle(headerCellStyle);
			HSSFCell orderTotal = headerRow.createCell(3);
			orderTotal.setCellValue("OrderTotal");
			orderTotal.setCellStyle(headerCellStyle);
			int i=1;
			for(CustomerOrder customerOrders : customerOrder) {
				HSSFRow bodyRow=workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle=workbook.createCellStyle();
				bodyCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
				HSSFCell orderIdValue=bodyRow.createCell(0);
				orderIdValue.setCellValue(customerOrders.getId());
				orderIdValue.setCellStyle(bodyCellStyle);
				HSSFCell orderDateValue=bodyRow.createCell(1);
				orderDateValue.setCellValue(customerOrders.getOrderDate());
				orderDateValue.setCellStyle(bodyCellStyle);
				HSSFCell orderStatusValue=bodyRow.createCell(2);
				orderStatusValue.setCellValue(customerOrders.getOrderStatus());
				orderStatusValue.setCellStyle(bodyCellStyle);
				HSSFCell orderTotalValue=bodyRow.createCell(3);
				orderTotalValue.setCellValue(String.valueOf(customerOrders.getOrderTotal()));
				orderTotalValue.setCellStyle(bodyCellStyle);
				
				i++;
				
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	
	}

	private boolean createPaymentExcel(List<PaymentMethod> paymentMethod, ServletContext context) {
		// TODO Auto-generated method stub
		String filePath=context.getRealPath("/resources/reports");
		File file=new File(filePath);
		boolean exists=new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream=new FileOutputStream(file+"/"+"payment"+".xls");
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet workSheet=workbook.createSheet("payment");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle=workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFRow headerRow = workSheet.createRow(0);
			HSSFCell ownerName = headerRow.createCell(0);
			ownerName.setCellValue("Owner Name");
			ownerName.setCellStyle(headerCellStyle);
			HSSFCell creditCardNumber = headerRow.createCell(1);
			creditCardNumber.setCellValue("Credit Card Number");
			creditCardNumber.setCellStyle(headerCellStyle);
			HSSFCell expirationMonth = headerRow.createCell(2);
			expirationMonth.setCellValue("Expiration Month");
			expirationMonth.setCellStyle(headerCellStyle);
			HSSFCell expirationYear = headerRow.createCell(3);
			expirationYear.setCellValue("Expiration Year");
			expirationYear.setCellStyle(headerCellStyle);
			int i=1;
			for(PaymentMethod paymentMethods : paymentMethod) {
				HSSFRow bodyRow=workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle=workbook.createCellStyle();
				bodyCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
				HSSFCell ownerNameValue=bodyRow.createCell(0);
				ownerNameValue.setCellValue(paymentMethods.getCardOwner());
				ownerNameValue.setCellStyle(bodyCellStyle);
				HSSFCell creditCardNumberValue=bodyRow.createCell(1);
				creditCardNumberValue.setCellValue(paymentMethods.getCreditCardNumber());
				creditCardNumberValue.setCellStyle(bodyCellStyle);
				HSSFCell expirationMonthValue=bodyRow.createCell(2);
				expirationMonthValue.setCellValue(paymentMethods.getExpirationMonth());
				expirationMonthValue.setCellStyle(bodyCellStyle);
				HSSFCell ExpirationYearValue=bodyRow.createCell(3);
				ExpirationYearValue.setCellValue(String.valueOf(paymentMethods.getExpirationYear()));
				ExpirationYearValue.setCellStyle(bodyCellStyle);
				
				i++;
				
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	
	}	


}




	
	




	



