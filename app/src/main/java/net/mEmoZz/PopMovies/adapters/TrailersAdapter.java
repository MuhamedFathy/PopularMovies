package net.mEmoZz.PopMovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import net.mEmoZz.PopMovies.R;
import net.mEmoZz.PopMovies.models.Trailer;

import java.util.List;

import static net.mEmoZz.PopMovies.extras.Url.PARAM_THUMB;
import static net.mEmoZz.PopMovies.extras.Url.THUMB_URL;
import static net.mEmoZz.PopMovies.extras.Url.VID_URL;
import static net.mEmoZz.PopMovies.frags.DetailFragment.trailerList;

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
public class TrailersAdapter extends BaseAdapter {

    private List<Trailer> trailers;
    private Context context;
    private LayoutInflater inflater;

    public TrailersAdapter(List<Trailer> trailers, Context context) {
        this.trailers = trailers;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.video_item, parent, false);
            holder = new ViewHolder();
            holder.tubeThumb = (ImageView) convertView.findViewById(R.id.tubeThumb);
            holder.trailerTitle = (TextView) convertView.findViewById(R.id.trailerTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Trailer trailer = trailers.get(position);
        String thumbUrl = THUMB_URL + trailer.getKey() + PARAM_THUMB;
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .displayer(new FadeInBitmapDisplayer(1500))
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.vid_hover)
                .showImageOnFail(R.drawable.vid_hover)
                .build();

        ImageLoader.getInstance()
                .displayImage(thumbUrl, holder.tubeThumb, mOptions);
        holder.tubeThumb.setImageResource(R.drawable.vid_hover);
        for (int i = 0; i < trailers.size(); i++) {
            holder.trailerTitle.setText(trailer.getName());
        }
        trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vidUrl = VID_URL + trailers.get(position).getKey();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(vidUrl)));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView tubeThumb;
        TextView trailerTitle;
    }
}
