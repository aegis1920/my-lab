package springbook.user;

import java.sql.SQLException;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setId("hello");
        user.setName("빙봉");
        user.setPassword("bingbong");

        userDao.add(user);

        System.out.println(user.getId() + " 등록성공");

        User user1 = userDao.get(user.getId());

        System.out.println(user1.getName() + " 조회 성공");


    }
}
