package lk.ijse.hostelManagementSystem.bo.custom.impl;

import lk.ijse.hostelManagementSystem.bo.custom.StudentBo;
import lk.ijse.hostelManagementSystem.dao.DAOFactory;
import lk.ijse.hostelManagementSystem.dao.custom.QueryDAO;
import lk.ijse.hostelManagementSystem.dao.custom.StudentDAO;
import lk.ijse.hostelManagementSystem.dto.StudentDTO;
import lk.ijse.hostelManagementSystem.entity.Student;
import lk.ijse.hostelManagementSystem.util.Converter;
import lk.ijse.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class StudentBoImpl implements StudentBo {
    private final StudentDAO studentDao;
    private final QueryDAO queryDao;

    public StudentBoImpl() {
        studentDao = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
        queryDao = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    }

    @Override
    public Boolean save(StudentDTO entity) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            studentDao.save(Converter.getInstance().toStudentEntity(entity), session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException("Student not added");
        }
    }

    @Override
    public Boolean update(StudentDTO entity) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            studentDao.update(Converter.getInstance().toStudentEntity(entity), session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException("Student not updated");
        }
    }

    @Override
    public Boolean delete(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            studentDao.delete(id, session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException("Student not Deleted");
        }
    }

    @Override
    public StudentDTO view(String id) throws RuntimeException {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            Student student = studentDao.view(id, session);
            if (student != null) return Converter.getInstance().toStudentDto(student);
            throw new RuntimeException("Student not found");
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public List<StudentDTO> getAll() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            List<Student> studentList = studentDao.getAll(session);
            if (studentList.size() > 0) {
                return studentList.stream().map(student -> Converter.getInstance().toStudentDto(student)).collect(Collectors.toList());
            }
        }
        throw new RuntimeException("Empty Student list!");
    }

    @Override
    public String getLastId() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            String lastId = studentDao.getLastId(session);
            if (lastId == null) {
                return "IT000001";
            } else {
                String[] split = lastId.split("[I][T]");
                int lastDigits = Integer.parseInt(split[1]);
                lastDigits++;
                return (String.format("IT%06d", lastDigits));
            }
        }
    }

    @Override
    public List<StudentDTO> getUnpaidStudents() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        List<Student> unpaidStudents = queryDao.getUnpaidStudents(session);
        if (unpaidStudents.size() > 0) {
            return unpaidStudents.stream().map(student -> Converter.getInstance().toStudentDto(student)).collect(Collectors.toList());
        }
        throw new RuntimeException("No any unpaid case yet!");
    }

    @Override
    public List<StudentDTO> searchStudentByText(String text) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            List<Student> searchStudentByText = studentDao.searchStudentByText(text, session);
            if (searchStudentByText.size() > 0) {
                return searchStudentByText.stream().map(student -> Converter.getInstance().toStudentDto(student)).collect(Collectors.toList());
            }
            throw new RuntimeException("No any match found");
        }
    }
}
