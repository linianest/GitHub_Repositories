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

###1、properties配置数据源

```properties
    <!--
    所有标签的名称和顺序排列
    Content Model : (properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, 
 objectWrapperFactory?, reflectorFactory?, plugins?, environments?, databaseIdProvider?, 
 mappers
      -->
```



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

   

   



   ### 2、settings标签

   

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

      

      

###3、别名typeAliases

      - typeAliases别名处理器:可以为java类起别名 ，**别名不区分大小写**
             type:要起别名的全类名；默认别名：employee
           	    alias:新别名
           	     <typeAlias type="com.ln.mybaits.bean.Employee" alias="emp"/>
           	     package:为包路径及子包下的类批量起别名，采用的是默认别名（类名小写）
      - @Alias注解为类起别名



###4、环境配置environments

```properties
environments：环境集合：myBatis可以配置多种环境，default指定使用某种环境，达到快速切换    
      environment：配置一个具体的环境信息；必须有两个标签；id代表当前环境的唯一标识；
         transactionManager：事务管理器；
              type：事务管理器的类型：JDBC(JdbcTransactionFactory)|MANAGED(ManagedTransactionFactory)
                           自定义事务管理器:实现接口TransactionFactory。type指定为全类名（一般用spring来配置事务管理器）
         dataSource:数据源；
              type:UNPOOLED(不使用连接池：UnPooledDataSourceFactory)
                   |POOLED(PooledDataSourceFactory)默认使用。
                   |JNDI(JndiDataSourceFactory)
                          自定义数据源:实现DataSourceFactory接口，type是全类名
```



### 5、DatabaseldProvider多数据库

```xml
	<!--
	     5、  databaseIdProvider:支持多数据库厂商的；
	       type="DB_VENDOR"  作用是得到数据库厂商的标识（驱动自带的），mybatis根据数据库厂商标识来执行不同的sql,通过databaseId="mysql"
	       MYSQL、Orace、sqlserver   xxxx
	-->
	<databaseIdProvider type="DB_VENDOR">
	<!-- 为不同的数据库厂商起别名 -->
	<property name="MySQL" value="mysql"/>
	<property name="Oracle" value="oracle"/>
	<property name="SQL Server" value="sqlserver"/>
	</databaseIdProvider>
```

**通过databaseId="oracle"来区分厂商**

```xml


     <select id="getEmployee" resultType="com.ln.mybaits.bean.Employee" databaseId="mysql">
     select * from tbl_employee where id = #{id}
     </select>
     <select id="getEmployee" resultType="com.ln.mybaits.bean.Employee" databaseId="oracle">
         select * from tbl_employee where id = #{id}
     </select>

```



### 6、mappers---sql映射文件

mappers：将sql映射注册到全局配置中

 mapper：注册一个sql映射  

>    resource：引用类路径下的sql映射文件
>    url：引用网络路径或者磁盘路径下的资源
>    class:引用接口
>

```properties
mappers：将sql映射注册到全局配置中
    mapper：注册一个sql映射  
        注册配置文件
            EmployMapper.xml
                resource：引用类路径下的sql映射文件
                url：引用网络路径或者磁盘路径下的资源
        注册接口的形式
            class:注册接口（全类名）
                1、有sql映射文件，映射文件名必须和接口同名并且放在与接口同目录下
                2、没有sql映射文件，所有的sql都是利用注解写在接口上
               	推荐：
	              比较重要的和复杂的Dao接口，写sql映射文件，
	              不重要的Dao接口为了开发快速可以使用注解版的
     package:批量注册 （可以将EmployeeMapp.xml在类路径下conf创建一个与src同名的包）
	         就不用每个sql配置文件都注册写一遍
```

基于注解版的mybatis

```java
/**
 * 注解版的接口，不需要配置文件，但是每个方法都得写sql语句
 * @author LiNian
 *
 */
public interface EmployeeMapperAnnotation {

	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmployee(Integer id);
}

```



​	







## 4、mapper.xml配置

1. resource:类路径下的资源

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




# 三、mybatis-crud

  ### 1、创建项目配置环境

1. 创建bean类
2. 创建接口Employee.java
3. 配置myabtis.xml文件

```java
package com.ln.mybaits.bean;

public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	
	
	public Employee() {
	}
	public Employee(Integer id, String lastName, String email, String gender) {
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}
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



创建接口dao

```java
package com.ln.mybaits.dao;

import com.ln.mybaits.bean.Employee;

public interface EmployeeMapper {

	
	public Employee getEmployee(Integer id);
	
	public void addEmp(Employee employee);
	public void updateEmp(Employee employee);
	
	public void deleteEmpById(Integer id);
}

```

配置mybatis.xml

配置数据源dbconfig.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis
jdbc.username=root
jdbc.password=root
```



```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
 PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
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
		<mapper resource="com/ln/mybatis/dao/EmployeeMapper.xml" />
	</mappers>
</configuration>
```

EmployeeMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ln.mybaits.dao.EmployeeMapper">
	<!-- namespace名称空间:指定接口的全类名 id 唯一表示 resultType 返回值类型 #{id}:从传来的参数中取出id值 
		public Employee getEmployee(Integer id); -->
	<select id="getEmployee" resultType="com.ln.mybaits.bean.Employee">
		select id,last_name lastName,gender,email from tbl_employee where id = #{id}
	</select>
	
	<!--parameterType可以省略 如果要写，就指定全类名或者别名 -->
	<insert id="addEmp" parameterType="com.ln.mybaits.bean.Employee">
	    insert into tbl_employee (last_name,email,gender)
	    values(#{lastName},#{email},#{gender}) 
	</insert>
	
	<update id="updateEmp">
	   update tbl_employee
	    set last_name=#{lastName}, email=#{email}, gender=#{gender}
	    where id =${id}
	</update>
	
	<delete id="deleteEmpById">
	   delete from tbl_employee where id=#{id}
	</delete>
</mapper>
```

测试:

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
	
	
	
	/**
	 * 测试增删改查
	 * 1、mybatis允许增删改直接定义以下数据类型
	 *    Integer、Long、Boolean
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException{
		//1.创建一个SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory=getSqlSessionFactory();
		//2.获取sqlSession对象，不会自动提交数据
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		//3.获取接口的实现类对象
		//会为接口自动的创建一个代理对象，代理对象去执行CRUD
		try {
			EmployeeMapper employeeMapper=sqlSession.getMapper(EmployeeMapper.class);
			// 测试添加
//			Employee employee=new Employee(null, "jerry", "jerry@ln.com", "1");
//			employeeMapper.addEmp(employee);
		    // 测试修改
//			Employee employee=new Employee(1, "jerry", "jerry@ln.com", "1");
//			employeeMapper.updateEmp(employee);
			// 测试删除
			employeeMapper.deleteEmpById(2);
			
			// 手动提交数据
			sqlSession.commit();
		} finally {
			// TODO: handle finally clause
			sqlSession.close();
		}
		
	}
}

```

### 2、注意事项

1. 需要手动提交sql语句

   ​	sqlSession.commit();

   ​        sqlSession.commit(true);自动提交

2. 返回值类型，只需要在接口中定义就好Integer、Long、boolean

3. parameterType可以省略 如果要写，就指定全类名或者别名 

4. 获取自增主键的值

    mysql支持自增主键 ，自增主键值的获取，mybatis也是利用statement.getGenreatedKeys();
    useGeneratedKeys="true"，使用自增主键获取主键值

    keyProperty:指定对应的主键属性，也就是mybatis获取以后，将这个值封装给Javabean的哪个属性

5. Oracle不支持自增，Oracle使用序列来模拟自增；
    每次插入的数据的主键都是从序列中获取的值；

   通过先获取序列号后再执行添加语句

   ```xml &lt;insert id=&quot;addEmp&quot; parameterType=&quot;com.ln.mybaits.bean.Employee&quot;
   <insert id="addEmp" databaseId="oracle">
       <!-- keyProperty="id":查出的主键值封装给javabean的id属性
     order="BEFORE"  ：这个sql在插入sql之前执行
            AFTER:在插入sql执行之后，如果需要获取值用xxx.curval
     resultType="Integer"：查出的数据返回值类型
     运行顺序：
          先运行selectKey查询id的sql,查出的id值封装给javabean的id属性
          再运行插入的sql；就可以去除id属性对应的值
      -->
       <selectKey keyProperty="id" order="BEFORE" resultType="Integer">
           select xxxx.nextval from dual;
       </selectKey>
       insert into tbl_employee (id,last_name,email,gender)
       values(#{id},#{lastName},#{email},#{gender}) 
   </insert>
   ```

   
# 四、mybatis的参数处理

   ```properties
单个参数：mybatis不会做任何处理
  #{参数名}：取出参数值
多个参数：mybatis会做特殊处理
    多个参数会被封装成一个map
    key:param1.....paramN,或者参数的索引也可以,从0开始。
    value:传入的参数值
    #{}就是从map中获取指定的key的值。
    操作：
        方法：public Employee getEmpByIdAndLastName(String id,String lastName);
        取值方案1：#{parm1},#{parm2}
        
命名参数：明确指定封装参数时map的key: 
   多个参数会被封装成一个map
     key：使用@Param注解指定的值           
     value:参数值
     public Employee getEmpByIdAndLastName(@Param("id") String id,@Param("lastName") String lastName);   
     取值方案2：#{id},${lastName}
     #{指定的key}：取出参数值
     
如果多个参数正好是我们业务逻辑的数据模型，直接传入pojo（简单模型model）
     #{属性名}：取出传入的pojo的属性值
     
如果多个参数不是我们业务逻辑的数据模型，不经常使用，为了方便,我们可以传入map
    public Employee getEmpByIdAndLastName(Map<key,String> map);
    #{key}:取出map中对应的值stringval
    
TO:   
如果多个参数不是我们业务逻辑的数据模型，但是经常使用，推荐编写一个TO（Transfer Object）
Page{
   int index;
   int size;
}

===========================思考========================
1.   public Employee getEmp(@Param("id") String id,String lastName);
           取值：id==>#{id/param1}  lastName==>#{param2}
    
2.   public Employee getEmp(@Param("id") String id,@Param("emp") Employee emp);
           取值：id==>#{param1}  lastName==>#{param2.lastName/emp.lastName}   
 
3.   ##特别注意：如果是Connection (List、Set)类型或者是数组
                                  也会特殊处理。也是把传入的list或者数组封装在map中。
                 key:Connection(connection),如果是List还可以使用这个key(list), 数组（array）
       public Employee getEmp(List<Integer> ids)
             取值：取出第一个id的值：   #{list[0]}
 
 ==================结合源码，mybatis怎么处理参数===============
 
 
   ```



### 1、返回参数使用map封装，主键id做key

   ```java
// 返回一条记录的map<Integer, Employee>：key就是就是记录的主键，值就是对应的对象
	//告诉mybatis封装这个map的时候使用哪个属性封装成 key
	@MapKey("id")
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastname)		
        
   Map<Integer, Employee> map = employeeMapper.getEmpByLastNameLikeReturnMap("%r%");
    System.out.println(map);
   ```

	<!--
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName); 
	 -->
	<select id="getEmpByLastNameLikeReturnMap" resultType="com.ln.mybaits.bean.Employee">
	select * from tbl_employee where last_name like #{lastName}
	</select>
  ### 2、resultMap（自定义返回类型）与resultType

1. resultType:mybatis自动匹配pojo的属性值，如果有个别不一样，可以起别名，如果类似，可以用开启驼峰命名法。
2. resultMap：自定义返回类型封装
3. **==resultMap与rsultType只能用一个==**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
 "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ln.mybaits.dao.EmployeeMapperPlus">
	<!-- 自定义返回类型resultMap与resultType只能用一个 -->

    <!-- 自定义某个javaBean的封装规则
       type:自定义规则的java类型
       id:唯一id方便引用
    -->
    <resultMap type="com.ln.mybaits.bean.Employee" id="myEmp">
    <!-- 指定主键列的封装规则
    id定义主键会底层优化
    column：指定数据表中的那一列
    property:对应的哪个javaBean属性
     -->
       <id column="id" property="id"></id>
       <result column="last_name" property="lastName"/>
       <!-- 其他不指定的列会自动封装，我们只要写resultMap,就会把全部的映射规则都写上 -->
    </resultMap>
    
           
	<!-- public Employee getEmployee(Integer id); -->
	<!--
	@Alias("emp")给pojo起别名 
	 -->
	<select id="getEmployee" resultMap="myEmp">
		select * from tbl_employee where id=#{id}
	</select>

</mapper>
```



接口方法

```java
package com.ln.mybaits.dao;


import com.ln.mybaits.bean.Employee;

public interface EmployeeMapperPlus {


	public Employee getEmployee(Integer id);


}

```

测试

```java
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

```

#### 2.1、resultMap封装联合查询：级联查询

```xml
<!--
	  联合查询：级联属性封装结果集 
	 -->
	<resultMap type="com.ln.mybaits.bean.Employee" id="MyDifEmp">
	   <id column="id" property="id"></id>
       <result column="last_name" property="lastName"/>
       <result column="did" property="dept.id"/>
       <result column="dept_name" property="dept.department"/>
	</resultMap>
	
	<select id="" resultMap="MyDifEmp">
	   联合查询sql语句
	</select>
```



####2.2、resultMap封装联合查询：嵌套封装级联查询

```xml
<!--
	  联合查询：使用association定义关联单个对象的封装规则
	 -->
	<resultMap type="com.ln.mybaits.bean.Employee" id="MyDifEmp2">
	   <id column="id" property="id"></id>
       <result column="last_name" property="lastName"/>
       <!--association可以指定联合的javabean对象
                     指定哪个javaBean中的哪个属性是联合的对象
           javaType:指定这个属性对象的类型[不可省略]
         -->
      <association property="dept" javaType="com.ln.mybaits.bean.Department">
         <result column="did" property="id"/>
         <result column="dept_name" property="departmentName"/>      
      </association>
	</resultMap>
	
	<select id="" resultMap="MyDifEmp2">
	  <!--  联合查询sql语句 -->	
	</select>
```

#### 2.3、分布查询

```xml
<resultMap type="com.ln.mybaits.bean.Employee" id="MyDifEmp3">
	   <id column="id" property="id"></id>
       <result column="last_name" property="lastName"/>
       <!--association定义关联对象的封装规则
          select:表名当前属性是调用select指定的方法查出的结果
           column:指定将哪一列的值传给这个方法（因为先执行了employy的查询语句，有结果）
           
           流程：使用select指定的方法（传入cloumn指定的这列参数的值）查出对象，并封装给property指定的属性
                     
         -->
      <association property="dept" select="xxxdao.Mapper.getById" 
      column="d_id">   
      </association>
	</resultMap>
	
	<select id="" resultMap="MyDifEmp3">
	  <!--  查询员工表 -->	
	</select>
```



   #### 3、resultMap延迟加载（懒加载）

####1、使用association分段查询和延迟加载

只需要在分段查询的基础上，加上两个配置，只有在需要的查询

			<!-- 开启懒加载 默认为false -->
			<setting name="lazyLoadingEnabled" value="true" />
			<!-- 侵入懒加载，只有在需要的再查询默认为false -->
			<setting name="aggressiveLazyLoading" value="false"/>

#### 2、使用connection标签，分段查询，懒加载

查询部门信息的时候，也把相对应的员工的信息查询出来。

效果与association一样，一样的运用。

- 通过select,选择分段查询的的sql语句，**注意要全类名**，
- column,将上个语句的参数，传给resultMap,继续查询。
- 如果传递多列的值。使用column={key1=column1,key2=colum2}

#### 3、discriminator鉴别器

>mybatis可以使用discriminator鉴别器判断某列的值，然后根据某列的值改变封装行为封装Employee.

```xml
<!-- <discriminator javaType=""></discriminator> 鉴别器：mybatis可以使用discriminator鉴别器判断某列的值，然后根据某列的值改变封装行为封装Employee 
  例子： 如果查出的是女生（gender），就把部门信息查询出来,否则不查询;如果查出的是男生，就把lastNamt这一列的值赋值给email -->
<resultMap type="com.ln.mybaits.bean.Employee" id="MyDifEmp4">
    <id column="id" property="id"></id>
    <result column="last_name" property="lastName" />
    <!--association定义关联对象的封装规则 select:表名当前属性是调用select指定的方法查出的结果 column:指定将哪一列的值传给这个方法（因为先执行了employy的查询语句，有结果） 
   流程：使用select指定的方法（传入cloumn指定的这列参数的值）查出对象，并封装给property指定的属性 -->
    <!-- column：指定要判断的列名 javaType：列值对应的java类型 -->
    <discriminator javaType="String" column="gender">
        <!-- 女生 -->
        <case value="0" resultType="com.ln.mybaits.bean.Employee">
            <!-- association查询select的sql语句的是部门信息 -->
            <association property="dept" select="xxxdao.Mapper.getById"
                         column="d_id">
            </association>
        </case>
        <!-- 男生 如果查出的是男生，就把lastNamt这一列的值赋值给email -->
        <case value="1" resultType="com.ln.mybaits.bean.Employee">
            <id column="id" property="id"></id>
            <result column="last_name" property="lastName" />
            <result column="last_name" property="email" />

        </case>
    </discriminator>
</resultMap>

<select id="查询的emp" resultMap="MyDifEmp4">
    <!-- 查询员工表 -->
</select>
```



# 五、mybatis动态sql处理

><!--
>	if|
>	choose(when,otherwise)|
>	trim(where,set)|
>	foreach
>	  -->
>
>

###1、if标签

```xml
	<!-- test:判断表达式 (OGNL) c:if test一样 从参数中取值进行判断 遇见特殊符号应该去写转义字符 ''=====&quot;&quot; 
				trim():截取 -->
			<if test="id!=null">
				id=#{id} and
			</if>
```

### 2、where、trim标签（封装查询条件）

```properties
/* 如果某个条件没带，sql拼装可能出问题
* 1、sql的where后面加上 1=1 以后的条件都会and xxxx
* 2、使用where标签拼装sql条件语句,它会将多出来的and或or去掉
*    where只会去掉第一个多出来的and或or去掉
* 3、
* */
```



#### 1、if语句中的and|or放在前面用where

>它会将判断条件自动拼装组合，(and 、or)

```xml
<where>
    <!-- test:判断表达式 (OGNL) c:if test一样 从参数中取值进行判断 遇见特殊符号应该去写转义字符 ''=====&quot;&quot; 
    trim():截取 -->
    <if test="id!=null">
        id=#{id} and
    </if>
    <if test="lastName!=null and lastName!=&quot;&quot;">
        and last_name like #{lastName}
    </if>
    <!-- -->
    <if test="email!=null and email.trim()!=&quot;&quot;">
        and email=#{email} 
    </if>
    <!-- ognl会进行字符串与数字的转换判断 -->
    <if test="gender==0 or gender==1">
        and gender=#{gender}
    </if>
</where>
```

#### 2、if语句中的and|or放在后面用trim

>**后面多出来的and或者or，where标签并不能解决**
>		prefix="":前缀：trim标签体是整个字符串拼装后的结果；
>		            prefix给整个拼串后的结果加前缀
>		prefixOverrides="":
>		              前缀覆盖：去掉整个字符串前面多余的字符字符
>		suffix="":后缀
>		            suffix给整个拼串后的结果加后缀
>		suffixOverrides="" 
>		             后缀覆盖：去掉整个字符串后面多余的字符字符

```xml
<!-- public List<Employee> getEmpsByConditionTrim(Employee employee) ; -->
<select id="getEmpsByConditionTrim" resultType="com.ln.mybaits.bean.Employee">
    select * from tbl_employee
    <!-- 后面多出来的and或者or，where标签并不能解决
  prefix="":前缀：trim标签体是整个字符串拼装后的结果；
              prefix给整个拼串后的结果加前缀
  prefixOverrides="":
                前缀覆盖：去掉整个字符串前面多余的字符字符
  suffix="":后缀
              suffix给整个拼串后的结果加后缀
  suffixOverrides="" 
               后缀覆盖：去掉整个字符串后面多余的字符字符
  -->
    <trim prefix="where" prefixOverrides="" suffix="" suffixOverrides="and">		
        <if test="id!=null">
            id=#{id} and
        </if>
        <if test="lastName!=null and lastName!=&quot;&quot;">
            last_name like #{lastName} and
        </if>
        <!-- -->
        <if test="email!=null and email.trim()!=&quot;&quot;">
            email=#{email} and
        </if>
        <!-- ognl会进行字符串与数字的转换判断 -->
        <if test="gender==0 or gender==1">
            gender=#{gender}
        </if>
    </trim>
</select>
```



### 3、choose (when,otherwise)分支选择

```xml
<!-- public List<Employee> getEmpsByConditionChoose(Employee employee) ; -->
<select id="getEmpsByConditionChoose" resultType="com.ln.mybaits.bean.Employee">
    select * from tbl_employee
    <!-- choose(when,otherwise)如果携带了id，就用id查询，如果携带了lastName，用lastName查询 -->
    <where>
        <choose>
            <when test="id!=null">
                id=#{id}
            </when>
            <when test="lastName!=null and lastName!=&quot;&quot;">
                last_name like #{lastName}
            </when>
            <!-- -->
            <when test="email!=null and email.trim()!=&quot;&quot;">
                email=#{email}
            </when>
            <otherwise>
                gender=0
            </otherwise>
        </choose>
    </where>
</select>
```



### 4、set（封装修改条件）

```xml
<!-- public void updateEmp(Employee employee) ; -->
<update id="updateEmp">
    <!--
   1、使用set、if方案
   2、使用trim、if方案。能解决，问题 
  -->
    <!-- 	
 update tbl_employee
  <set>
   <if test="last!=null">
    last_name=#{lastName},
   </if>
   <if test="email!=null">
    email=#{email},
   </if>
   <if test="gender!=null">
    gender=#{gender}
   </if>
  </set>
  where id=#{id} 
  -->


    update tbl_employee
    <trim prefix="set" suffixOverrides=",">
        <if test="lastName!=null">
            last_name=#{lastName},
        </if>
        <if test="email!=null">
            email=#{email},
        </if>
        <if test="gender!=null">
            gender=#{gender}
        </if>
    </trim>
    where id=#{id} 

</update>
```



### 5、foreach批量CRUD

#### 1、批量查询

java接口，通过**==@Param("ids")==**，设置固定的参数名称

```java
public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids) ;
```

xml配置文件

```xml
<!-- public List<Employee> getEmpsByConditionForeach(List<Integer> ids) ; -->
<select id="getEmpsByConditionForeach" resultType="com.ln.mybaits.bean.Employee">
    select * from tbl_employee where id in 
    <!--
    collection:指定要遍历的集合
       list类型的参数会做特殊处理封装在map中，map的key就叫list
       item:将遍历出的元素赋值给指定的变量
       #{变量名}就能取出变量的值也就是当前遍历的元素
       separator:每个元素之间的分隔符
       open:遍历所有结果拼接一个开始的字符
       close:遍历所有结果拼接一个结束的字符
       index:索引。遍历list的时候index是索引, 遍历map的时候index是map的key,item就是map的值

   -->
    <foreach collection="ids" item="item_id" separator=","
             open="(" close=")">
        #{item_id}
    </foreach>

</select>

```

#### 2、批量保存、修改

接口

```java
public void addEmp(@Param("emps") List<Employee> employee) ;
```



xml配置：有两种方案:**==mysql下批量保存：可以用foreach遍历，因为mysql可以支持values(),(),(),() 语法==**

- values(),(),(),() 支持mysql，不支持oracle
- 如果oracle想用，用第二种方案

```xml
<！--使用foreach,values批量支持多个-->
    <!-- public void addEmp(@Param("emps") List<Employee> employee) ; -->
    <insert id="addEmp">
        insert into tbl_employee(last_name,email,gender)
        values
        <foreach collection="emps" item="emp" separator=",">
            (#{emp.lastName},#{emp.email},#{emp.gender})
        </foreach>
    </insert>
<！--使用foreach,sql语句批量，但是要开启mysql支持，在连接url方式上添加allowMultiQueries=true                                                               -->
    <insert id="addEmp">	
        <foreach collection="emps" item="emp" separator=";">
            insert into tbl_employee(last_name,email,gender)
            values(#{emp.lastName},#{emp.email},#{emp.gender})
        </foreach>
    </insert>
     
    
```

test.java

```java
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
```



##### 1、oracle批量方式

多个insert语句放在begin、end；中

```java

<!-- oracle批量保存 使用begin、end;-->
	<insert id="addEmp" databaseId="oracle">	
		<foreach collection="emps" item="emp" separator=";" open="begin" close="end;">
		insert into tbl_employee(last_name,email,gender)
		values(#{emp.lastName},#{emp.email},#{emp.gender})
		</foreach>
	</insert>
```

使用中间表的方式







### 6、内置参数

>两个内置参数
>	  不只是方法传递过来的参数可以被用来判断、取值
>	  mybatis默认还有两个内置参数
>	  _parameter:代表整个参数
>	          单个参数：_parameter:就是这个参数
>	          多个参数：参数会被封装为一个map； _parameter就是这个map;   
>	  _databaseId：如果配置了DatabaseIdProvider标签，
>	     _databaseId代表当前数据库的别名oracle

前提是配置了这个

```xml
	<!-- 给数据库厂商起别名 -->
	<databaseIdProvider type="DB_VENDOR">
	 <property name="MYSQL" value="mysql"/>
	 <property name="Oracle" value="orace"/>
	 <property name="SQL Server" value="sqlserver"/>
	</databaseIdProvider>
```

接着配置

```xml
<select id="getEmpsByConditionForeach" resultType="com.ln.mybaits.bean.Employee">
    <if test="_databaseId=='mysql'">
        select * from tbl_employee
        <if test="_parameter!=null">
            where last_name = #{_parameter.lastName}
        </if>
    </if>
    <if test="_databaseId=='oracle'">
        select * from employees
        <if test="_parameter!=null">
            where last_name = #{_parameter.lastName}
        </if>
    </if>

</select>
```

**bind:可以将OGNL表达式的值绑定到一个变量中，方便后来引用**

```xml
<select id="getEmpsByConditionForeach" resultType="com.ln.mybaits.bean.Employee">
    <!--bind:可以将OGNL表达式的值绑定到一个变量中，方便后来引用  -->
    <bind name="_lastName" value="'%'+lastName+'%'"/>
    <if test="_databaseId=='mysql'">
        select * from tbl_employee
        <if test="_parameter!=null">
            where last_name like #{_lastName}
        </if>
    </if>
    <if test="_databaseId=='oracle'">
        select * from employees
        <if test="_parameter!=null">
            where last_name like #{lastName}
        </if>
    </if>

</select>
```



### 7、sql抽取可重用的语句

>- sql抽取
>
>- include引用
>
>   refid:引用的唯一标识
>
>  property：定义属性，通过${属性名}取值

```xml
<!-- 抽取可重用的sql片段。方便后面引用  -->
<sql id="insertColumn">

    last_name,email,gender
</sql>
<!-- include引用
  refid：声明的唯一标识
   -->
<include refid="insertColumn"></include>
```







# 六、MyBatis的缓存机制

## 1、缓存简介

通过缓存机制，提升查询效率。

**一级缓存和二级缓存.**

> 1. 默认情况下，只有一级缓存（SqlSession级别的缓存，也成为本地缓存）开启
> 2. 二级缓存需要手动开启和配置，它是基于namespace级别的缓存
> 3. 为了提高扩展性。mybatis定义了缓存接口cache。我们可以通过实行cache接口来定义二级缓存。

## 2、一级缓存（本地缓存sqlSession）

```
 * 一级缓存：(本地缓存) sqlSession级别的缓存。默认是开启的
 *    与数据库同一次会话期间查询到的数据会存放在本地缓存中
 *    以后如果需要相同的数据，直接从缓存获取。
 *    
 *    一级缓存失效：每次访问都会查询库
 *       1、sqlSession不同
 *       2、sqlSession相同，但是查询条件不同（当前一级缓存中还没有这个数据）
 *       3、sqlSession相同；两次查询期间，执行的增删改操作（这次增删改可能对数据有影响）
 *       4、sqlSession相同，手动清空缓存。openSession.clearCache();
```

```java
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
```



## 3、二级缓存（全局缓存namespace）

### 1、使用及细节

二级缓存配置：

```xml
<cache eviction="FiFO" flushInterval="60000" readOnly="false" size="1024"></cache>
```

信息解析：

```
eviction:缓存回收策略； 默认的是LRU
- LRU：最近最少使用的；移除最长时间不被使用的对象；
- FiFO：先进先出；按对象进出缓存的顺序来移除它们；
- SOFT：软引用；移除基于垃圾回收器状态和软引用规则的对象。
- WEAK：弱引用；更积极的移除基于垃圾回收器状态和弱引用规则的对象。

flushInterval：缓存刷新间隔；缓存多长时间清空一次，默认是不清空。单位是毫秒
readOnly：是否只读；
true:只读；mybatis认为所有从缓存中获取数据的操作都是只读操作，不会修改数据。
        mybatis为了加快获取速度，直接就会从缓存中的引用交给用户。不安全，但是速度快
false：非只读：mybatis认为获取的数据可能会被修改。mybatis会利用序列化和反序列化的技术克隆一份新的数据给你。
安全，但是速度慢
size:缓存存放多少元素；
type:指定自定义缓存的全类名；实现Cache接口即可；
```

```properties
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
```

```java
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
```



### 2、设置和属性

```pro
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
```

### 3、Mybatis缓存详解

1. 每个一级缓存数据都是保存在sqlSqlsession会话当中，如果想当前会话关闭后，还想用数据，就得使用二级缓存（namespace）
2. 一个namespace对应一个二级缓存。只有当一级缓存会话关闭或者提交后，数据才会转移到二级缓存中。
3. 每个二级缓存都是有生命周期的（时间限制）；
4. 如果开启了二级缓存，新会话会从二级缓存中获取数据，如果没有才会查询数据库，查询完成后，根据策略配置，是否保存到二级缓存中。



## 4、MyBatis 整合Ehcache

1. 配置包

   - ehcache-core-2.6.8.jar
   - slf4j-api-1.6.1.jar
   - slf4j-log4j12-1.6.2.jar

2. 修改缓存

   ```xml
   <!--  使用ehcache缓存-->
       <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
   ```

3. 添加ehcache.xml配置文件

   ```xml
   <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:noNamespaceSchemaLocation="../config/ehcache.xsd">
   <!-- 磁盘保存路径  -->
   <diskStore path="F:\develop\ehcache" />
   <!--
    diskStore：指定数据在磁盘中存储的位置
    defaultCache：默认缓存的配置
    maxElementsInMemory：内存中缓存的数量
    maxElementsOnDisk：磁盘中保存的数量
    eternal
    overflowToDisk：内存溢出是否保存在磁盘中
    
    -->
   <defaultCache
   maxElementsInMemory="1000"
   maxElementsOnDisk="10000000"
   eternal="false"
   overflowToDisk="true"
   timeToIdleSeconds="120"
   timeToLiveSeconds="120"
   diskExpiryThreadIntervalSeconds="120"
   memoryStoreEvictionPolicy="LRU">
   </defaultCache>
   </ehcache>
   
   ```

   

# 七、Spring+SpringMVC+MyBatis整合（SSM）

## 1、配置环境

### 0、基本准备导包

1. 导包（spring+springmvc+mybatis+中间包（mybatis-spring-1.3.0.jar））
2. 配置mybatis环境
   - mybatis-config.xml(数据连接)
   - bean类
   - dao接口中EmployeeMapper
   - EmployeeMapper.xml的配置

### 1、配置MyBatis环境

1. 配置mybatis-config.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   	<properties resource="dbconfig.properties"></properties>
   	<settings>
   	<!--显示的指定我们需要的每个配置，防止版本更新后修改了配置  -->
   		<setting name="cacheEnabled" value="true" />
   		<!-- 开启懒加载  -->
   		<setting name="lazyLoadingEnabled" value="true" />
   		<!-- 侵入懒加载，只有在需要的再查询 -->
   		<setting name="aggressiveLazyLoading" value="false"/>
   		<setting name="multipleResultSetsEnabled" value="true" />
   		<setting name="useColumnLabel" value="true" />
   		<setting name="useGeneratedKeys" value="false" />
   		<setting name="autoMappingBehavior" value="PARTIAL" />
   		<setting name="defaultExecutorType" value="SIMPLE" />
   		<setting name="defaultStatementTimeout" value="25" />
   		<setting name="safeRowBoundsEnabled" value="false" />
   		<setting name="mapUnderscoreToCamelCase" value="true" />
   		<setting name="localCacheScope" value="SESSION" />
   		<setting name="jdbcTypeForNull" value="OTHER" />
   		<setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString" />
   	</settings>
   
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
   	<!-- 给数据库厂商起别名 -->
   	<databaseIdProvider type="DB_VENDOR">
   	 <property name="MYSQL" value="mysql"/>
   	 <property name="Oracle" value="orace"/>
   	 <property name="SQL Server" value="sqlserver"/>
   	</databaseIdProvider>
   	<!-- 将我们写好的sql映射文件（EmployeeMapper.xml）一定要注册到全局的配置文件中 （mybais-config.xml） -->
   	<mappers>
   		<mapper resource="com/ln/mybatis/dao/EmployeeMapper.xml" />
   	</mappers>
   </configuration>
   ```

   

2. 配置基本的接口和xml文件

   ```java
   package com.ln.mybatis.bean;
   
   import java.io.Serializable;
   
   import org.apache.ibatis.type.Alias;
   
   
   public class Employee implements Serializable{
   	/**
   	 * 
   	 */
   	private static final long serialVersionUID = 1L;
   	private Integer id;
   	private String lastName;
   	private String email;
   	private String gender;
   	
   	
   	public Employee() {
   	}
   	public Employee(Integer id, String lastName, String email, String gender) {
   		this.id = id;
   		this.lastName = lastName;
   		this.email = email;
   		this.gender = gender;
   	}
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

   

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.ln.mybaits.dao.EmployeeMapper">
   
   	<!-- public Employee getEmployee(Integer id); -->
   	<select id="getEmployee" resultType="com.ln.mybatis.bean.Employee">
   	select * from tbl_employee where id=#{id}
   	</select>
   	
   	
   </mapper>
   ```

   ```java
   package com.ln.mybaits.dao;
   
   import com.ln.mybaits.bean.Employee;
   
   public interface EmployeeMapper {
   
   	public Employee getEmployee(Integer id);
   
   	public Long addEmp(Employee employee);
   
   	public boolean updateEmp(Employee employee);
   
   	public void deleteEmpById(Integer id);
   }
   
   ```

   

### 2、配置Spring环境

1. 配置spring的环境applicationContext.xml以及web.xml中配置

   ```xml
    <!--spring的配置 needed for ContextLoaderListener -->
   	<context-param>
   		<param-name>contextConfigLocation</param-name>
   		<param-value>location:applicationContext.xml</param-value>
   	</context-param>
   
   	<!-- Bootstraps the root web application context before servlet initialization -->
   	<listener>
   		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   	</listener>
   ```

   applicationContext.xml需要引入成c3p0的jar包和dbcofig.properties

   dbcofig.properties

   ```xml
   jdbc.driver=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/mybatis?allowMultiQueries=true
   jdbc.username=root
   jdbc.password=root
   ```

   applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
   	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
   	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
   		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
   
   	<!--Spring希望管理所有的业务组件 ，等。。。。 -->
   	<context:component-scan base-package="com.ln.mybaits">
   		<!-- 除了控制器组件，其他所有组件都有spring管理 -->
   		<context:exclude-filter type="annotation"
   			expression="org.springframework.stereotype.Controller" />
   	</context:component-scan>
   
   	<!-- 引入数据库配置文件 -->
   	<context:property-placeholder location="classpath:dbconfig.properties" />
   	<!-- Spring用来控制业务逻辑。数据源、事务控制、aop -->
   	<bean id="pooledDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
   		<property name="driverClass" value="${jdbc.driver}" />
   		<property name="jdbcUrl" value="${jdbc.url}" />
   		<property name="user" value="${jdbc.username}" />
   		<property name="password" value="${jdbc.password}" />
   	</bean>
   
   </beans>
   
   ```

   

### 3、配置SpringMVC环境

   配置springMVC环境.默认的springmvc配置文件名字是：**==spring-servlet.xml==**在web-inf下

web.xml中假如springMVC的xml配置

```xml
<!-- SpringMVC配置 -->
  <!-- The front controller of this Spring Web application, responsible for handling all application requests -->
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<!-- Map all requests to the DispatcherServlet for handling -->
	<servlet-mapping>
		<servlet-name>spring</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```



spring-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
<!-- SpringMVC只是控制网站跳转逻辑 -->

<!-- 自动扫描组件：只扫描控制器；
use-default-filters: 关闭默认的过滤行为，下面的context:include-filter才会起作用
-->
<context:component-scan base-package="com.ln.mybatis.bean" use-default-filters="false">
<!--只扫描Controller控制器组件-->
<context:include-filter type="annotation" 
expression="org.springframework.stereotype.Controller"/>
</context:component-scan>

<!-- 视图解析器 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
 <property name="prefix" value="/WEB-INF/pages/"></property>
 <property name="suffix" value=".jsp"></property>
</bean>

<!-- 能处理动态资源 -->
<mvc:annotation-driven></mvc:annotation-driven>
<!-- 能正确处理静态资源 -->
<mvc:default-servlet-handler/>

</beans>

```



### 4、整合spring-mybatis

1. 配置事务管理器，基于连接池

   ```xml
   <!-- spring事务管理器 -->
   <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="pooledDataSource"></property>
   </bean>
   ```

   

2. 配置基于注解的事务管理器

   ```xml
   <!-- 开启基于注解的事务 -->
   <tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>
   ```

   

3. spring的配置文件中配置sqlSessionFactory(bean)

   ```xml
   <!-- spring会创建出SqlSessionFactory -->
   <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
   <property name="dataSource" ref="pooledDataSource"></property>
   <!-- configLocation指定mybatis全局配置文件的位置 -->
   <property name="configLocation" value="classpath:mybatis-config.xml"></property>
   <!-- mapperLocations:指定mapper文件的配置文件位置 -->
   <property name="mapperLocations" value="classpath:mybatis/mapper/*xml"></property>
   </bean>
   ```

4. mybatis的配置文件就可以精简

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   	<properties resource="dbconfig.properties"></properties>
   	<settings>
   	<!--显示的指定我们需要的每个配置，防止版本更新后修改了配置  -->
   		<setting name="cacheEnabled" value="true" />
   		<!-- 开启懒加载  -->
   		<setting name="lazyLoadingEnabled" value="true" />
   		<!-- 侵入懒加载，只有在需要的再查询 -->
   		<setting name="aggressiveLazyLoading" value="false"/>
   		<setting name="multipleResultSetsEnabled" value="true" />
   		<setting name="useColumnLabel" value="true" />
   		<setting name="useGeneratedKeys" value="false" />
   		<setting name="autoMappingBehavior" value="PARTIAL" />
   		<setting name="defaultExecutorType" value="SIMPLE" />
   		<setting name="defaultStatementTimeout" value="25" />
   		<setting name="safeRowBoundsEnabled" value="false" />
   		<setting name="mapUnderscoreToCamelCase" value="true" />
   		<setting name="localCacheScope" value="SESSION" />
   		<setting name="jdbcTypeForNull" value="OTHER" />
   		<setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString" />
   	</settings>
   
   	
   	<!-- 给数据库厂商起别名 -->
   	<databaseIdProvider type="DB_VENDOR">
   	 <property name="MYSQL" value="mysql"/>
   	 <property name="Oracle" value="orace"/>
   	 <property name="SQL Server" value="sqlserver"/>
   	</databaseIdProvider>
   
   </configuration>			
   
   
   ```

5.指定spring扫描的mybatis的接口mapper

```xml
   <!--扫描所有的mapper接口的实现，让这些mapper能够自动注入：
   mybatis-spring:scan:指定mapper接口的包名
     -->
   <mybatis-spring:scan base-package="com.ln.mybatis.dao"/>
```

   第二种方案(以前老版的项目使用方式)

```xml
   <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
   <property name="basePackage" value="com.ln.mybatis.dao"></property>
   </bean>			
```

6.测试



# 八、MyBatis逆向工程（MBG）

1. 下载mybatis-generator-core-1.3.6.jar的包

2. 导jar包

3. 模板mgb.xml

   > MyBatis3Simple:简单版的crud
   > MyBatis3:豪华版的，生成的是带有动态sql的

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE generatorConfiguration
     PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
     "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
   
   <generatorConfiguration>
     <classPathEntry location="/Program Files/IBM/SQLLIB/java/db2java.zip" />
   
     <context id="DB2Tables" targetRuntime="MyBatis3">
       <jdbcConnection driverClass="COM.ibm.db2.jdbc.app.DB2Driver"
           connectionURL="jdbc:db2:TEST"
           userId="db2admin"
           password="db2admin">
       </jdbcConnection>
   
       <javaTypeResolver >
         <property name="forceBigDecimals" value="false" />
       </javaTypeResolver>
   
       <javaModelGenerator targetPackage="test.model" targetProject="\MBGTestProject\src">
         <property name="enableSubPackages" value="true" />
         <property name="trimStrings" value="true" />
       </javaModelGenerator>
   
       <sqlMapGenerator targetPackage="test.xml"  targetProject="\MBGTestProject\src">
         <property name="enableSubPackages" value="true" />
       </sqlMapGenerator>
   
       <javaClientGenerator type="XMLMAPPER" targetPackage="test.dao"  targetProject="\MBGTestProject\src">
         <property name="enableSubPackages" value="true" />
       </javaClientGenerator>
   
       <table schema="DB2ADMIN" tableName="ALLTYPES" domainObjectName="Customer" >
         <property name="useActualColumnNames" value="true"/>
         <generatedKey column="ID" sqlStatement="DB2" identity="true" />
         <columnOverride column="DATE_FIELD" property="startDate" />
         <ignoreColumn column="FRED" />
         <columnOverride column="LONG_VARCHAR_FIELD" jdbcType="VARCHAR" />
       </table>
   
     </context>
   </generatorConfiguration>
   
   ```

   

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
<!--
MyBatis3Simple:简单版的crud
MyBatis3:生成的是带有动态sql的
 -->
  <context id="DB2Tables" targetRuntime="MyBatis3Simple">
  <!--jdbcConnection如何连接到目标数据库  -->
    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
        connectionURL="jdbc:mysql://localhost:3306/mybatis?allowMultiQueries=true"
        userId="root"
        password="root">
    </jdbcConnection>
<!-- java类型解析器 -->
    <javaTypeResolver >
      <property name="forceBigDecimals" value="false" />
    </javaTypeResolver>
<!--javaModelGenerator指定javaBean的生成策略
targetPackage：目标包名  
targetProject:目标工程
 -->
    <javaModelGenerator targetPackage="com.ln.mybatis.bean" targetProject=".\src">
      <property name="enableSubPackages" value="true" />
      <property name="trimStrings" value="true" />
    </javaModelGenerator>
<!--sqlMapGenerator:sql映射生成策略
  -->
    <sqlMapGenerator targetPackage="com.ln.mybatis.dao"  targetProject=".\conf">
      <property name="enableSubPackages" value="true" />
    </sqlMapGenerator>
<!--javaClientGenerator:指定mapper接口所在的位置
  -->
    <javaClientGenerator type="XMLMAPPER" targetPackage="com.ln.mybatis.dao"  targetProject=".\src">
      <property name="enableSubPackages" value="true" />
    </javaClientGenerator>
<!--table:指定要逆向生成那些表,根据表要创建javaBean
schema:哪一个数据库
tableName:数据库表名
domainObjectName:表名对应生成的javaBean的名字
  -->
    <table schema="mybatis" tableName="tbl_employee" domainObjectName="Employee" >
    </table>

  </context>
</generatorConfiguration>

```

使用代码生成

```java
@Test
public void testMBG() throws Exception{
    List<String> warnings = new ArrayList<String>();
    boolean overwrite = true;
    File configFile = new File("mbg.xml");
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(configFile);
    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    myBatisGenerator.generate(null);

}
```



# 九、Mybatis运行原理

## 1、运行流程梳理



1. 获取sqlSessionFactory对象
   1. 创建sqlSessionFactoryBuilder对象
   2. builder(inputStream)->xmlConfigBuilder
   3. 创建解析器parser
   4. 解析每一个标签把详细信息保存在configuration中
   5. 解析mapper.xml中的每一个元素系信息并保存在全局配置中，封装成一个mappedStatement,一个mappedStatement代表一个增删改查标签的详细信息
   6. 把所有的配置文件信息解析保存在Configuration对象中，返回DefaultSqlSession对象。
2. 获取SqlSession对象
   1. 调用openSession
   2. openSessionFromDataSource()
   3. 获取一些信息，创建tx
   4. new Executor(),创建一个执行器
   5. 根据Executor在全局配置中的类型，创建出SimpleExecutor（简单处理器）/ReuseExecutor/BatchExecutor(批处理)
   6. 如果有二级缓存开启，创建CacheExecutor(返回executor接口)
   7. executor=(Executor)interceptorChain.pluginAll(exector);使用每一个拦截器重新包装executor并返回
   8. 创建DefaultSqlSession,包含Configuration和Executor
   9. 返回DefaultSqlSession对象
   10. 返回sqlSession的实现类DefaultSqlSession对象，它里面包含了Configuration和Executor
3. 获取接口实现类对象(MapperProxy)
   1. getMapper(type)->Configuration->MapperRegistry->MapperProxFactory
   2. 根据接口类型获取MapperProxyFactory
   3. newInstance(sqlSession)
   4. 创建MapperProxy，它是一个InvocationHandler
   5. 创建MapperProxy的代理对象,并返回
   6. getMapper返回接口的代理对象，包含了sqlSession对象
4. 执行增删改查
   1. MapperProxy.invoke()->MapperMethod
   2. 判断crud类型,并包装参数为一个map或者直接返回
   3. sqlSession.selectOne()-》defaultsqlsession
   4. selectList(),获取mappedStatement
   5. 返回list的第一个



## 2、运行流程总结

1. 根据全局配置文件（全局，sql映射）初始化出configuration对象。
2. 创建一个DefaultSqlSession对象，它里面包含了configuration以及Executor(根据全局配置文件的defaultExecutorType创建出对应的Executor)
3. DefaultSqlSession.getMapper();拿到mapper接口对应的MapperProxy;
4. MapperProxy里面有（DefaultSqlSession）
5. 执行增删改查方法:
   1. 调用DefaultSqlSession的增删改查（Executor）
   2. 创建一个StatementHandler对象.(同时也会创建出ParementerHandler（处理参数封装）和ResultSetHandler(处理封装结果))
   3. 调用StatementHandler的预编译参数以及设置参数值；
      - 使用ParementerHandler来给sql设置参数
   4. 调用StatementHandler的增删改查方法；
   5. ResultSetHandler封装结果

注意：四大对象每个创建的时候都有一个interceptorChain.pluginAll(ParementerHandler);

- executor执行器
- ParameterHandler参数预编译处理器
- ResultSetHandler结果封装处理器
- StatementHandler增删改查执行器



# 十、MyBatis插件

## 1、插件原理

```tex
* 插件机制：
* 在四大对象创建的时候：
* 1、每个创建出来的对象不是直接返回的，而是 interceptorChain.pluginAll(ParementerHandler);
* 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）
*    调用interceptor.plugin(target);返回target包装后的对象。
*    
* 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP(面向切面)
*    我们的插件可以为四大对象创建代理对象；
*    代理对象就可以拦截四大对象的每一个执行；
```

## 2、开发插件

### 1、插件原理解析

开发步骤

```properties
* 1、编写一个Interceptor的实现类:MyFirstPlugin
* 2、使用@Intercept注解完成插件签名：使用注解配置
* 3、将写好的插件注册到全局配置文件中：添加配置文件
测试
```

MyFirstPlugin.java实现mybatis的插件接口

```java
package com.ln.mybaits.dao;

import java.beans.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 完成插件签名：
 * type:四大对象哪个对象
 * method:对象的哪个方法
 * args:参数；方法重载
 * @author LiNian
 *
 */

@Intercepts(value = { @Signature(args = java.sql.Statement.class, method = "parameterize", type = StatementHandler.class) })
public class MyFirstPlugin implements Interceptor {

	/**
	 * intercept：拦截 拦截目标对象的目标方法的执行；
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 执行目标方法
		Object proceed = invocation.proceed();
		// 返回执行后的返回值
		return proceed;
	}

	/**
	 * plugin: 包装目标对象；包装：为目标对象创建代理对象
	 */
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		// 我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们的目标对象

		Object warp = Plugin.wrap(target, this);
		// 返回当前的target创建的动态代理
		return warp;
	}

	/**
	 * setProperties: 将插件注册时的property属性设置进来
	 */
	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		System.out.println("插件配置的信息" + properties);
	}

}

```

添加配置:在全局文件中注册编写好的插件；可以自定义一些属性及数据

多个插件开发：**==创建动态代理的时候，按照配置的顺序创建层次代理对象。执行的时候，按照逆向顺序执行。==**

```xml
<!--plugins：注册插件 -->
<plugins>
    <plugin interceptor="com.ln.mybaits.dao.MyFirstPlugin">
        <property name="username" value="root" />
        <property name="password" value="123456" />
    </plugin>
</plugins>
```



测试代码及结果

```java
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
```



```tex
插件配置的信息{password=123456, username=root}
MyFirstPlugin....plugin:mybatis将要包装的对象org.apache.ibatis.executor.CachingExecutor@2f686d1f
MyFirstPlugin....plugin:mybatis将要包装的对象org.apache.ibatis.scripting.defaults.DefaultParameterHandler@3327bd23
MyFirstPlugin....plugin:mybatis将要包装的对象org.apache.ibatis.executor.resultset.DefaultResultSetHandler@5ec0a365
MyFirstPlugin....plugin:mybatis将要包装的对象org.apache.ibatis.executor.statement.RoutingStatementHandler@5383967b
2019-04-11 09:34:15:259 org.apache.ibatis.logging.jdbc.BaseJdbcLogger.debug(BaseJdbcLogger.java:145) ==>  Preparing: select id,last_name lastName,gender,email from tbl_employee where id = ? 
MyFirstPlugin....intercept:public abstract void org.apache.ibatis.executor.statement.StatementHandler.parameterize(java.sql.Statement) throws java.sql.SQLException
2019-04-11 09:34:15:301 org.apache.ibatis.logging.jdbc.BaseJdbcLogger.debug(BaseJdbcLogger.java:145) ==> Parameters: 1(Integer)
2019-04-11 09:34:15:369 org.apache.ibatis.logging.jdbc.BaseJdbcLogger.debug(BaseJdbcLogger.java:145) <==      Total: 1
org.apache.ibatis.binding.MapperProxy@26a7b76d
Employee [id=1, lastName=admin, email=jerry@ln.com, gender=1]

```

### 2、开发插件

1. 开发简单插件,通过拦截器修改获取的参数，然后继续执行语句

   ```java
   package com.ln.mybaits.dao;
   
   import java.beans.Statement;
   import java.util.Properties;
   
   import javax.crypto.spec.IvParameterSpec;
   
   import org.apache.ibatis.executor.statement.StatementHandler;
   import org.apache.ibatis.plugin.Interceptor;
   import org.apache.ibatis.plugin.Intercepts;
   import org.apache.ibatis.plugin.Invocation;
   import org.apache.ibatis.plugin.Plugin;
   import org.apache.ibatis.plugin.Signature;
   import org.apache.ibatis.reflection.MetaObject;
   import org.apache.ibatis.reflection.SystemMetaObject;
   
   /**
    * 完成插件签名：
    * type:四大对象哪个对象
    * method:对象的哪个方法
    * args:参数；方法重载
    * @author LiNian
    *
    */
   
   @Intercepts(value = { @Signature(args = java.sql.Statement.class, method = "parameterize", type = StatementHandler.class) })
   public class MyFirstPlugin implements Interceptor {
   
   	/**
   	 * intercept：拦截 拦截目标对象的目标方法的执行；
   	 */
   	@Override
   	public Object intercept(Invocation invocation) throws Throwable {
   		System.out.println("MyFirstPlugin....intercept:"+invocation.getMethod());
   		// 动态的改变一个下午sql的运行参数；以前查1号员工，现在改成查3号员工
   		Object target=invocation.getTarget();
   		System.out.println("当前拦截到的对象："+invocation.getTarget());
   		//拿到：StatementHandler==>parameterHandler===>parameterObject
   		//拿到target的元数据		
   		MetaObject metaObject=SystemMetaObject.forObject(target);
   		Object value=metaObject.getValue("parameterHandler.parameterObject");
   		// 修改完sql完语句要用的参数
   		metaObject.setValue("parameterHandler.parameterObject", 3);
   		System.out.println("sql语句用的参数是："+value);
   		// 执行目标方法
   		Object proceed = invocation.proceed();
   		// 返回执行后的返回值
   		return proceed;
   	}
   
   	/**
   	 * plugin: 包装目标对象；包装：为目标对象创建代理对象
   	 */
   	@Override
   	public Object plugin(Object target) {
   		// TODO Auto-generated method stub
   		// 我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们的目标对象
   		System.out.println("MyFirstPlugin....plugin:mybatis将要包装的对象"+target);
   		Object warp = Plugin.wrap(target, this);
   		// 返回当前的target创建的动态代理
   		return warp;
   	}
   
   	/**
   	 * setProperties: 将插件注册时的property属性设置进来
   	 */
   	@Override
   	public void setProperties(Properties properties) {
   		// TODO Auto-generated method stub
   		System.out.println("插件配置的信息" + properties);
   	}
   
   }
   
   ```



# 十一、MyBatis实用场景

## 1、PageHelper插件进行分页

mybatis分页教程

https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md

1. 下载分页所需要的源码包：jsqlparser-0.9.5.jar、pagehelper-5.0.0、jar

2. 在全局配置文件中添加拦截器

   ```xml
   <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
   ```

3. 使用分页插件

   ```java
   @Test
   	public void test() throws IOException {
   		SqlSession openSession = getSqlSessionFactory().openSession();
   		try {
   			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
   			// 1、只需要在查询以前调用一下分页插件
   			Page<Object> page = PageHelper.startPage(3, 2);
   			List<Employee> list = mapper.getEmps();
   			// 2、封装分页信息，连续显示几页
   			PageInfo<Employee> info = new PageInfo<>(list,3);
   			for (Employee employee : list) {
   				System.out.println(employee);
   			}
   			// System.out.println("当前页码"+page.getPageNum());
   			// System.out.println("总记录数"+page.getTotal());
   			// System.out.println("每页的录数"+page.getPageSize());
   			// System.out.println("总页码"+page.getPages());
   
   			System.out.println("当前页码:" + info.getPageNum());
   			System.out.println("总记录数:" + info.getTotal());
   			System.out.println("每页的录数:" + info.getPageSize());
   			System.out.println("总页码:" + info.getPages());
   			System.out.println("是否第一页:" + info.isIsFirstPage());
   
   			int[] nums=info.getNavigatepageNums();
   			for (int i = 0; i < nums.length; i++) {
   				System.out.println(nums[i]);
   			}
   			
   		} catch (Exception e) {
   			// TODO: handle exception
   			e.printStackTrace();
   		} finally {
   			openSession.close();
   		}
   
   	}
   ```

## 2、批量操作

1. mybatis执行代码**==,只需要在获取sqlSession的时候设置成获取批量的类型==**

   ```java
   /**
   	 * 批量处理
   	 * 
   	 * @throws IOException
   	 */
   @Test
   public void testBatch() throws IOException {
       SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
       // 通过sqlSessionFactory.openSession(ExecutorType.BATCH);获取一个可以批量操作的sqlSession
       SqlSession openSession = sqlSessionFactory.openSession();
       long start = System.currentTimeMillis();
       try {
           EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
   
           for (int i = 0; i < 10000; i++) {
               mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0, 5), "b", "c"));
           }
   
           openSession.commit();
           long end = System.currentTimeMillis();
           // 批量：(预编译sql一次==》设置参数==》10000次==》执行一次)Parameters: 3f18a(String), b(String), c(String)==>执行时长：8116
           // 非批量：(（预编译sql==》设置参数==》执行）==》10000)执行时长(毫秒)：20678;
           System.out.println("执行时长(毫秒)：" + (end - start));
       } catch (Exception e) {
           // TODO: handle exception
           e.printStackTrace();
       } finally {
           openSession.close();
       }
   }
   ```

2. spring整合mybatis的时候,配置批量sqlSession

   ```xml
   <!-- 配置一个可以进行批量执行的sqlSession -->
   <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
       <!--引入spring配置的数据源  -->
       <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
       <!-- 设置获取sqlSession的后的执行器类型 -->
       <constructor-arg name="executorType" value="BATCH"></constructor-arg>
   </bean>
   ```

   使用方式

   ```java
   @Service
   public class EmployeeService {
   
       @Autowired
       private EmployeeMapper employeeMapper;
   
       @Autowired
       private SqlSession sqlSession;
   
       public List<Employee> getEmps(){
           // 通过自动注入sqlSession的方式，调用spring配置中的sqlSession对象
           //EmployeeMapper mapper=sqlSession.getMapper(EmployeeMapper.class);
           return employeeMapper.getEmps();
       }
   
   }
   ```

   

## 3、存储过程

1. 笔记

   实际开发中，我们也会写一些存储过程，mybatis也支持存储过程。

   - 一个做简单的存储过程

     ```properties
     delimiter $$
     create procedure test()
     begin
        select "hello";
     end $$
     delimiter;
     ```

   - 存储过程的调用

     > 1. select标签中statementType="CALLABLE"
     >
     > 2. 标签中调用语法：
     >
     >    {call procedure_name(#{param1_info},#{param2_info})}

2. oracle分页存储过程

   1.创建oracle存储过程

   ```properties
   create or replace procedure
          hello_test(
             p_start in int,p_end in int,p_cout out int,p_emps out sys_refcursor
          )as
   begin
          select count(*) into p_count from employees;
          open p_emps for
               select * from (selct rownum rn,e.* from employees where rownum<=p_end) where rownum>=p_start;
   end hello_start;            
   ```

   配置xml

   ```xml
   <!-- public void getPageByProcedure();
    使用 select标签定义调用存储过程。
    statementType="CALLABLE"：表示要调用存储过程
    databaseId="oracle"全局配置中的oracle别名
    -->
   <select id="getPageByProcedure" statementType="CALLABLE" databaseId="oracle">
       {call hello_test(
       #{start,mode=IN,jdbcType=INTEGER},
       #{end,mode=IN,jdbcType=INTEGER},
       #{count,mode=OUT,jdbcType=INTEGER},
       #{emps,mode=OUT,jdbcType=CURSOR,javaType=ResultSet,ResultMap=PageEmp}
       )}
   </select>
   <resultMap type="com.ln.mybaits.bean.Employee" id="PageEmp">
       <id column="EMPLOYEE_ID" property="id"/>
       <result column="LAST_NAME" property="lastName"/>	
       <result column="EMAIL" property="email"/>	
   </resultMap>
   ```

   ```java
   /**
   	 * oracle分页 ： 借助rownum:行号；子查询； 存储过程保证分页逻辑
   	 * 
   	 * @throws IOException
   	 */
   	@Test
   	public void testProcedure() throws IOException {
   		SqlSession openSession = getSqlSessionFactory().openSession();
   		try {
   			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
   			OraclePage page = new OraclePage();
   			page.setStart(1);
   			page.setEnd(5);
   			mapper.getPageByProcedure(page);
   
   			
   			System.out.println("总记录数：" + page.getCount());
   			System.out.println("总记录数：" + page.getEmps().size());
   			System.out.println("查出的数据：" + page.getEmps());
   
   		} catch (Exception e) {
   			// TODO: handle exception
   			e.printStackTrace();
   		} finally {
   			openSession.close();
   		}
   
   	}
   ```

   





# 十二、自定义TypeHandler处理枚举

TypeHandler：在mybatis中，进行数据库类型与javaBean类型的映射

## 1、使用枚举

1. 默认的是使用名字保存枚举类型到数据库，如果需要保存枚举的索引在数据库，需要修改配置

   - 创建枚举类型

     ```java
     package com.ln.mybaits.bean;
     
     /**
      * 员工状态的枚举类型
      * @author LiNian
      *
      */
     public enum EmpStatus {
     
     	LOGIN,LOGOUT,REMOVE
     }
     
     ```

   - 修改全局配置文件

     ```xml
     <!-- 声明枚举类型处理器:使用默认的枚举类型处理器 -->
     <typeHandlers>
     <!-- EnumOrdinalTypeHandler是索引枚举处理器，保存在数据库的是枚举的索引 -->
     <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"
     javaType="com.ln.mybaits.bean.EmpStatus"/>
     </typeHandlers>
     ```

     

   - java代码

     ```java
     /**
     	 * 默认mybatis在处理枚举对象的时候保存枚举的是枚举的名字：EnumTypeHandler
     	 * 改变使用：EnumOrdinalTypeHandler
     	 */
     @Test
     public void testEnumUse(){
         EmpStatus login=EmpStatus.LOGIN;
         System.out.println("枚举索引："+login.ordinal());
         System.out.println("枚举名字："+login.name());
     }
     
     /**
     	 * 枚举
     	 * @throws IOException
     	 */
     @Test
     public void testEnum() throws IOException{
         SqlSession openSession = getSqlSessionFactory().openSession();
         try {
             EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
             Employee employee=new Employee("test_enum", "enum@ln.com", "1");
             mapper.addEmp(employee);
             System.out.println("保存成功:"+employee.getId());
             openSession.commit();
         } catch (Exception e) {
             // TODO: handle exception
             e.printStackTrace();
         } finally {
             openSession.close();
         }
     }
     ```

     

     

## 2、自定义枚举类型处理器

操作步骤：

1. 创建枚举类型

   ```java
   package com.ln.mybaits.bean;
   
   /**
    * 员工状态的枚举类型
    * 希望数库保存的是100,200这些状态码，而不是0,1枚举索引
    * @author LiNian
    *
    */
   public enum EmpStatus {
   
   	LOGIN(100,"用户登录"),LOGOUT(200,"用户登出"),REMOVE(300,"用户不存在");
   	
   	private Integer code;
   	private String msg;
   	private EmpStatus(Integer code,String msg){
   		this.code=code;
   		this.msg=msg;
   	}
   	public Integer getCode() {
   		return code;
   	}
   	public void setCode(Integer code) {
   		this.code = code;
   	}
   	public String getMsg() {
   		return msg;
   	}
   	public void setMsg(String msg) {
   		this.msg = msg;
   	}
   	
   	// 按照状态码返回枚举的状态码
   	public static EmpStatus getEmpStatusByCode(Integer code){
   		switch (code) {
   		case 100:
   			return LOGIN;
   		case 200:
   			return LOGOUT;
   		case 300:
   			return REMOVE;
   		default:
   			return LOGIN;
   		}
   	}
   }
   
   ```

   

2. 创建一个类MyEnumEmpStatusTypeHandler，实现TypeHandler接口

   ```java
   package com.ln.mybaits.typeHandler;
   
   import java.sql.CallableStatement;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   
   import org.apache.ibatis.type.JdbcType;
   import org.apache.ibatis.type.TypeHandler;
   
   import com.ln.mybaits.bean.EmpStatus;
   
   /**自定义类型处理器
    * 1、实现TypeHandler接口。或者继承BaseTypeHandler
    * @author LiNian
    *
    */
   public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus>{
   
   	/**
   	 * 定义当前数据如何保存到数据库中
   	 */
   	@Override
   	public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
   		// TODO Auto-generated method stub
   		System.out.println("要保存的状态码："+parameter.getCode());
   		ps.setString(i, parameter.getCode().toString());
   	}
   	
   	@Override
   	public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
   		// TODO Auto-generated method stub
   		// 需要根据从数据库中拿到的枚举的状态码
   		int code=rs.getInt(columnName);
   		System.out.println("从数据库中获取的状态码："+code);
   		EmpStatus status=EmpStatus.getEmpStatusByCode(code);
   		return status;
   	}
   
   	@Override
   	public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
   		// TODO Auto-generated method stub
   		int code=rs.getInt(columnIndex);
   		System.out.println("从数据库中获取的状态码："+code);
   		EmpStatus status=EmpStatus.getEmpStatusByCode(code);
   		return status;
   	}
   
   	@Override
   	public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
   		// TODO Auto-generated method stub
   		int code=cs.getInt(columnIndex);
   		System.out.println("从数据库中获取的状态码："+code);
   		EmpStatus status=EmpStatus.getEmpStatusByCode(code);
   		return status;
   	}
   
   
   
   }
   
   ```

3. 在全局配置中使用自定义枚举类型处理器

   ```xml
   <!-- 声明枚举类型处理器 -->
   <typeHandlers>
       <!-- EnumOrdinalTypeHandler是索引枚举处理器，保存在数据库的是枚举的索引 -->
       <!-- 1、使用默认的的TypeHandler -->
       <!-- 		<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"
      javaType="com.ln.mybaits.bean.EmpStatus" /> -->
       <!--2、配置我们自定义的TypeHandler处理器 -->
       <typeHandler handler="com.ln.mybaits.typeHandler.MyEnumEmpStatusTypeHandler" javaType="com.ln.mybaits.bean.EmpStatus"/>
   </typeHandlers>
   ```

4. 第二种使用方式

   ```xml
   <!-- 声明枚举类型处理器 -->
   <typeHandlers>
       <!-- EnumOrdinalTypeHandler是索引枚举处理器，保存在数据库的是枚举的索引 -->
       <!-- 1、使用默认的的TypeHandler -->
       <!-- 		<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"
      javaType="com.ln.mybaits.bean.EmpStatus" /> -->
       <!--2、配置我们自定义的TypeHandler处理器 -->
       <typeHandler handler="com.ln.mybaits.typeHandler.MyEnumEmpStatusTypeHandler" javaType="com.ln.mybaits.bean.EmpStatus"/>
       <!--3、也可以在处理某个字段的时候告诉mybatis用什么类型处理器
              保存： 
             <insert id="addEmp" parameterType="com.ln.mybaits.bean.Employee"
               useGeneratedKeys="true" keyProperty="id">
                    insert into tbl_employee (last_name,email,gender,empStatus)
               values(#{lastName},#{email},#{gender},#{empStatus,type=com.ln.mybaits.typeHandler.MyEnumEmpStatusTypeHandler})
                  </insert>
             查询：1、配置用的resultMap封装类型
              2、使用resultMap
               <resultMap type="com.ln.mybaits.bean.Employee" id="MyEmp">
                       <id column="id" property="id"/>
                       <result column="empStatus" property="empStatus" typeHandler="com.ln.mybaits.typeHandler.MyEnumEmpStatusTypeHandler"/>
                     </resultMap>  
          注意：如果在参数位置修改TypeHandler,应该保证保存数据和查询数据用的TypeHandler是一样的
      -->
   </typeHandlers>
   ```

   























