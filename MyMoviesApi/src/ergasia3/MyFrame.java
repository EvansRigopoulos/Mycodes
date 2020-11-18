package ergasia3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;

import java.net.MalformedURLException;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EvansPc
 */
public class MyFrame extends JFrame {

    Container container;
    JMenuBar menuBar;
    JMenu recoverData, manageData, searchData, statistics, exitMenu;
    JMenuItem createNew, editList, deleteList;
    JButton newDataBase, favoriteList, searchMovies, statisticsButton, exitButton, button1, button2;
    JProgressBar jb =new JProgressBar();
    JTextField textField;
    JLabel imageLabel;
    ActionListener actionhandler = new MenuItemHandler();
    JTable table;
    JFrame frame, frame1,frame2;
    String[] columnNames = {"Title", "Rating"};
    String[] columnNames1 = {"Title"};
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Ergasia3PU");
    EntityManager em = emf.createEntityManager();

    public MyFrame() throws MalformedURLException, IOException {
        super("MY MOVIES");

        container = getContentPane();

        menuBar = new JMenuBar();

        recoverData = new JMenu("1.Ανάκτηση και Αποθήκευση Δεδομένων Ταινιών");
        newDataBase = new JButton("Ανάκτηση και Αποθήκευση Δεδομένων Ταινιών");
        
        recoverData.add(newDataBase);
        newDataBase.addActionListener(actionhandler);
        
        menuBar.add(recoverData);

        manageData = new JMenu("2.Διαχείριση Λιστών Αγαπημένων Ταινιών");
        favoriteList = new JButton("Διαχείριση Λιστών Αγαπημένων Ταινιών");
        manageData.add(favoriteList);
        
        favoriteList.addActionListener(actionhandler);

        menuBar.add(manageData);
        searchData = new JMenu("3.Αναζήτηση Ταινιών");
        searchMovies = new JButton("Αναζήτηση Ταινιών");
        searchData.add(searchMovies);
        searchMovies.addActionListener(actionhandler);

        menuBar.add(searchData);
        statistics = new JMenu("4.Στατιστικά");
        statisticsButton = new JButton("Στατιστικά");
        statistics.add(statisticsButton);
        statisticsButton.addActionListener(actionhandler);
        menuBar.add(statistics);

        exitMenu = new JMenu("5.Έξοδος");
        exitButton = new JButton("Έξοδος");
        exitButton.addActionListener(actionhandler);
        exitMenu.add(exitButton);
        menuBar.add(exitMenu);

        button1 = new JButton("Οι καλύτερες 10 ταινίες");

        button1.addActionListener(actionhandler);

        button2 = new JButton("Οι καλύτερες ταινίες ανά λίστα");

        button2.addActionListener(actionhandler);

       
        menuBar.setSize(100,100);
        FlowLayout flow = new FlowLayout();
        container.setLayout(flow);
       ImageIcon ic1 = new ImageIcon("C:\\Users\\evans\\OneDrive\\Documents\\NetBeansProjects\\MyMoviesApi\\images\\movies.png");
       ImageLabel label = new ImageLabel(ic1);
       container.add(label);

       

        container.add(menuBar);
        container.add(jb);
    }

    class MenuItemHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button2) {
                frame = new JFrame("Top Movies From Lists");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames1);
                String title = "";

                try {
                    String url1 = "jdbc:derby://localhost:1527/MyMovies";

                    Connection con1 = DriverManager.getConnection(url1, "Ergasia3", "12345");
                    String sql1 = ("select id from Favorite_list  ");
                    Statement st1 = con1.createStatement();
                    ResultSet rs1;
                    rs1 = st1.executeQuery(sql1);
                    while (rs1.next()) {
                        int i = rs1.getInt(1);
                        System.out.println(i);
                        String url = "jdbc:derby://localhost:1527/MyMovies";

                        Connection con = DriverManager.getConnection(url, "Ergasia3", "12345");
                        String sql = ("select max(rating) from MOVIE where Favorite_List_Id  = " + i + "");
                        Statement st = con.createStatement();
                        ResultSet rs;
                        rs = st.executeQuery(sql);
                        while (rs.next()) {
                            int j = rs.getInt(1);
                            System.out.println(j);
                            String url2 = "jdbc:derby://localhost:1527/MyMovies";

                            Connection con2 = DriverManager.getConnection(url2, "Ergasia3", "12345");
                            String sql2 = ("select title from MOVIE where rating  = " + j + " and Favorite_List_id=" + i + "");
                            Statement st2 = con2.createStatement();
                            ResultSet rs2;
                            rs2 = st2.executeQuery(sql2);
                            while (rs2.next()) {
                                title = rs2.getString(1);

                                model.addRow(new Object[]{title});
                            }
                        }

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                table = new JTable();
                table.setModel(model);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setFillsViewportHeight(true);

                JScrollPane scroll = new JScrollPane(table);
                scroll.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                frame.add(scroll);
                frame.setVisible(true);
                frame.setSize(400, 300);
            } else if (event.getSource() == newDataBase) {
                     
                      
                      
                try {
                         
                   
                    CreateDB();
                    

                } catch (Exception ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                

                JOptionPane.showMessageDialog(null, "Database Created");
            } else if (event.getSource() == favoriteList) {
                MyFrame2 myframe2 = new MyFrame2();

                myframe2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                myframe2.setSize(600, 600);
                FlowLayout flow = new FlowLayout();
                myframe2.setLayout(flow);
                myframe2.setDefaultLookAndFeelDecorated(true);

                myframe2.setVisible(true);
            } else if (event.getSource() == searchMovies) {
                try {
                    new SearchMovies();
                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event.getSource() == button1) {

                frame = new JFrame("Top Ten");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                String title = "";
                String rating = "";

                try {
                    String url1 = "jdbc:derby://localhost:1527/MyMovies";

                    Connection con = DriverManager.getConnection(url1, "Ergasia3", "12345");
                    String sql = ("select title,rating from MOVIE  order by rating desc fetch first 10 rows only");
                    Statement st = con.createStatement();
                    ResultSet rs;
                    rs = st.executeQuery(sql);
                    while (rs.next()) {

                        title = rs.getString(1);
                        rating = rs.getString(2);

                        model.addRow(new Object[]{title, rating});

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                table = new JTable();
                table.setModel(model);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setFillsViewportHeight(true);

                JScrollPane scroll = new JScrollPane(table);
                scroll.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                frame.add(scroll);
                frame.setVisible(true);
                frame.setSize(400, 300);

            } else if (event.getSource() == statisticsButton) {
                frame1 = new JFrame("Στατιστικά");
                button1 = new JButton("Οι καλύτερες 10 ταινίες");

                button1.addActionListener(actionhandler);

                button2 = new JButton("Οι καλύτερες ταινίες ανά λίστα");

                button2.addActionListener(actionhandler);
                frame1.add(button1);
                frame1.add(button2);
                FlowLayout flow = new FlowLayout();
                frame1.setLayout(flow);
                 frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame1.setSize(200, 250);
                frame1.setVisible(true);

            }else if(event.getSource() == exitButton){
                System.exit(0);
            }

        }
    }
    

    public void CreateDB() throws Exception {
       
     
    
        MoviesData mv = new MoviesData();
        

        jb.setValue(20);
        mv.DeleteDB();
        jb.setValue(40);
        mv.CreateDBGenre();
       jb.setValue(60);
        mv.createMovieDB();
      jb.setValue(100);
    }
    
    class ImageLabel extends JLabel {

  public ImageLabel(String img) {
    this(new ImageIcon(img));
  }

  public ImageLabel(ImageIcon icon) {
    setIcon(icon);
    // setMargin(new Insets(0,0,0,0));
    setIconTextGap(0);
    // setBorderPainted(false);
    setBorder(null);
    setText(null);
    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
  }

}
  
}

