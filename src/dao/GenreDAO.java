package dao;

import connector.DBConnector;
import entity.Genre;
import entity.Store;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by B on 13.11.2016.
 */
public class GenreDAO implements DAO<Genre> {
    public static final String ADD = "INSERT INTO GENRES (GENRE_NAME) VALUES(?)";
    public static final String GET_ALL = "SELECT GENRE_ID, GENRE_NAME FROM GENRES";
    public static final String DELETE = "DELETE FROM GENRES WHERE GENRE_ID=?";

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<Genre> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            Genre store = new Genre();
            store.setId(rs.getInt(1));
            store.setName(rs.getString(2));
            list.add(store);
        }
        return list;
    }

    public int add(Genre genre) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD, new String[]{"GENRE_ID"});
        st.setString(1,genre.getName());
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
