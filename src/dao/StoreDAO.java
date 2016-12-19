package dao;

import connector.DBConnector;
import entity.Store;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by B on 30.10.2016.
 */
public class StoreDAO implements DAO<Store> {
    public static final String ADD = "INSERT INTO STORE_TYPES (STOPE_NAME) VALUES(?)";
    public static final String GET_ALL = "SELECT STORE_ID, STOPE_NAME FROM STORE_TYPES";
    public static final String DELETE = "DELETE FROM STORE_TYPES WHERE STORE_ID=?";

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<Store> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            Store store = new Store();
            store.setId(rs.getInt(1));
            store.setName(rs.getString(2));
            list.add(store);
        }
        return list;
    }

    public int add(Store store) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD, new String[]{"STORE_ID"});
        st.setString(1,store.getName());
        st.executeUpdate();
        ResultSet resultSet = st.getGeneratedKeys();
        if(resultSet.next()){
            int newsId =resultSet.getInt(1);
            return newsId;
        }
        else return -1;
    }

    @Override
    public void remove(int id) throws IOException, SQLException {
        PreparedStatement st = DBConnector.getInstance().getConnection().prepareStatement(DELETE);
        st.setInt(1,id);
        st.executeUpdate();
    }
}
