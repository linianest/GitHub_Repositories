# MyBatis

# 一、简介

1. 下载地址

   https://github.com/mybatis/mybatis-3

2. 引入包

   - mysql-connector-java-bin.jar
   - mybatis.jar包

# 二、HelloWorld初体验

## 1、sql-id方式

步骤：

1. 创建全局配置文件：mybatis-config.xml
2. 创建sql配置文件：EmployeeMapper.xml
3. 创建日志文件log4j.xml
4. 其他配置 .根据配置文件（全局配置文件）创建一个SqlSessionFactory对象
   	 *    有数据源运行环境信息
      	 * 2.sql映射文件:配置了每一个sql，以及sql的封装规则等
      	 * 3.将sql映射文件配置在全局配置文件中
      	 * 4.写代码
      	 *    1）、根据配置文件得到SqlSessionFactory
      	 *    2）、使用SqlSessionFactory获取到SqlSession使用它来完成增删改查，
      	 *         一个SqlSession就是代表和数据库的一次会话，用完关闭
      	 *    3）、使用一个sql的唯一标识来告诉mybatis来执行哪个sql；sql都保存在EmployeeMapper.xml中

实体类，映射数据库

```java
package com.ln.mybaits.bean;

public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + "]";
	}

	
}

```

log4j.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
<appender name="console" class="org.apache.log4j.ConsoleAppender">
<param name="Target" value="System.out" />
<layout class="org.apache.log4j.PatternLayout">
<param name="ConversionPattern" value="[CateringLog] %d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c - %m%n" />
</layout>
</appender>
<appender class="org.apache.log4j.RollingFileAppender" name="file">
<param name="File" value="d:/companyProject/logs/catering.log" />
<param name="Append" value="true" />
<param name="MaxFileSize" value="1024KB" />
<param name="MaxBackupIndex" value="5" />
<layout class="org.apache.log4j.PatternLayout">
<param name="ConversionPattern" value="[CateringLog] %d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c - %m%n" />
</layout>
</appender>
<root>
<priority value="debug" />
<appender-ref ref="console" />
<appender-ref ref="file" />
</root>
<!--通过<logger></logger>的定义可以将各个包中的类日志输出到不同的日志文件中-->
<logger name="org.springframework">
<level value="ERROR" />
</logger>
<logger name="org.mybatis.spring">
<level value="ERROR" />
</logger>
<logger name="net.sf.ehcache">
<level value="ERROR" />
</logger>
<logger name="com.mchange.v2">
<level value="ERROR" />
</logger>
<logger name="java.sql">
<level value="debug" />
</logger>
</log4j:configuration>

```



全局配置文件：mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
 PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url" value="jdbc:mysql://localhost:3306/mybatis" />
				<property name="username" value="root" />
				<property name="password" value="root" />
			</dataSource>
		</environment>
	</environments>
	<!-- 将我们写好的sql映射文件（EmployeeMapper.xml）一定要注册到全局的配置文件中 （mybais-config.xml）-->
	<mappers>
		<mapper resource="EmployeeMapper.xml" />
	</mappers>
</configuration>
```



Employee.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ln.mybaits.employeeMapper">
<!--
namespace名称空间
id 唯一表示
resultType 返回值类型 
#{id}:从传来的参数中取出id值
-->
 <select id="selectEmp" resultType="com.ln.mybaits.bean.Employee">
 select id,last_name lastName,gender,email from tbl_employee where id = #{id}
 </select>
</mapper>
```



Test测试类：

```java
package com.ln.mybaits.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ln.mybaits.bean.Employee;

public class MyBatisTest {
	
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

}

```



## 2、接口方式-mapper

步骤：

1. 创建查询接口：EmployyMapper.java
2. 修改EmployeeMapper.xml
   - namespace:必须是接口的全类名
   - id必须和操作的接口方法一样
3. 测试方法test

EmployyMapper.java

```java
package com.ln.mybaits.dao;

import com.ln.mybaits.bean.Employee;

public interface EmployeeMapper {

	
	public Employee getEmployee(Integer id);
}

```



EmployeeMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ln.mybaits.dao.EmployeeMapper">
<!--
namespace名称空间:指定接口的全类名
id 唯一表示
resultType 返回值类型 
#{id}:从传来的参数中取出id值

public Employee getEmployee(Integer id);
-->
 <select id="getEmployee" resultType="com.ln.mybaits.bean.Employee">
 select id,last_name lastName,gender,email from tbl_employee where id = #{id}
 </select>
</mapper>
```

test2

```java
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

public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
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

```

对比

***
 * 1、接口编程
 * 原生：                     Dao    =====>  DaoImpl
 * mybatis        Mapper ======>  xxMaper.xml
 * 
 * 2.SqlSession代表和数据库的一次会话：用完必须关闭
 * 3.SqlSession和Connection一样都是非线程安全的。每次使用都应该去获取新的对象
 * 4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * （将接口与xml绑定）
 * EmployeeMapper employeeMapper=sqlSession.getMapper(EmployeeMapper.class);
 * 5.两个重要的配置文件
 * mybatis的全局配置文件：数据库连接池信息、事务管理器信息。。。。系统运行环境信息
 * sql映射文件：包含了每一个sql语句的映射信息
 * 将sql抽取出来




## 3、全局配置文件

1. 使用properties配置数据源

   dbconfig.properties

   ```properties
   jdbc.driver=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/mybatis
   jdbc.username=root
   jdbc.paassword=root
   ```

   修改mybatis-config.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   	<!-- 1、mybatis可以使用 properties来引入外部的配置文件的内容 
   	      resources:引入类路径下资源 
   	      url:引入网络资源或者磁盘路径下资源 
   	-->
   	<properties resource="dbconfig.properties"></properties>
   	<environments default="development">
   		<environment id="development">
   			<transactionManager type="JDBC" />
   			<dataSource type="POOLED">
   				<property name="driver" value="${jdbc.driver}" />
   				<property name="url" value="${jdbc.url}" />
   				<property name="username" value="${jdbc.username}" />
   				<property name="password" value="${jdbc.password}" />
   			</dataSource>
   		</environment>
   	</environments>
   
   	<!-- 将我们写好的sql映射文件（EmployeeMapper.xml）一定要注册到全局的配置文件中 （mybais-config.xml） -->
   	<mappers>
   		<mapper resource="EmployeeMapper.xml" />
   	</mappers>
   </configuration>
   ```

   

2. settings标签

   1. 开启驼峰命名法：默认是false

      ```xml
      <!--
         2. settings 包含很多重要的设置项
          setting  用来设置每一项
             name:设置项名
             value:设置项的值
       -->
      <settings>
      <setting name="mapUnderscoreToCamelCaseEnables" value="true"/>
      </settings>
      ```

3. 别名

   - typeAliases别名处理器:可以为java类起别名 ，**别名不区分大小写**
     	    type:要起别名的全类名；默认别名：employee
     	    alias:新别名
     	     <typeAlias type="com.ln.mybaits.bean.Employee" alias="emp"/>
     	     package:为包路径及子包下的类批量起别名，采用的是默认别名（类名小写）
   - @Alias注解为类起别名

## 4、mapper配置

1.  resource:类路径下的资源

2. url:网络资源文件或者磁盘资源文件  

3. class：注册接口

   - EmployeeMapper.xml必须和接口在同一包下并且接口的名字必须是EmployeeMapper

   - 注解版**==:不需要EmployeeMapper.xml配置文件==**

     ```java
     package com.ln.mybaits.dao;
     
     import org.apache.ibatis.annotations.Select;
     
     import com.ln.mybaits.bean.Employee;
     
     public interface EmployeeMapperAnnotation {
     
     	@Select("select * from tbl_employee where id = #{id}")
     	public Employee getEmployee(Integer id);
     }
     
     ```

     mybatis-config.xml修改一下

     ```xml
     	<!--
     	 将我们写好的sql映射文件（EmployeeMapper.xml）一定要注册到全局的配置文件中 （mybais-config.xml）
     	 
     	 1.重要的使用mapper.xml
     	 2.不重要的使用注解版的。方便快捷
     	 -->
     	<mappers>
     		<mapper resource="EmployeeMapper.xml" />
     		<mapper class="com.ln.mybaits.dao.EmployeeMapperAnnotation" />
     	</mappers>
     ```

     