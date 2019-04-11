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
	 * ������ɾ�Ĳ� 1��mybatis������ɾ��ֱ�Ӷ��������������� Integer��Long��Boolean
	 * 
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException {
		// 1.����һ��SqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2.��ȡsqlSession���󣬲����Զ��ύ����
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 3.��ȡ�ӿڵ�ʵ�������
		// ��Ϊ�ӿ��Զ��Ĵ���һ��������󣬴������ȥִ��CRUD
		try {
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			// �������
			// Employee employee=new Employee(null, "jerry", "jerry@ln.com",
			// "1");
			// employeeMapper.addEmp(employee);
			// System.out.println(employee.getId());
			// �����޸�
			// Employee employee=new Employee(1, "tom", "jerry@ln.com", "1");
			// boolean b=employeeMapper.updateEmp(employee);
			// System.out.println(b);
			// ����ɾ��
			// employeeMapper.deleteEmpById(2);

			List<Employee> list = employeeMapper.getEmpByLastNameLike("%e%");
			for (Employee employee:list) {
               System.out.println(employee);
			}

			Map<Integer, Employee> map = employeeMapper.getEmpByLastNameLikeReturnMap("%r%");
			System.out.println(map);
			// �ֶ��ύ����
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}

	}
	
	
	@Test
	public void test04() throws IOException {
		// 1.����һ��SqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2.��ȡsqlSession���󣬲����Զ��ύ����
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 3.��ȡ�ӿڵ�ʵ�������
		// ��Ϊ�ӿ��Զ��Ĵ���һ��������󣬴������ȥִ��CRUD
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
