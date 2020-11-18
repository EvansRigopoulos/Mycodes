/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ergasia3;




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EvansPc
 */
public class SearchMovies extends JFrame implements ActionListener {

        Container container;
        JFrame frame3;
        JLabel label0,label1,addToList;
        JButton search,clear,removeFromList;
        JComboBox box1,box2;
        JTextField text1;
        JTable table1;
        Connection con;
        ResultSet rs, rs1;
        Statement st, st1;
        PreparedStatement pst,pst1;
        String title1;
        String[] columnNames={"Title","Rating","Overview"};
        Integer title,from;

        
        SearchMovies() throws SQLException{
            
            label0 = new JLabel("Αναζήτηση ταινιών");
            label0.setForeground(Color.blue);
            label0.setFont(new Font("New Times Roman",Font.BOLD,20));
            
           
                    
                    
            search = new JButton("Αναζήτηση");
            clear = new JButton("Καθαρισμός");
            label0.setBounds(100, 50, 350, 40);
            search.setBounds(150, 150, 100, 20);
            clear.setBounds(300, 150, 100, 20);
            search.addActionListener(this);
            clear.addActionListener(this);
            text1 = new JTextField("Εισαγετε ημερομηνια");
          
            text1.setBounds(300,110, 70, 30);
            text1.addActionListener(this);
            
            setTitle("Αναζήτηση ταινιών");
            setLayout(null);
            setVisible(true);
            setSize(500, 500);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            ImageIcon ic1 = new ImageIcon("C:/Users/evans/OneDrive/Documents/NetBeansProjects/MyMoviesApi/images/search.png");
            
            add(label0);
            
            add(search);
            
            add(clear);
            add(text1);
            try{
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/MyMovies", "Ergasia3", "12345");  
            st = con.createStatement();
            rs = st.executeQuery("select * from GENRE");
            Vector v = new Vector();
            while (rs.next()) {
                
                title1 =rs.getString(2);
               
                v.add(title1);
             
                
               
            }
            
            
            box1 = new JComboBox(v);
            box1.setBounds(120, 110, 120, 20);
            
            add(box1);
            
            st.close();
            rs.close();
            
            
            

            
        }catch(Exception e){
        }
            
        }
        
        
             public void actionPerformed(ActionEvent event)  {
            
        if (event.getSource() == search ) {
            try {
                
                    
                
                try {
                    showTableData();
                } catch (ParseException ex) {
                    Logger.getLogger(SearchMovies.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchMovies.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(event.getSource() == clear){
            text1.setText(null);
            
        }
        else if(event.getSource() == box2) {
            try {
                String boxData =(String) box2.getSelectedItem();
                System.out.println(boxData);
                int column = 0;
                int row = table1.getSelectedRow();
                String value = table1.getModel().getValueAt(row, column).toString();
                System.out.println(value);
                String sql = "Select ID from FAVORITE_LIST where NAME ='" +boxData+"'";
                String url = "jdbc:derby://localhost:1527/MyMovies";
                Connection conn = DriverManager.getConnection(url, "Ergasia3", "12345");
                Statement stmt = conn.createStatement();
                ResultSet rs;
                rs= stmt.executeQuery(sql);
                while(rs.next()){
                    int i = rs.getInt(1);
                    System.out.println(i);
                    String sql2="Update Movie set Favorite_List_Id = "+i+" where title = '"+value+"'";
                    String url2 = "jdbc:derby://localhost:1527/MyMovies";
                    Connection conn2 = DriverManager.getConnection(url2, "Ergasia3", "12345");
                    Statement stmt2 = conn2.createStatement();
                    stmt2.executeUpdate(sql2);
                    
                }   } catch (SQLException ex) {
                Logger.getLogger(SearchMovies.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (event.getSource() == removeFromList){
            int column = 0;
            int row = table1.getSelectedRow();
            String value = table1.getModel().getValueAt(row, column).toString();
            try {
                removeFromList(value);
                box1.setSelectedIndex(0);
            } catch (SQLException ex) {
                Logger.getLogger(SearchMovies.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
             
             public void showTableData() throws SQLException, ParseException {
       
        frame3 = new JFrame("Database Search Result");
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3.setLayout(new BorderLayout());
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        
       
       
                
        
        String rating = "";
     
        String overview ="";
        String title= "";
        String year = text1.getText();
        
         
               
             
               
        Integer i=0;
        title = (String)box1.getSelectedItem();
               
                 if(title.contains("Action")){
                     i=28;
                 }else if(title.contains("Romance")){
                     i=10749;
                 }else if(title.contains("Science Fiction")){
                     i=878;
                 }
        try {
            if(year.isEmpty()){
            pst = con.prepareStatement("select * from MOVIE where Genre_ID   =  "  + i );
           
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                
             
                rating = rs.getString(5);
                
                
                overview = rs.getString(6);
                title = rs.getString(2);
                model.addRow(new Object[]{title,rating,overview});
                
            }
            
             table1 = new JTable();
        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setFillsViewportHeight(true);
        try{
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/MyMovies", "Ergasia3", "12345");  
            st = con.createStatement();
            rs = st.executeQuery("select * from Favorite_List");
            Vector v = new Vector();
            while (rs.next()) {
                
                title1 =rs.getString(2);
               
                v.add(title1);
                if(title.isEmpty()){
                    JOptionPane.showMessageDialog(null,"please enter favorite list");
                }
         box2 = new JComboBox(v);
         box2.addActionListener(this);
           addToList = new JLabel("Προσθήκη σε λίστα");
           addToList.setForeground(Color.blue);
           addToList.setFont(new Font("New Times Roman",Font.BOLD,20));
           removeFromList = new JButton("Αφαίρεση από λίστα");
           removeFromList.addActionListener(this);
           
        }   }catch(Exception e){
        }
            
            
        JScrollPane scroll = new JScrollPane(table1);
       JPanel panel1 = new JPanel(new FlowLayout());
       panel1.add(box2);
       panel1.add(addToList);
       panel1.add(removeFromList);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        frame3.setLayout(new GridLayout(2,1));
        frame3.add(scroll);
        frame3.add(panel1);
        
        
        frame3.setVisible(true);
        frame3.setSize(400, 300);
       
       

            }else{
                pst = con.prepareStatement("select * from MOVIE where Genre_ID   =  "  + i + "AND YEAR(RELEASE_DATE) = "+year);
           
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                
             
                rating = rs.getString(5);
                
                
                overview = rs.getString(6);
                title = rs.getString(2);
                model.addRow(new Object[]{title,rating,overview});
                
            }
            
             table1 = new JTable();
        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setFillsViewportHeight(true);
        try{
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/MyMovies", "Ergasia3", "12345");  
            st = con.createStatement();
            rs = st.executeQuery("select * from Favorite_List");
            Vector v = new Vector();
            while (rs.next()) {
                
                title1 =rs.getString(2);
               
                v.add(title1);
                if(title.isEmpty()){
                    JOptionPane.showMessageDialog(null,"please enter favorite list");
                }
         box2 = new JComboBox(v);
         box2.addActionListener(this);
           addToList = new JLabel("Προσθήκη σε λίστα");
           addToList.setForeground(Color.blue);
           addToList.setFont(new Font("New Times Roman",Font.BOLD,20));
           removeFromList = new JButton("Αφαίρεση από λίστα");
           removeFromList.addActionListener(this);
           
        }   }catch(Exception e){
        }
            
            
        JScrollPane scroll = new JScrollPane(table1);
       JPanel panel1 = new JPanel(new FlowLayout());
       panel1.add(box2);
       panel1.add(addToList);
       panel1.add(removeFromList);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        frame3.setLayout(new GridLayout(2,1));
        frame3.add(scroll);
        frame3.add(panel1);
        
        
        frame3.setVisible(true);
        frame3.setSize(400, 300);
       
       
                
        
        
            }}
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         
       
                            
        
}
             }
            
                
                    
                    
                    
                
                
             public void removeFromList(String text) throws SQLException{
                 System.out.println(text);
                String url = "jdbc:derby://localhost:1527/MyMovies";
                Connection conn = DriverManager.getConnection(url,"Ergasia3","12345");
                Statement stmt = conn.createStatement();
                 String sql2 = ("Select * From MOVIE where title ='" +text+"'");
               
              
                ResultSet rs;
                conn.setAutoCommit(false);
                rs = stmt.executeQuery(sql2);
                while(rs.next()){
                    
                
                conn.setAutoCommit(false);
                 
                 String sql = ("Update  MOVIE Set FAVORITE_LIST_ID =null where TITLE='"+text+"'");
                 Connection conn2 = DriverManager.getConnection(url,"Ergasia3","12345");
                 Statement stmt2 = conn2.createStatement();
                 stmt2.executeUpdate(sql); 
                 
             
             }
 
                    
                    
            }
}
             
                     

    
    
    
    
    
    
    
    
    
    

