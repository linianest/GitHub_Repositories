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

