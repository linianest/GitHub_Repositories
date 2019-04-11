package com.ln.mybaits.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ln.mybaits.bean.Employee;
import com.ln.mybaits.dao.EmployeeMapper;

/***
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
	 * 1、获取sqlSessionFactory对象
	 * 2、获取SqlSession对象
	 * 3、获取接口实现类对象(MapperProxy)
	 * 4、执行增删改查
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession=sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper=openSession.getMapper(EmployeeMapper.class);
			Employee employee=employeeMapper.getEmployee(1);			
			System.out.println(employeeMapper);
			System.out.println(employee);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			openSession.close();
		}
	
	}
	
	/**
	 * 开发插件：
	 * 在四大对象创建的时候：
	 * 1、每个创建出来的对象不是直接返回的，而是 interceptorChain.pluginAll(ParementerHandler);
	 * 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）
	 *    调用interceptor.plugin(target);返回target包装后的对象。
	 *    
	 * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP(面向切面)
	 *    我们的插件可以为四大对象创建代理对象；
	 *    代理对象就可以拦截四大对象的每一个执行；
	 * 
	 * 
	 * public Object pluginAll(Object target){
			for (Interceptor interceptor : interceptors) {
				 target=interceptor.plugin(target);
			}
			return target;
		}
	 * @throws IOException
	 */
	/**
	 * 编写插件：
	 * 1、编写一个Interceptor的实现类
	 * 2、使用@Intercept注解完成插件签名
	 * 3、将写好的插件注册到全局配置文件中
	 * @throws IOException
	 */
	@Test
	public void testPlugin() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession=sqlSessionFactory.openSession();
		try {
			EmployeeMapper employee=openSession.getMapper(EmployeeMapper.class);
			System.out.println(employee);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			openSession.close();
		}
		

	}

//	public Object pluginAll(Object target){
//		for (Interceptor interceptor : interceptors) {
//			 target=interceptor.plugin(target);
//		}
//		return target;
//	}
}
