package panels;

import dao.DAO;
import dao.PersonDAO;
import entity.Genre;
import entity.Person;

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
public class PersonPanel extends JPanel {
    JFrame parent;
    DefaultTableModel model;
    JTable table;
    DAO personDAO = new PersonDAO();
    public static final Object[] COLUMNS = new Object[]{
            "ID",
            "Name RU",
            "Name"
    };

    public PersonPanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
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
                PersonPanel.AddDialog dialog = new PersonPanel.AddDialog(parent);
                dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                Person person = dialog.getPerson();
                if(person!=null){
                    try {
                        personDAO.add(person);
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
                    personDAO.remove((Integer) model.getValueAt(table.getSelectedRow(),0));
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
        java.util.List<Person> list = personDAO.getAll();
        model.setRowCount(list.size());
        for(int i=0; i<list.size();i++){
            Person genre = list.get(i);
            model.setValueAt(genre.getId(),i,0);
            model.setValueAt(genre.getNameRu(),i,1);
            model.setValueAt(genre.getName(),i,2);
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class AddDialog extends JDialog{

        Person person;

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
            input.add(new JLabel("Name Ru"));
            JTextField nameRu = new JTextField();
            input.add(nameRu);
            input.add(new JLabel("Name"));
            JTextField name = new JTextField();
            input.add(name);
            add(input, BorderLayout.CENTER);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    Person person1 = new Person();
                    person1.setNameRu(nameRu.getText());
                    person1.setName(name.getText());
                    person =person1;
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

        public Person getPerson() {
            return person;
        }
    }
}


