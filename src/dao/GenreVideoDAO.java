package dao;

import connector.DBConnector;
import entity.Genre;
import entity.GenreVideo;
import entity.PersonVideo;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by B on 14.11.2016.
 */
public class GenreVideoDAO implements DAO<GenreVideo> {
    public static final String ADD = "INSERT INTO GENRES_VIDEOS (GENRE_ID, VIDEO_ID, Mark) VALUES(?,?,?)";
    public static final String GET_ALL = "SELECT gv.GENRE_ID, g.GENRE_NAME, gv.VIDEO_ID, v.NAME_RU, gv.mark FROM GENRES_VIDEOS gv" +
            " INNER JOIN GENRES g on g.GENRE_ID=gv.GENRE_ID " +
            "INNER JOIN VIDEOS v on v.VIDEO_ID=gv.VIDEO_ID";
    public static final String DELETE = "DELETE FROM GENRES_VIDEOS WHERE GENRE_ID=? AND VIDEO_ID=?";

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<GenreVideo> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            GenreVideo genreVideo = new GenreVideo();
            genreVideo.setGenreId(rs.getInt(1));
            genreVideo.setGenreName(rs.getString(2));
            genreVideo.setVideoId(rs.getInt(3));
            genreVideo.setVideoName(rs.getString(4));
            genreVideo.setMark(rs.getInt(5));
            list.add(genreVideo);
        }
        return list;
    }


    public int add(GenreVideo genreVideo) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD);
        st.setInt(1,genreVideo.getGenreId());
        st.setInt(2,genreVideo.getVideoId());
        st.setInt(3,genreVideo.getMark());
        st.executeUpdate();
        return 0;
    }

    @Override
    public void remove(int id) throws IOException, SQLException {

    }

    public void remove(int genreId, int videoId) throws IOException, SQLException {
        PreparedStatement st = DBConnector.getInstance().getConnection().prepareStatement(DELETE);
        st.setInt(1,genreId);
        st.setInt(2,videoId);
        st.executeUpdate();
    }
}
