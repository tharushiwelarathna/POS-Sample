package lk.ijse.pos.dao;

import lk.ijse.pos.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class CrudUtil {

    public static PreparedStatement getPreparedStatement
            (String sql,Object...args) throws Exception {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement stm=connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stm.setObject((i+1),args[i]);
        }
        return stm;
    }

    public static ResultSet executeQuery(String sql, Object...args) throws Exception {
        return getPreparedStatement(sql,args).executeQuery();
    }

    public static boolean executeUpdate(String sql, Object...args) throws Exception {
        return getPreparedStatement(sql,args).executeUpdate()>0;
    }

//    public static <T> T execute(String sql,Object...args) throws SQLException, ClassNotFoundException {
//        PreparedStatement stm=DBConnection.getInstance()
//                .getConnection().prepareStatement(sql);
//        for (int i = 0; i < args.length; i++) {
//            stm.setObject((i+1),args[i]);
//        }
//
//        if (sql.startsWith("SELECT")){
//            return (T)stm.executeQuery();
//        }
//
//        return ((T)(Boolean)(stm.executeUpdate()>0));
//
//    }
}
