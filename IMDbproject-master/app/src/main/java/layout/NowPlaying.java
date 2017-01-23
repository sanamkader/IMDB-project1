package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.assignment.acadgild.anup.imdbproject.DownloadJSON;
import com.android.assignment.acadgild.anup.imdbproject.DownloadJSON_Favorites_Watchlist;
import com.android.assignment.acadgild.anup.imdbproject.R;

public class NowPlaying extends Fragment {

    public NowPlaying() {
    }

    RecyclerView lv_nowPlaying;
    public View view_nowPlaying;
    int mode =0;
    String URL_NP = "http://api.themoviedb.org/3/movie/now_playing?api_key=8496be0b2149805afa458ab8ec27560c";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onPause() {
        setRetainInstance(true);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onViewCreated(container, savedInstanceState);
        view_nowPlaying = inflater.inflate(R.layout.fragment_now_playing, container, false);
        lv_nowPlaying = (RecyclerView) view_nowPlaying.findViewById(R.id.recyclerView_nowPlaying);
        loadRecyclerViewData();
        return view_nowPlaying;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_favorites:
                mode = 1;
                DownloadJSON_Favorites_Watchlist downloadJSON_favorites_watchlist = new DownloadJSON_Favorites_Watchlist(getActivity().getApplicationContext(), lv_nowPlaying, mode);
                downloadJSON_favorites_watchlist.execute();
                break;
            case R.id.menu_watchlist:
                mode = 2;
//                DownloadJSON_Favorites_Watchlist downloadJSON_favorites_watchlist1 = new DownloadJSON_Favorites_Watchlist(getApplicationContext(), mode);
//                downloadJSON_favorites_watchlist1.execute();
                break;
            case R.id.menu_refresh:
                    new NowPlaying();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void loadRecyclerViewData(){
        DownloadJSON downloadJSON = new DownloadJSON(getActivity().getApplication().getApplicationContext(), lv_nowPlaying);
        downloadJSON.getJSON(URL_NP);
    }

}






