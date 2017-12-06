package com.team_monkey.team_monkeysetup;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by noahs on 12/5/2017.
 */

public class FavoritesTab extends android.support.v4.app.Fragment {

    private Buffer buffer;

    public FavoritesTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_tab, container, false);
        // Inflate the layout for this fragment
        ListView lv = (ListView) view.findViewById(R.id.favoritesList);

        String[] bufferData = getArguments().getStringArray("bufferFavTab");
        String[] favData = getArguments().getStringArray("bufferFavTab");

        buffer = new Buffer(getContext(),
                new LinkedList<String>(Arrays.asList(bufferData)),
                new LinkedList<String>(Arrays.asList(favData)));

        ArrayAdapter<String> lva = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, favData);
        lv.setAdapter(lva);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                buffer.FillClipboardWithFavoritesElementAtIndex(i);
                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.favoritesTab);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ((ClipboardScreenActivity) getActivity()).refreshNow();
                        Toast.makeText(getContext(), "Updating Favorites", Toast.LENGTH_LONG).show();
                    }
                }
        );

        return view;
    }
}