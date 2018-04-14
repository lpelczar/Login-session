package com.codecool.login.data;

class UserStatement {

    String selectUserByLoginAndPassword() {
        return "SELECT * FROM users WHERE login = ? AND password = ?;" ;
    }

    String selectUserById() {
        return "SELECT * FROM users WHERE userID = ?;" ;
    }
}
