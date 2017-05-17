package com.android.databasserne.gutenberg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.databasserne.gutenberg.Adapters.MultilineArrayAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by kasper on 5/10/17.
 */

public class PageFragment extends Fragment implements OnMapReadyCallback {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View view;
    private MapView mapView;
    private GoogleMap map;
    private ListView resultsListView;
    private Button searchBtn;
    private MultilineArrayAdapter adapter;

    private ArrayList<SingleResult> dummyList;

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
                insertCityResults(resultsListView);
            }
        });
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
                insertBookResults();
            }
        });
    }

    private void setupAuthorView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.author_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.authorMap);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        resultsListView = (ListView) view.findViewById(R.id.authorResults);
        // TODO - Add dummy data to make sure it works with list of book titles aswell as city markers
    }

    private void setupLocationView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_fragment, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mapView.onResume();
    }

    private void insertCityResults(ListView view) {
        dummyList = getCityTestData();
        adapter = new MultilineArrayAdapter(this.getContext(), dummyList);
        view.setAdapter(adapter);
    }

    private void insertBookResults() {
        dummyList = getBookTestData();
        map.clear();

        LatLng latlong;

        for (SingleResult marker : dummyList) {
            latlong = new LatLng(Double.parseDouble(marker.getFirst()), Double.parseDouble(marker.getSecond()));

            map.addMarker(new MarkerOptions().position(latlong));
        }


    }

    // TODO - Change to use JSON data from rest api
    private ArrayList<SingleResult> getCityTestData() {
        ArrayList<SingleResult> tempList = new ArrayList<SingleResult>();

        SingleResult sr = new SingleResult();
        sr.setFirst("Shakespeare");
        sr.setSecond("Hamlet");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Shakespeare");
        sr.setSecond("Romeo & Juliet");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("Kasper for president");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("#Believe");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("#YOLO");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("#Hashtag");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("#TooManyBooks");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("How to fill data");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("This title needs to be long so i can test it - therefor blaalbblaalbblaalb");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("I am running out of ideas :S");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("Gotta find em all!");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("Mio");
        sr.setSecond("Android development 101");
        tempList.add(sr);

        return tempList;
    }

    private ArrayList<SingleResult> getBookTestData() {
        ArrayList<SingleResult> tempList = new ArrayList<SingleResult>();

        SingleResult sr = new SingleResult();
        sr.setFirst("51.509865");
        sr.setSecond("-0.118092");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("55.676098");
        sr.setSecond("12.568337");
        tempList.add(sr);

        sr = new SingleResult();
        sr.setFirst("59.334591");
        sr.setSecond("18.063240");
        tempList.add(sr);

        return tempList;
    }

}
