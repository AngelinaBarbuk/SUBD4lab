package panels;

import dao.DAO;
import dao.GenreDAO;
import entity.Genre;
import entity.Store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by B on 13.11.2016.
 */
public class GenrePanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    DAO genreDAO = new GenreDAO();
    public static final Object[] COLUMNS = new Object[]{
            "ID",
            "Name"
    };

    public GenrePanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
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
                GenrePanel.AddDialog dialog = new GenrePanel.AddDialog(parent);
                dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                Genre genre = dialog.getGenre();
                if(genre!=null){
                    try {
                        genreDAO.add(genre);
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
                    genreDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0));
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
        java.util.List<Genre> list = genreDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            Genre genre = list.get(i);
            model.setValueAt(genre.getId(),i,0);
            model.setValueAt(genre.getName(),i,1);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        Genre genre;

        AddDialog(JFrame owner) {
            super(owner, "Add dialog", true);
            JButton ok = new JButton("ok");
            JButton cancel = new JButton("cancel");

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(ok);
            panel.add(cancel);
            add(panel, BorderLayout.SOUTH);
            setSize(260, 160);

            JPanel input = new JPanel(new GridLayout(1,2));
            input.add(new JLabel("Name"));
            JTextField name = new JTextField();
            input.add(name);
            add(input, BorderLayout.CENTER);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    Genre genre1 = new Genre();
                    genre1.setName(name.getText());
                    genre =genre1;
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

        public Genre getGenre() {
            return genre;
        }
    }
}

