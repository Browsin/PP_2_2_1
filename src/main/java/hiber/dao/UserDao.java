package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {//todo: codeStyle

    void add(User user);

    List<User> listUsers();

    List<User> carByUsers(String model, int series);
}
