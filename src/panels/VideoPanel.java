package panels;

import dao.*;
import entity.Country;
import entity.Person;
import entity.Store;
import entity.Video;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by B on 24.10.2016.
 */
public class VideoPanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    VideosDAO videosDAO = new VideosDAO();
    CountryDAO countryDAO=new CountryDAO();
    PersonDAO personDAO=new PersonDAO();
    StoreDAO storeDAO=new StoreDAO();
    public static final Object[] COLUMNS = new Object[]{
            "ID",
            "Name RU",
            "Name EN",
            "YEAR",
            "COUNTRY",
            "STORE"
    };

    public VideoPanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
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

        videosDAO = new VideosDAO();
        List<Video> list = videosDAO.getAll();
        model = new DefaultTableModel(COLUMNS,list.size());
        table = new JTable(model);
        for(int i=0; i<list.size();i++){
            Video v = list.get(i);
            model.setValueAt(v.getId(),i,0);
            model.setValueAt(v.getNameRu(),i,1);
            model.setValueAt(v.getName(),i,2);
            model.setValueAt(v.getYear(),i,3);
            model.setValueAt(v.getCountry().getName(),i,4);
            model.setValueAt(v.getStoreType().getName(),i,5);
        }
        model.fireTableDataChanged();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        add(new JScrollPane(table),BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDialog dialog = new AddDialog(parent);
                dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                Video video = dialog.getVideo();
                if(video!=null){
                    try {
                        videosDAO.add(video);
                        fillTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
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
                    videosDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0));
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
        List<Video> list = videosDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            Video v = list.get(i);
            model.setValueAt(v.getId(),i,0);
            model.setValueAt(v.getNameRu(),i,1);
            model.setValueAt(v.getName(),i,2);
            model.setValueAt(v.getYear(),i,3);
            model.setValueAt(v.getCountry().getName(),i,4);
            model.setValueAt(v.getStoreType().getName(),i,5);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        Video video;

        AddDialog(JFrame owner) {
            super(owner, "Add dialog", true);
            try {
                JButton ok = new JButton("ok");
                JButton cancel = new JButton("cancel");

                JPanel panel = new JPanel(new FlowLayout());
                panel.add(ok);
                panel.add(cancel);
                add(panel, BorderLayout.SOUTH);
                setSize(260, 160);

                JPanel input = new JPanel(new GridLayout(6,2));
                input.add(new JLabel("Name RU"));
                JTextField nameRu = new JTextField();
                input.add(nameRu);
                input.add(new JLabel("Name"));
                JTextField name = new JTextField();
                input.add(name);
                input.add(new JLabel("Year"));
                JTextField year = new JTextField();
                input.add(year);
                input.add(new JLabel("Country"));
                List<Country> countries = countryDAO.getAll();
                JComboBox<Country> country = new JComboBox<>(countries.toArray(new Country[countries.size()]));
                input.add(country);
                input.add(new JLabel("Director"));
                List<Person> persons = personDAO.getAll();
                JComboBox<Person> director = new JComboBox<>(persons.toArray(new Person[persons.size()]));
                input.add(director);
                input.add(new JLabel("Store"));
                List<Store> stores = storeDAO.getAll();
                JComboBox<Store> store = new JComboBox<>(stores.toArray(new Store[stores.size()]));
                input.add(store);

                add(input, BorderLayout.CENTER);

                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        Video video1 = new Video();
                        video1.setNameRu(nameRu.getText());
                        video1.setName(name.getText());
                        try {
                            video1.setYear(Integer.parseInt(year.getText()));
                        }catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Year is not number!");
                            return;
                        }
                        video1.setCountry((Country) country.getSelectedItem());
                        video1.setDirector((Person) director.getSelectedItem());
                        video1.setStoreType((Store) store.getSelectedItem());
                        video = video1;
                        setVisible(false);
                    }
                });
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            pack();
        }

        public Video getVideo() {
            return video;
        }
    }
}
