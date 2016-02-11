package net.mEmoZz.PopMovies.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.mEmoZz.PopMovies.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT;
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
public class MainActivity extends AppCompatActivity {

    public static RelativeLayout cornStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Calligraphy lib.
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Regular.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_main);

        // Initialize UIL.
        ImageLoaderConfiguration mConfiguration =
                new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(mConfiguration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout bar = (AppBarLayout) findViewById(R.id.appBar);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
        bar.measure(WRAP_CONTENT, WRAP_CONTENT);
        int num = bar.getMeasuredHeight();
        layout.setPadding(0, num, 0, 0);
        cornStamp = (RelativeLayout) findViewById(R.id.cornStamp);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(wrap(newBase));
    }
}

