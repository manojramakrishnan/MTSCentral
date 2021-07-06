package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplicandin.mts.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findById(int id);

}
