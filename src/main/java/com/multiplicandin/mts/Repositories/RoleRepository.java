package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.multiplicandin.mts.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{

	@Query("SELECT r FROM Role r where role_id= :id")
	Role findById(int id);

}
