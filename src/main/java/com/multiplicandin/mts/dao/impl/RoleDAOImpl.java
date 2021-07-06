package com.multiplicandin.mts.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.RoleRepository;
import com.multiplicandin.mts.dao.RoleDAO;
import com.multiplicandin.mts.model.Role;

@Component("roleDAO")
public class RoleDAOImpl implements RoleDAO {

	private RoleRepository roleRepository;
	
	public RoleDAOImpl(@Qualifier("roleRepository") RoleRepository roleRepository) {
		this.roleRepository =  roleRepository;
		
	}
	@Override
	public Role findById(int id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id);
	}

}
