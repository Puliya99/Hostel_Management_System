package lk.ijse.hostelManagementSystem.bo.custom.impl;

import lk.ijse.hostelManagementSystem.bo.custom.ReservationBo;
import lk.ijse.hostelManagementSystem.dao.DAOFactory;
import lk.ijse.hostelManagementSystem.dao.custom.ReservationDAO;
import lk.ijse.hostelManagementSystem.dao.custom.RoomDAO;
import lk.ijse.hostelManagementSystem.dto.ReservationDTO;
import lk.ijse.hostelManagementSystem.entity.Reservation;
import lk.ijse.hostelManagementSystem.util.Converter;
import lk.ijse.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationBoImpl implements ReservationBo {
    private final ReservationDAO reservationDao;
    private final RoomDAO roomDao;

    public ReservationBoImpl() {
        reservationDao = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
        roomDao = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    }
    @Override
    public Boolean save(ReservationDTO entity) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            reservationDao.save(Converter.getInstance().toReservationEntity(entity), session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Boolean update(ReservationDTO entity) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            reservationDao.update(Converter.getInstance().toReservationEntity(entity), session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Boolean delete(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            reservationDao.delete(id, session);
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public ReservationDTO view(String id) throws RuntimeException {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            return Converter.getInstance().toReservationDto(reservationDao.view(id, session));
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<ReservationDTO> getAll() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        List<Reservation> reservationDtoList = reservationDao.getAll(session);
        if (reservationDtoList.size() > 0) {
            return reservationDtoList.stream().map(reservation -> Converter.getInstance().toReservationDto(reservation)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Room list!");
    }

    @Override
    public String getLastId() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        String lastId = reservationDao.getLastId(session);
        if (lastId == null) {
            return "REC-000001";
        } else {
            String[] split = lastId.split("[R][E][C][-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            return (String.format("REC-%06d", lastDigits));
        }
    }
}
