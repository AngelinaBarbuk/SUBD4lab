package panels;

import dao.*;
import entity.Country;

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
 * Created by B on 31.10.2016.
 */
public class CountryPanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    CountryDAO countryDAO=new CountryDAO();
    public static final Object[] COLUMNS = new Object[]{
            "ID",
            "Name"
    };

    public CountryPanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
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

        List<Country> list = countryDAO.getAll();
        model = new DefaultTableModel(COLUMNS,list.size());
        table = new JTable(model);
        for(int i=0; i<list.size();i++){
            Country country = list.get(i);
            model.setValueAt(country.getId(),i,0);
            model.setValueAt(country.getName(),i,1);
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
                Country country = dialog.getCountry();
                if(country!=null){
                    try {
                        countryDAO.add(country);
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
                    countryDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0));
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
        List<Country> list = countryDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            Country country = list.get(i);
            model.setValueAt(country.getId(),i,0);
            model.setValueAt(country.getName(),i,1);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        Country country;

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
                    Country country1 = new Country();
                    country1.setName(name.getText());
                    country=country1;
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

        public Country getCountry() {
            return country;
        }
    }
}
