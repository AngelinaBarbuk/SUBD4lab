package dao;

import connector.DBConnector;
import entity.Country;

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
public class CountryDAO implements DAO<Country> {

    public static final String ADD = "INSERT INTO COUNTRIES (COUNTRY_NAME) VALUES(?)";
    public static final String GET_ALL = "SELECT COUNTRY_ID, COUNTRY_NAME FROM COUNTRIES";
    public static final String DELETE = "DELETE FROM COUNTRIES WHERE COUNTRY_ID=?";

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<Country> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            Country country = new Country();
            country.setId(rs.getInt(1));
            country.setName(rs.getString(2));
            list.add(country);
        }
        return list;
    }


    public int add(Country country) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD, new String[]{"COUNTRY_ID"});
        st.setString(1,country.getName());
        st.executeUpdate();
        ResultSet resultSet = st.getGeneratedKeys();
        if(resultSet.next()){
            int newsId =resultSet.getInt(1);
            System.out.println(newsId);
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
