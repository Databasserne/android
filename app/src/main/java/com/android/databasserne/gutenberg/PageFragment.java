package com.android.databasserne.gutenberg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kasper on 5/10/17.
 */

public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View view;

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
//            case 1: {
//                view = inflater.inflate(R.layout.city_fragment, container, false);
//                break;
//            }
            case 2: {
                view = inflater.inflate(R.layout.book_fragment, container, false);
                break;
            }
            case 3: {
                view = inflater.inflate(R.layout.author_fragment, container, false);
                break;
            }
            case 4: {
                view = inflater.inflate(R.layout.location_fragment, container, false);
                break;
            }
            default: {
                view = inflater.inflate(R.layout.city_fragment, container, false);
                break;
            }
        }
        return view;
    }
}
