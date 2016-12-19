package panels;

import dao.GenreDAO;
import dao.GenreVideoDAO;
import dao.TriggerDAO;
import dao.VideosDAO;
import entity.Genre;
import entity.GenreVideo;
import entity.Video;

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
public class TriggerPanel extends JPanel {
    JFrame parent;
    TriggerDAO triggerDAO = new TriggerDAO();
    public static final Object[] COLUMNS = new Object[]{
            "Trigger Name",
            "On/Off"
    };

    public static final String[] TRIGGERS = new String[]{
            "count_of_genres",
            "count_of_videos_for_actor",
            "name_for_russian_video"
    };

    public static final JCheckBox[] FLAGS = new JCheckBox[]{
            new JCheckBox(),
            new JCheckBox(),
            new JCheckBox()
    };

    public TriggerPanel(JFrame parent) throws PropertyVetoException, SQLException, IOException {
        this.parent=parent;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Save");
        buttonPanel.add(addButton);

        setLayout(new BorderLayout());
        add(buttonPanel,BorderLayout.NORTH);

        JPanel triggerPanel = new JPanel(new GridLayout(3,2));
        triggerPanel.add(new JLabel(TRIGGERS[0]));
        triggerPanel.add(FLAGS[0]);
        triggerPanel.add(new JLabel(TRIGGERS[1]));
        triggerPanel.add(FLAGS[1]);
        triggerPanel.add(new JLabel(TRIGGERS[2]));
        triggerPanel.add(FLAGS[2]);

        FLAGS[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    triggerDAO.update(TRIGGERS[0],FLAGS[0].isSelected());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        });
        FLAGS[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    triggerDAO.update(TRIGGERS[1],FLAGS[1].isSelected());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        });
        FLAGS[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    triggerDAO.update(TRIGGERS[2],FLAGS[2].isSelected());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        });
        add(triggerPanel, BorderLayout.CENTER);

    }

}


