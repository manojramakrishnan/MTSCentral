package com.multiplicandin.mts.service;

import java.util.List;

import com.multiplicandin.mts.model.Alert;


public interface AlertService {


	List<Alert> findAllByCustomerId(int id);

}
