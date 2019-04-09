# 一、Spring Boot 入门

## 1、Spring Boot 简介

> 简化Spring应用开发的一个框架；
>
> 整个Spring技术栈的一个大整合；
>
> J2EE开发的一站式解决方案；

## 2、微服务

微服务：架构风格

一个应用应该是一组小型服务；可以通过HTTP的方式进行互通；



每一个功能都是可以单独升级的服务单元；

## 3、环境配置

jdk-1.8+； java version "1.8.0_201"

IDEL编辑器

maven;    apapche maven 3.6.0

## 4、Maven配置

在maven的安装目录下/conf/settings.xml 添加如下内容

- 在<mirrors></mirrors>之间添加如下内容

- 作用：访问国内maven仓库镜像（中央maven仓库不好访问）

  ```xml
  <mirror>  
      <id>alimaven</id>
      <mirrorOf>central</mirrorOf>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
  </mirror>
  ```

- 在<profiles></profiles>之间添加如下内容

- 作用：指定项目编译运行使用的jdk版本

  

```xml
<profile>    
    <id>jdk-1.8</id>    
    <activation>    
        <activeByDefault>true</activeByDefault>    
        <jdk>1.8</jdk>    
    </activation>    
    <properties>    
        <maven.compiler.source>1.8</maven.compiler.source>    
        <maven.compiler.target>1.8</maven.compiler.target>    
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>   
    </properties>    
    <!--如果实在学校或者公司有maven私服，可以在这里配置私服-->
      <repositories>
        <repository>
          <id>accp</id>
          <name>accp</name>
          <url>http://192.168.1.44:8081/repository/accp/</url>
          <snapshots>
              <enabled>true</enabled>
           </snapshots>
            <releases>
               <enabled>true</enabled>
            </releases>
        </repository>
      </repositories>
     <!--如果没有私服，这里不用配置-->
</profile>
```

- 本地仓库地址的配置

```xml
  <localRepository>D:\apache-maven-3.6.0\LocalWarehouse</localRepository>
```



## 5、IDEA配置

在IDEA内置setting中设置maven的配置目录

>- ctrl+shift+/  快速注解
>
>

## 6、修改Banner

```properties
${AnsiColor.BLUE}
.----------------.  .-----------------.
| .--------------. || .--------------. |
| |   _____      | || | ____  _____  | |
| |  |_   _|     | || ||_   \|_   _| | |
| |    | |       | || |  |   \ | |   | |
| |    | |   _   | || |  | |\ \| |   | |
| |   _| |__/ |  | || | _| |_\   |_  | |
| |  |________|  | || ||_____|\____| | |
| |              | || |              | |
| '--------------' || '--------------' |
 '----------------'  '----------------' 
 
 ----------版本号------------${spring-boot.version}
```

> 网址http://patorjik.com/software/taag
>
> ${AnsiColor.BLUE}    表示Banner文字的颜色
>
> ${spring-boot.version}  当前使用的SpringBoot版本





# 二、初体验Spring Boot HelloWorld

## 1、使用IDEA创建一个项目

>- 目录结构说明
>- POM文件说明



## 2、添加Banner文件

>1. 在项目resources目录下创建文件：banner.txt
>
>2. 在文件中添加想打印的benner图案即可
>
>
>
>

## 3、创建Controller类

>要求：浏览器发送hello请求，服务器响应请求并处理，响应helloworld字符串；
>
>

```java

@Controller
public class HelloController {


    @ResponseBody  //将返回内容添加到响应体中，不用解析模版页面
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){

        return "hello SpringBoot";
    }
}

```

还有一种响应方式：

```java
@ResController
public class HelloController {


    @ResponseBody  //将返回内容添加到响应体中，不用解析模版页面
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){

        return "hello SpringBoot";
    }
}

```

注解说明：

>@ResController=@Controller+ @ResponseBody 
>
>

## 4、启动访问

>- 执行main方法
>- 浏览器访问：http://localhost:8080/hello



主程序启动：

```java

/**
 * springboot主程序类
 */
@SpringBootApplication
public class HelloWorldApplication {
 //主程序启动
    public static void main(String[] args) {

        SpringApplication.run(HelloWorldApplication.class,args);
    }
}
```



## 5、参数传递

参数传递可以说是服务端与外界沟通的主要方式，这节非常重要。

本节内容包括：

```properties
通过URL传参
     |----get方式url传参
         |---@PathVariable 即：url/id/1994 形式
         |---@requestParam 即：url?username=lisi形式
     |----POST方式传参
         |---@requestParam
         |---请求体中加入文本
配置文件传参         
```



### 5.1、get方式url传参

```java
    @GetMapping("/hello/${id}")
    public String hello(@PathVariable("id") String id) {

        return "id="+id;
    }
```

- rest访问方式：

  >http://localhost:8080/hello/1
  >
  >通过@PathVariable("id")获取到url中变量id的值，然后赋值(绑定)给形参变量String id
  >
  >结果：id=1

还有一种方式,请求参数key=value方式

```java
 @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",defaultValue="admin") String name) {

        return "name="+name;
    }
```

- http普通访问方式：

> http://localhost:8080/hello?name=张三
>
> 通过@PathParam("name")获取到url中变量name的值，然后赋值(绑定)给形参变量String name(如果请求url中name与形参name一致才行)
>
> 如果不传参数，默认值是admin
>
> ```java
> //表示该参数非必须
> @RequestParam(value = "name"，required = false)
> ```
>
> 结果：name=张三



### 5.2、post方式传参

```java
  @PostMapping("/hello")
    public String hello(String name,Integer age) {

        return "name="+name+" age="+age;
    }
```

- postman测试访问：结果是name=张三  age=12

  

### 5.3、post方式传递字符串文本

>通过HttpServletRequest获取输入流

```java
/**
     * POST传递字符流
     * @param request
     * @return
     */
    @PostMapping("/postString")
    public String postString(HttpServletRequest request) {
        ServletInputStream is = null;
        try {
            is = request.getInputStream();
            StringBuffer sb = new StringBuffer();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
```



### 5.4、@requestbody接受参数

>- @requestbody可以接收get或者post请求中的参数
>- 把json作为参数传递，要用【requestbody】
>- 附带说明使用postman方式设置content-type为application/json方式测试后台接口
>
>

```java
@PostMapping("/save")
@ResponseBody
public Map<String,Object> save(@RequestBody User user){
    Map<String,Object> map=new HashMap<String,Object>();
    map.put("user",user);
    return map;
}
```

## 6、Spring Boot 使用jar包运行

>在pom.xml文件中添加maven的插件依赖
>
>可以打包成jar包，然后使用 java -jar  xxxxx.jar运行项目

```xml
<!--添加maven插件依赖，可以打包成jar包-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

## 7、使用SwaggerAPI框架

>- Swagger可以自动生成api接口文档
>
>

### 7.1添加依赖

```xml
<!--add swagger starter-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
<!--add swagger end-->

<!--lombok可以自动生成getset方法-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.4</version>
    <scope>provided</scope>
</dependency>
```

### 7.2、Swagger配置

Swagger配置文件

```java
package com.ln.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  //必须存在
@EnableSwagger2 //必须存在
@EnableWebMvc   //必须存在
@Data          //lomlok插件，快速生成get和set方法
@ConfigurationProperties(prefix = "swagger")
//必须存在 扫描的api controller所在的包
@ComponentScan(basePackages = {"com.ln.controller"})
public class SwaggerConfig {

    private String title;
    private String description;
    private String version;
    private String name;
    private String url;
    private String email;

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact(name, url, email);
        return new ApiInfoBuilder().title(title).description(description).contact(contact).version(version).build();
    }
}

```



MVC配置

>作用：过滤网页静态资源

```java
package com.ln.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


}

```





## 8、配置文件

### 8.1、配置文件的使用



>**修改配置方式1: src\main\resources\application.properties**

```
#修改端口号
server.port=8081
#端口号后额外添加访问路径
server.context-path=/test
```

- 访问:http://localhost:8081/test/hello

```java
@RestController
public class HelloController {


    @GetMapping("/hello")
    public String hello() {
        return "hello SpringBoot";
    }
}
```

> **修改配置方式1: src\main\resources\application.yml**

```yaml
server:
  port: 8081
  servlet:
    context-path: /test
```

>个人比较喜欢yml



### 8.2、切换配置文件

#### 8.2.1、多配置文件

src/mian/resources/application-dev.yml

```yaml
# 开发环境
server:
  port: 8081
```

src/mian/resources/application-prod.yml

```yaml
# 生产环境
server:
  port: 8082
```

src/mian/resources/application.yml

- **根据active：来切换配置文件**

```yaml
# 激活生产环境配置
spring:
  profiles:
    active: prod
```



#### 8.2.2单个配置文件（分块）

>- 在单一文件中，可用连续三个横线（---）区分多个文件
>- 根据active:来切换配置文件

```yaml
# 开发环境
server:
  port: 8081
spring:
  profiles: dev
---
# 生产环境
server:
  port: 8080
spring:
  profiles: prod
---
# 配置环境
server:
  profiles:
    actives: prod
```



####8.2.3、激活指定profile

>1. 在配置文件中指定spring.profiles.active=dev
>
>2. 命令行：
>
>java  -jar   xxx.jar   ==--spring.profiles.active=dev==
>
>可以直接在测试的时候，配置传入命令行参数
>
>3. 虚拟机参数
>
>==-Dspring.profiles.active=dev==



### 8.3、配置文件加载位置顺序

>springboot 启动后扫描一下位置的applicaiton.properties或者application.yml文件，作为SpringBoot的默认配置文件

| 位置               | 说明                    |
| ------------------ | ----------------------- |
| -file:./config/    | 项目目录下的config      |
| -file:./           | 项目目录下              |
| -classpath:/config | resources目录下的config |
| -classpath:/       | resources目录下         |

优先级由高到低，高优先级的配置会覆盖低优先级的配置

SpringBoot会从这四个位置全部加载主配置文件；形成**互补配置**

我们还可以通过spring.config.location来改变默认的配置文件位置

**项目打包好后，我们可以通过命令参数的形式，启动项目的时候来指定配置文件新的位置；**

**指定配置文件和默认加载的这些配置文件共同形成互补配置；**

java  -jar   xxx.jar   ==--spring.config.location=E:/application.yml==



## 9、spring boot中yaml简介以及语法

1. Spring Boot使用一个全局的配置文件（配置文件名是固定的）
   a)	application.properties
   b)	application.yml
   配置文件放在src/main/resources目录或者类路径/config/下
2. .yml是YAML（YAML Ain’t Markup Language）语言的文件，以数据为中心，比json/xml等更适合做配置文件
   全局配置文件可以对一些默认配置值进行修改
3. .yml配置示例
   配置端口号：

```yaml
server:
  port: 8081
```

1. yaml基本语法：
   a)	k:(空格)v：表示一对键值对（空格必须有），以空格的缩进来控制层级关系；只要是左对齐的一列数据，则表示都是同一个层级的。例如如下代码:

```yaml
server:
  port: 8081

```

注意：属性值大小写敏感

1. 值的写法
   a)	字面量（K:空格v）：普通的值（数字，字符串，布尔）

   i.	字符串默认不用加上单引号或者双引号
   ii.	“”：双引号；不会转义字符串里面的特殊字符，特殊字符会作为本身想表示的意思，例如：
   name: “zhangsan \n lisi” 输出 zhangsan 换行 lisi
   iii.	‘’：单引号；会转移特殊字符，特殊字符最终只是一个普通的字符串数据
   name: ‘zhangsan \n lisi’ 输出 zhangsan \n lisi
   b)	对象/Map（属性和值）（键值对）（k: v）语法示例如下：

```yaml
friends:
  lastName: zhangsan
  age: 20

```

ii.	行内写法:

```
friends: {lastName: zhangsan,age: 18}
1
```

c)	数组（List，Set）（用-值表示数组中的一个元素）语法示例如下：

```
pets:
  -	cat
  -	dog
  -	pig

```

ii.	行内写法：

```
pets: [cat,dog,pig]

```

1. 配置文件值注入给JavaBean示例如下：
   注：我们可以利用Maven导入配置文件处理器，方便在配置文件中提示我们，要导入的Maven坐标如下:

```
<!-- 导入配置文件处理器,配置文件绑定后就会有提示 -->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-configuration-processor</artifactId>
   <optional>true</optional>
</dependency>

```

创建一个Person类，如下代码片段：（需要注意的是，需要自动注入到Spring容器中的类，要跟SpringBoot主配置类是同包下，或是主配置类所处包的子包下）

```
package test.spring.boot.demo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.*;
//将配置文件中的Person属性赋的值映射到该组件中
//@ConfigurationProperties：告诉SpringBoot将本类的所有属性和配置文件中相应的属性进行相互关联映射赋值
//该注解中的prefix属性告知绑定配置文件中的哪个属性
//当前的Person组件必须要加载到Spring容器中，才能使用@ConfigurationProperties提供的功能
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
	private Dog dog;
	//…省略getter/setter以及toString()方法
}
123456789101112131415161718192021
```

创建一个Dog类，示例如下：

```
package test.spring.boot.demo;

public class Dog {
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    //...省略getter/setter方法的生成
}

```

在src/main/resources/目录中建立application.yml，配置代码如下：

```
person:
  lastName: zhangsan
  age: 18
  boss: false
  birth: 2017/12/12
  maps: {k1: v1,k2: 12}
  lists:
    - lisi
    - zhaoliu
  dog:
  	name: luck
  	age: 3

```

最后进入在src/test/java/中的SpringBoot单元测试中，编写如下代码，进行测试，看在application.yml中配置的person属性是否绑定映射到Person对象中：

```
package test.spring.boot.demo;
import org.springframework.context.ApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试
 * 可以在测试期间很方便的类似编码一样进行自动注入到容器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	Person person;

	@Test
	public void contextLoads() {
		System.out.print(person.toString());
	}
}

```

最后控制太输出如下效果：

![](E:\文档\SpringBoot课件\Spring Boot 笔记\images\20180920162034110.png)



# 三、日志

## 1.如何在系统中使用SLF4J

以后在开发的时候，日志记录方法的调用，不应该来直接调用实现类，而应该调用日志抽象层里面的方法；

**==SpringBoot选用SLF4J和logback;==**

首先给系统里面导入slf4j的jar和logback的实现jar

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

图示：

![images/concrete-bindings.png](images/concrete-bindings.png)

每一个日志的实现框架都有自己的配置文件。使用slf4j以后，**配置文件还是做成日志实现框架的配置文件；**

如何让系统中所有的日志都统一到slf4j；

>1. 将系统中其他日志框架排除出去
>2. 用中间包来替代原有的日志框架
>3. 我们导入slf4j其他的实现



## 2、SpringBoot日志关系

springboot的日志依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```





#四、SpringBoot与web开发

## 1、使用springboot:



>1. **创建Springboot应用，选择我们需要的模块**
>2. **Springboot已经默认把所有的场景配置好了，只需要在配置文件中指定少量配置就可以运行起来**
>3. **自己编写业务代码**



**自动配置原理是什么？**能不能修改成需要的配置

```properties
xxxxAutoConfiguration:帮助我们给容器中自动配置组件
xxxxProperties:配置类来封装配置文件的内容
```



## 2、Springboot对静态资源的映射规则

```java
@ConfigurationProperties(
    prefix = "spring.resources",
    ignoreUnknownFields = false
)
public class ResourceProperties {
    //可以设置和静态资源有关的参数，缓存时间
```





```java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
    } else {
        Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
        CacheControl cacheControl =             this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        if (!registry.hasMappingForPattern("/webjars/**")) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }

        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }

    }
}
```

1. 所有/webjars/**，都去classpath:/META-INF/resources/webjars/找资源；

   webjars：以jar包的形式引入静态资源；

   http://www.webjars.org/

```xml
<!--引入jquery-->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.3.1</version>
</dependency>
```

访问方式：localhost:8080/webjars/jquery/3.3.1/jquery.js

2. “/**” :访问当前项目的任何资源，（静态资源的文件夹）

```properties
"classpath:/META-INF/resources/"
"classpath:/resources/"
"classpath:/static/"
"classpath:/public/"
"/",当前项目的根路径

```

localhost:8080/abc ===去静态资源文件夹里面找abc

```xml
<!--一旦使用自定义的静态文件路径，默认的静态路径不起作用了-->
spring.resources.static-location=classpath:/static/
```



3.欢迎页：静态资源文件夹下的所有的index.html，都会被“/**”映射

localhost:8080/   默认找index页面

4.所有的网页标签图标**/favicon.ico都是在静态资源文件夹下找；







## 3、SpringBoot模版引擎Thymeleaf

### 1、引入依赖

```xml
<!--启动thymeleaf模板引擎-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```



### 2、Thymeleaf使用规则

```java
private static final Charset DEFAULT_ENCODING;
public static final String DEFAULT_PREFIX = "classpath:/templates/";
public static final String DEFAULT_SUFFIX = ".html";
private boolean checkTemplate = true;
private boolean checkTemplateLocation = true;
private String prefix = "classpath:/templates/";
private String suffix = ".html";

```

**只要我们把html放在类路径下的templates文件夹下，Thymeleaf就会自动渲染**

使用：

1.导入thymeleaf的名称空间

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

2.使用的语法

```html
<!DOCTYPE html>

<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="https://getbootstrap.com/favicon.ico">

        <title>Signin Template for Bootstrap</title>

        <!-- Bootstrap core CSS -->
        <link href="asserts/css/bootstrap.css" th:href="@{/webjars/bootstrap/4.0.0/css/bootstrap.css}" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="asserts/css/signin.css" th:href="@{/asserts/css/signin.css}" rel="stylesheet">



    </head>

    <body class="text-center">
        <form class="form-signin" action="dashboard.html" th:action="@{/user/login}" method="post">
            <img class="mb-4" th:src="@{/asserts/img/bootstrap-solid.svg}" src="asserts/img/bootstrap-solid.svg" alt=""
                 width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
            <label for="inputEmail" class="sr-only" th:text="#{login.username}">Username</label>
            <input type="text" name="username" id="inputEmail" class="form-control" th:placeholder="#{login.username}" placeholder="Username"
                   required="" autofocus="">
            <label for="inputPassword" class="sr-only" th:text="#{login.password}">Password</label>
            <input type="password" name="password" id="inputPassword" class="form-control" th:placeholder="#{login.password}"
                   placeholder="Password" required="">
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" value="remember-me"> [[#{login.remember}]]
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
            <p class="mt-5 mb-3 text-muted">© 2017-2018</p>
            <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
            <a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">Englise</a>
        </form>
    </body>
</html>
```



## 4、SpringMVC自动配置

@EnableWebMvc  //表示全年接管的springboot的SpringMVC,自动配置的设置不起作用了

## 5、如何修改SpringBoot的默认配置

> 模式：

1. Springboot在自动配置很多组件的时候，先看容器中有没有用户自己配置的组件（@bean、@Compontent）,如果有就用用户自己配置的，如果没有就用默认自动配置的；如果组件可以有多个（ViewResolver）将用户配置的和自己默认的组合起来；

  2.在springboot中会有很多的xxxxconfig进行扩展配置

  3.在springboot中会有很多的xxxxCustomizer来进行扩展servlet配置

## 6、RestfulCRUD



### 1、访问首页

```java
package com.ln.springboot.config;

import com.ln.springboot.component.LoginHandlerInterceptor;
import com.ln.springboot.component.MyLocaleResolver;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 使用WebMvcConfigurerAdapter可以扩展springmvc的功能
 */

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);

    }

    //所有的WebMvcConfigurerAdapter组件都会一起起作用
    @Bean  //将组件注册在容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter=new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");

            }

  
        };
        return adapter;
    }

}

```



### 2、国际化

**以前的方式**

>1. 编写国际化配置文件
>2. 使用ResourceBundleMessageSource管理国际化资源文件
>3. 在页面使用fmt:message去过国际化内容

现在的方式

> 1. 编写国际化配置文件,放在resources文件夹下
> 2. 创建一个文件夹i18n存放国际化文件，创建两个文件login_zh_CN.properties（中文）和login_en_US.properties(英文)以及login.properties(默认语言)
>
> 

login.properties

```properties
login.btn=登录~
login.password=密码~
login.remember=记住我~
login.tip=请登录~
login.username=用户名~
```

login_en_US.properties

```properties
login.btn=Sing in
login.password=Password
login.remember=remember me
login.tip=Please Sign in
login.username=UserName
```

login_zh_CN.properties

```properties
login.btn=登录
login.password=密码
login.remember=记住我
login.tip=请登录
login.username=用户名
```

![](E:\文档\SpringBoot课件\Spring Boot 笔记\images\2019-04-03_192911.png)



2、springboot自动配置好了管理国际化文件的组件

```java
public class MessageSourceAutoConfiguration {
    private static final Resource[] NO_RESOURCES = new Resource[0];

    public MessageSourceAutoConfiguration() {
    }

    @Bean
    @ConfigurationProperties(
        prefix = "spring.messages"
    )
    @Bean
    public MessageSource messageSource(MessageSourceProperties properties) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
               //设置国际化资源文件的基础名（去掉语言国家代码的） 
        if (StringUtils.hasText(properties.getBasename())) {
                              messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
        }

        if (properties.getEncoding() != null) {
            messageSource.setDefaultEncoding(properties.getEncoding().name());
        }

        messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
        Duration cacheDuration = properties.getCacheDuration();
        if (cacheDuration != null) {
            messageSource.setCacheMillis(cacheDuration.toMillis());
        }

        messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
        return messageSource;
    }
```



**设置资源文件的路径位置**

```yaml
#禁用模板引擎缓存，开发期间，通过ctrl+F9重新编译
spring.thymeleaf.cache=false

#设置国际化包名
spring.messages.basename=i18n.login
```



3、去页面获取国际化的值:#{login.username}

```html

<body class="text-center">
<form class="form-signin" action="dashboard.html" th:action="@{/user/login}" method="post">
    <img class="mb-4" th:src="@{/asserts/img/bootstrap-solid.svg}" src="asserts/img/bootstrap-solid.svg" alt=""
         width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
    <label for="inputEmail" class="sr-only" th:text="#{login.username}">Username</label>
    <input type="text" name="username" id="inputEmail" class="form-control" th:placeholder="#{login.username}" placeholder="Username"
           required="" autofocus="">
    <label for="inputPassword" class="sr-only" th:text="#{login.password}">Password</label>
    <input type="password" name="password" id="inputPassword" class="form-control" th:placeholder="#{login.password}"
           placeholder="Password" required="">
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> [[#{login.remember}]]
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
    <p class="mt-5 mb-3 text-muted">© 2017-2018</p>
    <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
    <a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">Englise</a>
</form>
```

4、页面效果

通过页面选择切换的话，需要创造自己的国际化解析器，通过请求头信息来切换国家化语言

```java
package com.ln.springboot.component;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化:可以在请求头上获取区域信息
 */

public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l =  request.getParameter("l");

        Locale locale = Locale.getDefault();
        //如果不为空的话
        if (!StringUtils.isEmpty(l)){
            String split[]= l.split("_");
            locale = new Locale(split[0],split[1]);
        }
        return locale;
    }
    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}

```

并添加到视图解析器中MyMvcConfig中

```java

    /**
     *  添加国际化组件
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
```



### 3、登录

```java
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(username)&&"123456".equals(password)){
            //登录成功,防止表单重复提交，使用重定向
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";

        }else{
            map.put("msg","用户名密码错误");
            return "login";
        }

    }

}
```



### 4、添加拦截器，配置国际化

1. 添加拦截器

   ```java
   package com.ln.springboot.component;
   
   import org.springframework.web.servlet.HandlerInterceptor;
   import org.springframework.web.servlet.ModelAndView;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   /**
    * 登录检查
    */
   
   public class LoginHandlerInterceptor implements HandlerInterceptor {
   
       //目标方法执行之前
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           Object loginUser = request.getSession().getAttribute("loginUser");
           if (loginUser==null){
               //未登录，返回登录页面
               request.setAttribute("msg","没有权限请先登录");
               request.getRequestDispatcher("/index.html").forward(request,response);
   
               return false;
           }else{
               return true;
           }
   
       }
   
       @Override
       public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
   
       }
   
       @Override
       public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
   
       }
   }
   
   ```

   

   

```java
package com.ln.springboot.config;

import com.ln.springboot.component.LoginHandlerInterceptor;
import com.ln.springboot.component.MyLocaleResolver;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 使用WebMvcConfigurerAdapter可以扩展springmvc的功能
 */

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {


    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
        return new EmbeddedServletContainerCustomizer(){

            //定制嵌入式的servlet容器相关规则
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container){
                container.setPort(8083);
            }
        };
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);

    }

    //所有的WebMvcConfigurerAdapter组件都会一起起作用
    @Bean  //将组件注册在容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter=new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");

            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
              //  super.addInterceptors(registry);
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html", "/", "/user/login");
            }
        };
        return adapter;
    }

    /**
     *  添加国际化组件
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}

```





### 5、CRUD-列表

实验要求

1.RestfuleCRUD：CRUD满足Rest风格

2.实验的请求架构；

| 实验功能                             | 请求URL | 请求方式 |
| ------------------------------------ | ------- | -------- |
| 查询所有员工                         | emps    | GET      |
| 查询某个员工（来到修改页面）         | emp/1   | GET      |
| 来到添加页面                         | emp     | GET      |
| 添加员工                             | emp     | POST     |
| 来到修改页面（查出员工进行信息回显） | emp/1   | GET      |
| 修改员工                             | emp     | PUT      |
| 删除员工                             | emp/1   | DELETE   |

3、员工列表:

**thymeleaf公共页面元素抽取**

```html
1、抽取公共片段
<div th:fragment="copy">
    sdsdfsdfdsfsd
</div>


2、引入公共片段
<div th:insert="~{footer::copy}"></div>
~{templatename::selector}  模版名::选择器
~{templatename::fragmentname} 模版名::片段名

3、默认效果
insert的功能是添加到指定div标签中
如果使用th:insert等属性进行引入，可以不用写~{};
行内写法可以加上[[~{}]];[(~{})];
```



三种引入功能片段的th属性：

**th:insert**：将公共片段整个插入到声明引入的元素中

**th:replace**：将声明的元素替换为公共片段

**th:include：**将被引入的片段的内容包含进这个片段中

```html
<div th:fragment="copy">
    sdsdfsdfdsfsd
</div>

引入方式
<div th:insert="~{footer::copy}"></div>
<div th:replace="~{footer::copy}"></div>
<div th:include="~{footer::copy}"></div>


效果
<div>
    <footer>
        sdsdfsdfdsfsd
    </footer>
</div>


<footer>
    sdsdfsdfdsfsd
</footer>


<div>
sdsdfsdfdsfsd
</div>
```

可以自由选择配置



## 7、错误处理机制





## 8、配置嵌入式Servlet容器

springboot默认的是嵌入式servlet容器（tomcat）

问题？

### 1、如何定制和修改默认servlet容器的配置



- 修改默认的servlet配置

  ```properties
  #设置端口号
  #server.port=8001
  
  #设置全局访问路径
  server.servlet.context-path=/crud
  
  #禁用模板引擎
  spring.thymeleaf.cache=false
  
  #设置国际化包名
  spring.messages.basename=i18n.login
  
  #格式化日期字符
  spring.mvc.date-format=yyyy-MM-dd
  ```

- 编写一个**EmbeddedServletContainerCustomizer**类，作为定制的servlet容器，来修改默认servlet的配置

  ```java
  @Bean
  public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
      return new EmbeddedServletContainerCustomizer(){
  
          //定制嵌入式的servlet容器相关规则
          @Override
          public void customize(ConfigurableEmbeddedServletContainer container){
              container.setPort(8083);
          }
      };
  }
  
  ```



### 2、注册Servlet三大组件【Servlet、Filter、Listener】

由于springboot默认使用jar包的方式启动嵌入式的servlet容器来启动springboo的web应用，没有web.xml文件；

**注册三大组件用以下方式：**
ServletRegistrationBean

```java
//注册三大组件
@Bean
public ServletRegistrationBean myServlet(){
    //将自己的servlet注册到servlet容器中
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(),"/myServlet");
    return registrationBean;
}
```

FilterRegistrationBean

```java
  //注册过滤器
    @Bean
    public FilterRegistrationBean myFilter(){
        //将自己的servlet注册到servlet容器中
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
        return registrationBean;
    }
```



ServletListenerRegistrationBean

```java
//注册监听器
@Bean
public ServletListenerRegistrationBean myListener(){
    //将自己的servlet注册到servlet容器中
    ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
    registrationBean.setListener(new MyListener());

    return registrationBean;
}
```

springboot帮我们自动springmvc的时候，自动的注册springmvc的前端控制器：DispatcherServlet

```java
@Bean(
    name = {"dispatcherServletRegistration"}
)
@ConditionalOnBean(
    value = {DispatcherServlet.class},
    name = {"dispatcherServlet"}
)
public DispatcherServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet) {
    //默认拦截所有请求“/”包括静态资源，但是不拦截jsp请求 /*会拦截jsp
    // 可以通过server.serverPath()来修改springmvc默认的前端控制器的访问路径
    DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(dispatcherServlet, this.webMvcProperties.getServlet().getPath());
    registration.setName("dispatcherServlet");
    registration.setLoadOnStartup(this.webMvcProperties.getServlet().getLoadOnStartup());
    if (this.multipartConfig != null) {
        registration.setMultipartConfig(this.multipartConfig);
    }

    return registration;
}
```



1. springboot能不能支持其他的servlet容器？



# 五、Docker

## 1、简介

**Docker是一个开源的应用容器引擎；**

>Docker 是一个[开源](https://baike.baidu.com/item/%E5%BC%80%E6%BA%90/246339)的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的容器中，然后发布到任何流行的 [Linux](https://baike.baidu.com/item/Linux) 机器上，也可以实现[虚拟化](https://baike.baidu.com/item/%E8%99%9A%E6%8B%9F%E5%8C%96/547949)。容器是完全使用[沙箱](https://baike.baidu.com/item/%E6%B2%99%E7%AE%B1/393318)机制，相互之间不会有任何接口。 

![](E:\文档\SpringBoot课件\Spring Boot 笔记\images\2019-04-04_105628.png)



![](E:\文档\SpringBoot课件\Spring Boot 笔记\images\2019-04-04_105504.png)



## 2、docker的核心概念



![](E:\文档\SpringBoot课件\Spring Boot 笔记\images\2019-04-04_105759.png)



## 3、docker安装(centos7)

步骤：

```shell
1. 检查内核版本,必须是3.10及以上
    uname -r
2. 安装docker
    yum -y install docker
3.启动docker
	systemctl start docker
4.查看docker版本
	docker -v
5.设置开机启动docker
	systemctl enable docker
6.停止docker
	systemctl stop docker

```



## 4、Docker常用命令&操作

### 1、镜像操作

| 操作 | 命令                                         | 说明                                                 |
| ---- | -------------------------------------------- | ---------------------------------------------------- |
| 检索 | docker search 关键字 eg: docker search redis | 去镜像仓库查询镜像的信息                             |
| 拉取 | docker pull 镜像名:tag                       | :tag是可选的，tag是标签，多为软件的版本，默认是lates |
| 列表 | docker images                                | 查看本地所有镜像                                     |
| 删除 | docker rmi <image-id>                        | 删除指定的本地镜像                                   |

https://hub.docker.com

### 2、容器操作

软件镜像---->运行镜像----->产生一个容器实例（正在运行的镜像）

| 操作         | 命令                                                         | 说明                                                         |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 运行         | dcoker run --name container-name -d image-name                eg:docker run --name myredis -d redis | --name:自定义容器名      -d:  后台运行                                  image-name:指定的镜像模板 |
| 列表         | docker ps(查看运行中的容器)                                  | 加上-a;  可以查看所有容器                                    |
| 停止         | docker stop container-name/contain-id                        | 停止当前运行的容器                                           |
| 启动         | docker start container-name/contain-id                       | 启动容器                                                     |
| 删除         | docker rm container-id                                       | 删除指定的容器                                               |
| 端口映射     | -p 6379:6379     eg:docker run -d -p 6379:6379 --name myredis docker.io/redis | -p 主机端口（映射到）容器内部的端口                          |
| 容器日志     | docker logs container-name/container-id                      | 查看容器日志                                                 |
| 停止所有容器 | docker stop $(docker ps -a -q)                               | 停止所有运行的景象实例                                       |
| 删除         | docker rm -f $(docker ps -a -q)                              | 如果想要删除所有镜像container实例（不包括正在运行的镜像）    |

更多的命令：https://docs.docker.com/engine/reference/commandline/docker/

中文网：https://docs.docker-cn.com/engine/reference/commandline/docker/#child-commands





# 六、SpringBoot数据访问

## 1、JDBC



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

2.配置application.yml

```yaml
1 spring:
2   datasource:
3     username: root
4     password: 123456
5     url: jdbc:mysql://192.168.1.104:3306/jdbc
6     driver-class-name: com.mysql.jdbc.Driver
//如果是springboot2.0以后需要添加时区serverTimezone=UTC
url: jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC
   driver-class-name: com.mysql.cj.jdbc.Driver


```

效果:

　　默认使用org.apache.tomcat.jdbc.pool.DataSource作为数据源;

　　数据源的相关配置都在DataSourceProperties里面;

自动配置原理:

org.springframework.boot.autoconfigure.jdbc：

1、参考DataSourceConfiguration，根据配置创建数据源，默认使用Tomcat连接池；可以使用spring.datasource.type指定自定义的数据源类型；

2、SpringBoot默认可以支持；

```
org.apache.tomcat.jdbc.pool.DataSource、HikariDataSource、BasicDataSource
```

3、自定义数据源类型

```java
/**
* Generic DataSource configuration.
*/
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type")
static class Generic {

    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        //使用DataSourceBuilder创建数据源 ,利用反射创建响应type的数据源,并且绑定相关属性         
        return properties.initializeDataSourceBuilder().build();
    }

}
```

4、**DataSourceInitializer:ApplicationListener**

　　作用:

　　　1)、runSchemaScripts();运行建表语句;

　　　 2)、runDataScripts();运行插入数据的sql语句;

默认只需要将文件命名为:

```properties
schema-*.sql、data-*.sql
默认规则：schema.sql，schema-all.sql；
可以使用   
    schema:
      - classpath:department.sql
      指定位置
```



5、操作数据库:自动配置jdbcTemplate操作数据库

## 2、整合Druid数据源



导入依赖

```xml
    <!--引入druid数据源类型-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.8</version>
</dependency>
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

yml配置

```yaml
spring:
  datasource:
#  数据源基本配置
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver
#   数据源类型
    type: com.alibaba.druid.pool.DruidDataSource
#   数据源其他配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    #   配置监控统计拦截的filters 去掉监控后界面sql无法统计，‘wall’用于防火墙
    filters: stat,wall,log4j
    maxPoolPreparedStatementsPreConnectionSize: 20
    userGlobalDataSourceSata: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500


```



导入Druid数据源

```java
  @Configuration
  public class DruidConfig {
  
      @ConfigurationProperties(prefix = "spring.datasource")
      @Bean
      public DataSource druid(){
          return new DruidDataSource();
      }
  
     //配置Druid的监控
     //1、配置一个管理后台的Servlet
     @Bean
     public ServletRegistrationBean statViewServlet(){
         ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
         Map<String,String> initParams = new HashMap<>();
         initParams.put("loginUsername","admin");
         initParams.put("loginPassword","123456");
         initParams.put("allow","");//默认允许所有访问
         initParams.put("deny","192.168.1.102");
         bean.setInitParameters(initParams);
         return bean;
     }
 
     //2、配置一个web监控的filter
     @Bean
     public FilterRegistrationBean webStatFilter(){
         FilterRegistrationBean bean = new FilterRegistrationBean();
         bean.setFilter(new WebStatFilter());
         Map<String,String> initParams = new HashMap<>();
         initParams.put("exclusions","*.js,*.css,/druid/*");
         bean.setInitParameters(initParams);
         bean.setUrlPatterns(Arrays.asList("/*"));
         return bean;
     }
 
 }
```



## 3、整合Mybatis



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<!--引入druid数据源-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.8</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

![img](https://images2018.cnblogs.com/blog/1069029/201808/1069029-20180812213539762-1167101130.png)

 

步骤:

　　1)、配置数据源相关属性(见上一节Druid)

　　2)、给数据库建表

　　3)、创建JavaBean

**1)、注解版**



```java
//指定这是一个操作数据库的mapper
@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(departmentName) values (#{departmentName})")
    int insertDept(Department department);

    @Update("update department set departmentName=#{departmentName} where id=#{id}")
    int updateDept(Department department);

}
```



问题:

自定义MyBatis的配置规则,给容器中添加一个ConfigurationCustomizer;

```java
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }

}
```



 

```java
使用MapperScan批量扫描所有的Mapper接口；
@MapperScan(value = "com.young.springboot.mapper")
@SpringBootApplication
public class SpringBoot06DataMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot06DataMybatisApplication.class, args);
    }
}
```

 

**2)、配置文件版**

```yaml
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml 指定全局配置文件的位置
  mapper-locations: classpath:mybatis/mapper/*.xml  指定sql映射文件的位置
```

 

**4、整合SpringData JPA**

**1)、SpringData简介**

**![img](https://images2018.cnblogs.com/blog/1069029/201808/1069029-20180813000247652-642832410.png)**

 

**2)、整合SpringData JPA**

JPA:ORM(Object Relational Mapping)对象关系映射

1)、编写一个实体类(bean)和数据表进行映射,并且配置好映射关系;

 

```java
//使用JPA注解配置映射关系
@Entity//告诉JPA这是一个实体类(和数据表映射的类)
@Table(name = "tbl_user")//@Table来指定和哪个数据表对应;如果省略默认表名就是user
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

    @Column(name = "last_name",length = 50)//这是和数据表对应的一个列
    private String lastName;

    @Column //省略默认列名就是属性名
    private String email;
```

 

2)、编写一个Dao接口来操作实体类对应的数据表(Repository)

```java
//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<User,Integer>{
}
```

3)、基本的配置

```yaml
spring:
  jpa:
    hibernate:
#     更新或者创建数据表结构
      ddl-auto: update
#   控制台显示SQL
    show-sql: true
```







































 



















