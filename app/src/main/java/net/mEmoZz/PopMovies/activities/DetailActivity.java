package net.mEmoZz.PopMovies.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.mEmoZz.PopMovies.R;
import net.mEmoZz.PopMovies.extras.Url;
import net.mEmoZz.PopMovies.frags.MoviesFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap;

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
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Regular.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(this.getIntent().getExtras().getString("title"));

        ImageView detailBackdrop = (ImageView) findViewById(R.id.backdrop);
        String backdropUrl = Url.POSTERS_URL + "w780"
                + this.getIntent().getExtras().getString("backdrop");

        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .displayer(new FadeInBitmapDisplayer(1500))
                .showImageOnFail(R.drawable.no_poster)
                .showImageForEmptyUri(R.drawable.no_poster)
                .build();

        ImageLoader.getInstance().displayImage(
                backdropUrl,
                detailBackdrop,
                mOptions,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        Palette p = Palette.from(loadedImage).generate();
                        collapsingToolbar.setBackgroundColor(p.getVibrantColor(0));
                        collapsingToolbar.setContentScrimColor(p.getVibrantColor(0));
                        if (p.getVibrantColor(0) == Color.TRANSPARENT) {
                            collapsingToolbar.setBackgroundColor(p.getMutedColor(0));
                            collapsingToolbar.setContentScrimColor(p.getMutedColor(0));
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MoviesFragment.adapter.notifyDataSetChanged();
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(wrap(newBase));
    }
}
