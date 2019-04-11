package com.ln.mybaits.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ln.mybaits.bean.Employee;
import com.ln.mybaits.dao.EmployeeMapper;

/***
 * 1、接口编程
 *   原生：                     Dao    =====>  DaoImpl
 *   mybatis        Mapper ======>  xxMaper.xml
 *   
 *  2.SqlSession代表和数据库的一次会话：用完必须关闭
 *  3.SqlSession和Connection一样都是非线程安全的。每次使用都应该去获取新的对象
 *  4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 *      （将接口与xml绑定）
 *  	EmployeeMapper employeeMapper=sqlSession.getMapper(EmployeeMapper.class);
 *  5.两个重要的配置文件
 *      mybatis的全局配置文件：数据库连接池信息、事务管理器信息。。。。系统运行环境信息
 *      sql映射文件：包含了每一个sql语句的映射信息
 *                 将sql抽取出来
 *      
 * 
 * 
 * @author LiNian
 *
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	/**
	 * 1.根据配置文件（全局配置文件）创建一个SqlSessionFactory对象
	 *    有数据源运行环境信息
	 * 2.sql映射文件:配置了每一个sql，以及sql的封装规则等
	 * 3.将sql映射文件配置在全局配置文件中
	 * 4.写代码
	 *    1）、根据配置文件得到SqlSessionFactory
	 *    2）、使用SqlSessionFactory获取到SqlSession使用它来完成增删改查，
	 *         一个SqlSession就是代表和数据库的一次会话，用完关闭
	 *    3）、使用一个sql的唯一标识来告诉mybatis来执行哪个sql；sql都保存在EmployeeMapper.xml中
	 * @param args
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//获取一个SqlSession实例对象，能直接执行已经映射的sql语句
		SqlSession openSession=sqlSessionFactory.openSession();
		try {
			Employee employee=openSession.selectOne("com.ln.mybaits.employeeMapper.selectEmp",1);
			System.out.println(employee);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			openSession.close();
		}
	
	}

	@Test
	public void test2() throws IOException{
		//1.创建一个SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory=getSqlSessionFactory();
		//2.获取sqlSession对象
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		//3.获取接口的实现类对象
		//会为接口自动的创建一个代理对象，代理对象去执行CRUD
		try {
			EmployeeMapper employeeMapper=sqlSession.getMapper(EmployeeMapper.class);
			Employee employee=employeeMapper.getEmployee(1);
			System.out.println(employee);
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}
		
	}
}
