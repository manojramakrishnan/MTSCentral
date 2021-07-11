package com.multiplicandin.mts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.RoleDAO;
import com.multiplicandin.mts.model.Role;
import com.multiplicandin.mts.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService  {

	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public Role findById(int id) {
		// TODO Auto-generated method stub
		return roleDAO.findById(id);
	}

}
