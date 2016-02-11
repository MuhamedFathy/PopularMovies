package net.mEmoZz.PopMovies.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;

import net.mEmoZz.PopMovies.R;

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
 * <p/>
 * The solution from this guy:
 * http://stackoverflow.com/a/23203614/4043944
 */
public class DetailShareProvider extends ShareActionProvider {

    private final Context mContext;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public DetailShareProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        ActivityChooserView chooserView =
                (ActivityChooserView) super.onCreateActionView();

        // Set your drawable here
        Drawable icon =
                mContext.getResources().getDrawable(R.drawable.action_share);
        chooserView.setExpandActivityOverflowButtonDrawable(icon);

        return chooserView;
    }
}
