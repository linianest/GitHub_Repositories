package com.ln.mybaits.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ln.mybaits.bean.Employee;
import com.ln.mybaits.dao.EmployeeMapper;
import com.ln.mybaits.dao.EmployeeMapperPlus;

public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	/**
	 * 测试增删改查 1、mybatis允许增删改直接定义以下数据类型 Integer、Long、Boolean
	 * 
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException {
		// 1.创建一个SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2.获取sqlSession对象，不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 3.获取接口的实现类对象
		// 会为接口自动的创建一个代理对象，代理对象去执行CRUD
		try {
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			// 测试添加
			// Employee employee=new Employee(null, "jerry", "jerry@ln.com",
			// "1");
			// employeeMapper.addEmp(employee);
			// System.out.println(employee.getId());
			// 测试修改
			// Employee employee=new Employee(1, "tom", "jerry@ln.com", "1");
			// boolean b=employeeMapper.updateEmp(employee);
			// System.out.println(b);
			// 测试删除
			// employeeMapper.deleteEmpById(2);

			List<Employee> list = employeeMapper.getEmpByLastNameLike("%e%");
			for (Employee employee:list) {
               System.out.println(employee);
			}

			Map<Integer, Employee> map = employeeMapper.getEmpByLastNameLikeReturnMap("%r%");
			System.out.println(map);
			// 手动提交数据
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}

	}
	
	
	@Test
	public void test04() throws IOException {
		// 1.创建一个SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2.获取sqlSession对象，不会自动提交数据
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 3.获取接口的实现类对象
		// 会为接口自动的创建一个代理对象，代理对象去执行CRUD
		try {
			EmployeeMapperPlus mapperPlus = sqlSession.getMapper(EmployeeMapperPlus.class);
		    Employee employee=mapperPlus.getEmployee(1);
		    
		    System.out.println(employee);
			
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}

	}
}
