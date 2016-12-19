package panels;

import dao.*;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by B on 14.11.2016.
 */
public class GenreVideoPanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    GenreVideoDAO genreVideoDAO = new GenreVideoDAO();
    GenreDAO genreDAO = new GenreDAO();
    VideosDAO videoDAO =new VideosDAO();
    public static final Object[] COLUMNS = new Object[]{
            "Genre id",
            "Genre",
            "Video id",
            "Video",
            "Mark"
    };

    public GenreVideoPanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
        this.parent=parent;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton changeButton = new JButton("Change");
        JButton removeButton = new JButton("Remove");
        buttonPanel.add(addButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(removeButton);

        setLayout(new BorderLayout());
        add(buttonPanel,BorderLayout.NORTH);

        model = new DefaultTableModel(COLUMNS,0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        add(new JScrollPane(table),BorderLayout.CENTER);
        fillTable();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenreVideoPanel.AddDialog dialog = null;
                try {
                    dialog = new GenreVideoPanel.AddDialog(parent);
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                GenreVideo genreVideo = dialog.getGenreVideo();
                if(genreVideo!=null){
                    try {
                        genreVideoDAO.add(genreVideo);
                        fillTable();
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null,"Name should be unique!");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (PropertyVetoException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    genreVideoDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0),(Integer) model.getValueAt(table.getSelectedRow(),2));
                    fillTable();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    private void fillTable() throws SQLException, IOException, PropertyVetoException {
        model.getDataVector().clear();
        java.util.List<GenreVideo> list = genreVideoDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            GenreVideo genreVideo = list.get(i);
            model.setValueAt(genreVideo.getGenreId(),i,0);
            model.setValueAt(genreVideo.getGenreName(),i,1);
            model.setValueAt(genreVideo.getVideoId(),i,2);
            model.setValueAt(genreVideo.getVideoName(),i,3);
            model.setValueAt(genreVideo.getMark(),i,4);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        GenreVideo genreVideo;

        AddDialog(JFrame owner) throws PropertyVetoException, SQLException, IOException {
            super(owner, "Add dialog", true);
            JButton ok = new JButton("ok");
            JButton cancel = new JButton("cancel");

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(ok);
            panel.add(cancel);
            add(panel, BorderLayout.SOUTH);
            setSize(260, 160);

            JPanel input = new JPanel(new GridLayout(3,2));
            input.add(new JLabel("Genre"));
            java.util.List<Genre> genres = genreDAO.getAll();
            JComboBox<Genre> genre = new JComboBox<>(genres.toArray(new Genre[genres.size()]));
            input.add(genre);

            input.add(new JLabel("Video"));
            java.util.List<Video> videos = videoDAO.getAll();
            JComboBox<Video> video = new JComboBox<>(videos.toArray(new Video[videos.size()]));
            input.add(video);

            input.add(new JLabel("Mark"));
            JTextField mark = new JTextField();
            input.add(mark);

            add(input, BorderLayout.CENTER);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    GenreVideo genreVideo1 = new GenreVideo();
                    genreVideo1.setGenreId(((Genre)genre.getSelectedItem()).getId());
                    genreVideo1.setVideoId(((Video)video.getSelectedItem()).getId());
                    genreVideo1.setMark(Integer.parseInt(mark.getText()));
                    genreVideo =genreVideo1;
                    setVisible(false);
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
            pack();
        }

        public GenreVideo getGenreVideo() {
            return genreVideo;
        }
    }
}

