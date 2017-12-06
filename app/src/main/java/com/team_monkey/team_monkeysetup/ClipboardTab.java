package com.team_monkey.team_monkeysetup;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ClipboardTab extends Fragment {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Buffer buffer;

    public ClipboardTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clipboard_tab, container, false);
        // Inflate the layout for this fragment

        final ListView lv = (ListView) view.findViewById(R.id.clipboardList);

        String[] data = getArguments().getStringArray("bufferTab");
        buffer = new Buffer(getContext(), new LinkedList<String>(Arrays.asList(data)));
        ArrayAdapter<String> lva = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, data);
        lv.setAdapter(lva);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                buffer.FillClipboardWithElementAtIndex(i);
                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.clipboardTab);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ((ClipboardScreenActivity) getActivity()).refreshNow();
                        Toast.makeText(getContext(), "Updating Clipboard", Toast.LENGTH_LONG).show();
                    }
                }
        );

        return view;
    }
}
