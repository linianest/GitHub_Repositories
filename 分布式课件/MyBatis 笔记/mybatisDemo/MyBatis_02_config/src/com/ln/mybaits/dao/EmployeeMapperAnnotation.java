package com.ln.mybaits.dao;

import org.apache.ibatis.annotations.Select;

import com.ln.mybaits.bean.Employee;
/**
 * 注解版的接口，不需要配置文件，但是每个方法都得写sql语句
 * @author LiNian
 *
 */
public interface EmployeeMapperAnnotation {

	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmployee(Integer id);
}
