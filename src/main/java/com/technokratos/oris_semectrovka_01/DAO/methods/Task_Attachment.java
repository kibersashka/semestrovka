package com.technokratos.oris_semectrovka_01.DAO.methods;

import com.technokratos.oris_semectrovka_01.entity.Attachments;
import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface Task_Attachment {
    List<Attachments> getAllAttachmentsForUser(User user, Task task) throws SQLException;
    void addAttachement(Task task, Long user_id, Attachments attachments) throws SQLException;
}
