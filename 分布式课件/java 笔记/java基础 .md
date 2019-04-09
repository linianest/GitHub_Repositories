# java基础

# 一、基础入门

## 1、准备环境

1. 安装jdk：

2. 配置环境变量

   - JAVA_HOME     D:\Java\jdk1.8.0_201
   - path       D:\Java\jdk1.8.0_201\bin
   - CLASSPATH  .

3. 测试环境

  - java、javac、java -version

## 2、第一个程序HelloWorld

```java
public class HelloWorld{
    public static void main(String []args){
        System.out.println("helloWord!");
    }
}
```

## 3、Java的基本元素

>1. 空白分隔符 ： 空格、Tab键、换行符
>
>2. 关键字：是不能用于变量名、类名、对象名、方法名等
>
>   - 访问修饰符：public private protected
>
>   - 类、方法和变量修饰符：abstract、class、extends、final、implements、interface\
>
>     native、new、 static、strictfp、synchronized、transient、volatile
>
>   - 程序控制：break、continue、return、do、while、if、else、for、instanceof、switch、case、default
>
>   - 错误处理：try 、catch、throws
>
>   - 包相关：import、package
>
>   - 基本数据类型：boolean、byte、char、double、float、int、long、short
>
>     (null、true、false这三个不是关键字，它们是单独的标识类型)
>
>   - 变量引用：supper、this、void
>
>   - 保留字：goto、const
>
>3. 标识符：
>
>   标识符是类、方法、变量的名字。
>
>   命名规则：
>
>   - 可以包含数字、字母、下划线_、美元符号$,但是不能以数字开头作为。
>
>   - 区分大小写
>
>   - 关键字是不能作为标识符的。
>
>     HelloWorld  符合
>
>     goto              不符合
>
>     agoto             符合
>
>     123goto        不符合
>
>     
>
>4. 注释：
>
>   - 单行注释：    //这是注释
>   - 多行注释：   /*   这是多行注释     */
>   - 文档注释：  /**      这是文档注释，可以换行.文档注释允许把关于程序的信息嵌入到程序内部，我们可以用javadoc工具 来提取这些信息，形成帮助文档     **/
>
>5. 分隔符：**==除了注释内容可以出现中文，其他地方一律是英文，表单符号都是英文状态下的。==**
>
>   - ()圆括号：定义方法的参数表，条件语句、循环语句中的条件定义。定义表达式的优先级。
>
>   - {}花括号：初始化数组，定义程序块、类、方法。
>
>   - []中括号：申明数组类型
>
>   - ；分号：表示一个语句的结束
>
>   - ，逗号：变量声明时，用于分割多个变量
>
>   - .点：用于软件包和子包或类、对象和变量或者方法分割   System.out.println("Hello world");
>
>     

## 4、Eclipse安装、配置、使用

1. Eclipse的下载与解压
2. 切换工作区;file->switch workspace->other
3. 创建一个案例Helloworld

## 5、八大数据类型

1. 整形（1字节占8位二进制）
   - byte:1字节（-128~127）
   - short:2字节（-32768~32767）
   - int：4字节（正负20亿多一点）
   - long：8字节
2. 字符型
   - char：2字节，采用的是Unicode格式，支持中文（一个中文占两个字节）
3. 浮点型
   - float：4字节  单精度（1.2f）,小数后面8位
   - double：8字节  双精度（小数默认是double）
4. 布尔型
   - boolean：true、false



## 6、变量和常量

1. Java变量：可以改变的量

   命名规则：(非强制性)

   - 遵循标识符命名规则

   - 尽量使用有意义的单词

   - 一般首字母小写

     eg:成绩：score     身高：height   汤姆的身高：heightOfTom

2. java常量：不可改变的量

   一般用final关键字修饰，只能赋值一次

   命名规则：

   - 遵循标识符命名规则
   - 尽量使用有意义的单词
   - 一般全部大写，字母之间用下划线_连接

## 7、数据类型转换

1. 自动类型转换：隐式转换

   - 目的类型比原来的类型要大(字节数)

   - 两种类型是相互兼容的

     byte->short

     short->int

     char->int

     int->long

     int-double

     float->double

2. 强制类型转换：显式转换

   - 由大到小的转换（字节）



## 38、接口的应用

1. 方法的修饰符

   - 接口中变量的修饰符：public、static、final

   - 接口中方法的修饰符：public、abstract

     接口类：

     ```java
     public interface Modifier{
         void print();
         public void print1()；
         abstract void print2();
         public abstract void print3();
         abstract public void print4();
         public void print5();
         //编译后都会编译成public abstract
     }
     ```

     实现接口：

     ```java
     public interface ModifierTest implements Modifier{
        
     }
     ```

     

2. 接口类型引用变量



# 二、线程

## 52、线程基本知识

1. 线程与进程

   - 进程：执行中的程序。

   - 线程：轻量级的进程。

     线程本身不能单独运行，必须放在一个进程中才能执行。

2. java的线程模型：生命周期

   1. 新建状态
      - 线程被创建之后，便处于新建状态。
   2. 就绪状态
      - 新建状态的线程调用start()进入就绪状态。
      - 阻塞状态的线程解除阻塞之后进入就绪状态。
   3. 运行状态
      - 处于就绪状态的线程获得了cpu的资源，该线程就进入了运行状态。
   4. 阻塞状态
      - 是一个正在运行的线程，因为一些原因，让出cpu的资源暂时中止而进入的状态。
   5. 终止状态
      1. 正常终止：执行完任务，正常终止
      2. 强制终止：stop、destroy
      3. 异常终止：当线程执行过程中产生了异常，线程会终止。



![](E:\文档\分布式课件\java 笔记\images\795235-20161115162228717-961430892.png)



## 53、继承Thread类创建线程

1. 继承Thread类创建线程

   ```java
   class  MyThread extends  Thread{
       public void run(){
        //相关代码
          
   	}
   }
   
   ```

   **注意：如果运行的是run()方法，那么就是单线程，运行start()方法，就会开辟一个新线程运行。**

## 54、实现Runnable接口创建线程

```java
class  MyThread implements Runnable{
    public void run(){
     //相关代码
       
	}
}
```



## 55、多线程的运用

1. 多线程的并发执行

   **==java对于线程启动后唯一能保证的是每个线程都被启动并且结束==**。但是对于哪个线程先执行，哪个后执行，什么时候执行，是没有保证的。

   ```java
   public class Test{
       public static void main(String []args){
        //相关代码
          Thread t1=new Thread(new MyRunnable1);
          Thread t2=new Thread(new MyRunnable1);
           t1.start();
           t2.start();
   	}
   }
   class MyRunnanle1 implements Runnable{
       public void run(){
           for(int i=0;i<10;i++){
               System.out.print("+");
           }
       }
   }
   class MyRunnanle2 implements Runnable{
       public void run(){
           for(int i=0;i<10;i++){
               System.out.print("*");
           }
       }
   }
   ```

   

2. 线程的优先级

   java中优先级高的线程有更大的可能性获得获得cpu，但不是优先级高的总是先执行，也不是优先级低的线程总不执行。用概率来解释。

   - 获得优先级getPriority();
   - 设置优先级setPriority();如：setPriority(Thread.MAX_PRIORITY);
     - Thread.MAX_PRIORITY最高优先级：10
     - Thread.MIN_PRIORITY最低优先级：1



## 56、线程调度的三个方法

1. 休眠方法sleep(毫秒数)   sleep(毫秒数，纳秒数)

   指定时间内线程休眠，时间到后，继续进入就绪状态，等待cpu分配资源，然后运行。

2. 暂停方法yield()：表示线程释放cpu资源，然后再同时和其他线程竞争cpu资源

3. 挂起方法join()：表示该线程加塞。先执行完该线程，再执行剩下的线程



## 57、线程并发的问题以及解决方案

1. 线程同步问题的由来

   多个线程共享资源没有进行控制

2. 同步问题java的解决方案

   - 同步方法：当线程进入同步方法的时候，会获得同步方法所属对象的锁，一旦获得对象锁，则其他线程不能再执行被所对象的其他任何同步方法，只有在同步方法执行完毕之后释放了锁，其他线程才能执行。
     - synchronized   方法声明{}
   - 同步块：
     - synchronized (资源对象){}

3. 死锁的问题

   线程A占有资源1，请求资源2

   线程B占有资源2，请求资源1

## 58、生产者和消费者模型

1. 简介：

   生产者生产了数据，存放在仓库，消费者通过仓库消费数据，当仓库满了，生产者等待，当仓库空了，消费者等待。

2. 代码

   生产者：

   ```java
   public class Producer extends Thread{
       private List<Integer> list;
       private int max;//仓库容量
       
       public Producer(String name,int max,List<Integer> list){
           super(name);
           this.max=max;
           this.list=list;
       }
       
       public void run(){
           while(true){
               synchronized(list){
                   while(list.size()==max){
                       System.out.println("仓库满了");
                       try{
                           //当仓库满了，生产者等待
                           list.wait();//线程挂起
                       }catch(InterruptedException e){
                           e.printStackTrace();
                       }
                      
                   }
                   int num=(int)(Math.random()*100)+;
                   list.add(num);
                   System.out.println(this.getName()+"生产了"+num);
                   list.notifyAll();
               }
           }
       }
   }
   ```

   

   消费者：

   ```java
   public class Consumer extends Thread{
       private List<Integer> list;
       private int max;//仓库容量
       
       public Consumer(String name,int max,List<Integer> list){
           super(name);
           this.max=max;
           this.list=list;
       }
       
       public void run(){
           while(true){
               synchronized(list){
                   while(list.isEmpty){
                       System.out.println("仓库空了");
                       try{
                           //当仓库空了，消费者等待
                           list.wait();
                       }catch(InterruptedException e){
                           e.printStackTrace();
                       }
                      
                   }
                    System.out.println(this.getName()+"正在消费"+list.get(list.size()-1));
                   list.remove(list.size()-1);
                   list.notifyAll();
               }
           }
       }
   }
   ```

   测试：

   ```java
   public class Test{
       public static void main(String []args){
            List<Integer> list=new ArrayList<Integer>();
           int max=100;
           Producer p=new Producer("生产者"，max,list);
           Consumer c=new Consumer("消费者"，max,list);
           p.start();
           c.start();
       }
   }
   ```

   



