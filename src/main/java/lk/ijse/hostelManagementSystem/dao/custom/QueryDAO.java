package lk.ijse.hostelManagementSystem.dao.custom;

import lk.ijse.hostelManagementSystem.dao.SuperDAO;
import lk.ijse.hostelManagementSystem.entity.Student;
import org.hibernate.Session;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Student> getUnpaidStudents(Session session);
}
