package lk.ijse.hostelManagementSystem.dao;

import lk.ijse.hostelManagementSystem.dao.custom.impl.QueryDAOImpl;
import lk.ijse.hostelManagementSystem.dao.custom.impl.RoomDAOImpl;
import lk.ijse.hostelManagementSystem.dao.custom.impl.StudentDaoImpl;
import lk.ijse.hostelManagementSystem.dao.custom.impl.UserDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,STUDENT,QUERY,ROOM
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case USER:
                return new UserDaoImpl();
            case STUDENT:
                return new StudentDaoImpl();
            case QUERY:
                return new QueryDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            default:
                return null;
        }
    }
}
