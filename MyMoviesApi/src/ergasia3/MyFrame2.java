/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ergasia3;


import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EvansPc
 */
public class MyFrame2 extends JFrame {

    Container container;
    JMenuBar favoriteBar;
    JMenu dhmiourgia, akirosi, diagrafi, epexergasia;
    JList list;
    JButton create, cancel, delete, process;
    JTable table;
    JFrame frame,frame2;
    String[] columnNames = {"Title", "Rating", "Overview"};
    String selection;
    ArrayList Al = new ArrayList<>();
    Connection con, con1;
    ResultSet rs, rs1;
    Statement st, st1;
    PreparedStatement pst, pst1;
    
    public JOptionPane pane = new JOptionPane();
    ActionListener handler2 = new MenuItemHandler2();
    ListSelectionListener lsl = new ListHandler(); 
    ActionListener handler3 = new ListnameHandler();
    JTextField field = new JTextField(25);
     JTextField field2 = new JTextField(25);
    JButton save = new JButton("Αποθήκευση");
    JButton cancel1 = new JButton("Ακύρωση");
    JButton save1 = new JButton("Αποθήκευση"); 
    JButton cancel2 = new JButton("Ακύρωση");
    JButton yes = new JButton("Ναι"); 
    JButton cancel3 = new JButton("Ακύρωση");
    JFrame frame4= new JFrame("Επεξεργασία λίστας");
    JFrame frame5 = new JFrame("Διαγραφή λίστας");
    
    public MyFrame2() {
        super("Διαχείριση Λιστών Αγαπημένων Ταινιών");

        container = getContentPane();

        favoriteBar = new JMenuBar();

        list = new JList();
        try {
            fillList();
        } catch (SQLException ex) {
            Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
        }

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(20);
        list.addListSelectionListener(lsl);
        
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(80, 80));
        favoriteBar.add(listScroller);

        
        create = new JButton("Δημιουργία");
       
        create.addActionListener(handler2);
        

        
        process = new JButton("Επεξεργασία");
        process.setVisible(false);
        process.addActionListener(handler2);
       

        delete = new JButton("Διαγραφή");
        delete.addActionListener(handler2);
        delete.setVisible(false);

        JPanel panel1 = new JPanel();
      
        panel1.add(favoriteBar);
        FlowLayout flow = new FlowLayout();
        panel1.setLayout(flow);
        
        panel1.add(create);
        panel1.add(process);
        panel1.add(delete);
                

        container.add(panel1, BorderLayout.NORTH);

       

    }

    public void fillList() throws SQLException {
        DefaultListModel listModel = new DefaultListModel();
        String sql = "Select * From Favorite_List";
        String url = "jdbc:derby://localhost:1527/MyMovies";
        Connection conn = DriverManager.getConnection(url, "Ergasia3", "12345");
        Statement stmt = conn.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery(sql);
        String name = null;
        while (rs.next()) {
            name = rs.getString(2);
            if (name != null) {
                listModel.addElement(name);
            }
            list.setModel(listModel);
        }
    }

    class MenuItemHandler2 implements ActionListener {

        DefaultListModel listModel = new DefaultListModel();
        MoviesData md = new MoviesData();

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == create) {
                list.clearSelection();
                try {
                    fillList();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame2= new JFrame("Δημιουργία λίστας");
                frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                
                save.addActionListener(handler3);
               
                cancel1.addActionListener(handler3);
                frame2.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
                frame2.add(field);
                frame2.add(save);
                
                frame2.add(cancel1);
                frame2.setVisible(true);
                frame2.setSize(250, 85);
                
               
             

        
    }else if(event.getSource() == process){
                
                frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                String listname = list.getSelectedValue().toString();
                field2.setText(listname);
                
                
                save1.addActionListener(handler3);
                
                cancel2.addActionListener(handler3);
                frame4.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
                frame4.add(field2);
                frame4.add(save1);
                
                frame4.add(cancel2);
                frame4.setVisible(true);
                frame4.setSize(250, 85);
               
    }else if(event.getSource() == delete){
            String value = list.getSelectedValue().toString();
            
            
            
            yes.addActionListener(handler3);
            cancel3.addActionListener(handler3);
            frame5.add(yes);
            frame5.add(cancel3);
            FlowLayout flow = new FlowLayout();
            frame5.setLayout(flow);
            frame5.setVisible(true);
            frame5.setSize(250,100);
            JOptionPane.showMessageDialog(null,null,"Διαγραφή λίστας ",JOptionPane.WARNING_MESSAGE);
            
    }
    }
    }

            class  ListHandler implements ListSelectionListener{
            public void valueChanged(ListSelectionEvent event){
                try{
                String name = list.getSelectedValue().toString();
                process.setVisible(true);
                delete.setVisible(true);
                if (!event.getValueIsAdjusting() ){
                   
                System.out.println(name);
                frame = new JFrame("Database Search Result");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                String rating = "";

                String overview = "";
                String title = "";
                    
                try {
                    String url1 = "jdbc:derby://localhost:1527/MyMovies";
                    con = DriverManager.getConnection(url1, "Ergasia3", "12345");
                    String sql = ("select * from FAVORITE_LIST WHERE NAME = '"+name+"'");
                    Statement st = con.createStatement();
                    ResultSet rs ;
                    rs = st.executeQuery(sql);
                    
                    while (rs.next()) {
                        
                        Integer i = rs.getInt(1);
                       
                        String url ="jdbc:derby://localhost:1527/MyMovies";
                        con1 = DriverManager.getConnection(url, "Ergasia3", "12345");
                        pst1 = con1.prepareStatement("Select * FROM MOVIE WHERE FAVORITE_LIST_ID = " + i);
                        ResultSet rs1 = pst1.executeQuery();
                        while(rs1.next()){
                        rating = rs1.getString(7);
                        overview = rs1.getString(4);
                        title = rs1.getString(2);
                           
                        model.addRow(new Object[]{title, rating, overview});

                    }
                    }
                    table = new JTable();
                    table.setModel(model);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table.setFillsViewportHeight(true);

                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                JScrollPane scroll = new JScrollPane(table);
                scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                frame.add(scroll);
                frame.setVisible(true);
                frame.setSize(400, 300);
               
                
                
                
            } 
            }catch(Exception e){
                
            }
            
            
            }
            }
            class ListnameHandler implements ActionListener{
                 DefaultListModel listModel = new DefaultListModel();
                 MoviesData md = new MoviesData();
                    public void actionPerformed(ActionEvent event) {
                            if(event.getSource() == save){
                String name = field.getText();

                try {
                    md.createFavListname(name);
                    listModel.addElement(name);
                    list.setModel(listModel);

                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fillList();
                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame2.dispose();

            }else if((event.getSource() == cancel1) ){
                frame2.dispose();
            }else if(event.getSource() == save1){
                DefaultListModel listModel = new DefaultListModel();
                 MoviesData md = new MoviesData();
                     
                           
                String name = field2.getText();

                try {
                    String sql = "Select ID from FAVORITE_LIST where NAME ='" +list.getSelectedValue().toString()+"'";
                    String url = "jdbc:derby://localhost:1527/MyMovies";
                    Connection conn = DriverManager.getConnection(url, "Ergasia3", "12345");
                    Statement stmt = conn.createStatement();
                    ResultSet rs;
                    rs= stmt.executeQuery(sql);
                    while(rs.next()){
                        int i = rs.getInt(1);
                        System.out.println(i);
                        String sql2="Update FAVORITE_LIST set NAME = '"+name+"' where ID="+i+"";
                        String url2 = "jdbc:derby://localhost:1527/MyMovies";
                        Connection conn2 = DriverManager.getConnection(url2, "Ergasia3", "12345");
                        Statement stmt2 = conn2.createStatement();
                        stmt2.executeUpdate(sql2);
                        
                    }
                    
                    listModel.removeElement(list.getSelectedValue().toString());
                   
                    listModel.addElement(name);
                    list.setModel(listModel);

                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fillList();
                } catch (SQLException ex) {
                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame4.dispose();
                
            }
            
                else if(event.getSource() == cancel2) {
                frame4.dispose();
            }else if (event.getSource()== yes){
                String name = list.getSelectedValue().toString();
                listModel.removeElement(list.getSelectedValue());
                                try {
                                    
                                    String sql = "Select ID from FAVORITE_LIST where NAME ='" +name+"'";
                                    String url = "jdbc:derby://localhost:1527/MyMovies";
                                    Connection conn = DriverManager.getConnection(url, "Ergasia3", "12345");
                                    Statement stmt = conn.createStatement();
                                    ResultSet rs;
                                    rs= stmt.executeQuery(sql);
                                    while(rs.next()){
                                        int i = rs.getInt(1);
                                        System.out.println(i);
                                         String sql3="UPDATE  MOVIE  set FAVORITE_LIST_ID= null where FAVORITE_LIST_ID="+i+"";
                                        String url3 = "jdbc:derby://localhost:1527/MyMovies";
                                        Connection conn3 = DriverManager.getConnection(url3, "Ergasia3", "12345");
                                        Statement stmt3 = conn3.createStatement();
                                        stmt3.executeUpdate(sql3);
                                        String sql2="DELETE FROM FAVORITE_LIST where ID="+i+"";
                                        String url2 = "jdbc:derby://localhost:1527/MyMovies";
                                        Connection conn2 = DriverManager.getConnection(url2, "Ergasia3", "12345");
                                        Statement stmt2 = conn2.createStatement();
                                        stmt2.executeUpdate(sql2);
                                       
                                        stmt3.executeUpdate(sql3);
                                    }           } catch (SQLException ex) {
                                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listModel.removeElement(list.getSelectedValue().toString());
                                list.clearSelection();
                                list.setModel(listModel);
                                try {
                                    fillList();
                                } catch (SQLException ex) {
                                    Logger.getLogger(MyFrame2.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                process.setVisible(false);
                                delete.setVisible(false);
                                frame5.dispose();
            }else if (event.getSource() == cancel3){
                frame5.dispose();
 
        }
            }
    }
}



