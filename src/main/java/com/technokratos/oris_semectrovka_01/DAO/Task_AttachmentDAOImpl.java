package com.technokratos.oris_semectrovka_01.DAO;

import com.technokratos.oris_semectrovka_01.DAO.methods.Task_Attachment;
import com.technokratos.oris_semectrovka_01.connection.DBConnection;
import com.technokratos.oris_semectrovka_01.entity.Attachments;
import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Task_AttachmentDAOImpl implements Task_Attachment {

    @Override
    public List<Attachments> getAllAttachmentsForUser(User user, Task task) throws SQLException {
        String sql = "select url,\n" +
                "       title\n" +
                "from attachment\n" +
                "join users u on attachment.user_id = u.id\n" +
                "join public.task t on u.id = t.users_id\n" +
                "where u.login = ? and \n" +
                "      u.password = ? and\n" +
                "      t.id = ?;";
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Attachments> attachments = new ArrayList<>();
        while (resultSet.next()) {
            Attachments attachment = new Attachments();
            attachment.setUrl(resultSet.getString("url"));
            attachment.setTitle(resultSet.getString("title"));
            attachments.add(attachment);
        }
        preparedStatement.close();
        resultSet.close();
        connection.commit();
        connection.close();

        return attachments;
    }
    //создать вложение
    @Override
    public void addAttachement(Task task, Long user_id, Attachments attachments) throws SQLException {
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        String sql0 = "select id from task where users_id = ? and title = ?";
        PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
        preparedStatement0.setLong(1, user_id);
        preparedStatement0.setString(2, task.getTitle());
        ResultSet resultSet0 = preparedStatement0.executeQuery();
        Long task_id = null;
        if (resultSet0.next()) {
            task_id = resultSet0.getLong("id");
        } else {
            throw new SQLException();
        }
        preparedStatement0.close();
        resultSet0.close();


        //получить айдишник нового вложения
        String sql = "select id from nextval('seq_attachment') as id";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        Long attachment_id = null;
        if (resultSet.next()) {
            attachment_id = resultSet.getLong("id");
        } else {
            throw new SQLException();
        }
        preparedStatement.close();
        resultSet.close();

        //вставить в таблицу связи
        String sql1 = "insert into task_attachment values(?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setLong(1, task_id);
        preparedStatement1.setLong(2, attachment_id);
        int res1 = preparedStatement1.executeUpdate();
        preparedStatement1.close();

        //вставить в таблицу вложений
        String sql2 = "insert into attachment values(?, ?, ?, ?)";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.setLong(1, attachment_id);
        preparedStatement2.setString(2, attachments.getUrl());
        preparedStatement2.setString(3, attachments.getTitle());
        preparedStatement2.setLong(4, user_id);
        int res2 = preparedStatement2.executeUpdate();
        preparedStatement2.close();


        connection.commit();
        connection.close();
    }
}
