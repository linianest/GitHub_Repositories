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
	 * һ�����棺(���ػ���) sqlSession����Ļ��档Ĭ���ǿ�����
	 *    �����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ����ڱ��ػ�����
	 *    �Ժ������Ҫ��ͬ�����ݣ�ֱ�Ӵӻ����ȡ��
	 *    
	 *    һ������ʧЧ��ÿ�η��ʶ����ѯ��
	 *       1��sqlSession��ͬ
	 *       2��sqlSession��ͬ�����ǲ�ѯ������ͬ����ǰһ�������л�û��������ݣ�
	 *       3��sqlSession��ͬ�����β�ѯ�ڼ䣬ִ�е���ɾ�Ĳ����������ɾ�Ŀ��ܶ�������Ӱ�죩
	 *       4��sqlSession��ͬ���ֶ���ջ��档openSession.clearCache();
	 * �������棺(ȫ�ֻ���)������namespace����Ļ��棺һ��namespace��Ӧһ����������
	 *     �������ƣ�
	 *     1��һ���Ự����ѯһ�����ݣ�������ݾͻᱻ���ڵ�ǰ�Ự��һ�������У�
	 *     2������Ự�رգ�һ�������е����ݻᱻ���浽���������С��µĻỰ��ѯ��Ϣ���Ϳ��Բ��ն�������
	 *     3��sqlSession==EmployeeMapper==>Employee
	 *                  ==DepartmentMapper==>Department
	 *             ��ͬnamespace��������ݻ�����Լ���Ӧ�Ļ����У�map��
	 *         Ч������Ӷ��������л�ȡ����
	 *              ��������ݶ��ᱻĬ���ȷ���һ��������
	 *              ֻ�е��Ự�ύ���߹ر��Ժ�һ�������е����ݲŻ�ת�Ƶ��������档
	 *             
	 *             
	 *      ʹ�ò��裺
	 *        1������ȫ�ֶ����������ã�<setting name="cacheEnabled" value="true" />
	 *        2��ȥmapper.xml����ʹ�ö������棻      
	 *            ���<cache></cache>
	 *        3�����ǵ�pojo��Ҫʵ�����л��ӿ�, Serializable  
	 * �ͻ����йص����á�����
	 *        1��cacheEnabled="true"��false��ʾ�رջ��棨�������棩(һ������һֱ�ǿ��õ�)	 *        
	 *        2��ÿ��select��ǩ����useCache='true';Ĭ�϶���true
	 *           false����ʾ��ʹ�ö������棨һ�����滹�ǿ��ã�
	 *        3����ÿ����ɾ�ı�ǩ�ġ���flushCache=true;Ĭ����true��һ������������գ�
	 *           ��ɾ��ִ����ɺ���ջ��档��һ���Ͷ������������
	 *           ��ѯ��ǩselect: flushCache=false;Ĭ����false
	 *               ����޸ĳ�true��ÿ�β�ѯ֮�󶼻���ջ��档
	 *        4��sqlSession.clearCache();ֻ�������ǰsession��һ�����档��������Ӧ��
	 *        5��localCacheScope:���ػ��棻��һ������SESSION��Ĭ�ϣ�����ǰ�Ự���������ݶ��ᱣ���ڻỰ������
	 *            STATEMENT�����Խ���һ�����档
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
	
	// ��������
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
            
           //�ڶ��β�ѯ��Ӷ��������л�ȡ���ݣ���û�з����µ�����
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
