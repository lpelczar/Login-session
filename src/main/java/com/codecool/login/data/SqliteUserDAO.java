package com.codecool.login.data;

import com.codecool.login.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class SqliteUserDAO extends DbHelper implements UserDAO {

    private UserStatement userStatement = new UserStatement();
    private PreparedStatementCreator psc = new PreparedStatementCreator();

    @Override
    public User getById(int id) {
        String sqlStatement = userStatement.selectUserById();
        PreparedStatement statement = psc.getPreparedStatementBy(Collections.singletonList(id), sqlStatement);
        return getUser(statement);
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        String sqlStatement = userStatement.selectUserByLoginAndPassword();
        PreparedStatement statement = psc.getPreparedStatementBy(Arrays.asList(login, password), sqlStatement);
        return getUser(statement);
    }

    private User getUser(PreparedStatement statement) {
        User user = null;
        try {
            ResultSet resultSet = query(statement);
            while (resultSet.next())
                user = new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
        return user;
    }
}
