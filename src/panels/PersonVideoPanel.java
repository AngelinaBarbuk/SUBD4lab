package panels;

import dao.CountryDAO;
import dao.PersonDAO;
import dao.PersonsVideosDAO;
import dao.VideosDAO;
import entity.Genre;
import entity.Person;
import entity.PersonVideo;
import entity.Video;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by B on 13.11.2016.
 */
public class PersonVideoPanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    PersonsVideosDAO personsVideosDAO = new PersonsVideosDAO();
    VideosDAO videosDAO = new VideosDAO();
    PersonDAO personDAO=new PersonDAO();
    public static final Object[] COLUMNS = new Object[]{
            "Person id",
            "Person",
            "Video id",
            "Video"
    };

    public PersonVideoPanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
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
                AddDialog dialog = null;
                try {
                    dialog = new AddDialog(parent);
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                PersonVideo personVideo = dialog.getPersonVideo();
                if(personVideo!=null){
                    try {
                        personsVideosDAO.add(personVideo);
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
                    personsVideosDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0),(Integer) model.getValueAt(table.getSelectedRow(),2));
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
        java.util.List<PersonVideo> list = personsVideosDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            PersonVideo personVideo = list.get(i);
            model.setValueAt(personVideo.getPersonId(),i,0);
            model.setValueAt(personVideo.getPersonName(),i,1);
            model.setValueAt(personVideo.getVideoId(),i,2);
            model.setValueAt(personVideo.getVideoName(),i,3);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        PersonVideo personVideo;

        AddDialog(JFrame owner) throws PropertyVetoException, SQLException, IOException {
            super(owner, "Add dialog", true);
            JButton ok = new JButton("ok");
            JButton cancel = new JButton("cancel");

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(ok);
            panel.add(cancel);
            add(panel, BorderLayout.SOUTH);
            setSize(260, 160);

            JPanel input = new JPanel(new GridLayout(2,2));
            input.add(new JLabel("Actor"));
            java.util.List<Person> persons = personDAO.getAll();
            JComboBox<Person> actor = new JComboBox<>(persons.toArray(new Person[persons.size()]));
            input.add(actor);

            input.add(new JLabel("Video"));
            java.util.List<Video> videos = videosDAO.getAll();
            JComboBox<Video> video = new JComboBox<>(videos.toArray(new Video[videos.size()]));
            input.add(video);

            add(input, BorderLayout.CENTER);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    PersonVideo personVideo1 = new PersonVideo();
                    personVideo1.setPersonId(((Person)actor.getSelectedItem()).getId());
                    personVideo1.setVideoId(((Video)video.getSelectedItem()).getId());
                    personVideo =personVideo1;
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

        public PersonVideo getPersonVideo() {
            return personVideo;
        }
    }
}

