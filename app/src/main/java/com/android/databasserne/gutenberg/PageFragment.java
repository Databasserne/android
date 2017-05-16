package com.android.databasserne.gutenberg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by kasper on 5/10/17.
 */

public class PageFragment extends Fragment implements OnMapReadyCallback {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View view;
    private MapView mapView;
    private GoogleMap map;
    private ListView authorRes;

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
    }

    private void setupBookView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.bookMap);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
    }

    private void setupAuthorView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.author_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.authorMap);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        authorRes = (ListView) view.findViewById(R.id.authorResults);
        // TODO - Add dummy data to make sure it works with list of book titles aswell as city markers
    }

    private void setupLocationView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_fragment, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView.onResume();
    }
}
