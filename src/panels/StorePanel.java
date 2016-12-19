package panels;

import dao.*;
import entity.Country;
import entity.Store;

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
public class StorePanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    StoreDAO storeDAO = new StoreDAO();
    public static final Object[] COLUMNS = new Object[]{
            "ID",
            "Name"
    };

    public StorePanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
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

        List<Store> list = storeDAO.getAll();
        model = new DefaultTableModel(COLUMNS,list.size());
        table = new JTable(model);
        for(int i=0; i<list.size();i++){
            Store store = list.get(i);
            model.setValueAt(store.getId(),i,0);
            model.setValueAt(store.getName(),i,1);
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
                Store store = dialog.getStore();
                if(store!=null){
                    try {
                        storeDAO.add(store);
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
                    storeDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0));
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
        List<Store> list = storeDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            Store store = list.get(i);
            model.setValueAt(store.getId(),i,0);
            model.setValueAt(store.getName(),i,1);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        Store store;

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
                    Store store1 = new Store();
                    store1.setName(name.getText());
                    store =store1;
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

        public Store getStore() {
            return store;
        }
    }
}
