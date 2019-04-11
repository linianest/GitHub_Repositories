package com.ln.mybaits.dao;

import java.util.List;

import com.ln.mybaits.bean.Employee;
import com.ln.mybaits.bean.OraclePage;

public interface EmployeeMapper {

	
	public Employee getEmployeeById(Integer id);
	public List<Employee> getEmps();
	public Long addEmp(Employee employee);
	public void getPageByProcedure(OraclePage page);
}
