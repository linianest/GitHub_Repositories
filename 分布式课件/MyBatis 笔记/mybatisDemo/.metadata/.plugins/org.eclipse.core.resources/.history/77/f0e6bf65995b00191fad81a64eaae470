package com.ln.mybaits.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.ln.mybaits.bean.Employee;

public interface EmployeeMapper {

	// 返回一条记录的map<Integer, Employee>：key就是就是记录的主键，值就是对应的对象
	//告诉mybatis封装这个map的时候使用哪个属性封装成 key
	@MapKey("id")
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);

	// 返回一条记录的map：key就是列名，值就是对应的值
	public Map<String, Object> getEmpByidReturnMap(int id);

	public List<Employee> getEmpByLastNameLike(String lastName);

	public Employee getEmployee(Integer id);

	public Long addEmp(Employee employee);

	public boolean updateEmp(Employee employee);

	public void deleteEmpById(Integer id);
}
