package com.android.databasserne.gutenberg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.databasserne.gutenberg.Adapters.MultilineArrayAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kasper on 5/10/17.
 */

public class PageFragment extends Fragment implements OnMapReadyCallback {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static String server = "http://bdea7eae.ngrok.io/web/api/";

    private int mPage;
    private View view;
    private MapView mapView;
    private GoogleMap map;
    private ListView resultsListView;
    private Button searchBtn;
    private MultilineArrayAdapter adapter;
    boolean authorLastClicked = true;
    private TextView test;
    private EditText input;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        switch (mPage) {
            default: {
                setupCityView(inflater, container, savedInstanceState);
                break;
            }
            case 2: {
                setupBookView(inflater, container, savedInstanceState);
                break;
            }
            case 3: {
                setupAuthorView(inflater, container, savedInstanceState);
                break;
            }
            case 4: {
                setupLocationView(inflater, container, savedInstanceState);
                break;
            }
        }
        return view;
    }

    private void setupCityView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.city_fragment, container, false);
        resultsListView = (ListView) view.findViewById(R.id.cityResults);
        searchBtn = (Button) view.findViewById(R.id.cityButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCityQueryData(resultsListView, "city");
            }
        });
        test = (TextView) view.findViewById(R.id.fragment);
        input = (EditText) view.findViewById(R.id.cityInsert);
    }

    private void setupBookView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.bookMap);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        searchBtn = (Button) view.findViewById(R.id.bookButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBookQueryData("book");
            }
        });
        test = (TextView) view.findViewById(R.id.fragment2);
        input = (EditText) view.findViewById(R.id.bookInsert);
    }

    private void setupAuthorView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.author_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.authorMap);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        resultsListView = (ListView) view.findViewById(R.id.authorResults);

        Button listBtn = (Button) view.findViewById(R.id.authorBtnList);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authorLastClicked = true;
                mapView.onPause();
                mapView.setVisibility(View.GONE);
                resultsListView.setVisibility(View.VISIBLE);
            }
        });

        Button mapBtn = (Button) view.findViewById(R.id.authorBtnMap);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authorLastClicked = false;
                mapView.onResume();
                resultsListView.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);

            }
        });

        searchBtn = (Button) view.findViewById(R.id.authorButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAuthorQueryData(resultsListView, "author");
            }
        });
        test = (TextView) view.findViewById(R.id.fragment3);
        input = (EditText) view.findViewById(R.id.authorInsert);

    }

    private void setupLocationView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_fragment, container, false);
        resultsListView = (ListView) view.findViewById(R.id.locationResults);
        searchBtn = (Button) view.findViewById(R.id.locationButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationQueryData(resultsListView, "location");
            }
        });
        test = (TextView) view.findViewById(R.id.fragment4);
        input = (EditText) view.findViewById(R.id.locationInsert);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mapView.onResume();
    }

    private void getCityQueryData(final ListView view, String query) {
        final ArrayList<SingleResult> tempList = new ArrayList<SingleResult>();
        String db = MainActivity.database + "/";
        String type = query + "/";
        String city = input.getText().toString();
        String url = server + db + type + city;

        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject temp = arr.getJSONObject(i);
                                JSONObject authorArr = temp.getJSONObject("author");
                                String authorName = authorArr.getString("name");

                                //Add to list
                                SingleResult sr = new SingleResult();
                                sr.setFirst(authorName);
                                sr.setSecond(temp.getString("name"));
                                tempList.add(sr);
                            }
                            adapter = new MultilineArrayAdapter(getContext(), tempList);
                            view.setAdapter(adapter);

                        } catch (Exception e) {
                            test.setText("Shit hit the fan: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText(getString(R.string.http_error) + server);
            }
        });
        queue.add(stringRequest);
    }

    private void getBookQueryData(String query) {
        final ArrayList<SingleResult> tempList = new ArrayList<SingleResult>();

        String db = MainActivity.database + "/";
        String type = query + "/";
        String book = input.getText().toString();
        //Avoid error on whitespace
        book = book.replaceAll(" ", "%20");
        String url = server + db + type + book;

        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        //Make sure map is clear
        map.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LatLng latlong;
                        try {
                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject temp = arr.getJSONObject(i);
                                double lat = temp.getDouble("geolat");
                                double lng = temp.getDouble("geolng");
                                latlong = new LatLng(lat, lng);
                                map.addMarker(new MarkerOptions().position(latlong).title("test"));
                            }

                        } catch (Exception e) {
                            test.setText("Shit hit the fan: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText(getString(R.string.http_error) + server + " --- ");
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    private void getAuthorQueryData(final ListView view, String query) {
        final ArrayList<SingleResult> tempList = new ArrayList<SingleResult>();
        final Set<String> tempSet = new HashSet<>();

        String db = MainActivity.database + "/";
        String type = query + "/";
        String author = input.getText().toString();
        author = author.replaceAll(" ", "%20");
        String url = server + db + type + author;

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LatLng latlong;
                        try {
                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject temp = arr.getJSONObject(i);
                                JSONArray citiesArr = temp.getJSONArray("cities");
                                JSONObject tempCity = citiesArr.getJSONObject(0);
                                double lat = tempCity.getDouble("geolat");
                                double lng = tempCity.getDouble("geolng");
                                latlong = new LatLng(lat, lng);

                                map.addMarker(new MarkerOptions().position(latlong));
                                //To avoid duplicate books
                                tempSet.add(temp.getString("name"));
                            }

                            //Add each book from set to list to be shown.
                            for (String single : tempSet) {
                                SingleResult sr = new SingleResult();
                                sr.setFirst(single);
                                sr.setSecond(null);
                                tempList.add(sr);
                            }

                            adapter = new MultilineArrayAdapter(getContext(), tempList);
                            view.setAdapter(adapter);

                        } catch (Exception e) {
                            test.setText("Shit hit the fan: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText(getString(R.string.http_error) + server);
            }
        });
        queue.add(stringRequest);
    }

    private void getLocationQueryData(final ListView view, String query) {
        final ArrayList<SingleResult> tempList = new ArrayList<SingleResult>();
        final Set<String> tempSet = new HashSet<>();

        String db = MainActivity.database+ "/";
        String type = query + "/";
        String city = input.getText().toString();
        String url = server + db + type + city;

        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject temp = arr.getJSONObject(i);

                                tempSet.add(temp.getString("name"));
                            }

                            //Add each book from set to list to be shown.
                            for (String single : tempSet) {
                                SingleResult sr = new SingleResult();
                                sr.setFirst(single);
                                sr.setSecond(null);
                                tempList.add(sr);
                            }

                            adapter = new MultilineArrayAdapter(getContext(), tempList);
                            view.setAdapter(adapter);

                        } catch (Exception e) {
                            test.setText("Shit hit the fan: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText(getString(R.string.http_error) + server);
            }
        });
        queue.add(stringRequest);
    }
}
