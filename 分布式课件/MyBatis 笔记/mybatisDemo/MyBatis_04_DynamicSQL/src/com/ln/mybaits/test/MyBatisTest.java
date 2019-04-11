package com.ln.mybaits.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ln.mybaits.bean.Employee;
import com.ln.mybaits.dao.EmployeeMapper;
import com.ln.mybaits.dao.EmployeeMapperDynamicSQL;
import com.ln.mybaits.dao.EmployeeMapperPlus;

public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	@Test
	public void testDynamicSql() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSQL employeeMapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee=new Employee(3,"%e%",null,null);
//			employee.setId(3);
//			employee.setLastName("%e%");
//			employee.setEmail("jerr@ln.com");
//			employee.setGender("1");
//			List<Employee> list=employeeMapper.getEmpsByConditionTrim(employee);
//					
//			for (Employee emp:list) {
//               System.out.println(emp);
//			}
         /* 如果某个条件没带，sql拼装可能出问题
          * 1、sql的where后面加上 1=1 以后的条件都会and xxxx
          * 2、使用where标签拼装sql条件语句,它会将多出来的and或or去掉
          *    where只会去掉第一个多出来的and或or去掉
          * 3、
          * */
	
			//测试Choose
//			List<Employee> list=employeeMapper.getEmpsByConditionChoose(employee);
//			
//			for (Employee emp:list) {
//               System.out.println(emp);
//			}
//		
			//测试set标签
//			Employee emp_set=new Employee(1,"admin",null,null);
//			employeeMapper.updateEmp(emp_set);
			
			// 批量查询遍历的参数时一个list的时候
//			List<Employee> emp_list=employeeMapper.getEmpsByConditionForeach(Arrays.asList(1,2,3));
//			
//			for (Employee emp:emp_list) {
//               System.out.println(emp);
//			}
//			
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}
		
	}
	
	@Test
	public void testBatchSave() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSQL employeeMapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			// 批量添加数据
			List<Employee> list=new ArrayList<Employee>();
			Employee employee=null;
			for (int i = 0; i < 3; i++) {
				employee=new Employee(null,"smith"+i,"smith"+i+"@ln.com","1");
				list.add(employee);
			}
			employeeMapper.addEmp(list);
		     
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}
	}
	
}
