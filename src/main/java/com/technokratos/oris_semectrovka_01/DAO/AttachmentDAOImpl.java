package com.technokratos.oris_semectrovka_01.DAO;

import com.technokratos.oris_semectrovka_01.DAO.methods.AttachmentDAO;
import com.technokratos.oris_semectrovka_01.DAO.methods.TaskDAO;
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
import java.util.Optional;

public class AttachmentDAOImpl implements AttachmentDAO {

    @Override
    public void updateAttachment(Attachments attachment) {

    }

    @Override
    public void deleteAttachment(Attachments attachment) {

    }

    @Override
    public Optional<Task> getAttachment(Long id) {
        return Optional.empty();
    }

}
