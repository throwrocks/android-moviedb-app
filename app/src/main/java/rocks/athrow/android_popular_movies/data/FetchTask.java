package rocks.athrow.android_popular_movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;
import rocks.athrow.android_popular_movies.service.UpdateDBService;

/**
 * DataHandler
 * A class to manage making calls to query the API, parsing the JSON, and storing the
 * <p/>
 * Created by josel on 8/23/2016.
 */
public class FetchTask extends AsyncTask<String, Void, ContentValues[]> {
    public OnTaskComplete mListener = null;
    private Context mContext;

    public FetchTask(Context mContext, OnTaskComplete listener) {
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    protected ContentValues[] doInBackground(String... String) {
        APIResponse apiResponse = API.getMoviesFromAPI();
        String moviesResult = apiResponse.getResponseText();
        ContentValues[] moviesContentValues = null;
        if (moviesResult != null) {
            moviesContentValues = JSONParser.getMoviesFromJSON(moviesResult);
        }
        return moviesContentValues;
    }

    @Override
    protected void onPostExecute(ContentValues[] moviesContentValues) {
        super.onPostExecute(moviesContentValues);
        mListener.OnTaskComplete(moviesContentValues);
    }
}
