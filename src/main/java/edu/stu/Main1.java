package edu.stu;

import edu.stu.domain.Car;
import edu.stu.domain.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main1 {

    // 创建Spring的IOC 容器
    // ApplicationContext 代表IOC 容器
    // ClassPathXmlApplicationContext: 是ApplicationContext 接口的实现类，该实现类从类路径下来加载配置文件
    public static final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) {

        // 通过Bean的id从IOC 容器中获取Bean的实例
        Car car1 = (Car) ctx.getBean("car1");
        System.out.println(car1);

        Car car2 = (Car) ctx.getBean("car2");
        System.out.println(car2);

        Car car3 = (Car) ctx.getBean("car3");
        System.out.println(car3);

        Car car4 = (Car) ctx.getBean("car4");
        System.out.println(car4);

        Person p1 = (Person) ctx.getBean("p1");
        System.out.println(p1);

        Person p2 = (Person) ctx.getBean("p2");
        System.out.println(p2);

        Person p3 = (Person) ctx.getBean("p3");
        System.out.println(p3);

//        Person p4 = (Person) ctx.getBean("p4");
//        System.out.println(p4);
    }


}
