package mx.evin.udacity.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import okio.Buffer;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Url;

/**
 * Created by evin on 1/3/16.
 */
public class RetrieveMovies extends AsyncTask<String, Void, Void>{

    private final String TAG = "PopularMoviesAsyncTAG";

    @Override
    protected Void doInBackground(String... params) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient httpClient = new OkHttpClient();
// add your other interceptors …

// add logging as last interceptor
        httpClient.interceptors().add(logging);  // <-- this is the important line!
        httpClient.interceptors().add(new LoggingInterceptor());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);
//
        Call<Page> listCall = service.listMovies();
        try{
            Page results = listCall.execute().body();
            for (Result result: results.getResults()
                 ) {
                System.out.println(result.getTitle() + " " + result.getPosterPath());
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
//        Log.d(TAG, listCall.toString());
//        try{
//            final List<Movie> results;
////            listCall.enqueue(new Callback<List<Movie>>() {
////                @Override
////                public void onResponse(Response<List<Movie>> response, Retrofit retrofit) {
////                    Log.d(TAG + "as", retrofit.baseUrl().url().toString());
////                    Log.d(TAG + "as", response.message());
////                    for (Movie result : response.body()) {
////                        Log.d(TAG, result.toString());
////                    }
////                    return;
////                }
////
////                @Override
////                public void onFailure(Throwable t) {
////                    Log.d(TAG + "as", t.toString());
////                    Log.d(TAG + "as", t.getMessage());
////                    t.printStackTrace();
////                    return;
////                }
////            });
////
////            listCall.execute().raw();
////
////            Gson gson = new Gson();
////            Result[] users = gson.fromJson(json, User[].class);
////            System.out.println(Arrays.toString(users));
////or since we know that there will be only one object in array
////            System.out.println(users[0]);
//
//            helloWorld();
//
//        }catch (Exception e){
//            Log.d(TAG, "Something didn't go well " + e.toString());
//        }
//        helloWorld();
        return null;
    }

    public interface MovieDBService {
        @GET("/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=100&api_key=" + Constants.MDB_API_KEY)
        Call<Page> listMovies();
    }

    void helloWorld(){
        String reader = getURL("http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=100&api_key=" + Constants.MDB_API_KEY);
//        try{
//            JSONObject jsonObject = new JSONObject(reader);
//            System.out.println("jsonObject = " + jsonObject.getInt("page"));
//            JSONArray array = (JSONArray) jsonObject.get("results");
//            System.out.println(array);
//        }catch (Exception e){
//            System.out.println(e.toString());
//        }
        Gson gson = new GsonBuilder().create();
        Page p = gson.fromJson(reader, Page.class);
////        Result r = gson.fromJson(reader, Result.class);
        Log.d("dwasddsadas", p.getResults().get(0).toString());
//        System.out.println(p);
////        System.out.println(r);
//
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=100&api_key=" + Constants.MDB_API_KEY);
//        stringBuilder.append("?api_key=" + Constants.MDB_API_KEY);
//        stringBuilder.append("&query=" + query);

        InputStream stream = null;
        try {
            URL url = new URL(stringBuilder.toString());
            // Establish a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept", "application/json"); // Required to get TMDB to play nicely.
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

            stream = conn.getInputStream();
//            Log.d(TAG, stringify(stream));
//            Log.d(TAG, reader);
            parseResult(stringify(stream));
//            parseResult(reader);
        } catch (Exception e){
        } finally {
            if (stream != null) {
                try{
                    stream.close();
                }catch (Exception e){

                }
            }
        }

    }

    private ArrayList<Page> parseResult(String result) {
        String streamAsString = result;
        ArrayList<Page> results = new ArrayList<Page>();
        try {
            JSONObject jsonObject = new JSONObject(streamAsString);
            JSONArray array = (JSONArray) jsonObject.get("results");
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.getJSONObject(i).getString("poster_path"));
            }
//                JSONObject jsonMovieObject = array.getJSONObject(i);
//                Builder movieBuilder = MovieResult.newBuilder(
//                        Integer.parseInt(jsonMovieObject.getString("id")),
//                        jsonMovieObject.getString("title"))
//                        .setBackdropPath(jsonMovieObject.getString("backdrop_path"))
//                        .setOriginalTitle(jsonMovieObject.getString("original_title"))
//                        .setPopularity(jsonMovieObject.getString("popularity"))
//                        .setPosterPath(jsonMovieObject.getString("poster_path"))
//                        .setReleaseDate(jsonMovieObject.getString("release_date"));
//                results.add(movieBuilder.build());
//            }
        } catch (JSONException e) {
            System.err.println(e);
            Log.d(TAG, "Error parsing JSON. String was: " + streamAsString);
        }
        return results;
    }

    public String stringify(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.readLine();
    }

    private String getURL(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
//            Log.d(TAG, response.body().string());
            return response.body().string();
        }catch (Exception e){
            return "";
        }
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public com.squareup.okhttp.Response intercept(Interceptor.Chain chain) throws IOException {
            Log.i("LoggingInterceptor", "inside intercept callback");
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }
            Log.d("TAG", "request" + "\n" + requestLog);
            com.squareup.okhttp.Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG", "response only" + "\n" + bodyString);

            Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();

        }


        public static String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }

}
