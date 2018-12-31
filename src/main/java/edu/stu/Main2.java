package edu.stu;

import edu.stu.domain.collections.DataSource;
import edu.stu.domain.collections.Human;
import edu.stu.domain.collections.Person;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class Main2 {

    private static final ApplicationContext ctx = Main1.ctx;

    public static void main(String[] args) {

        Person cp1 = (Person) ctx.getBean("c.p1");
        System.out.println(cp1);
        System.out.println("List的实现类为：" + cp1.getCars().getClass());

        Human ch1 = (Human) ctx.getBean("c.h1");
        System.out.println(ch1);
        System.out.println("Map的实现类为：" + ch1.getCars().getClass());

        DataSource dataSource = ctx.getBean(DataSource.class);
        for (Map.Entry entry : dataSource.getProperties().entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + "=" + value);
        }

        Person cp2 = (Person) ctx.getBean("c.p2");
        System.out.println(cp2);
        System.out.println("List的实现类为：" + cp2.getCars().getClass());

        Person cp3 = (Person) ctx.getBean("c.p3");
        System.out.println(cp3);

    }
}
