package ru.mvlikhachev.movies.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.mvlikhachev.movies.R;
import ru.mvlikhachev.movies.data.MovieAdapter;
import ru.mvlikhachev.movies.model.Movie;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;

    //Search
    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

    }

    //Click on searchButton button
    public void searchMovies(View view) {
        movies.clear();
        getMovies();
    }

    private void getMovies() {

        String search = searchEditText.getText().toString().trim();

        String url = "http://www.omdbapi.com/?apikey=b251238d&s=" + search;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Search");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String posterUrl = jsonObject.getString("Poster");

                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setYear(year);
                        movie.setPosterUrl(posterUrl);

                        movies.add(movie);
                    }
                    movieAdapter = new MovieAdapter(MainActivity.this, movies);
                    recyclerView.setAdapter(movieAdapter);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        requestQueue.add(request);
    }
}