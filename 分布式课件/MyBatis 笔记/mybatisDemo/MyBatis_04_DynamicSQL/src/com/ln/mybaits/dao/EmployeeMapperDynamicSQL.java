package com.ln.mybaits.dao;




import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ln.mybaits.bean.Employee;

public interface EmployeeMapperDynamicSQL {
  
	public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids) ;
	public List<Employee> getEmpsByConditionIf(Employee employee) ;
	public List<Employee> getEmpsTestInnerParameter(Employee employee) ;
	public List<Employee> getEmpsByConditionTrim(Employee employee) ;
	public List<Employee> getEmpsByConditionChoose(Employee employee) ;
	public void updateEmp(Employee employee) ;
	public void addEmp(@Param("emps") List<Employee> employee) ;


}
