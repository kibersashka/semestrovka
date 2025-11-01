package com.technokratos.oris_semectrovka_01.DAO;

import com.technokratos.oris_semectrovka_01.DAO.methods.TaskDAO;
import com.technokratos.oris_semectrovka_01.connection.DBConnection;
import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO status with chouse

//todo сделать надо через трай кетч!!
public class TaskDAOImpl implements TaskDAO {

    @Override
    public void addTask(Task task, Long user_id) throws SQLException {
        //получить айдишщник задачи из очереди
        String sql = "select id from nextval('seq_task') as id";
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        Long task_id = null;
        if (resultSet.next()) {
            task_id = resultSet.getLong("id");
        } else {
            throw new SQLException();
        }
        preparedStatement.close();
        resultSet.close();

        //вставитьь новую задачу - то что ожидается от метода
        String sql1 = "insert into task values(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

        preparedStatement1.setLong(1, task_id);
        preparedStatement1.setLong(2, user_id);
        preparedStatement1.setString(3, task.getTitle());
        preparedStatement1.setString(4, task.getDescription());
        preparedStatement1.setDate(5, (Date) task.getDate_create());
        preparedStatement1.setDate(6, (Date) task.getDate_end());
        preparedStatement1.setInt(7, task.getPriority());
        preparedStatement1.setString(8,task.getStatus());

        int res1 = preparedStatement1.executeUpdate();
        preparedStatement1.close();


        //получиться айдишник вложения из последовательности
        String sql2 = "select id from nextval('seq_attachment') as id";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        Long attachment_id = null;
        if (resultSet2.next()) {
            attachment_id = resultSet2.getLong("id");
        } else {
            throw new SQLException();
        }
        preparedStatement2.close();
        resultSet2.close();

        //создать пустое вложение, чтобы была свзять м2м
        String sql3 = "insert into attachment (id, user_id) values(?, ?)";
        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
        preparedStatement3.setLong(1, attachment_id);
        preparedStatement3.setLong(2, user_id);
        int res3 = preparedStatement3.executeUpdate();
        preparedStatement3.close();

        //вставить в таблицу свзять задачу - вложения
        String sql4 = "insert into task_attachment values(?, ?)";
        PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
        preparedStatement4.setLong(1, task_id);
        preparedStatement4.setLong(2, attachment_id);
        int res4 = preparedStatement4.executeUpdate();
        preparedStatement4.close();

        connection.commit();
        connection.close();
    }

    //обновление задачи ее полей!! не вложения
    @Override
    public void updateTask(Task task) throws SQLException {
        String sql = "update task set title = ?, description = ?, date_create = ?, date_end = ?, priority = ?, status = ? where id = ?";

        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, task.getTitle());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setDate(3, (Date) task.getDate_create());
        preparedStatement.setDate(4, (Date) task.getDate_end());
        preparedStatement.setInt(5, task.getPriority());
        preparedStatement.setLong(6, task.getId());
        preparedStatement.setString(7, task.getStatus());

        int res1 = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.commit();
        connection.close();
    }
    //удаление ТОлько задачи
    @Override
    public void deleteTask(Task task) throws SQLException {
        String sql = "delete from task where id = ?";
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, task.getId());
        int res1 = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.commit();
        connection.close();
    }

    //вернуть задачу по айди
    @Override
    public Optional<Task> getTask(Long id) throws SQLException {
        String sql = "select * from task where id = ?";
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Task task = new Task();

            task.setId(resultSet.getLong("id"));
            task.setUsers_id(resultSet.getLong("users_id"));
            task.setTitle(resultSet.getString("title"));
            task.setDescription(resultSet.getString("description"));
            task.setDate_create(resultSet.getDate("date_create"));
            task.setDate_end(resultSet.getDate("date_end"));
            task.setPriority(resultSet.getInt("priority"));
            task.setStatus(resultSet.getString("status"));

            preparedStatement.close();
            resultSet.close();

            connection.commit();
            connection.close();
            return Optional.of(task);
        }

        preparedStatement.close();
        resultSet.close();

        connection.commit();
        connection.close();

        return Optional.empty();
    }
    //все задачи!! без вложений у юзера
    @Override
    public List<Task> getTasksForUser(Long user_id) throws SQLException {
        String sql = "select " +
                "id, \n"+
                "user_id, \n" +
                "title,\n" +
                "       description,\n" +
                "       date_create,\n" +
                "       date_end,\n" +
                "       priority,\n" +
                "       status\n" +
                "from task t\n" +
                "join users u on t.users_id = u.id\n" +
                "\n" +
                "where users_id = ?\n" +
                "order by priority desc;\n";
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            task.setUsers_id(resultSet.getLong("users_id"));
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


    //TODO репозиторий такс атачмент
}
