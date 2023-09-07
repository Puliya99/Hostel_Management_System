package lk.ijse.hostelManagementSystem.bo.custom.impl;

import lk.ijse.hostelManagementSystem.bo.custom.UserBo;
import lk.ijse.hostelManagementSystem.dao.DAOFactory;
import lk.ijse.hostelManagementSystem.dao.custom.UserDao;
import lk.ijse.hostelManagementSystem.dto.UserDTO;
import lk.ijse.hostelManagementSystem.entity.User;
import lk.ijse.hostelManagementSystem.util.Converter;
import lk.ijse.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class UserBoImpl implements UserBo {

    private final UserDao userDao;

    public UserBoImpl() {
        userDao = (UserDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    }
    @Override
    public Boolean save(UserDTO entity) throws RuntimeException{
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User user = new User();
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());

        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            userDao.save(user, session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Boolean update(UserDTO entity) throws RuntimeException{
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User user = new User();
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());

        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            userDao.update(user, session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Boolean delete(String id) throws RuntimeException{
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            userDao.delete(id, session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public UserDTO view(String id) throws RuntimeException {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User entity = userDao.view(id, session);
        if (entity != null) {
            UserDTO dto = new UserDTO();
            dto.setUserName(entity.getUserName());
            dto.setPassword(entity.getPassword());
            return dto;
        }
        throw new RuntimeException("User Not Found !");
    }

    @Override
    public List<UserDTO> getAll() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        List<User> allUsers = userDao.getAll(session);
        if (allUsers.size() > 0) {
            return allUsers.stream().map(user -> Converter.getInstance().toUserDto(user)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty users list!");
    }

    @Override
    public String getLastId() {
        return null;
    }
}
