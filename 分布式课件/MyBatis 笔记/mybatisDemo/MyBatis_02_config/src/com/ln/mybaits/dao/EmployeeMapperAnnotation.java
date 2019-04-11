package com.ln.mybaits.dao;

import org.apache.ibatis.annotations.Select;

import com.ln.mybaits.bean.Employee;
/**
 * ע���Ľӿڣ�����Ҫ�����ļ�������ÿ����������дsql���
 * @author LiNian
 *
 */
public interface EmployeeMapperAnnotation {

	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmployee(Integer id);
}
