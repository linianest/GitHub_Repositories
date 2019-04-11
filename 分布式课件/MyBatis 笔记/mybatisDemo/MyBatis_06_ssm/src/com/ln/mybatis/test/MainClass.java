package com.ln.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ln.mybatis.bean.Employee;
import com.ln.mybatis.dao.EmployeeMapper;

public class MainClass {
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	@Test
	public void testFirstCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
            List<Employee> list=empMapper.getEmps();
            for (Employee employee : list) {
				System.out.println(employee);
			}
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}
	}
	


}
