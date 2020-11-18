/**
 *
 * @author EvansPc
 */
package JsonPackage;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

// Το αντικέιμενο αυτής της κλάσης χρησιμοποιείται για την εισαγωγή δεδομένων
//στον πίνακα της βάσης. Η κλήση γίνεται μια φορά και μπορεί να γίνει μέχρι 39 σελίδες.
public class GetJson {

    private final String apiKey = "6bf40514c58b4d35e8e74863ab8be752";
    private int j = 1;

    private JsonObject getMovieData(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        InputStream is = url.openStream();
        
        JsonReader rdr = Json.createReader(is);
        
        return rdr.readObject();
    }

    public JsonArrayBuilder getJson() throws Exception, IOException, MalformedURLException {
        String dataURL = "https://api.themoviedb.org/3/discover/movie?with_genres=28,10749,878&primary_release_date.gte=2000-01-01&api_key=" + apiKey;
        System.out.println(dataURL);
        int page = getMovieData(dataURL).getInt("total_pages");
        
       

       
        
         JsonArrayBuilder ab = Json.createArrayBuilder();
        
        for (j = 0; j < 20; j++) {
            JsonObject ok;
            String dataURLr = "https://api.themoviedb.org/3/discover/movie?page=" + (j + 1) + "&with_genres=28,10749,878&primary_release_date.gte=2000-01-01&api_key=" + apiKey;
            ok = getMovieData(dataURLr);
           
            ab.add(ok);
        }

        return ab;
    }

    public GetJson() {
    }

}
