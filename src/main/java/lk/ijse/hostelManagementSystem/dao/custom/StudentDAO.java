package lk.ijse.hostelManagementSystem.dao.custom;

import lk.ijse.hostelManagementSystem.entity.Student;
import org.hibernate.Session;
import java.util.List;

public interface StudentDAO extends CrudDao<Student>{
    List<Student> searchStudentByText(String text, Session session);
}
