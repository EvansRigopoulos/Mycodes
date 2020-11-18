/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ergasia3;

import JsonPackage.GetJson;
import MODEL.FavoriteList;
import MODEL.Genre;

import MODEL.Movie;

import java.io.IOException;

import java.net.MalformedURLException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.json.JsonArray;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.swing.JOptionPane;

/**
 *
 * @author EvansPc
 */
public class MoviesData {

    
    ArrayList<String> Favlist = new ArrayList<>();
    public JsonArray Job;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Ergasia3PU");
    EntityManager em = emf.createEntityManager();

    Genre genreAction = new Genre();
    Genre genreRomance = new Genre();
    Genre genreSciFi = new Genre();

    
     public JsonArray getData() throws IOException, Exception {
        GetJson obj = new GetJson();
        return  obj.getJson().build();
    }

    public void DeleteDB() {
        em.getTransaction().begin();

        em.createQuery("Delete From Movie").executeUpdate();

        em.createQuery("Delete From Genre").executeUpdate();
        em.createQuery("Delete FROM FavoriteList").executeUpdate();
        em.getFlushMode();
        em.getTransaction().commit();

    }

    public void CreateDBGenre() throws IOException, Exception {

         Job = getData();
      
            
           
            em.getTransaction().begin();
            
               
                genreAction.setId(28);
                genreAction.setName("Action");
                em.persist(genreAction);
                
                
                genreRomance.setId(10749);
                genreRomance.setName("Romance");
                em.persist(genreRomance);
                
                genreSciFi.setId(878);
                genreSciFi.setName("Science Fiction");
                em.persist(genreSciFi);
                
            
            
            
           
        
         
        
         em.flush();
         em.getTransaction().commit();

        
      
    }

    public void createMovieDB() throws MalformedURLException, IOException, ParseException, Exception {

         //Εδώ στη βάση δεδομένων πάρθηκαν τα δεδομένα από τον πίνακα Job 
            //ο οποίος είναι JsonArray και εισήχθησαν στη βάση
            em.getTransaction().begin();   
            
      
            
            int result=0;
           
             
            System.out.println(Job.size());
               
            //Οι Itr1,itr2 είναι iterators και χρησιμοποιούνται για την  
            // προσπέλαση του Job.
            
            Iterator itr1 = Job.iterator();
            
            while(itr1.hasNext()){
              
            
                Iterator itr2 = Job.getJsonObject(result).getJsonArray("results").iterator();
                int itr3 = 0;
               
                while(itr2.hasNext()){
                 
                
              
                Movie movie = new Movie();
                
                              
                
                movie.setId(Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getInt("id"));
                movie.setTitle(Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getString("title"));
        
                
               int itr4 =Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getJsonArray("genre_ids").size();
               
              
            for(int g =0; g<itr4; g++){   
                if (Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getJsonArray("genre_ids").getInt(g)== genreAction.getId()) {
                    movie.setGenreId(genreAction);
                    
                                 
                    }
                    if (Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getJsonArray("genre_ids").getInt(g)== genreRomance.getId() ) {
                        movie.setGenreId(genreRomance);

                        

        
                    }
                    if (Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getJsonArray("genre_ids").getInt(g) == genreSciFi.getId()) {
                        movie.setGenreId(genreSciFi);

                        
                    }                 
               
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date releaseDate = sdf.parse(Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getString("release_date"));
                movie.setReleaseDate(releaseDate);
                movie.setOverview(Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getString("overview"));
                movie.setRating(Float.parseFloat(Job.getJsonObject(result).getJsonArray("results").getJsonObject(itr3).getJsonNumber("vote_average").toString()));
                em.persist(movie);
               
               
            itr2.next();
            itr3++;
            }   
            result++;
            itr1.next();
            }  
            System.out.println();
         em.getTransaction().commit();
            
                 
         
        
    }

   
    

    public void createFavListname(String text) throws SQLException {
        FavoriteList favlistname = new FavoriteList();
        String sql = "SELECT NAME  FROM FAVORITE_LIST  ";
        String url = "jdbc:derby://localhost:1527/MyMovies";
        Connection conn = DriverManager.getConnection(url, "Ergasia3", "12345");
        Statement stmt = conn.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery(sql);
        
        conn.setAutoCommit(false);
        while (rs.next()) {

            Favlist.add(rs.getString(1));
        }
        Boolean flag = false;
        String c = "";
        
        for (int i=0;i<Favlist.size();i++) {
           
            if (text.equals(Favlist.get(i))) {
                flag = true;
                
                JOptionPane.showMessageDialog(null, "name exists");
               
               break;
               
                
            }
            if(text.equals(c) && Favlist.isEmpty()){
               flag = true;
                JOptionPane.showMessageDialog(null, "name field empty give a name for list");
                break;
            }
            if(text.equals(c) ){
               flag = true;
                JOptionPane.showMessageDialog(null, "name field empty give a name for list");
                break;
               
           }
        }

        if (flag == false) {

            favlistname.setName(text);
            em.getTransaction().begin();
            em.persist(favlistname);
            
            em.flush();
            em.getTransaction().commit();
           
        }
    }
}
