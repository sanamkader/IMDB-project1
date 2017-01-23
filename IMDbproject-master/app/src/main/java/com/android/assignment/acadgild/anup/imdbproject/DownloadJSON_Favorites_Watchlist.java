package com.android.assignment.acadgild.anup.imdbproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DownloadJSON_Favorites_Watchlist extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {
    ArrayList<IMDb_model> arrayList_imdb;
    RecyclerView fav_watch;
    Context context;
    IMDb_model imdb;
    DBHelper dbHelper = new DBHelper(context);
    RecyclerView currentView;
    int mode = 0;
    private ArrayList<HashMap<String, String>> arrayList;
    private JSONObject jsonObject;
    private String url_fav;

    public DownloadJSON_Favorites_Watchlist(Context applicationContext, RecyclerView view, int mode) {
        this.context = applicationContext;
        this.currentView = view;
        this.mode = mode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(Object... params) {
        arrayList_imdb = new ArrayList<>();
        arrayList_imdb.clear();

        if (mode == 1) {
            arrayList_imdb = dbHelper.get_favorite_entries();
            Log.e("Fav entry", String.valueOf(arrayList_imdb));
        } else if (mode == 2) {
            arrayList_imdb = dbHelper.get_watchlist_entries();
        }

        arrayList = new ArrayList<>();

        JSONdata jsoNdata = new JSONdata();
        try {
            for (int i = 0; i < arrayList_imdb.size(); i++) {
                url_fav = "http://api.themoviedb.org/3/movie/" + arrayList_imdb.get(i).getMovie_id()
                        + "?api_key=8496be0b2149805afa458ab8ec27560c";
                jsonObject = jsoNdata.getJSONFromURL(url_fav);
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("id", jsonObject.getString("id"));
                hashMap.put("original_title", jsonObject.getString("original_title"));
                hashMap.put("release_date", jsonObject.getString("release_date"));
                hashMap.put("popularity", jsonObject.getString("popularity"));
                hashMap.put("vote_count", jsonObject.getString("vote_count"));
                hashMap.put("vote_average", jsonObject.getString("vote_average"));
                hashMap.put("poster_path", "http://image.tmdb.org/t/p/original" +
                        jsonObject.getString("poster_path"));
                arrayList.add(hashMap);
            }

        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        Log.e("Arraylist fav",arrayList.toString());
        return arrayList;
    }
    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
        super.onPostExecute(result);
        MovieAdapter mAdapter = new MovieAdapter(context, result);
        currentView.setAdapter(mAdapter);
    }
}
