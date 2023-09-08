package lk.ijse.hostelManagementSystem.dao.custom;

import lk.ijse.hostelManagementSystem.dao.SuperDAO;
import org.hibernate.Session;
import java.util.List;

public interface CrudDao <T> extends SuperDAO {
    Boolean save(T entity, Session session);

    Boolean update(T entity, Session session);

    Boolean delete(String id ,Session session);

    T view(String id,Session session);

    List<T> getAll(Session session);

    String getLastId(Session session);
}
