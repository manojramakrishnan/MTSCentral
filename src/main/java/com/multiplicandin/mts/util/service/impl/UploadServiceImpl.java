package com.multiplicandin.mts.util.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static java.util.stream.Collectors.toMap;

import com.multiplicandin.mts.configuration.UploadUtil;
import com.multiplicandin.mts.util.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadUtil uploadUtil;
	
	@Override
	public List<Map<String, String>> upload(MultipartFile file) throws Exception{
		// TODO Auto-generated method stub
		Path tempdir= Files.createTempDirectory("");
		File tempFile= tempdir.resolve(file.getOriginalFilename()).toFile();
		file.transferTo(tempFile);
		Workbook workbook = WorkbookFactory.create(tempFile);
		Sheet sheet = workbook.getSheetAt(0);
		Supplier<Stream<Row>> rowStreamSupplier= uploadUtil.getRowStreamSupplier(sheet);
		Row headerRow = rowStreamSupplier.get().findFirst().get();
		List<String> headerCells= uploadUtil.getStream(headerRow).map(Cell::getNumericCellValue).map(String:: valueOf).collect(Collectors.toList());
		int colCount = headerCells.size();
		
		return rowStreamSupplier.get()
				.skip(1)
				.map(row -> {
			List<String> cellList= uploadUtil.getStream(row)
					.map(Cell::getStringCellValue)
					.collect(Collectors.toList());
			
			return uploadUtil.cellIteratorSupplier(colCount)
					.get()
					.collect(toMap(headerCells::get,cellList::get));
		}).collect(Collectors.toList());
	}

}
