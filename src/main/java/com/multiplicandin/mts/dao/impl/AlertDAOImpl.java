package com.multiplicandin.mts.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.AlertRepository;
import com.multiplicandin.mts.dao.AlertDAO;
import com.multiplicandin.mts.model.Alert;

@Component("alertDAO")
public class AlertDAOImpl implements AlertDAO {

private AlertRepository alertRepository;
	
	public AlertDAOImpl(@Qualifier("alertRepository") AlertRepository alertRepository) {
		this.alertRepository = alertRepository;
	}

	@Override
	public List<Alert> findById(int id) {
		return alertRepository.findById(id);
	}

}
