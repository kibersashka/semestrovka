package com.technokratos.oris_semectrovka_01.DAO.methods;

import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TaskDAO {
    void addTask(Task task, Long user_id) throws SQLException;
    void updateTask(Task task) throws SQLException;
    void deleteTask(Task task) throws SQLException;
    Optional<Task> getTask(Long id) throws SQLException;
    List<Task> getTasksForUser(Long user_id) throws SQLException;
    //void addAttachement(Task task, Long user_id, Attachments attachment) throws SQLException;

}
