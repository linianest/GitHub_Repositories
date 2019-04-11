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

	/**
	 * 一级缓存：(本地缓存) sqlSession级别的缓存。默认是开启的
	 *    与数据库同一次会话期间查询到的数据会存放在本地缓存中
	 *    以后如果需要相同的数据，直接从缓存获取。
	 *    
	 *    一级缓存失效：每次访问都会查询库
	 *       1、sqlSession不同
	 *       2、sqlSession相同，但是查询条件不同（当前一级缓存中还没有这个数据）
	 *       3、sqlSession相同；两次查询期间，执行的增删改操作（这次增删改可能对数据有影响）
	 *       4、sqlSession相同，手动清空缓存。openSession.clearCache();
	 * 二级缓存：(全局缓存)：基于namespace级别的缓存：一个namespace对应一个二级缓存
	 *     工作机制：
	 *     1、一个会话，查询一条数据；这个数据就会被放在当前会话的一级缓存中；
	 *     2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中。新的会话查询信息，就可以参照二级缓存
	 *     3、sqlSession==EmployeeMapper==>Employee
	 *                  ==DepartmentMapper==>Department
	 *             不同namespace查出的数据会放在自己对应的缓存中（map）
	 *         效果：会从二级缓存中获取数据
	 *              查出的数据都会被默认先放在一级缓存中
	 *              只有当会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存。
	 *             
	 *             
	 *      使用步骤：
	 *        1、开启全局二级缓存配置；<setting name="cacheEnabled" value="true" />
	 *        2、去mapper.xml配置使用二级缓存；      
	 *            添加<cache></cache>
	 *        3、我们的pojo需要实现序列化接口, Serializable  
	 * 和缓存有关的设置、属性
	 *        1、cacheEnabled="true"：false表示关闭缓存（二级缓存）(一级缓存一直是可用的)	 *        
	 *        2、每个select标签都有useCache='true';默认都是true
	 *           false：表示不使用二级缓存（一级缓存还是可用）
	 *        3、【每个增删改标签的】：flushCache=true;默认是true（一级二级都会清空）
	 *           增删改执行完成后清空缓存。（一级和二级都会清除）
	 *           查询标签select: flushCache=false;默认是false
	 *               如果修改成true，每次查询之后都会清空缓存。
	 *        4、sqlSession.clearCache();只是清除当前session的一级缓存。二级不收应用
	 *        5、localCacheScope:本地缓存；（一级缓存SESSION：默认）；当前会话的所有数据都会保存在会话缓存中
	 *            STATEMENT：可以禁用一级缓存。
	 *        
	 * @throws IOException 
	 */
	@Test
	public void testFirstCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee=empMapper.getEmployee(1);
            System.out.println(employee);
            
                     
            Employee employee2=empMapper.getEmployee(1);
            System.out.println(employee2); 
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}
	}
	
	// 二级缓存
	@Test
	public void testSecondCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		try {
			EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class);
			EmployeeMapper empMapper2 = sqlSession2.getMapper(EmployeeMapper.class);
			
            Employee employee=empMapper.getEmployee(1);
            System.out.println(employee);
            sqlSession.close();
            
           //第二次查询会从二级缓存中获取数据，并没有发送新的数据
            Employee employee2=empMapper2.getEmployee(1);
            System.out.println(employee2); 
            sqlSession2.close();
	//		sqlSession.commit();
		} finally {
			// TODO: handle finally clause
//			sqlSession.close();
//			sqlSession2.close();
		}
	}
	
	
	
}
