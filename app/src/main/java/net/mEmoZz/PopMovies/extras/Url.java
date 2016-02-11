package net.mEmoZz.PopMovies.extras;

import net.mEmoZz.PopMovies.BuildConfig;

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
public class Url {

    // Main URL
    public static final String BASE_URL = "http://api.themoviedb.org/3";
    public static final String PARAM_DISC = "/discover";
    public static final String PARAM_MOV = "/movie";
    public static final String PARAM_QUESTION_MARK = "?";
    public static final String PARAM_PAGE_NO = "page=";
    public static final String PARAM_SORT = "&sort_by=";
    public static final String PARAM_ADULT = "&include_adult=";
    public static final String PARAM_API = "&api_key=";
    public static final String KEY = BuildConfig.API_KEY;

    // Images URL
    public static final String POSTERS_URL = "http://image.tmdb.org/t/p/";

    // Trailers Params
    public static final String PARAM_TR_API = "?api_key=";
    public static final String PARAM_TRAIL = "/videos";
        public static final String PARAM_THUMB = "/default.jpg";

    // Reviews Params
    public static final String PARAM_REVS = "/reviews";

    // YouTube URL
    public static final String VID_URL = "https://www.youtube.com/watch?v=";
    public static final String THUMB_URL = "http://img.youtube.com/vi/";
}
