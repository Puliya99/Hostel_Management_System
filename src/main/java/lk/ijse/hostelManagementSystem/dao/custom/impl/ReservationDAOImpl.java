package lk.ijse.hostelManagementSystem.dao.custom.impl;

import lk.ijse.hostelManagementSystem.dao.custom.ReservationDAO;
import lk.ijse.hostelManagementSystem.entity.Reservation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public Boolean save(Reservation entity, Session session) {
        session.save(entity);
        return true;
    }

    @Override
    public Boolean update(Reservation entity, Session session) {
        session.update(entity);
        return true;
    }

    @Override
    public Boolean delete(String id, Session session) {
        Reservation reservation = new Reservation();
        reservation.setRes_id(id);
        session.delete(reservation);
        return true;
    }

    @Override
    public Reservation view(String id, Session session) {
        return session.get(Reservation.class,id);
    }

    @Override
    public List<Reservation> getAll(Session session) {
        String sql = "From Reservation";
        Query query = session.createQuery(sql);
        List<Reservation> list = query.list();
        return list;
    }

    @Override
    public String getLastId(Session session) {
        Query query = session.createQuery("SELECT res_id FROM Reservation ORDER BY res_id DESC");
        query.setMaxResults(1);
        List results = query.list();
        return (results.size() == 0) ? null : (String) results.get(0);
    }
}
