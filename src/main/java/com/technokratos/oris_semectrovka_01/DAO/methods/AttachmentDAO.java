package com.technokratos.oris_semectrovka_01.DAO.methods;

import com.technokratos.oris_semectrovka_01.entity.Attachments;
import com.technokratos.oris_semectrovka_01.entity.Task;
import com.technokratos.oris_semectrovka_01.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AttachmentDAO {
    void updateAttachment(Attachments attachment);
    void deleteAttachment(Attachments attachment);
    Optional<Task> getAttachment(Long id);
    //List<Attachments> getAllAttachmentsForUser(User user, Task task) throws SQLException;
}
