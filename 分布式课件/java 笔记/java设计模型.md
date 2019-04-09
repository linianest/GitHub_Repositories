# Java设计模型

# 设计模式简介及原则

### 简介：java的设计模式大体上分为三大类：

- 创建型模式（5种）：工厂方法模式，抽象工厂模式，单例模式，建造者模式，原型模式。
- 结构型模式（7种）：适配器模式，装饰器模式，代理模式，外观模式，桥接模式，组合模式，享元模式。
- 行为型模式（11种）：策略模式、模板方法模式、观察者模式、迭代子模式、责任链模式、命令模式、备忘录模式、状态模式、访问者模式、中介者模式、解释器模式。

### **设计模式遵循的原则有6个：**

**1、开闭原则（Open Close Principle）**

　　**对扩展开放，对修改关闭**。

**2、里氏代换原则（Liskov Substitution Principle）**

　　只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。

**3、依赖倒转原则（Dependence Inversion Principle）**

　　这个是开闭原则的基础，**对接口编程**，依赖于抽象而不依赖于具体。

**4、接口隔离原则（Interface Segregation Principle）**

　　使用多个隔离的借口来降低耦合度。

**5、迪米特法则（最少知道原则）（Demeter Principle）**

　　一个实体应当尽量少的与其他实体之间发生相互作用，使得系统功能模块相对独立。

**6、合成复用原则（Composite Reuse Principle）**

　　原则是尽量使用合成/聚合的方式，而不是使用继承。继承实际上破坏了类的封装性，超类的方法可能会被子类修改。



## 1、工厂模式

### 1.1、简单工厂模式

1. 工厂模式

   步骤:

   1. 通过new关键字
   2. 通过接口实现多态的方式
   3. 通过工厂的方式

   model代码

   apple.java

   ```java
   package com.ln;
   
   public class Apple {
   	/**
   	 * get
   	 */
   	public void get(){
   		System.out.println("苹果");
   	}
   }
   
   ```

   banana.java

   ```java
   package com.ln;
   
   public class Banana {
   	/**
   	 * get
   	 */
   	public void get(){
   		System.out.println("香蕉");
   	}
   
   }
   
   ```

   测试类：

   ```java
   package com.ln;
   
   public class MainClass {
   	
   	public static void main(String[] args) {
   		Apple apple=new Apple();
   		Banana banana=new Banana();
   		apple.get();
   		banana.get();
   	}
   
   }
   
   ```

2. 创建接口Fruit.java,让其他两个类实现接口

   ```java
   package com.ln;
   
   public interface Fruit {
   	
   	public void get();
   
   }
   
   ```

   然后再测试

   ```java
   package com.ln;
   
   public class MainClass {
   	
   	public static void main(String[] args) {
   		Fruit apple=new Apple();
   		Fruit banana=new Banana();
   		apple.get();
   		banana.get();
   	}
   
   }
   ```

3. 通过工厂创建实例，创建一个工厂类FruitFactory.java

   ```java
   package com.ln;
   
   public class FruitFactory {
   	
   	public static Apple getAapple(){
   		return new Apple();
   	}
   	
   	public static Banana getBanana(){
   		return new Banana();
   	}
   
   }
   
   ```

   ```java
   package com.ln;
   
   public class MainClass {
   	
   	public static void main(String[] args) {
   		//1.最简单的new关键字创建
   //		Apple apple=new Apple();
   //		Banana banana=new Banana();
   //		apple.get();
   //		banana.get();
   		
   		//2.通过接口实现多台的方式
   //		Fruit apple=new Apple();
   //		Fruit banana=new Banana();
   //		apple.get();
   //		banana.get();
   		
   		//3.通过工厂的方式
   		Fruit apple=FruitFactory.getAapple();
   		Fruit banana=FruitFactory.getBanana();
   		apple.get();
   		banana.get();
   	}
   
   }
   
   ```

4. 通过工厂传参的形式

```java
package com.ln;

public class FruitFactory {
	
	/**
	 * 获得Apple的实例
	 * @return
	 */
	public static Apple getAapple(){
		return new Apple();
	}
	
	/**
	 * 获得Banana的实例
	 * @return
	 */
	public static Banana getBanana(){
		return new Banana();
	}
	
	public static Fruit getFruit(String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		//equalsIgnoreCase不区分大小写
		if (type.equalsIgnoreCase("apple")) {
			return Apple.class.newInstance();
		}else if(type.equalsIgnoreCase("banana")) {
			return Banana.class.newInstance();
		}else{
			System.out.println("找不到相应的实例化类");
			return null;
		}
		
		
//		Class fruit= Class.forName(type);
//		return (Fruit)fruit.newInstance();
		
	}

}

```

**一种是通过判断别名，然后工厂经过判断，返回实例化对象，一种是通过反射机制（需要全类名），获取实例化对象**

```java
		//3.1通过工厂模式传参，获得实例
		Fruit apple=FruitFactory.getFruit("Apple");
		Fruit banana=FruitFactory.getFruit("Banana");
		apple.get();
		banana.get();
		
//		Fruit apple=FruitFactory.getFruit("com.ln.Apple");
//		Fruit banana=FruitFactory.getFruit("com.ln.Banana");
//		apple.get();
//		banana.get();
```



### 1.2、工厂方法模式

**工厂方法模式同样属于类的创建型模式又被称为多态工厂模式。**

> 工厂方法模式：定义一个创建产品对象的工厂接口，将实际创建工作推迟到子类当中。
>
> 核心工厂类不再负责产品的创建，这样核心类成为一个抽象工厂角色，仅负责具体工厂子类必须实现的接口，这样进一步抽象的好处是使得工厂方法模式可以使系统在不修改具体工厂角色的情况下引进新的产品。

模式中包含的角色及其职责：

> 1. 抽象工厂（Creator）角色：
>
>    工厂方法的和兴，任何工厂类都必须实现这个接口。
>
> 2. 具体工厂（Concrete Creator）角色：
>
>    具体工厂类是抽象工厂的一个实现，具体负责实例化产品对象
>
> 3. 抽象（Product）角色：
>
>    工厂方法模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。
>
> 4. 具体产品（Concrete Product）角色：
>
>    工厂方法模式所创建的具体实例对象；
>
> 
>
> 

步骤:

1. 创建工厂接口

   ```java
   
   public interface FruitFactory {
   	
   	
   	public Fruit getFruit();
   
   }
   
   ```

   

2. 创建实例工厂

   ```java
   public class BananaFactory implements FruitFactory {
   
   	@Override
   	public Fruit getFruit() {
   		// TODO Auto-generated method stub
   		return new Banana();
   	}
   
   }
   
   ```

   

3. 获得实例

   ```java
   public class MainClass {
   	
   	public static void main(String[] args) {
   	
   		//通过工厂方法模式获得实例
   		//1.创建工厂实例
   		FruitFactory fruitFactory=new AppleFactory();
   		//2.通过AppleFactory获得banana实例
   		Fruit banana=fruitFactory.getFruit();
   		banana.get();
   		
   
   	}
   
   }
   ```

   

好处:**==可以不用像简单工厂模式一样，每新加一个类，就必须修改工厂模型，达到了解耦。而且只需要创建一个获取工厂实习工厂接口，通过实例工厂，就可以获取一个实例。==**

### 1.3、工厂方法模式和简单工厂模式比较

1. 工厂方法模式与简单工厂模式在结构上的不同不是很明显。工厂方法类的核心是一个抽象工厂类，而简单工厂模式把核心放在一个具体类上。
2. 工厂方法模式之所以有一个别名叫多态性工厂模式是因为具体工厂类都有共同的接口，或者有共同的抽象父类。
3. 当系统扩展需要添加新的产品对象是，仅仅需要添加一个具体对象以及一个具体工厂对象，原有工厂对象不需要进行任何修改，也不需要修改客户端，很好的符合了“开放-封闭”原则。而简单工厂模式在添加新产品对象后不得不修改工厂方法，扩展性不好。
4. 工厂方法模式退化后就是简单工厂模式。



### 1.4、抽象工厂模式

> 抽象工厂模式是所有形态的工厂模式中最为抽象和最其一般性的。抽象工厂模式可以向客户端提供一个借口，使得客户端在不必指定产品的具体类型的情况下，能够创建多个产品族的产品对象。

模式中包含的角色及其职责：

> 1. 抽象工厂（Creator）角色：Fruitfactory
>
>    抽象工厂方法的核心，包含对多个产品结构的声明，任何工厂类都必须实现这个接口。
>
> 2. 具体工厂（Concrete Creator）角色：southFactory
>
>    具体工厂类是抽象工厂的一个实现，负责实例化某个产品族中的产品对象。
>
> 3. 抽象（Product）角色:Fruit
>
>    抽象模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。
>
> 4. 具体产品（Concrete Product）角色：apple、banana
>
>    抽象模式所创建的具体实例对象；
>
> 

工厂模式获得产品：

产品接口

```java

public interface Fruit {
	
	public void get();

}

```

产品抽象类

```java
public abstract class Apple implements Fruit{
     
	public abstract void get();
}

public abstract class Banana implements Fruit {
	public abstract void get();
}

```

产品链

```java
public class NorthApple extends Apple{

	/**
	 * get
	 */
	public void get(){
		System.out.println("北方苹果");
	}
}

public class NorthBanana extends Banana{

	/**
	 * get
	 */
	public void get(){
		System.out.println("北方香蕉");
	}
}

```

```java

public class SouthApple extends Apple{
	/**
	 * get
	 */
	public void get(){
		System.out.println("南方苹果");
	}
}


public class SouthBanana extends Banana{
	/**
	 * get
	 */
	public void get(){
		System.out.println("南方香蕉");
	}
}

```



定义工厂模式，创建实例

```java
public interface FruitFactory {
	
	//实例化Apple
	public Fruit getApple();


}
```

```java
public class NorthFruitFactory implements FruitFactory {

	public Apple getApple() {
		// TODO Auto-generated constructor stub
		return new NorthApple();
	}

	@Override
	public Fruit getBanana() {
		// TODO Auto-generated method stub
		return new NorthBanana();
	}	

}

```

```java
public class SouthFruitFactory implements FruitFactory {

	public Fruit getApple() {
		// TODO Auto-generated constructor stub
		return new SouthApple();
	}

	@Override
	public Fruit getBanana() {
		// TODO Auto-generated method stub
		return new SouthBanana();
	}

	
}

```

测试：

```java

public class MainClass {
	
	public static void main(String[] args) {

		//北方水果
		FruitFactory ff=new NorthFruitFactory();
		Fruit apple=ff.getApple();
		apple.get();
		Fruit banana=ff.getBanana();
		banana.get();
		
		//南方水果
		FruitFactory ff2=new SouthFruitFactory();
		Fruit apple2=ff2.getApple();
		apple2.get();
		Fruit banana2=ff2.getBanana();
		banana2.get();

	}

}

```



### 1.5、工厂模式在开发中的应用

编写一个简单的计算器

#### 1.5.1、简单版本

1. 版本

```java
import java.util.Scanner;

/**
 * 编写一个计算器
 * 缺点：1.缺少代码的重复性。
 * @author LiNian
 *
 */
public class MainClass {

	public static void main(String[] args) {
		//1.接受控制台的输入
		System.out.println("------计算器程序-----");
		System.out.println("输入第一个操作数");
		Scanner scanner=new Scanner(System.in);
		String strnum1=scanner.nextLine();
		
		
		System.out.println("输入运算符");
		String oper=scanner.nextLine();
		
		System.out.println("输入第二个操作数");
		String strnum2=scanner.nextLine();
		double result=0;
		//2.进行运算,"+"放前面，防止空指针异常
		if("+".equals(oper)){
			result=Double.parseDouble(strnum1)+Double.parseDouble(strnum2);
		}else if("-".equals(oper)){
			result=Double.parseDouble(strnum1)-Double.parseDouble(strnum2);
		}else if("*".equals(oper)){
			result=Double.parseDouble(strnum1)*Double.parseDouble(strnum2);
		}else if("/".equals(oper)){
			result=Double.parseDouble(strnum1)/Double.parseDouble(strnum2);
		}
		
		//3.返回结果
		System.out.println(strnum1+oper+strnum2+"="+result);
	}
}

```



#### 1.5.2、通过继承操作类重构

2.版本，抽出重构的方法,创建一个操作类

```java
public abstract class Operation {

	private double num1;
	private double num2;
	public double getNum1() {
		return num1;
	}
	public void setNum1(double num1) {
		this.num1 = num1;
	}
	public double getNum2() {
		return num2;
	}
	public void setNum2(double num2) {
		this.num2 = num2;
	}
	
	public abstract double getResult();
}

```



```java
public class AddOperation extends Operation {

	@Override
	public double getResult() {
		// TODO Auto-generated method stub
		return this.getNum1()+this.getNum2();
	}

}

```

```java
import java.util.Scanner;

/**
 * 编写一个计算器
 * 缺点：1.缺少代码的重复性。
 * @author LiNian
 *
 */
public class MainClass {

	public static void main(String[] args) {
		//1.接受控制台的输入
		System.out.println("------计算器程序-----");
		System.out.println("输入第一个操作数");
		Scanner scanner=new Scanner(System.in);
		String strnum1=scanner.nextLine();
		
		
		System.out.println("输入运算符");
		String oper=scanner.nextLine();
		
		System.out.println("输入第二个操作数");
		String strnum2=scanner.nextLine();
		double result=0;
		Double num1=Double.parseDouble(strnum1);
		Double num2=Double.parseDouble(strnum2);
		//2.进行运算,"+"放前面，防止空指针异常
		if("+".equals(oper)){
			Operation operation=new AddOperation();
			operation.setNum1(num1);
			operation.setNum2(num2);
			result = operation.getResult();
		}else if("-".equals(oper)){
			result=Double.parseDouble(strnum1)-Double.parseDouble(strnum2);
		}else if("*".equals(oper)){
			result=Double.parseDouble(strnum1)*Double.parseDouble(strnum2);
		}else if("/".equals(oper)){
			result=Double.parseDouble(strnum1)/Double.parseDouble(strnum2);
		}
		
		//3.返回结果
		System.out.println(strnum1+oper+strnum2+"="+result);
	}
}

```



#### 1.5.3、通过工厂方法模式重构

创建工厂类OperationFactory

```java
public class OperationFactory {

	public static Operation getOperaction(String oper){
		if("+".equals(oper)){
			return new AddOperation();
		}else if("-".equals(oper)){
			return new SubtractionOperation();
		}
		return null;
	}
}

```

创建具体的操作类AddOperation继承抽象类Operation

Operation.java

```java
public abstract class Operation {

	private double num1;
	private double num2;
	public double getNum1() {
		return num1;
	}
	public void setNum1(double num1) {
		this.num1 = num1;
	}
	public double getNum2() {
		return num2;
	}
	public void setNum2(double num2) {
		this.num2 = num2;
	}
	
	public abstract double getResult();
}

```



AddOperation.java

```java
public class AddOperation extends Operation {

	@Override
	public double getResult() {
		// TODO Auto-generated method stub
		return this.getNum1()+this.getNum2();
	}

}

public class SubtractionOperation extends Operation {

	@Override
	public double getResult() {
		// TODO Auto-generated method stub
		Double result=this.getNum1()-this.getNum2();
		return result;
	}

}

```

Test.java

```java
import java.util.Scanner;

/**
 * 编写一个计算器
 * 缺点：1.缺少代码的重复性。
 * @author LiNian
 *
 */
public class MainClass {

	public static void main(String[] args) {
		//1.接受控制台的输入
		System.out.println("------计算器程序-----");
		System.out.println("输入第一个操作数");
		Scanner scanner=new Scanner(System.in);
		String strnum1=scanner.nextLine();
		
		
		System.out.println("输入运算符");
		String oper=scanner.nextLine();
		
		System.out.println("输入第二个操作数");
		String strnum2=scanner.nextLine();
		double result=0;
		Double num1=Double.parseDouble(strnum1);
		Double num2=Double.parseDouble(strnum2);
		//2.进行运算,"+"放前面，防止空指针异常
		Operation operation=OperationFactory.getOperaction(oper);
		operation.setNum1(num1);
		operation.setNum2(num2);
		result = operation.getResult();
		
		//3.返回结果
		System.out.println(strnum1+oper+strnum2+"="+result);
	}
}

```



#### 1.5.4、通过工厂方法模式重构

好处：

> 1. 好处：
>    - 不需要修改工厂，只需要添加一个相应的操作类
>    - 添加操作类的工厂就行
> 2. 坏处：
>    - 每次操作必须知道是那种操作类型，调用那种操作工厂



1. 创建操作类接口

   ```java
   public interface OperationFactory {
   
   	public Operation getOperaction();
   }
   
   ```

2. 创建操作类工厂

   ```java
   public class AddOperationFactory implements OperationFactory {
   
   	@Override
   	public Operation getOperaction() {
   		// TODO Auto-generated method stub
   		return new AddOperation();
   	}
   
   }
   
   public class AddOperation extends Operation {
   
   	@Override
   	public double getResult() {
   		// TODO Auto-generated method stub
   		return this.getNum1()+this.getNum2();
   	}
   
   }
   
   ```

3. 测试：

   ```java
   import java.util.Scanner;
   
   /**
    * 编写一个计算器
    * 缺点：1.缺少代码的重复性。
    * @author LiNian
    *
    */
   public class MainClass {
   
   	public static void main(String[] args) {
   		//1.接受控制台的输入
   		System.out.println("------计算器程序-----");
   		System.out.println("输入第一个操作数");
   		Scanner scanner=new Scanner(System.in);
   		String strnum1=scanner.nextLine();
   		
   		
   		System.out.println("输入运算符");
   		String oper=scanner.nextLine();
   		
   		System.out.println("输入第二个操作数");
   		String strnum2=scanner.nextLine();
   		double result=0;
   		Double num1=Double.parseDouble(strnum1);
   		Double num2=Double.parseDouble(strnum2);
   		//2.进行运算,"+"放前面，防止空指针异常
   		if("+".equals(oper)){
   			OperationFactory factory=new AddOperationFactory();		
   			Operation operation=factory.getOperaction();
   			operation.setNum1(num1);
   			operation.setNum2(num2);
   			result = operation.getResult();
   		}		
   		//3.返回结果
   		System.out.println(strnum1+oper+strnum2+"="+result);
   	}
   }
   
   ```

   

## 2、单例模式

定义:**==单例模式是一种对象创建型模式，使用单例模式，可以保证为一个类只生成唯一的实例对象。==**

也就是说，在整个程序空间中，该类只存在一个实例对象。

其实，GoG对单例模式的定义是：**保证一个类、只有一个实例存在，同时提供对该实例加以访问的全局访问方发。**

为什么要使用单例模式？

在应用系统开发过程中，我们常常有以下需求：

> 1. 在多个线程之间，比如servlet环境，共享同一个资源或者操作同一个对象。
> 2. 在整个程序空间使用全局变量，共享资源。
> 3. 大规模系统中，为了性能的考虑，需要节省对象的创建时间等等。
>
> 因为Singletno模式可以保证为一个类只生成唯一的实例对象，所以这些情况，singletno模式就派上用处了。

单例模式的实现：

>1. 饿汉式
>2. 懒汉式
>3. 双重检查

### 2.1、饿汉式

步骤：

1. 创建一个类，并且把构造方法私有化。

2. 创建一个全局的静态实例对象，并初始化对象；

3. 提供一个全局的静态方法，返回静态的对象实例。

   ```java
   
   public class Person {
   
   	private static Person person=new Person();
        	
   	private String name;
   
   	public String getName() {
   		return name;
   	}
   
   	public void setName(String name) {
   		this.name = name;
   	}
   
   	//构造方法私有化
   	private Person(){
   		
   	}
   	
   	//提供一个全局的静态方法
   	public static Person getPerson(){  
   		return person;
   	}
   	
   }
   
   ```

   

测试：

```java

public class MainClass {
	public static void main(String[] args) {
      Person p1=Person.getPerson();
      Person p2=Person.getPerson();
      p1.setName("1");
      p2.setName("2");
      System.out.println(p1);
      System.out.println(p2);
	}
}

```



### 2.2、懒汉式

步骤：

1. 创建一个类，并且把构造方法私有化。
2. 创建一个全局的静态对象；
3. 提供一个全局的静态方法，判断对象实例是否初始化，如果没有，则初始化后再返回，如果有直接返回对象。

```java

public class Person {

	private static Person person;
     	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造方法私有化
	private Person(){
		
	}
	
	//提供一个全局的静态方法
	public static Person getPerson(){  
		if(person==null){
			person=new Person();
		}
		return person;
	}
	
}

```



### 2.3、饿汉式与懒汉式比较

1. 多线程状态下

   - 饿汉式能保证只有一个实例；因为它是在类加载的时候，初始化的。但是消耗了资源，因为有可能初始化后，后期不会去用。
   - 懒汉式不能保证只有一个实例；线程并发的状态下，判断前后有时间差。

2. 单线程状态下：

   - 都能保证只有一个实例；

   

### 2.4、双重检查

步骤：

1. 创建一个类，并且把构造方法私有化。

2. 创建一个全局的静态对象；

3. 提供一个全局的静态方法，判断对象实例是否初始化，如果没有就用synchronized同步代码块，再次判断对象是否初始化，如果没有，则初始化后再返回，如果有直接返回对象。

   解释：线程A和线程B，同时到达synchronized代码块，因为A知道没有对象（外面判断了一次），就初始化后，返回走了并释放了锁，

   线程B拿到锁后，进入后，如果不判断，拿到的就是空，只有判断才能知道拿到的是空还是对象。（线程A可能阻塞或者死亡）。

4. 两种实现方案：第一种方案，synchronized修饰方法，第二种，synchronized修饰代码块。但是第二种方案效率高。

代码：person.java

```java

public class Person {

	private static Person person;
     	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造方法私有化
	private Person(){
		
	}
	
	
	////提供一个全局的静态方法，并设置一个线程锁，保证线程并发，只有一个实例
	public static synchronized Person getPerson(){  
	if(person==null){
		  person=new Person();
	}
	  return person;
    }
	
}

```

第二种方式，效率高，因为不要每次都锁，第一种方法，每次获取实例，线程都会阻塞，第二种，线程只会阻塞一次。

```java

public class Person {

	private static Person person;
     	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//构造方法私有化
	private Person(){
		
	}
	
	//提供一个全局的静态方法，并设置一个线程锁，保证线程并发，只有一个实例
	public static Person getPerson(){  
		if(person==null){//针对以后的访问用
			synchronized (Person.class) {
				if(person==null){//针对第一次用
				   person=new Person();
				}
			}
		
		}
		return person;
	}
}

```



## 3、原型模式

**Prototype模式是一种对象的创建型模式，它采用复制原型对象的方法来创建对象的实例。使用Prototype模式创建的实例，具有与原型一样的数据。**

特点：

>1. 由原型对象自身创建目标对象。也就是说，对象创建这一动作发自原型对象本身。
>2. 目标对象是原型对象的一个克隆。通过Prototype模式创建的对象，不仅仅与原型对象有相同的结构，还与原型对象有相同的值。
>3. 根据克隆对象的深度层次不同，分为浅度克隆和深度克隆。

### 3.1、浅度克隆

克隆的对象属性都有新的地址，结构相同，值也相同，只克隆数值型的属性数据，不可隆引用类型。（不包括属性是应用类型）

步骤：

1. 原型对象必须实现Cloneable克隆接口
2. 提供一个方法，返回克隆的对象。

代码：person.java

```java
/**
 * Cloneable接口：告诉虚拟机我是可以被克隆的
 * @author LiNian
 *
 */
public class Person implements Cloneable{
	private String name;
	private int age;
	private String sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Person clone(){
		try {
			return (Person)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}

```

测试

```java

public class MainClass {
	public static void main(String[] args) {
      Person person=new Person();
      person.setName("llll");
      person.setAge(12);
      person.setSex("男");
      
/*      Person p2=new Person();
      p2.setName("2222");
      p2.setAge(12);
      p2.setSex("男");
      */
      /**
       * p2=person，是在内存中，两个栈对象都引用堆中同一个对象
       * p3=person.clone();p3的地址是引用堆中person的克隆一个新的对象地址
       */
      Person p2=person;
      Person p3=person.clone();
      p3.setName("2222");
      System.out.println(person);
      System.out.println(p2);
      System.out.println(p3);
	}
}

```





### 3.2、深度克隆

克隆的所有对象属性都是独自的，在堆中有新的内存地址，两个引用类型对象是分离的。

person.java

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Cloneable接口：告诉虚拟机我是可以被克隆的
 * @author LiNian
 *
 */
public class Person implements Cloneable{
	private String name;
	private int age;
	private String sex;
	
	private List<String> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Person clone(){
		try {
			Person person=(Person)super.clone();
			List<String> friends=new ArrayList<String>();
			for(String friend:this.getList()){
				friends.add(friend);
			}
			person.setList(friends);
			return person;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}

```

测试：

```java
import java.util.ArrayList;
import java.util.List;

public class MainClass {
	public static void main(String[] args) {
/*      Person person=new Person();
      person.setName("llll");
      person.setAge(12);
      person.setSex("男");
      
      Person p2=new Person();
      p2.setName("2222");
      p2.setAge(12);
      p2.setSex("男");
      
      *//**
       * p2=person，是在内存中，两个栈对象都引用堆中同一个对象
       * p3=person.clone();p3的地址是引用堆中person的克隆一个新的对象地址
       *//*
      Person p2=person;
      Person p3=person.clone();
      p3.setName("2222");
      System.out.println(person);
      System.out.println(p2);
      System.out.println(p3);*/
		
		
		//浅度克隆：引用类型，只是克隆一个堆连接地址，没有克隆成一个新的对象
		Person person=new Person();
		List<String> friends=new ArrayList<String>();
		friends.add("1111");
		friends.add("222");
		
		person.setList(friends);
		
		Person p2=person.clone();
		
		System.out.println("person:"+person.getList());
		System.out.println("p2:"+p2.getList());
		
		friends.add("3333");
		person.setList(friends);
		
		System.out.println("person:"+person.getList());
		System.out.println("p2:"+p2.getList());
		
	}
}

```



### 3.3、原型模式应用场景

1. 在创建对象的时候，我们不只是希望被创建的对象继承其基类的基本结构，还希望继承原型对象的数据。
2. 希望对目标对象的修改不影响既有的原型对象。（深度克隆的时候可以完全互不影响）
3. 隐藏克隆的操作细节。很多时候，对对象本身的克隆需要涉及到类本身的数据细节。



## 4、建造者模式

>Builder模式也叫建造者模式或者生成器模式，是由GoF提出的23种设计模式中的一种。
>
>Builder模式是一种对象创建型模式之一，用来隐藏复合对象的创建过程，它把复合对象的创建过程加以抽象，通过子类继承或者重载的方式，动态的创建具有复合属性的对象。

### 4.1、创建步骤：

更新步骤：

- 由自己来执行(set)
- 由工程队来做(builder)
- 创建一个设计者，来执行工程队的任务（Director）

1. 创建基础模型
2. 创建工程队
3. 创建设计类
4. 创建建造者
5. 测试

创建基础模型House.java

```java

public class House {

	private String floor;
	
	private String wall;
	
	private String housetop;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getWall() {
		return wall;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	public String getHousetop() {
		return housetop;
	}

	public void setHousetop(String housetop) {
		this.housetop = housetop;
	}
	
}

```



创建建造者Housebuilder.java

```java

/**
 * 工程队
 * @author LiNian
 *
 */
public interface HouseBuilder {

	public void makeFloor();
	public void makeWall();
	public void makeHouseTop();
	public House getHouse();
}

```



创建工程队GongyuBuilder.java

```java

public class GongyuBuilder implements HouseBuilder{

	House house=new House();
	@Override
	public void makeFloor() {
		// TODO Auto-generated method stub
		house.setFloor("公寓--地板");
	}

	@Override
	public void makeWall() {
		// TODO Auto-generated method stub
		house.setWall("公寓--墙");
	}

	@Override
	public void makeHouseTop() {
		// TODO Auto-generated method stub
		house.setHousetop("公寓--顶");
	}

	@Override
	public House getHouse() {
		// TODO Auto-generated method stub
		return house;
	}

}

```



创建设计类HouseDirector.java

```java

public class HouseDirector {

	private HouseBuilder builder;
	
	public HouseDirector(HouseBuilder builder){
		this.builder=builder;
	}
	
	public void makeHouse(){
		builder.makeHouseTop();
		builder.makeFloor();
		builder.makeWall();
	}
}

```

测试

```java

public class MainClass {

	public static void main(String[] args) {
	
		//创建一个工程队，
		HouseBuilder houseBuilder=new GongyuBuilder();
		//创建一个设计者，由设计师来执行工程队任务
		HouseDirector hDirector=new HouseDirector(houseBuilder);
		hDirector.makeHouse();
		
		House house=houseBuilder.getHouse();
		
		System.out.println(house.getFloor());
		
	}
}

```

### 4.2、创建者应用场景

1. 对象的创建：Builder模式是为对象的创建而设计的模式。

2. 创建的是一个复合的对象：被创建的 对象为了具有复合属性的复合对象

3. 关注对象创建的各部分的创建过程：不同的工厂（这里指builder生成器）对产品属性有不同的创建方法。

   

##5、装饰模式

简介：装饰（Decorator）模式又叫包装模式。通过对客户端透明的方式来扩展对象的功能，是继承关系的一种替换方案。

通过传参，然后通过装饰类获取参数对象，在装饰类中创建新的方法，让对象去掉用，达到增加功能的效果。（与继承有类似）。

装饰模式的角色和职责：

>1. 抽象组件角色：一个抽象接口，是被装饰类和装饰类的父接口。
>2. 具体组件角色：为抽象组件的实现类。
>3. 抽象装饰角色：包含一个组件的引用，并定义了与抽象组件一致的接口
>4. 具体装饰角色：为抽象装饰角色的实现类。负责具体的装饰。
>
>



## 6、策略模式

Strategy模式也叫策略模式是行为模式之一，它对一系列的算法加以封装，为所有算法定义一个抽象的算法接口，并通过继承该抽象算法接口对所有的算法加以封装和实现，具体的算法选择交由客户端决定（策略）。strategy模式主要用于平滑的处理算法的切换。 

策略模式的缺点：

>1. 客户端必须知道所有的策略类，并知道他们之间的区别，自己决定调用哪个策略类。换而言之，策略模式只适合客户端知道所有的算法和行为的情况。
>2. 策略模式造成很多的策略类。有时候可以通过把依赖于环境的状态保存到客户端里面，而将策略类设计成可共享的，这样策略类实例可以被不同的客户端使用。换言之，可以使用享元模式来减少对象的数量。
>
>

步骤：

1. 创建算法接口Strategy.java

2. 创建实现类md5  mds

3. 创建封装类Context,让客户通过参数选择实现类

   Strategy.java

   ```java
   
   public interface Strategy {
   
   	//加密
   	public void encrpty();
   }
   
   ```

   MD5与mds

```java

public class MD5Strategy implements Strategy{

	@Override
	public void encrpty() {
		// TODO Auto-generated method stub
		System.out.println("执行md5加密");
	}

}

public class MDSStrategy implements Strategy{

	@Override
	public void encrpty() {
		// TODO Auto-generated method stub
		System.out.println("执行mds加密");
	}
}

```

创建封装类context.java

```java

public class Context {

	private Strategy strategy;
	
	public Context (Strategy strategy) {
		this.strategy=strategy;
	}
	
	public void encrpty(){
		this.strategy.encrpty();
	}
}

```

**策略模式与装饰模式的区别是：装饰模式不去实现统一的接口。**



## 7、观察者模式

Observer模式是行为模式之一，它的作用是当一个对象的状态发生变化是，能够自动通知其他的关联对象，自动的刷新对象状态。

Observer模式提供给关联对象一种同步通信的手段，使某个对象与依赖它的对象之间保持状态同步。



观察者模式的角色与责任

1. Subject(被观察者)

   被观察的对象。当需要被观察的状态发生变化时，需要通知队列中所有观察者对象。Subject需要维持（添加、删除、通知）一个观察值对象的队列列表。

2. ConcreteSubject

   被观察者的具体实现。包含一些基本的属性状态及其他操作。

3. Observer(观察者)

   接口或抽象类，当subject的状态发生变化时，observer对象将通过一个callback函数得到通知。

4. ConcerteObserver

   观察者的具体实现。得到通知后将完成一些具体的业务逻辑处理。



步骤：

1. 创建被观察者person.java,必须继承Observerable类,并且其属性值发生变化时，		notifyObservers();方法执行通知观察者。
2. 创建观察者MyObserver.java;必须实现Observer接口

注意：必须通过调用this.changed()方法来通知虚拟机，属性值发生了改变，然后执行notifyObservers();**它还可以穿一个对象进入，供回调的时候获取信息数据。**



person.java

```java
import java.util.Observable;

/**
 * 被观察者必须继承Observable
 * @author LiNian
 *
 */
public class Person extends Observable{

	
	private String name;
	private int age;
	private String sex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		//测试对象属性值是否改变
//		System.out.println(this.hasChanged());
		//设置值已经改变，相当于通知虚拟机
		this.setChanged();
		this.notifyObservers();
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
		this.setChanged();
		this.notifyObservers();
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
		this.setChanged();
		this.notifyObservers();
	}
}

```

观察者：MyObserver.java

```java
import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer{

	//方法回调
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("被观察的对象发生了变化");
	}

}

```

测试：

test.java

```java

public class MainClass {
	public static void main(String[] args) {
        Person person=new Person();
        
        //注册观察者
        person.addObserver(new MyObserver());
        person.addObserver(new MyObserver());
        //删除观察者
        //person.deleteObservers();
        person.setName("llll");
        person.setAge(12);
        person.setSex("男");
        
	}
}

```

### 7.2、观察者模式的典型应用

Observer模式的典型应用

>1. 侦听事件驱动程序设计中的外部事件。
>2. 侦听/监视某个对象的状态变化；
>3. 发布者/订阅者（publisher/subscriber）模型中，当一个外部事件（新的产品，消息的出现等待）被触发时，通知邮件列表中的订阅者。 
>
>

## 8、享元模式

**Flyweight模式又叫享元模式，是构造型模式之一，它通过与其他类似对象共享数据来减少内存的占有。**

享元模式的角色和职责：

>1. 抽象享元角色：
>
>   所有具体想享元类的父类，规定一些需要实现的公共接口。
>
>2. 具体享元角色
>
>   抽象享元角色的具体实现类，并实现了抽象享元角色规定的方法。
>
>3. 享元工厂角色。
>
>   负责创建和管理享元角色。



步骤：

1. 创建工厂类:MyCharacterFactory.java
2. 创建共享数据对象:MyCharacter.java
3. 测试:test.java

```java

public class MyCharacter {
	
	private char mychar;
	
	public MyCharacter(char mychar){
		this.mychar=mychar;
	}
	
	public void display(){
		System.out.println(mychar);
	}

}


import java.util.HashMap;
import java.util.Map;

public class MyCharacterFactory {

	public Map<Character, MyCharacter> pool;
	
	public MyCharacterFactory(){
		pool=new HashMap<Character, MyCharacter>();
	}
	
	public MyCharacter getMyCharacter(Character character){
		MyCharacter myCharacter=pool.get(character);
		if(myCharacter==null){
			myCharacter=new MyCharacter(character);
			pool.put(character, myCharacter);
		}
		return myCharacter;
	}
}





public class MainClass {
	public static void main(String[] args) {
		// MyCharacter myChar1=new MyCharacter('a');
		// MyCharacter myChar2=new MyCharacter('b');
		// MyCharacter myChar3=new MyCharacter('a');
		// MyCharacter myChar4=new MyCharacter('d');
		//
		// myChar1.display();
		// myChar2.display();
		// myChar3.display();
		// myChar4.display();

		// 1. 创建工厂
		MyCharacterFactory factory = new MyCharacterFactory();
		// 2.从工厂中取出相应的character
		MyCharacter myChar1 = factory.getMyCharacter('a');
		MyCharacter myChar2 = factory.getMyCharacter('b');
		MyCharacter myChar3 = factory.getMyCharacter('a');
		MyCharacter myChar4 = factory.getMyCharacter('d');
		myChar1.display();
		myChar2.display();
		myChar3.display();
		myChar4.display();
		if (myChar1 == myChar3) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
}

```







## 9、代理模式

Proxy模式又叫代理模式，是构造性设计模式之一，它可以为其它对象提供一种代理（proxy）,以控制对这个对象的访问。

所谓代理，是指具有与代理元（被代理的对象）具有相同的接口的类，客户端必须通过代理与被代理的目标类进行交互，而代理一般在交互的过程中（交互前后），进行某些特别的处理。



代理模式的角色和职责：

> 1. subject（抽象主题角色）：
>
> 真实主题与代理主题的共同接口；
>
> 2. RealSubject(真实主题角色)
>
> 定义了代理角色所代表的的真实对象。
>
> 3. Proxy(代理主题角色)：
>
> 含有对真实主题角色的引用，代理角色通常在将客户端调用传递给真实主题之前或者之后执行某些操作，而不是单纯返回真实的对象。
>
> 

### 9.1、静态代理



步骤：

1. 创建接口：subject.java
2. 创建真实对象:realSubject.java
3. 创建代理对象:proxySubject
4. 测试

```java
public interface Subject {

	public void sailBook();
}

public class RealSubject implements Subject{

	@Override
	public void sailBook() {
		// TODO Auto-generated method stub
		System.out.println("卖书");
	}
	
	

}

public class ProxySubject implements Subject{

	private RealSubject realSubject;
	
	public void setRealSubject(RealSubject realSubject) {
		this.realSubject = realSubject;
	}
	
	@Override
	public void sailBook() {
		// TODO Auto-generated method stub
		this.dazhe();
		this.realSubject.sailBook();
		this.give();
	}
	
	public void dazhe(){
		System.out.println("打折");
	}
	
	public void give(){
		System.out.println("赠送代金券");
	}

}



public class MainClass {

	public static void main(String[] args) {
		RealSubject realSubject=new RealSubject();
		realSubject.sailBook();
		
		ProxySubject proxySubject=new ProxySubject();
		proxySubject.setRealSubject(realSubject);
		proxySubject.sailBook();
	}  
}
 
```

### 9.2、动态代理

步骤：

1. 创建接口subject
2. 创建实现类realSubject
3. 创建代理对象MyHandle

特点：通过反射机制，进行动态代理

```java
package com.ln;

public interface Subject {

	public void sailBook();
}


package com.ln;

public class RealSubject implements Subject{

	@Override
	public void sailBook() {
		// TODO Auto-generated method stub
		System.out.println("卖书");
	}
	
	

}


package com.ln;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyHandle implements InvocationHandler{

	private RealSubject realSubject;
	
	public void setRealSubject(RealSubject realSubject) {
		this.realSubject = realSubject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object result=null;
		dazhe();
		result=method.invoke(realSubject, args);
		give();
		
		return result;
	}

    public void dazhe(){
    	System.out.println("打折");
    }
    public void give(){
    	System.out.println("赠送代金券");
    }
}




package com.ln;


import java.lang.reflect.Proxy;

public class MainClass {
	public static void main(String[] args) {
        RealSubject realSubject=new RealSubject();
        
        MyHandle myHandle=new MyHandle();
        myHandle.setRealSubject(realSubject);
        
        
        Subject proxySubject=(Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), realSubject.getClass().getInterfaces(), myHandle);
        proxySubject.sailBook();
	}
}

```







## 10、外观模式

Facade模式也叫外观模式。Facade模式为一组具有类似功能的类群，比如类库，子系统等待，提供一个一致的简单的界面。这个一致的简单的界面被称为facade.



外观模式的角色和职责：

facade：为调用定义简单的调用接口。

clients:调用者。通过Facade接口调用提供某功能的内部类群。

packages:功能提供者。提供功能的类群（模块或者子系统）

步骤：

1. 创建功能模块A、B
2. 创建外观类
3. 测试

```java
/**
 * A子系统
 * @author LiNian
 *
 */
public class SystemA {

	public void doSomething(){
		System.out.println("实现A子系统功能");
	}
}

/**
 * B子系统
 * @author LiNian
 *
 */
public class SystemB {

	public void doSomething(){
		System.out.println("实现B子系统功能");
	}
}



public class Facade {

	private SystemA systemA;
	private SystemB systemB;
	
    public Facade(){
    	systemA=new SystemA();
    	systemB=new SystemB();		
    }
    
    public void doSomething(){
    	this.systemA.doSomething();
    	this.systemB.doSomething();
    }
}



public class MainClass {
	public static void main(String[] args) {
       Facade facade=new Facade();
       facade.doSomething();
	}
}

```



## 11、组合模式

Composite模式也叫组合模式，是构造型的设计模式之一。通过递归调用手段来构造树形的对象结构，并可以通过一个对象来访问整个对象树。

组合模型的角色和职责

1. Component(树形结构的 节点抽象)：

   - 为所有的对象定义同一的接口（公共属性，行为等的定义）
   - 提供管理子节点对象的接口方法
   - （可选）提供管理父节点对象的接口方法

2. leaf（树形结构的叶节点）

   component的实现子类

3. composite(树形结构的枝节点)

​       component的实现子类

步骤：

1. 创建接口文件IFile，定义共有属性
2. 创建数的枝文件，文件夹
3. 创建普通文件

```java
import java.util.List;

/**
 * 文件节点抽象(是文件和目录的父类)
 * @author LiNian
 *
 */
public interface IFile {
    //显示文件或者文件夹的名称
    public void display();
    
    public boolean add(IFile file);
    
    public boolean remove(IFile file);
    
    public List<IFile> getChild();
    
}



import java.util.ArrayList;
import java.util.List;

public class Folder implements IFile {
    private String name;
    private List<IFile> children;
	
    public Folder(String name){
    	this.name=name;
    	children=new ArrayList<IFile>();
    }
	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(name);
	}

	
	@Override
	public List<IFile> getChild() {
		// TODO Auto-generated method stub
		return children;
	}
	@Override
	public boolean add(IFile file) {
		// TODO Auto-generated method stub
		
		return children.add(file);
	}
	@Override
	public boolean remove(IFile file) {
		// TODO Auto-generated method stub
		return children.remove(file);
	}

}



import java.util.List;

public class File implements IFile{
    private String name;
	
    public File(String name){
    	this.name=name;
    }
    
	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(name);
	}



	@Override
	public List<IFile> getChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(IFile file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(IFile file) {
		// TODO Auto-generated method stub
		return false;
	}

}



import java.util.List;

public class MainClass {
	public static void main(String[] args) {
        //C盘
		Folder rootFolder=new Folder("C:");
		
		//创建目录
		Folder folder=new Folder("folder");
		
		//创建文件
		File file=new File("file.txt");
		
		rootFolder.add(folder);
		rootFolder.add(file);
		
		//创建新的子目录
		Folder folder2=new Folder("folder2");
		File file2=new File("file2.txt");
		folder.add(folder2);
		folder.add(file2);
		
		displayTree(rootFolder);
	}
	
	
	public static void displayTree(IFile rootFolder){
		//显示自身的名称
		rootFolder.display();
		//获得子树
		List<IFile> children=rootFolder.getChild();
		//遍历子树
		for (IFile file:children) {
			if(file instanceof File){
				file.display();
			}else{
				displayTree(file);
			}
		}
	}
}

```



## 12、桥接模式

Bridge模式又叫桥接模式。是构造型模式之一。Bridge模式基于类的最小设计原则，通过使用封装，聚合以及继承等行为来让不同的类承担不同的责任。

特点：把抽象（abstraction）与行为实现（implementation）分离开来，从而可以保持各部分的独立性以及应对他们的功能扩展。

步骤：

1. 创建同一接口Engine
2. 创建接口的实现类Engine2000  Engine2200
3. 创建行为抽象类以及实现类Car

```java
package com.ln.eg3;

public interface Engine {

	public void installEngine();
}


package com.ln.eg3;

public class Engine2000 implements Engine{

	@Override
	public void installEngine() {
		// TODO Auto-generated method stub
		System.out.println("安装2000cc发送机");
	}

}


package com.ln.eg3;

public class Engine2200 implements Engine{

	@Override
	public void installEngine() {
		// TODO Auto-generated method stub
		System.out.println("安装2200cc发送机");
	}

}

```

创建行为类

```java
package com.ln.eg3;

public abstract class Car {

	private Engine engine;
	
	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Car(Engine engine){
		this.engine=engine;
	}
	
	public abstract void installEngine();
}


package com.ln.eg3;

public class Bus extends Car {

	
	public Bus(Engine engine){
		super(engine);
	}
	
	@Override
	public void installEngine() {
		// TODO Auto-generated method stub
		System.out.print("Bus安装：");
		this.getEngine().installEngine();
	}

}


package com.ln.eg3;

public class MainClass {
	public static void main(String[] args) {

		Engine engine2000=new Engine2000();
        Engine engine2200=new Engine2200();
        
        Car car1=new Bus(engine2200);
        car1.installEngine();
	}
}


```



## 13、适配器模式

Adapter模式也叫适配器模式，是构造型模式之一，通过Adpater模式可以改变已有类（或外部类）的接口形式。

适配器的应用场景：

Adapter模式通过定义一个新的接口（对要实现的功能加以抽象），和一个实现该接口的adapter(适配器)类来透明的调用外部组件。

适配器模式的结构：

1. 通过继承实现Adapter
2. 通过委让实现Adapter

```java
继承
public class Current {

	public void user220V(){
		System.out.println("使用220v电压");
		
	}
}

public class Adapter extends Current{
	
	
	public void user18v(){
		System.out.println("使用适配器");
		this.user220V();
	}

}


委让模式

public class Adapter2{
	
	private Current current;
	
	public Adapter2(Current current){
		this.current=current;
	}
	
	public void use18v(){
		System.out.println("使用适配器");
		this.current.user220V();
	}

}

public class MainClass {
	public static void main(String[] args) {
//        Current current=new Current();
//        current.user220V();
        
        //继承
        Adapter adapter=new Adapter();
        adapter.user18v();
        //委让
        Adapter2 adapter2=new Adapter2(new Current());
        adapter2.use18v();
	}
}

```



## 14、解释器模式

interpreter模式也叫解释器模式。它建立一个解释器，对于特定的计算机程序设计语言，用来解释预先定义的文法。interpreter模式是一种简单的语法解释器构架。

应用场景：

1. 当有一个语言需要解释执行，并且可将该语言的句子表示为一个抽象语法时，可使用解释器模式。

步骤：

1. 创建上下文环境
2. 创建抽象解释器和实现解释器
3. 测试

```java
/**
 * 上下文环境，用来保存文档
 * @author LiNian
 *
 */
public class Context {

	private String input;
	private int output;
	public Context(String input) {
		// TODO Auto-generated constructor stub
		this.input=input;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public int getOutput() {
		return output;
	}
	public void setOutput(int output) {
		this.output = output;
	}

}




/**
 * 抽象解释器
 * @author LiNian
 *
 */
public abstract class Expression {

	public abstract void interpret(Context context);
}




public class PlusExpression extends Expression{

	@Override
	public void interpret(Context context) {
		// TODO Auto-generated method stub
		//提示信息
		System.out.println("自动递增");
		//获取上下文环境
		String input=context.getInput();
		//进行类型装换
		int intInput=Integer.parseInt(input);
		//进行递增
		++intInput;
		//对上下文环境重新进行赋值
		context.setInput(String.valueOf(intInput));
		context.setOutput(intInput);
	}

}




public class MainClass {
	public static void main(String[] args) {
       String number="10";
       Context context=new Context(number);
       
       Expression expression=new PlusExpression();
       expression.interpret(context);
       System.out.println(context.getOutput());
	}
}

```



## 15、中介者模式

Mediator模式也叫中介者模式。在Mediator模式中，类之间的交互行为被统一放在Mediator的对象中，对象通过Mediator对象同其他对象交互，Mediator对象起着控制器的作用。

步骤：

1. 创建抽象类，定义共有的属性Person
2. 创建实现类Man,Woman,
3. 测试

```java
package com.ln.eg2;

public abstract class Persion {

	private String name;
	
	private int condition;
	
	private Mediator mediator;
	
	
	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	public Persion(String name, int condition, Mediator mediator) {
		super();
		this.name = name;
		this.condition = condition;
		this.mediator = mediator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}
	
	
	public abstract void getPartner(Persion persion);
}


package com.ln.eg2;

public class Man extends Persion{

	
	
	public Man(String name, int condition, Mediator mediator) {
		super(name, condition, mediator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getPartner(Persion persion){
		this.getMediator().setMan(this);
		this.getMediator().getPartner(persion);
	}

}


package com.ln.eg2;

public class Woman extends Persion{



	public Woman(String name, int condition, Mediator mediator) {
		super(name, condition, mediator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getPartner(Persion persion){
		this.getMediator().setWoman(this);
		this.getMediator().getPartner(persion);
	}

}



package com.ln.eg2;

public class Mediator {

	private Man man;

	private Woman woman;

	public void setMan(Man man) {
		this.man = man;
	}

	public void setWoman(Woman woman) {
		this.woman = woman;
	}

	public void getPartner(Persion persion) {
		// 将搭档设置上
		if (persion instanceof Man) {
			this.setMan((Man) persion);

		} else {
			this.setWoman((Woman) persion);
		}
		// 判断条件
		if (man == null || woman == null) {
			System.out.println("我不是同性恋");
		} else {

			if (man.getCondition() == woman.getCondition()) {
				System.out.println(man.getName() + "和" + woman.getName() + "绝配");
			} else {
				System.out.println(man.getName() + "和" + woman.getName() + "不相配");
			}
		}
	}
}


package com.ln.eg2;

public class MainClass {
	public static void main(String[] args) {
	 Mediator mediator=new Mediator(); 	
      Persion zhangsan=new Man("张三",5,mediator);
      Persion lisi=new Man("李四",5,mediator);
      Persion xiaofang=new Woman("小芳",5,mediator);
      
      zhangsan.getPartner(lisi);
      zhangsan.getPartner(xiaofang);
     
	}
}

```





## 16、职责链模式

Chain of Responsibility (CoR)模式也叫职责链模式。该模式构造一系列分别担当不同的职责的类的对象来共同完成一个任务，这些类的对象之间像链条一样紧密相连，所以被称作职责链模式。

基本条件：

> 1. 对象链的组织。需要将某任务的所有职责执行对象以链条的形式加以组织。
> 2. 消息或者请求的船体。将消息或者请求沿着对象链船体。以让处于对象链中的对象得到处理机会
> 3. 处于对象链中的对象的职责分配。不同的对象完成不同的职责。
> 4. 任务的完成。处于对象链的末尾的对象结束任务并停止消息或者请求的继续传递。

代码

```java
package com.ln.ex2;

public abstract class CarHandler {

    protected CarHandler carHandler;
    
    public CarHandler setNextHandler(CarHandler carHandler){
    	this.carHandler=carHandler;
    	return carHandler;
    }
 
	public abstract void HandlerCar();
}


package com.ln.ex2;

public class CarHeadHandler extends CarHandler {

	@Override
	public void HandlerCar() {
		// TODO Auto-generated method stub
		System.out.println("组装车头");
		if(this.carHandler!=null){
			this.carHandler.HandlerCar();
		}
	}

}


package com.ln.ex2;

public class CarBodyHandler extends CarHandler {

	@Override
	public void HandlerCar() {
		// TODO Auto-generated method stub
		System.out.println("组装车身");
		if(this.carHandler!=null){
			this.carHandler.HandlerCar();
		}
	}

}


package com.ln.ex2;

public class MainClass {

	public static void main(String[] args) {
		CarHandler headh=new CarHeadHandler();
		CarHandler bodyh=new CarBodyHandler();
		
		//组装顺序预先设置好
		headh.setNextHandler(bodyh);
		
		//调用职责链的链头执行任务，完成操作
		headh.HandlerCar();
		
	}
}

```



## 17、迭代模式

### 17.1、简介

Iterator模式也叫迭代模式。是行为模式之一，他把对容器中包含的内部对象的访问委让给外部类，使用Iterator（遍历）按顺序进行遍历访问的设计模式。

### 17.2、不使用迭代模式的应用：



在应用iterator模式之前，首先应该明白iterator模式用来解决什么问题。或者说不使用iterator模式，会存在什么问题？

> 1. 由容器自己实现顺序遍历。直接在容器类里直接添加顺序遍历方法。
> 2. 让调用者自己实现遍历。直接暴露数据细节给外部。

不使用迭代模式有什么缺点：

> 1. 容器类承担了太多功能：一方面需要提供添加删除等本身应有的功能；另一方面还需要提供遍历访问功能。
> 2. 往往容器在遍历的过程中，需要保存遍历状态，当跟元素的添加删除等功能夹杂在一起，很容易引起混乱和程序的运行错误等。
>
> 

### 17.3、使用迭代模式的应用：



Iterator模式就是为了有效的处理按顺序进行遍历访问的一种设计模式，简单的说，Iterator模式提供一种有效的方法，可以屏蔽聚焦对象集合的容器类的实现细节，而能对容器内包含的对象元素按顺序进行有效的遍历访问。

> 使用时应满足的条件：
>
> 1. 访问容器内包含的内部对象
> 2. 按顺序访问

### 17.4、迭代器模式的角色和职责

> Iterator(迭代器接口)：
>
> 该接口必须定义实现迭代功能的最小定义方法集；比如：提供hashNext()和next()等；
>
> ConcreteIterator（迭代器的实现类）
>
> 迭代器接口Iterator的实现类。可以根据具体情况加以实现。
>
> Aggregate（容器接口）
>
> 定义基本功能以及提供类似Iterator iterator()的方法
>
> concreteAggregate(容器实现类)：
>
> 容器接口的实现类。必须实现iterator iterator()方法。
>
> 

### 17.5、迭代模式的优点：

1. 实现功能分离，简化容器接口。让容器只实现本身的基本功能，把迭代功能为让给外部类实现，符合类的设计原则。
2. 隐藏容器的实现细节。
3. 为容器或其子容器提供了一个统一接口，一方面方便调用：另一方便使得调用者不必关注迭代器的实现细节。
4. 可以为容器或者其子容器实现不同的迭代方法或多个迭代方法。

测试代码：

```java
public class Book {

	public Book(String name, String price, int iSBN) {
		super();
		this.name = name;
		this.price = price;
		ISBN = iSBN;
	}
	private String name;
	private String price;
	private int ISBN;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public void display(){
		System.out.println("ISBN="+ISBN+",name="+name+",price="+price);
	}
}

import java.util.ArrayList;
import java.util.List;

public class BookList {

	private List<Book> booklist;
	private int index;
	

	public BookList() {
		// TODO Auto-generated constructor stub
		this.booklist=new ArrayList<Book>();
	}

	public void addBook(Book book){
		booklist.add(book);
	}
	
	public void deleteBook(Book book){
		int bookIndex=booklist.indexOf(book);
		booklist.remove(bookIndex);
	}
/*	
	//判断是否有下一本数
	public boolean hashNext(){
		if(index>=booklist.size()){
			return false;
		}
		return true;
	}
	//获得下一本数
	public Book getNext(){
		return booklist.get(index++);
	}*/
	
	//第二种方式，客户自己遍历
	public List<Book> getBookList(){
		return booklist;
	}
	
}


import java.util.List;

public class MainClass {

	public static void main(String[] args) {
		
		BookList bookList=new BookList();
		
		Book book1=new Book("010203","java编程思想",90);
		Book book2=new Book("010204","java从入门到精通",60);
		
		bookList.addBook(book1);
		bookList.addBook(book2);
		//第一种方式
//		while(bookList.hashNext()){
//			Book book=bookList.getNext();
//			book.display();
//		}
		
		//第二种方式
		List<Book> bookDataList=bookList.getBookList();
		for (int i = 0; i < bookDataList.size(); i++) {
			Book book=bookDataList.get(i);
			book.display();
		}
		
	}
}

```

遍历模式代码：

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookList {

	private List<Book> booklist;
	private int index;
	
	private Iterator iterator;
	

	public BookList() {
		// TODO Auto-generated constructor stub
		this.booklist=new ArrayList<Book>();
	}

	public void addBook(Book book){
		booklist.add(book);
	}
	
	public void deleteBook(Book book){
		int bookIndex=booklist.indexOf(book);
		booklist.remove(bookIndex);
	}
/*	
	//判断是否有下一本数
	public boolean hashNext(){
		if(index>=booklist.size()){
			return false;
		}
		return true;
	}
	//获得下一本数
	public Book getNext(){
		return booklist.get(index++);
	}*/
	
	//第二种方式，客户自己遍历
//	public List<Book> getBookList(){
//		return booklist;
//	}
	
	// 使用接口实现
	public Iterator iterator(){
		return new Itr();
	}	
	
	// 通过内部类实现，因为需要内部类的信息数据
	private class Itr implements Iterator{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if(index>=booklist.size()){
				return false;
			}
			return true;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return booklist.get(index++);
		}
		
	}

	
}


import java.util.Iterator;
import java.util.List;

public class MainClass {

	public static void main(String[] args) {
		
		BookList bookList=new BookList();
		
		Book book1=new Book("010203","java编程思想",90);
		Book book2=new Book("010204","java从入门到精通",60);
		
		bookList.addBook(book1);
		bookList.addBook(book2);
		//第一种方式
//		while(bookList.hashNext()){
//			Book book=bookList.getNext();
//			book.display();
//		}
		
		//第二种方式
//		List<Book> bookDataList=bookList.getBookList();
//		for (int i = 0; i < bookDataList.size(); i++) {
//			Book book=bookDataList.get(i);
//			book.display();
//		}
		
		Iterator iter=bookList.iterator();
		while(iter.hasNext()){
			Book book=(Book) iter.next();
			book.display();
		}
		
	}
}

```



## 18、模板方法模式

### 1、定义

Template Method模式也叫模板方法模式。是行为模式之一，它把具有特定步骤算法中的某些必要的处理委让给抽象方法，通过子类继承对抽象方法的不同实现改变整个算法的行为。

### 2、应用场景

> 1. 具有统一的操作步骤或者操作过程
> 2. 具有不同的操作细节
> 3. 存在多个具有相同操作步骤的应用场景，但某些具体的操作细节却各不相同。

代码：

```java
/**
 * 组装车
 * @author LiNian
 *
 */
public abstract class MakeCar {

	public abstract void makeHead(); 
	public abstract void makeBody(); 
	public abstract void makeTail(); 
	 
	// 创建模板方法
	public void make(){
		this.makeHead();
		this.makeBody();
		this.makeTail();
	}
}



public class MakeBus extends MakeCar{

	@Override
	public void makeHead() {
		System.out.println("bus:组装车头");
		
	}

	@Override
	public void makeBody() {
		System.out.println("bus:组装车身");
		
	}

	@Override
	public void makeTail() {
		
		System.out.println("bus:组装车尾");
	}

}


public class MakeJeep extends MakeCar{

	@Override
	public void makeHead() {
		System.out.println("Jeep:组装车头");
		
	}

	@Override
	public void makeBody() {
		System.out.println("Jeep:组装车身");
		
	}

	@Override
	public void makeTail() {
		
		System.out.println("Jeep:组装车尾");
	}

}



public class MainClass {
	public static void main(String[] args) {
       MakeCar bus=new MakeBus();
//       bus.makeHead();
//       bus.makeBody();
//       bus.makeTail();
       
       
       MakeCar jeep=new MakeJeep();
       //抽象方法，关注细节，但是细节是由子类实现的，每个子类实现的细节都不相同。
//       jeep.makeHead();
//       jeep.makeBody();
//       jeep.makeTail();
       //调用模板方法，关注步骤
       jeep.make();
	}
}


```



## 19、备忘录模式

### 19.1、定义

Memento模式也叫备忘录模式。是行为模式之一。作用是保存对象的内部装填，并在需要的时候（undo/rollback）恢复对象的以前状态。

### 19.2、应用场景：



> 1. 一个类需要保存它的对象状态（相当于originator角色）
> 2. 设计一个类，该类只是用来保存上述对象的状态（相当于Memento角色）
> 3. 需要的时候，caretake角色要求originator返回一个memento并加以保存。
> 4. undo或rollback操作时，通过caretaker保存的memento恢复originator对象的状态。
>
> 

### 19.3、备忘录模式的角色和职责

> 1. Originator(愿生者)
>
> 需要被保存状态以便恢复的那个对象
>
> 2. Memento（备忘录）
>
> 该对象有Oringinator创建，主要用来保存originator的内部状态。
>
> 3. caretaker(管理者)
>
> 负责在适当的时间保存、恢复originator对象的状态。
>
> 

### 19.4、实现步骤：

步骤：

1. 创建原始类person.java
2. 创建备份类Memento.java
3. 测试

```java
package com.ln.ex2;

public class Person {

	private String name;
	private String sex;
	private int age;

	public Person() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person(String name, String sex, int age) {

		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	// 创建一个备份
	public Memento createMemento() {
		return new Memento(name, sex, age);
	}

	// 恢复备份数据，还原
	public void setMemento(Memento memento) {
		this.name = memento.getName();
		this.age = memento.getAge();
		this.sex = memento.getSex();
	}
}



package com.ln.ex2;

public class Memento {
	private String name;
	private String sex;
	private int age;
	
	

	public Memento(String name, String sex, int age) {
		
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}


package com.ln.ex2;

/**
 * 管理角色
 * @author LiNian
 *
 */
public class Caretaker {

	private Memento memento;

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}
	
}


package com.ln.ex2;

public class MainClass {
	public static void main(String[] args) {

		Person person = new Person("张三", "男", 12);

		// 保存内部状态
//		Memento memento = person.createMemento();
		Caretaker caretaker=new Caretaker();
		caretaker.setMemento(person.createMemento());
		System.out.println(person.toString());

		// 修改
		person.setAge(22);
		System.out.println(person.toString());

		// 回滚，还原
		person.setMemento(caretaker.getMemento());
		System.out.println(person.toString());

	}
}

```





## 20、状态模式

### 1、定义

state模式也叫状态模式，是行为模式的一种。state模式允许通过改变对象的内部状态而改变对象的行为，这个对象表现得好像修改了它的类一样。

### 2、应用场景

状态模式主要用于解决当控制一个对象状态转换的条件表达式过于复杂时的情况。把状态的判断逻辑转移到表现不同状态的一系列类当中，可以把复杂的判断逻辑简化。

### 3、设计步骤

代码：

```java
package com.ln.ex3;

public class Person {

	

	
	private int hour;
	
	private State state;
	
	public Person(){
		this.state=new MState();
	}
	
	public void doSomething(){
//		if(hour==7){
//			state=new MState();		
//		}else if(hour==12){
//			state=new LState();
//		}else{
//			state=new NoState();			
//		}
		
	    state.doSomething(this);
	    state=new MState();
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

package com.ln.ex3;

public abstract class State {
  public abstract void doSomething(Person person);
}



package com.ln.ex3;

public class MState extends State {

	@Override
	public void doSomething(Person person) {
		if (person.getHour() == 7) {
			System.out.println("吃早餐");
		} else {
			person.setState(new LState());
			person.doSomething();
		}

	}

}


package com.ln.ex3;

public class LState extends State {

	@Override
	public void doSomething(Person person) {
		if (person.getHour() == 12) {
			System.out.println("吃午餐");
		} else {
			person.setState(new NoState());
			person.doSomething();
		}

	}

}


package com.ln.ex3;

public class NoState extends State {

	@Override
	public void doSomething(Person person) {
		// TODO Auto-generated method stub
		System.out.println(person.getHour() + "未定义");
	}

}


package com.ln.ex3;

public class MainClass {
	public static void main(String[] args) {
     
	  Person person=new Person();
      person.setHour(7);
      person.doSomething();
      
      person.setHour(12);
      person.doSomething();
      
      person.setHour(18);
      person.doSomething();
      
      person.setHour(12);
      person.doSomething();
	}
}



```



## 21、命令模式

### 1、定义

Command模式也叫命令模式，是行为模式之一。Command模式通过被称为Command的类封装了对目标对象的调用行为以及调用参数。

### 2、应用场景

一般场景调用：

- 创建目标对象实例
- 设置调用参数
- 调用目标对象的方法

命令模式调用：

- 使用Command类对该调用加以封装，便于功能的再利用。
- 调用前后需要对调用参数进行某些处理
- 调用前后需要进行某些额外的处理，比如日志、缓存、记录历史操作等。

### 3、命令模式的角色和职责

Command

​    Command抽象类

ConcreteCommand

   Command的具体实现类

Receiver

   需要被调用的目标对象

Invorker

   通过执行Invorker执行Command对象



### 4、设计步骤

```java
package com.ln.ex3;

/**
 * 小商贩
 * @author LiNian
 *
 */
public class Peddler {

	public void sailApple(){
		System.out.println("卖苹果");
	}
	public void sailBanana(){
		System.out.println("卖香蕉");
	}
}



package com.ln.ex3;

public abstract class Command {

	private Peddler peddler;

	public Command(Peddler peddler) {
		
		this.peddler = peddler;
	}

	public Peddler getPeddler() {
		return peddler;
	}

	public void setPeddler(Peddler peddler) {
		this.peddler = peddler;
	}

	public abstract void sail();

}




package com.ln.ex3;

public class AppleCommand extends Command{

	public AppleCommand(Peddler peddler) {
		super(peddler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sail() {
		this.getPeddler().sailApple();
	}

}


package com.ln.ex3;

public class BananaCommand extends Command{

	public BananaCommand(Peddler peddler) {
		super(peddler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sail() {
		this.getPeddler().sailBanana();
	}

}


package com.ln.ex3;

import java.util.ArrayList;
import java.util.List;

public class Waiter {
	
	private List<Command> commands=new ArrayList<Command>();
	private Command command;



	public void setOrder(Command command) {
		commands.add(command);		
	}

	
	public void removeOrder(Command command) {
		commands.remove(command);		
	}
	
	public void Invorker(){
		for(Command command:commands){
			command.sail();	
		}
		
	}

}




package com.ln.ex3;

public class MainClass {
	public static void main(String[] args) {
      Peddler peddler=new Peddler();
//      peddler.sailApple();
      Command appleCommand=new AppleCommand(peddler);
      Command bananaCommand=new BananaCommand(peddler);
      
      Waiter waiter=new Waiter();
      waiter.setOrder(appleCommand);
      waiter.setOrder(bananaCommand);
      waiter.Invorker();      
    
	}
}



```



## 22、访问者模式

### 1、定义

Visitor模式也叫访问者模式，是行为模式之一。它分离对象的数据和行为，使用Visitor模式可以不修改已有类的情况下，增加新的操作。



### 2、应用示例

如有一个公园，它有很多部分组成；该公园存在很多访问者：清洁工A负责A部分的清洁工作；清洁工B负责B部分的清洁工作，公园的管理员负责检查是否完成工作。上级领导可以视察公园等。对于同一个公园，不同的访问者有不同的行为操作。而且访问者的种类也可能需要根据时间的推移而改变（行为的扩展性）。

根据软件的设计开闭原则（对修改关闭，对扩展开放），如何去实现？

### 3、访问者模式的角色和职责

1. 访问者角色（Visitor）:Visitor

   为该对象结构中具体元素角色声明一个访问操作接口。该操作接口的名字和参数的标识了发送者访问请求给具体访问者的具体元素角色。这样访问者可以通过该元素角色的特定接口直接访问它。

2. 具体访问者角色（Concrete visistor）:VistitorA、VisitorB

   实现每个由访问者角色（Visitor）声明的操作。

3. 元素角色（Element）:ParkElement

   定义一个Accept操作，它以一个访问者为参数

4. 具体元素角色（Concrete Element）:ParkA、ParKB

   实现由元素角色提供的Accept操作

5. 对象结构角色（Object Structure）:park

   这是使用访问者模式必备的角色，它要具备以下特征：

   - 能枚举它的元素；
   - 可以提供一个高层的接口以允许该访问者访问它的元素；
   - 可以是一个复合（组合模式）或者是一个集合，如一个列表或一个无序集合。

### 4、设计代码

```java
/**
 * 公园每一部分的抽象
 * 
 * @author LiNian
 *
 */
public interface ParkElement {
	// 用来接纳访问者的
	public void accept(Visitor visitor);
}



/**
 * 公园A部分
 * @author LiNian
 *
 */
public class ParkA implements ParkElement{

	public void accept(Visitor visitor) {
		
		visitor.visitor(this);
	}

}



/**
 * 公园B部分
 * 
 * @author LiNian
 *
 */
public class ParkB implements ParkElement {

	public void accept(Visitor visitor) {
     visitor.visitor(this);
	}
}



/**
 * 公园
 * @author LiNian
 *
 */
public class Park implements ParkElement{

	private ParkA parkA;
	private ParkB parkB;
	
	public void accept(Visitor visitor) {
		visitor.visitor(this);
		parkA.accept(visitor);
		parkB.accept(visitor);
	}


	public Park() {
		this.parkA=new ParkA();
		this.parkB=new ParkB();
	}

}



/**
 * 访问者
 * @author LiNian
 *
 */
public interface Visitor {
	
	public void visitor(Park park);
	public void visitor(ParkA parkA);
	public void visitor(ParkB parkB);

}



/**
 * 清洁工A负责parkA的卫生情况
 * @author LiNian
 *
 */
public class VisitorA implements Visitor{

	@Override
	public void visitor(Park park) {
		
	}

	@Override
	public void visitor(ParkA parkA) {
		System.out.println("清洁工A：完成公园A部分的卫生");
	}

	@Override
	public void visitor(ParkB parkB) {
		
	}

}



/**
 * 清洁工B负责parkB的卫生情况
 * @author LiNian
 *
 */
public class VisitorB implements Visitor{

	@Override
	public void visitor(Park park) {
		
	}

	@Override
	public void visitor(ParkA parkA) {
		
	}

	@Override
	public void visitor(ParkB parkB) {
		System.out.println("清洁工B：完成公园B部分的卫生");
	}

}


/**
 * 管理员
 * @author LiNian
 *
 */
public class VisitorManager implements Visitor {

	@Override
	public void visitor(Park park) {
		System.out.println("管理员：负责公园检查");
	}

	@Override
	public void visitor(ParkA parkA) {
		System.out.println("管理员：负责公园A部分检查");
	}

	@Override
	public void visitor(ParkB parkB) {
		System.out.println("管理员：负责公园B部分检查");
	}

}




public class MainClass {

	public static void main(String[] args) {
		Park park = new Park();

		VisitorA visitorA = new VisitorA();

		park.accept(visitorA);

		VisitorB visitorB = new VisitorB();

		park.accept(visitorB);
		
		
		VisitorManager visitorManager=new VisitorManager();
		park.accept(visitorManager); 
	}

}

```

















































