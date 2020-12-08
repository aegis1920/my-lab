package springbook.user;

import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;
import springbook.user.dao.CountingConnectionMaker;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.NConnectionMaker;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;
import sun.jvmstat.perfdata.monitor.CountedTimerTask;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        CountingConnectionMaker countingConnectionMaker = context.getBean("connectionMaker", CountingConnectionMaker.class);

        User user = new User();
        user.setId("hello");
        user.setName("빙봉");
        user.setPassword("bingbong");

        userDao.add(user);

        System.out.println(user.getId() + " 등록성공");

        User user1 = userDao.get(user.getId());

        System.out.println(user1.getName() + " 조회 성공");

        System.out.println(countingConnectionMaker.getCounter());


    }
}
