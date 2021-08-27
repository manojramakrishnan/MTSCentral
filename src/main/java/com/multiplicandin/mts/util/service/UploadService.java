package com.multiplicandin.mts.util.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	List<Map<String, String>> upload(MultipartFile file) throws Exception;

}
