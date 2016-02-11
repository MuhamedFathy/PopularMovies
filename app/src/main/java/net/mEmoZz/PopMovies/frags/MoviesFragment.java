package net.mEmoZz.PopMovies.frags;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import net.mEmoZz.PopMovies.R;
import net.mEmoZz.PopMovies.adapters.CoreAdapter;
import net.mEmoZz.PopMovies.models.Movie;
import net.mEmoZz.PopMovies.utils.DBHelper;
import net.mEmoZz.PopMovies.utils.HttpManager;
import net.mEmoZz.PopMovies.utils.MoreListener;
import net.mEmoZz.PopMovies.views.ScaleInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static net.mEmoZz.PopMovies.activities.MainActivity.cornStamp;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_BACKDROP;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_DATE;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_ID;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_OVERVIEW;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_POSTER;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_TITLE;
import static net.mEmoZz.PopMovies.extras.Keys.KEY_VOTE_AVR;
import static net.mEmoZz.PopMovies.extras.Url.BASE_URL;
import static net.mEmoZz.PopMovies.extras.Url.KEY;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_ADULT;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_API;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_DISC;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_MOV;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_PAGE_NO;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_QUESTION_MARK;
import static net.mEmoZz.PopMovies.extras.Url.PARAM_SORT;

/**
 * Copyright (C) 2015 Mohamed Fathy
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class MoviesFragment extends Fragment {

    public static final String TAG = "---->";
    public static final String EX = " Exception";
    public static final String SORT_SATE = "state";
    private static final String SORT = "sort_data";

    public static MenuItem up, pop, high, fav;
    public static RecyclerView gridView;
    public static CoreAdapter adapter;

    private SwipeRefreshLayout mRefreshLayout;
    private List<Movie> movies = new ArrayList<>();
    private LinearLayoutManager manager;
    private DBHelper helper;
    private ImageView noConn;
    private TextView noConnTxt;
    private Handler handler = new Handler();
    private Animation anim;
    private Configuration conf;
    private int pageNum = 1;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        noConn = (ImageView) v.findViewById(R.id.noConn);
        noConnTxt = (TextView) v.findViewById(R.id.noConnTxt);
        gridView = (RecyclerView) v.findViewById(R.id.mainGrid);
        mRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout);
        mRefreshLayout.setColorSchemeColors(0xff6b4d9c);
        conf = getResources().getConfiguration();
        int rotation = getResources().getConfiguration().orientation;

        LinearLayoutManager tabPort = manager = new GridLayoutManager(getActivity(), 1);
        LinearLayoutManager tabLand = manager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager mobPort = manager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager mobLand = manager = new GridLayoutManager(getActivity(), 3);

        if (conf.smallestScreenWidthDp >= 600) {
            switch (rotation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    gridView.setLayoutManager(tabPort);
                    loadMore(tabPort);
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    gridView.setLayoutManager(tabLand);
                    loadMore(tabLand);
                    break;
            }
        } else {
            switch (rotation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    gridView.setLayoutManager(mobPort);
                    loadMore(mobPort);
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    gridView.setLayoutManager(mobLand);
                    loadMore(mobLand);
                    break;
            }
        }
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        helper = new DBHelper(getActivity());
        if (isNetworkAvailable()) {
            handler.postDelayed(refreshing, 100);
            new CountDownTimer(400, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    if (conf.smallestScreenWidthDp >= 600) {
                        cornStamp.setVisibility(VISIBLE);
                        cornStamp.startAnimation(anim);
                    }
                }
            }.start();
        } else {
            noConn.setVisibility(VISIBLE);
            noConnTxt.setVisibility(VISIBLE);
        }

        anim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        anim.setDuration(200);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    movies.clear();
                    SharedPreferences prefs = getActivity().getSharedPreferences(SORT, 0);
                    String state = prefs.getString(SORT_SATE, "No pop!");
                    if (state.equals("popChecked")) {
                        fullTask("popularity.desc");
                        adapter.notifyDataSetChanged();
                    } else if (state.equals("highChecked")) {
                        fullTask("vote_average.desc");
                        adapter.notifyDataSetChanged();
                    } else if (state.equals("favChecked")) {
                        dbTask();
                    }
                    new CountDownTimer(400, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            if (conf.smallestScreenWidthDp >= 600) {
                                cornStamp.setVisibility(VISIBLE);
                                cornStamp.startAnimation(anim);
                            }
                        }
                    }.start();
                } else {
                    gridView.setVisibility(GONE);
                    noConn.setVisibility(VISIBLE);
                    noConnTxt.setVisibility(VISIBLE);
                    mRefreshLayout.setRefreshing(false);
                    if (conf.smallestScreenWidthDp >= 600) {
                        cornStamp.setVisibility(VISIBLE);
                        cornStamp.startAnimation(anim);
                    }
                }
            }
        });
    }

    private void loadMore(final LinearLayoutManager manager) {
        gridView.addOnScrollListener(new MoreListener(manager) {
            @Override
            public void onLoadMore(int current_page) {
                if (pop.isChecked()) {
                    pageNum++;
                    fullTaskEnd("popularity.desc");
                }
                if (high.isChecked()) {
                    fullTaskEnd("vote_average.desc");
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_fragment, menu);
        up = menu.findItem(R.id.action_up).setVisible(false);
        pop = menu.findItem(R.id.action_pop);
        high = menu.findItem(R.id.action_high);
        fav = menu.findItem(R.id.action_fav);
        SharedPreferences prefs = getActivity().getSharedPreferences(SORT, 0);
        SharedPreferences.Editor editor = prefs.edit();
        if (pop.isChecked()) {
            editor.putString(SORT_SATE, "popChecked");
        } else if (high.isChecked()) {
            editor.putString(SORT_SATE, "highChecked");
        } else {
            editor.putString(SORT_SATE, "favChecked");
        }
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_pop:
                if (pop.isChecked()) {
                    return false;
                } else {
                    pop.setChecked(true);
                    movies.clear();
                    adapter.clear();
                    fullTask("popularity.desc");
                    adapter.notifyDataSetChanged();
                    gridView.setAdapter(adapter);
                }
                break;
            case R.id.action_high:
                if (high.isChecked()) {
                    return false;
                } else {
                    high.setChecked(true);
                    movies.clear();
                    if (adapter != null) {
                        adapter.clear();
                        fullTask("vote_average.desc");
                        adapter.notifyDataSetChanged();
                        gridView.setAdapter(adapter);
                    }
                }
                break;
            case R.id.action_fav:
                if (fav.isChecked()) {
                    return false;
                } else {
                    fav.setChecked(true);
                    dbTask();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager mConnectivity =
                (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivity.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting();
    }

    public static void setAdapter(CoreAdapter adapter) {
        gridView.setAdapter(adapter);
    }

    private void dbTask() {
        adapter = new CoreAdapter(getOfflineData(), getActivity(), getActivity());
        gridView.setAdapter(adapter);
        mRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    public List<Movie> getOfflineData() {
        List<Movie> movies = new ArrayList<>();
        Cursor c = helper.getData();
        if (c != null) {
            while (c.moveToNext()) {
                String movId = c.getString(1);
                String movPoster = c.getString(2);
                String movBackdrop = c.getString(3);
                String movName = c.getString(4);
                String movDate = c.getString(5);
                String movVote = c.getString(6);
                String movOverview = c.getString(7);

                Movie movie = new Movie();
                movie.setId(movId);
                movie.setPoster(movPoster);
                movie.setBackdrop(movBackdrop);
                movie.setTitle(movName);
                movie.setDate(movDate);
                movie.setVoteAvr(movVote);
                movie.setOverview(movOverview);
                movies.add(movie);
            }
            return movies;
        } else {
            return null;
        }
    }

    private ScaleInAnimationAdapter adapterAnim(CoreAdapter adapter) {
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
        alphaAdapter.setDuration(200);
        return alphaAdapter;
    }

    private void fullTask(final String sort) {
        parseMovies(1, sort);
    }

    private void fullTaskEnd(final String sort) {
        parseMovies(pageNum, sort);
    }

    private void parseMovies(int page, String sort) {
        new MoviesTask().execute(mainUrl(page, sort));
        noConn.setVisibility(GONE);
        noConnTxt.setVisibility(GONE);
    }

    private String mainUrl(int page, String sort) {
        return BASE_URL + PARAM_DISC + PARAM_MOV + PARAM_QUESTION_MARK
                + PARAM_PAGE_NO + page + PARAM_SORT + sort
                + PARAM_ADULT + "false" + PARAM_API + KEY;
    }

    private final Runnable refreshing = new Runnable() {
        public void run() {
            mRefreshLayout.setRefreshing(true);
            try {
                if (mRefreshLayout.isRefreshing()) {
                    fullTask("popularity.desc");
                    handler.removeCallbacks(this);
                } else {
                    mRefreshLayout.setRefreshing(false);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    };

    private class MoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            gridView.setVisibility(GONE);
            if (!mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(true);
            }
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            String response = HttpManager.getJSON(params[0]);
            try {
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    JSONArray arr = object.getJSONArray("results");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jObj = arr.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(jObj.getString(KEY_ID));
                        movie.setPoster(jObj.getString(KEY_POSTER));
                        movie.setBackdrop(jObj.getString(KEY_BACKDROP));
                        movie.setTitle(jObj.getString(KEY_TITLE));
                        movie.setOverview(jObj.getString(KEY_OVERVIEW));
                        movie.setDate(jObj.getString(KEY_DATE));
                        movie.setVoteAvr(jObj.getString(KEY_VOTE_AVR));
                        movies.add(movie);
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG + " Mov" + EX, e.getMessage());
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            adapter = new CoreAdapter(movies, getActivity(), getActivity());
            final ScaleInAnimationAdapter alphaAdapter = adapterAnim(adapter);
            gridView.setAdapter(alphaAdapter);
            gridView.startAnimation(anim);
            gridView.setVisibility(VISIBLE);
            adapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }
    }
}