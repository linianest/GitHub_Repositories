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
         /* ���ĳ������û����sqlƴװ���ܳ�����
          * 1��sql��where������� 1=1 �Ժ����������and xxxx
          * 2��ʹ��where��ǩƴװsql�������,���Ὣ�������and��orȥ��
          *    whereֻ��ȥ����һ���������and��orȥ��
          * 3��
          * */
	
			//����Choose
//			List<Employee> list=employeeMapper.getEmpsByConditionChoose(employee);
//			
//			for (Employee emp:list) {
//               System.out.println(emp);
//			}
//		
			//����set��ǩ
//			Employee emp_set=new Employee(1,"admin",null,null);
//			employeeMapper.updateEmp(emp_set);
			
			// ������ѯ�����Ĳ���ʱһ��list��ʱ��
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
			// �����������
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
