package com.example.whatsthewait.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsthewait.R;
import com.example.whatsthewait.ReservationAdapter;
import com.example.whatsthewait.ReservationItem;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {

    public static final String TAG = "ReservationFragment";

    private RecyclerView rvReservations;
    private ReservationAdapter adapter;
    private List<ReservationItem> allReservations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false);    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvReservations = view.findViewById(R.id.rvReservations);

        allReservations = new ArrayList<>();
        adapter = new ReservationAdapter(getContext(), allReservations);
        rvReservations.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReservations.setLayoutManager(linearLayoutManager);

        // Testing the RV with sample Restaurants
        allReservations.add(new ReservationItem());
        allReservations.add(new ReservationItem());
        allReservations.add(new ReservationItem());
        allReservations.add(new ReservationItem());
        adapter.notifyDataSetChanged();
    }
}
