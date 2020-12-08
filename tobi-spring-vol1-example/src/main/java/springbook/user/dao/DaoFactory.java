package springbook.user.dao;

public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = getConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);

        return userDao;
    }

    private NConnectionMaker getConnectionMaker() {
        return new NConnectionMaker();
    }
}
