package com.multiplicandin.mts.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Alert;


@Repository("alertRepository")
public interface AlertRepository extends JpaRepository<Alert, Integer>{

	List<Alert> findById(int id);

}
