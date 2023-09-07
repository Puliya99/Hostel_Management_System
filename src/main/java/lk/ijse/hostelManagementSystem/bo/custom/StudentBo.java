package lk.ijse.hostelManagementSystem.bo.custom;

import lk.ijse.hostelManagementSystem.bo.SuperBo;
import lk.ijse.hostelManagementSystem.dto.StudentDTO;

import java.util.List;

public interface StudentBo extends SuperBo<StudentDTO> {
    List<StudentDTO> getUnpaidStudents();
    List<StudentDTO>searchStudentByText(String text);
}
