package MODEL;

import MODEL.FavoriteList;
import MODEL.Genre;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-14T20:59:30")
@StaticMetamodel(Movie.class)
public class Movie_ { 

    public static volatile SingularAttribute<Movie, Genre> genreId;
    public static volatile SingularAttribute<Movie, String> overview;
    public static volatile SingularAttribute<Movie, FavoriteList> favoriteListId;
    public static volatile SingularAttribute<Movie, Date> releaseDate;
    public static volatile SingularAttribute<Movie, Float> rating;
    public static volatile SingularAttribute<Movie, Integer> id;
    public static volatile SingularAttribute<Movie, String> title;

}