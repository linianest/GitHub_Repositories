package com.ln.mybaits.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.ln.mybaits.bean.Employee;

public interface EmployeeMapper {

	// ����һ����¼��map<Integer, Employee>��key���Ǿ��Ǽ�¼��������ֵ���Ƕ�Ӧ�Ķ���
	//����mybatis��װ���map��ʱ��ʹ���ĸ����Է�װ�� key
	@MapKey("id")
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);

	// ����һ����¼��map��key����������ֵ���Ƕ�Ӧ��ֵ
	public Map<String, Object> getEmpByidReturnMap(int id);

	public List<Employee> getEmpByLastNameLike(String lastName);

	public Employee getEmployee(Integer id);

	public Long addEmp(Employee employee);

	public boolean updateEmp(Employee employee);

	public void deleteEmpById(Integer id);
}
