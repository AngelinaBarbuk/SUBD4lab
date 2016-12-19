package dao;

import connector.DBConnector;
import entity.Person;
import entity.PersonVideo;

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
public class PersonsVideosDAO implements DAO<PersonVideo> {
    public static final String ADD = "INSERT INTO PERSONS_VIDEOS (PERSON_ID, VIDEO_ID) VALUES(?,?)";
    public static final String GET_ALL = "SELECT pv.PERSON_ID, p.NAME_RU, pv.VIDEO_ID, v.NAME_RU FROM PERSONS_VIDEOS pv" +
            " INNER JOIN PEOPLES p on p.PERSON_ID=pv.PERSON_ID " +
            "INNER JOIN VIDEOS v on v.VIDEO_ID=pv.VIDEO_ID";
    public static final String DELETE = "DELETE FROM PERSONS_VIDEOS WHERE PERSON_ID=? AND VIDEO_ID=?";

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<PersonVideo> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            PersonVideo personVideo = new PersonVideo();
            personVideo.setPersonId(rs.getInt(1));
            personVideo.setPersonName(rs.getString(2));
            personVideo.setVideoId(rs.getInt(3));
            personVideo.setVideoName(rs.getString(4));
            list.add(personVideo);
        }
        return list;
    }


    public int add(PersonVideo personVideo) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD);
        st.setInt(1,personVideo.getPersonId());
        st.setInt(2,personVideo.getVideoId());
        st.executeUpdate();
        return 0;
    }

    @Override
    public void remove(int id) throws IOException, SQLException {

    }

    public void remove(int personId, int videoId) throws IOException, SQLException {
        PreparedStatement st = DBConnector.getInstance().getConnection().prepareStatement(DELETE);
        st.setInt(1,personId);
        st.setInt(2,videoId);
        st.executeUpdate();
    }
}

