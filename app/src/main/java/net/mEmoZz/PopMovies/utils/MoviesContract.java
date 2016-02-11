package net.mEmoZz.PopMovies.utils;

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
public class MoviesContract {

    public static final class MoviesEntry {

        public static final String TABLE_NAME = "movies";
        public static final String COL_ID = "id";
        public static final String COL_POSTER = "poster";
        public static final String COL_BACKDROP = "backdrop";
        public static final String COL_MOV_ID = "movie_id";
        public static final String COL_MOV_NAME = "mov_name";
        public static final String COL_VOTE = "vote_avr";
        public static final String COL_DATE = "year";
        public static final String COL_OVERVIEW = "overview";

    }
}
