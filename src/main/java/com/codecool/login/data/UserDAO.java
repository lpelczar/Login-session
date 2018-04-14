package com.codecool.login.data;

import com.codecool.login.models.User;

public interface UserDAO {
    User getById(int id);
    User getByLoginAndPassword(String login, String password);
}
