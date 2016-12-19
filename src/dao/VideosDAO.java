package dao;

import connector.DBConnector;
import entity.Country;
import entity.Person;
import entity.Store;
import entity.Video;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VideosDAO implements DAO<Video> {

    public static final String ADD = "INSERT INTO VIDEOS (NAME_RU, NAME, YEAR, COUNTRY_ID, PERSON_ID, STORE_ID) VALUES(?,?,?,?,?,?)";
    public static final String GET_ALL_VIDEOS = "SELECT v.VIDEO_ID, v.NAME, v.NAME_RU, v.YEAR, c.COUNTRY_ID, c.COUNTRY_NAME, d.PERSON_ID, d.NAME, d.NAME_RU, " +
            "s.STORE_ID, s.STOPE_NAME FROM VIDEOS v " +
            "INNER JOIN COUNTRIES c ON v.COUNTRY_ID=c.COUNTRY_ID " +
            "INNER JOIN PEOPLES d ON v.PERSON_ID=d.PERSON_ID " +
            "INNER JOIN STORE_TYPES s ON v.STORE_ID=s.STORE_ID ";
    public static final String DELETE = "DELETE FROM VIDEOS WHERE VIDEO_ID=?";

    public VideosDAO(){
    }

    public List getAll() throws SQLException, IOException, PropertyVetoException {
        ArrayList<Video> list = new ArrayList();
        PreparedStatement preparedStatement = DBConnector.getInstance().getConnection().prepareStatement(GET_ALL_VIDEOS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            Video video = new Video(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),
                    new Country(rs.getInt(5), rs.getString(6)), new Person(rs.getInt(7),rs.getString(8),rs.getString(9)),
                    new Store(rs.getInt(10),rs.getString(11)));
            list.add(video);
        }
        return list;
    }

    @Override
    public int add(Video video) throws SQLException, IOException, PropertyVetoException {
        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD, new String[]{"VIDEO_ID"});
        st.setString(1,video.getNameRu());
        st.setString(2,video.getName());
        st.setInt(3,video.getYear());
        st.setInt(4,video.getCountry().getId());
        st.setInt(5,video.getDirector().getId());
        st.setInt(6,video.getStoreType().getId());
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

//    public int add(Video news) throws SQLException, IOException, PropertyVetoException {
//        int authorId = getAuthorId(news);
//        if(authorId==-1)
//            authorId = addAuthor(news);
//        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD_NEWS, new String[]{"ID"});
//        st.setInt(1,authorId);
//        st.setString(2,news.getTitle());
//        st.setString(3,news.getContent());
//        st.setDate(4, (Date) news.getDate());
//        st.setString(5,news.getImageURI());
//        st.executeUpdate();
//
//        ResultSet resultSet = st.getGeneratedKeys();
//        if(resultSet.next()){
//            int newsId =resultSet.getInt(1);
//            System.out.println(newsId);
//            return newsId;
//        }
//        else return -1;
//    }
//
//    private int getAuthorId(News news) throws SQLException, IOException, PropertyVetoException {
//        int authorId = -1;
//        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(GET_AUTHOR_ID);
//        st.setString(1,news.getAuthor());
//        ResultSet rs = st.executeQuery();
//        if(rs.next())
//            authorId=rs.getInt(1);
//        return authorId;
//    }
//
//    private int addAuthor(News news) throws SQLException, IOException, PropertyVetoException {
//        ResultSet rs;
//        PreparedStatement st= DBConnector.getInstance().getConnection().prepareStatement(ADD_AUTHOR, new String[]{"ID"});
//        st.setString(1,news.getAuthor());
//        st.executeUpdate();
//        rs = st.getGeneratedKeys();
//        if(rs.next())
//            return rs.getInt(1);
//        return -1;
//    }
//
//
//    public void deleteNews(int id) throws SQLException, IOException, PropertyVetoException {
//        PreparedStatement st = DBConnector.getInstance().getConnection().prepareStatement(DELETE_NEWS);
//        st.setInt(1,id);
//        st.executeUpdate();
//    }
//
//    public void changeNews(News news) throws SQLException, IOException, PropertyVetoException {
//        PreparedStatement st = DBConnector.getInstance().getConnection().prepareStatement(CHANGE_NEWS);
//        st.setString(1,news.getTitle());
//        st.setString(2,news.getContent());
//        st.setDate(3, (Date) news.getDate());
//        st.setString(4, news.getImageURI());
//        st.setInt(5,news.getId());
//        st.executeUpdate();
//    }


    public static void main(String args[]) throws SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        VideosDAO dao= new VideosDAO();

        List<Video> videos = dao.getAll();
        for(Video v:videos){
            System.out.println(v);
        }


    }
}

