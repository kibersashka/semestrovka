package com.technokratos.oris_semectrovka_01.DAO;

import com.technokratos.oris_semectrovka_01.DAO.methods.UserDAO;
import com.technokratos.oris_semectrovka_01.connection.DBConnection;
import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * сохранить пользователя - регистарция
 * проверить по логину паролю - вход
 * обновить
 * удалить
 *
 */
//TODO rollback!!! + extract
public class UserDAOImpl implements UserDAO {


    @Override
    public void save(User user) throws SQLException {
        //добавляется !!

        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        System.out.println("save user000000");

        String sql = "INSERT INTO users (id, login, password, name, email) VALUES (?, ?, ?, ?, ?)";


        PreparedStatement preparedStatement = connection.prepareStatement("select id from nextval('seq_users') as id");

        ResultSet resultSet = preparedStatement.executeQuery();
        //находим айди из последовательности, чтобы автоматом вставить и задачи
        Long id = null;
        if(resultSet.next()) {
            id = resultSet.getLong("id");
        }
        if(id != null) {
            user.setId(id);
        } else {

            throw new SQLException("not add user");
        }
        System.out.println(user);
        resultSet.close();
        preparedStatement.close();

        PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
        //вставка данных в sql
        insertDataOfUserInSqlParametrs(user, preparedStatement1);
        System.out.println("nnnnn");

        int resultSet1 = preparedStatement1.executeUpdate();
        System.out.println(resultSet1 > 0);
        preparedStatement1.close();
        System.out.println(resultSet1);


        connection.commit();
        connection.close();

    }

    @Override
    public boolean update(User user) throws SQLException {

        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        //есть проблема, которая решается уникальностью логина и пароля при фильтре
        PreparedStatement preparedStatement = connection.prepareStatement("update users set password = ?, name = ?, email = ? where login = ?");
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getLogin());


        int resultSet = preparedStatement.executeUpdate();

        preparedStatement.close();

        connection.commit();
        connection.close();

        return resultSet > 0;
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "delete from users where login = ? and password = ?";

        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        int resultSet = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.commit();
        connection.close();

    }

    @Override
    public Optional<User> find(User user) throws SQLException {

        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        String sql = "SELECT * FROM users WHERE login = ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getLogin());


        ResultSet resultSet = preparedStatement.executeQuery();


        if(resultSet.next()) {
            User userEntity = new User();
            userEntity.setId(resultSet.getLong("id"));
            userEntity.setLogin(resultSet.getString("login"));
            userEntity.setPassword(resultSet.getString("password"));
            userEntity.setName(resultSet.getString("name"));
            userEntity.setEmail(resultSet.getString("email"));
            return Optional.of(userEntity);
        }

        preparedStatement.close();
        resultSet.close();

        connection.commit();
        connection.close();

        return Optional.empty();
    }

    @Override
    public List<Task> findAllTaskForUser(User user) throws SQLException {
        String sql = "select title,\n" +
                "       description,\n" +
                "       date_create,\n" +
                "       date_end,\n" +
                "       priority,\n" +
                "       status\n" +
                "from task t\n" +
                "join users u on t.users_id = u.id\n" +
                "join task_attachment ta on t.id = ta.task_id;";
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setTitle(resultSet.getString("title"));
            task.setDescription(resultSet.getString("description"));
            task.setDate_create(resultSet.getDate("date_create"));
            task.setDate_end(resultSet.getDate("date_end"));
            task.setPriority(resultSet.getInt("priority"));
            task.setStatus(resultSet.getString("status"));
            tasks.add(task);
        }
        preparedStatement.close();
        resultSet.close();
        connection.commit();
        connection.close();

        return tasks;
    }

    private static void insertDataOfUserInSqlParametrs(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getName());
        preparedStatement.setString(5, user.getEmail());
    }


}
