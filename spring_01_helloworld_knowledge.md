#Spring HelloWorld

> Spring是一个IOC（DI）和AOP容器框架
>
> - 轻量级
>
>   ​Spring是非侵入性的，基于Spring开发的应用中的对象可以不依赖于Spring的API
>
> - 依赖注入（DI---Dependency Injection, 或者叫 IOC--Inversion of Control）
> - 面向切面编程（AOP---Aspect Oriented Programming）
> - 容器
>
>   ​Spring是一个容器，因为它包含并且管理应用对象的生命周期
>
> - 框架
>
>   ​Spring实现了使用简单的组件配置组合成一个复杂的应用。在Spring中可以使用XML和Java注解组合这些对象
>
> - 一站式
>
>   ​在IOC和AOP的基础上，可以整合各种企业应用的开源框架和优秀的第三方类库（实际上Spring自身也提供了展现层的SpringMVC和持久层的Spring JDBC）

![Spring模块](pic/1_1.png)

一个典型的Spring项目需要创建一个或多个Bean配置文件，这些配置文件用于在Spring IOC容器里配置Bean. Bean的配置文件可以放在classpath下，也可以放在其他目录下。



## Spring中Bean的配置

### IOC & DI 概述

配置Bean

* 配置形式：基于XML文件的方式；基于注解的方式
* Bean的配置方式：通过全类名（反射）、通过工厂方法（静态工厂方法或者实例工厂方法）、通过FactoryBean
* IOC容器BeanFactory & ApplicationContext 概述
* 依赖注入的方式：属性注入；构造器注入
* 注入属性值的细节
* 自动转配
* bean之间的关系：继承；依赖
* bean的作用域：singleton；prototype；WEB环境作用域
* 使用外部属性文件
* SpEL
* IOC容器中Bean的生命周期
* Spring4.x新特性：泛型依赖注入



一、Spring容器

从Spring IOC容器读取Bean配置并创建Bean实例之前，必须对IOC容器进行初始化，只有在容器实例化后，才可以从IOC容器中获取Bean实例并使用。

Spring提供了两种类型的IOC容器的实现：

1. `BeanFactory`（接口）：IOC容器的基本实现
2. `ApplicationContext`（接口）：提供了更多高级的特性，是`BeanFactory`的子接口

其中`BeanFactory`是Spring框架的基础设施，面向Spring本身；`ApplicationContext`面向使用Spring框架的开发者，**几乎所有的应用场合都直接使用`ApplicationContext`而非底层的`BeanFactory`**。无论使用哪一个，配置文件都是相同的。

#### `ApplicationContext`

1. `ApplicationContext`接口有两个主要的实现类
   * `ClassPathXmlApplicationContext`：从类路径下加载配置文件
   * `FileSystemXmlApplicationContext`：从文件系统中加载配置文件
2. `ConfigurableApplicationContext`接口是`ApplicationContext`接口的子接口，它新增了两个主要的方法：`refresh()`和`close()`，让`ApplicationContext`具有启动、刷新和关闭上下文的功能
3. <u>`ApplicationContext`在初始化上下文时就实例化所有**单例**的`Bean`</u>
4. `WebApplicationContext`是专门为WEB而准备的，它允许从相对于WEB应用程序的根目录的路径下加载配置文件来完成初始化工作

![ApplicationContext接口的继承关系](pic/1_2.png)

#### 依赖注入的方式

1. 属性注入
2. 构造器注入
3. 工厂方法注入（很少使用，不推荐）

##### 属性注入

属性注入是通过`setter`方法注入Bean的属性值或依赖的对象。属性注入使用`<property>`元素，使用`name`属性指定Bean的属性名称，`value`属性或者`<value>`子节点指定属性值。**属性注入是实际应用中最常用的注入方式。**

```xml
<bean id="p2" class="edu.stu.domain.Person">
        <property name="id" value="22"/>
        <property name="name" value="小张"/>
        <property name="gender" value="1"/>
</bean>
```

##### 构造方法注入

通过构造方法注入Bean的属性值或依赖的对象，它保证了Bean实例在实例化后就可以使用。构造器注入在`<constructor-arg>`元素里声明属性。例如，

```java
public class Car {

    private String brand;

    private String tire;

    private Integer price;

    private Integer maxSpeed;

    public Car() {
    }

    public Car(String brand, String tire, Integer price, Integer maxSpeed) {
        this.brand = brand;
        this.tire = tire;
        this.price = price;
        this.maxSpeed = maxSpeed;
    }
}
```

```xml
<!-- 通过构造方法配置bean的属性 -->
<bean id="c1" class="edu.stu.domain.Car">
    <!-- 按照构造器中参数定义的顺序 -->
  	<constructor-arg value="Audi"/>
    <constructor-arg value="ShangHai"/>
    <constructor-arg value="500000"/>
    <constructor-arg value="220"/>
</bean>
```

##### 字面值

可用字符串表示的值，可以通过`<value>`标签或者`value`属性进行注入。其中基本数据类型及其封装类，String类型都可以采用字面值注入的方式。若字面值中包含特殊字符，可以使用`<![CDATA[XXX]]>`把字面值XXX包裹起来。

##### 引用其他的Bean

组成应用程序的Bean经常需要相互协作以完成应用程序的功能。要使Bean能够互相引用，就必须在Bean配置文件中指定对Bean的引用。

* 在Bean的配置文件中，可以通过`<ref>`标签或者`ref`属性为Bean的属性或构造器参数指定对Bean的引用。

* 也可以在属性或者构造器里包含Bean的声明，这样的Bean成为**内部Bean**。

  ​	当Bean的实例仅仅给一个特定的属性使用时，可以将其声明为内部Bean。内部Bean的声明直接包含在`<property>`或者`<constructor-arg>`元素里，不需要为其设置任何id或name属性。内部Bean不能被其他的Bean引用到。

##### null值

可以使用专用的`<null/>`元素标签为Bean的引用类型的属性注入null值。

##### 级联属性

```xml
<!--级联属性举例-->
    <bean id="p3" class="edu.stu.domain.Person">
        <constructor-arg value="George"/>
        <constructor-arg value="36"/>
        <constructor-arg ref="car1"/>
        <!--为级联属性赋值。修改George拥有的car的价格-->
        <property name="car.price" value="800000"/>
    </bean>
```

为级联属性赋值时需要注意：<u>该属性必须先初始化后才可以为其级联属性赋值，否则会有异常</u>，这点和Struts2不同。例如如下配置会报异常，

```xml
<bean id="p4" class="edu.stu.domain.Person">
    <property name="name" value="Harley"/>
    <property name="age" value="34"/>
    <!--报异常的原因：属性car还未初始化，所以不能为该属性的brand和price属性赋值-->
    <property name="car.brand" value="Volkswagen"/>
    <property name="car.price" value="230000"/>
</bean>
```

##### 集合属性

在Spring中可以通过一组内置的xml标签（例如，`<list>`，`<set>`，`<map>`）来配置集合属性。

* 配置`java.util.List`类型的属性，需要指定`<list>`标签，在该标签里可以包含一些子标签，这些子标签可以是`<value>`指定简单的字面量值，可以是`<ref>`指定对其他Bean的引用，可以是`<null/>`指定空元素，也可以是`<bean>`指定内置Bean的定义，甚至可以内嵌其他集合。
* 配置`java.util.Set`需要使用`<set>`标签，定义元素的方法与List一样。
* `java.util.Map`通过`<map>`标签定义，`<map>`标签里可以使用多个`<entry>`作为子标签，每个条目包含一个键和一个值。可以键Map的键和值作为`<entry>`的属性定义：简单的字面量值可以直接用`key`和`value`属性来定义，Bean的引用通过`key-ref`和`value-ref`属性来定义。若不在`<entry>`标签中通过属性的方式定义键值对，那么必须在该标签里通过声明子标签`<key>`来定义键。因为键和值的类型没有限制，所以可以自由地为它们指定`<value>`，`<ref>`，`<bean>`或`<null/>`元素。
* `java.util.Properties`通过`<props>`标签定义。

##### 使用`utility scheme`定义集合

* 在使用基本的集合标签定义集合时，不能将这个已经定义好的集合作为独立的Bean使用，这导致其他Bean无法引用该集合，所以无法在不同的Bean之间共享集合。

* 可以使用`util schema`里的集合标签来定义独立的集合Bean。

  ​	先在配置文件的`<beans>`根元素里添加`util schema`的定义，然后定义独立的集合Bean，例如：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:util="http://www.springframework.org/schema/util"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/util
         http://www.springframework.org/schema/util/spring-util.xsd">
      
      <!--配置单例的集合类型bean，以供其他bean进行引用-->
      <util:list id="cars1" value-type="edu.stu.domain.Car" 
                 list-class="java.util.LinkedList">
          <ref bean="car1"/>
          <ref bean="car2"/>
          <ref bean="car3"/>
          <ref bean="car4"/>
          <bean class="edu.stu.domain.Car">
              <property name="brand" value="Lexus"/>
              <property name="price" value="750000"/>
              <property name="tire" value="40"/>
              <property name="maxSpeed" value="260"/>
          </bean>
      </util:list>
      
      <bean id="c.p2" class="edu.stu.domain.collections.Person">
          <property name="name" value="George Walker Bush"/>
          <property name="age" value="73"/>
          <!--引用上面定义好的集合Bean-->
          <property name="cars" ref="cars1"/>
      </bean>
      
  </beans>
  ```

##### 使用 p 命名空间

为了简化Spring配置文件的配置，越来越多的XML文件采用属性而非子元素配置信息。Spring从2.5版本开始引入了一个新的p命名空间，可以通过`<bean>`元素的属性的方式配置Bean的属性信息，从而简化配置方式。例如，

```xml
 <!--通过 p 命名空间为bean的属性赋值，使用之前需先导入 p 命名空间的 schema-->
<bean id="c.p3" class="edu.stu.domain.collections.Person"
      p:name="Lee" p:age="35" p:cars-ref="cars1"/>
```

