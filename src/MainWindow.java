import panels.*;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by B on 09.10.2016.
 */
public class MainWindow extends JFrame {

    public MainWindow() throws PropertyVetoException, IOException, SQLException {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,600);

        JTabbedPane jTabbedPane=new JTabbedPane();
        add(jTabbedPane);
        jTabbedPane.add("Video",new JScrollPane(new VideoPanel(this)));
        jTabbedPane.add("Country",new JScrollPane(new CountryPanel(this)));
        jTabbedPane.add("Store",new JScrollPane(new StorePanel(this)));
        jTabbedPane.add("Genres",new JScrollPane(new GenrePanel(this)));
        jTabbedPane.add("People",new JScrollPane(new PersonPanel(this)));
        jTabbedPane.add("Actors",new JScrollPane(new PersonVideoPanel(this)));
        jTabbedPane.add("Genre-Video",new JScrollPane(new GenreVideoPanel(this)));
        jTabbedPane.add("Triggers",new JScrollPane(new TriggerPanel(this)));


    }



    public static void main(String[] args) throws PropertyVetoException, IOException, SQLException {
        MainWindow wnd = new MainWindow();
        wnd.setVisible(true);

    }
}
