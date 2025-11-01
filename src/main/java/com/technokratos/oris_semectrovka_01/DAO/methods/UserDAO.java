package com.technokratos.oris_semectrovka_01.DAO.methods;


import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * сохранить пользователя - регистарция
 * проверить по логину паролю - вход
 * обновить
 * удалить
 *
 */
public interface UserDAO {
    void save(User user) throws SQLException;
    boolean update(User user) throws SQLException;
    void delete(User user) throws SQLException;
    Optional<User> find(User user) throws SQLException;
    List<Task> findAllTaskForUser(User user) throws SQLException;

}
