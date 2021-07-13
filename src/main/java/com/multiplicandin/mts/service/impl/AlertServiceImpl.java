package com.multiplicandin.mts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.AlertDAO;
import com.multiplicandin.mts.model.Alert;
import com.multiplicandin.mts.service.AlertService;

@Service("alertService")
public class AlertServiceImpl implements AlertService {

	@Autowired
	private AlertDAO alertDAO;

	@Override
	public List<Alert> findAllByCustomerId(int id) {
		return alertDAO.findById(id);
	}

}
