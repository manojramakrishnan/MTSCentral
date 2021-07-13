package com.multiplicandin.mts.dao;

import java.util.List;

import com.multiplicandin.mts.model.Alert;


public interface AlertDAO {

	List<Alert> findById(int id);

}
