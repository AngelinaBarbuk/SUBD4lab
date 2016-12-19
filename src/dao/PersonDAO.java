package dao;

import connector.DBConnector;
import entity.Person;

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
public class PersonDAO implements DAO<Person> {
    public static final String ADD = "INSERT INTO PEOPLES (NAME_RU, NAME) VALUES(?,?)";
    public static final String GET_ALL = "SELECT PERSON_ID, NAME_RU, NAME FROM PEOPLES";
    public static final String DELETE = "DELETE FROM PEOPLES WHERE PERSON_ID=?";

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<Person> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            Person person = new Person();
            person.setId(rs.getInt(1));
            person.setNameRu(rs.getString(2));
            person.setName(rs.getString(3));
            list.add(person);
        }
        return list;
    }


    public int add(Person person) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD, new String[]{"PERSON_ID"});
        st.setString(1,person.getNameRu());
        st.setString(2,person.getName());
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
