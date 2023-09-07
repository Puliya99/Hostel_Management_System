package lk.ijse.hostelManagementSystem.bo;

import lk.ijse.hostelManagementSystem.bo.custom.impl.StudentBoImpl;
import lk.ijse.hostelManagementSystem.bo.custom.impl.UserBoImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        User,STUDENT
    }

    public SuperBo getBO(BOTypes types){
        switch (types){
            case User:
                return new UserBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            default:
                return null;
        }
    }
}
