package dao;

import connector.DBConnector;
import entity.Store;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by B on 14.11.2016.
 */
public class TriggerDAO {

    public static final String CHANGE = "ALTER TRIGGER";
    public static final String GET_ALL = "SELECT STORE_ID, STOPE_NAME FROM STORE_TYPES";
    public static final String DELETE = "DELETE FROM STORE_TYPES WHERE STORE_ID=?";


    public void update(String trigger, boolean enable) throws SQLException, IOException, PropertyVetoException {
        DBConnector.getInstance().getConnection().createStatement().execute(CHANGE+" "+trigger+" "+(enable?"ENABLE":"DISABLE"));
        /*PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(CHANGE);
        st.setString(1,trigger);
        st.setString(2,enable?"ENABLE":"DISABLE");
        st.executeQuery();*/
    }
}
