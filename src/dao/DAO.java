package dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by VVV on 13.07.2016.
 */
public interface DAO<T> {

    List<T> getAll() throws SQLException, IOException, PropertyVetoException;
    int add(T news) throws SQLException, IOException, PropertyVetoException;
    void remove(int id) throws IOException, SQLException;
    //void changeNews(T news) throws SQLException, IOException, PropertyVetoException;

}
