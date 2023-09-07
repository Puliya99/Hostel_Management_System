package lk.ijse.hostelManagementSystem.bo;

import lk.ijse.hostelManagementSystem.dto.SuperDTO;

import java.util.List;

public interface SuperBo <T extends SuperDTO> {
    Boolean save(T entity);

    Boolean update(T entity);

    Boolean delete(String id);

    T view(String id) throws RuntimeException;

    List<T> getAll();
    String getLastId();
}
