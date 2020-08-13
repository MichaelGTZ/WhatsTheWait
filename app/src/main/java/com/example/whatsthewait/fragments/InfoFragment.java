package com.example.whatsthewait.fragments;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.whatsthewait.R;
import com.example.whatsthewait.RestaurantDetail;
import com.example.whatsthewait.models.BusinessDetailed;
import com.example.whatsthewait.models.Hour;
import com.example.whatsthewait.models.Open;
import com.example.whatsthewait.models.SpecialHour;

import org.parceler.Parcels;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class InfoFragment extends Fragment {

    public static final String TAG = "Info Fragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private TextView tvHours;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvDiningOptions;
    private TextView tvSpecialHours;
    private TextView tvViewYelp;

    private int mPage;
    private BusinessDetailed businessDetailed;

    public static InfoFragment newInstance(int page, BusinessDetailed businessDetailed) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putParcelable("restaurant", Parcels.wrap(businessDetailed));
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        businessDetailed = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHours = view.findViewById(R.id.tvHours);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvDiningOptions = view.findViewById(R.id.tvDiningOptions);
        tvSpecialHours = view.findViewById(R.id.tvSpecialHours);
        tvViewYelp = view.findViewById(R.id.tvYelpWebsite);

        if (businessDetailed.getHours() == null) {
            tvHours.setText("No hours provided");
        } else {
            String hoursString = "";
            for (int i = 0; i < businessDetailed.getHours().get(0).getOpen().size(); i++) {
                switch (i) {
                    case 0:
                        try {
                            hoursString += "Monday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            hoursString += "\nTuesday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            hoursString += "\nWednesday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            hoursString += "\nThursday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            hoursString += "\nFriday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            hoursString += "\nSaturday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            hoursString += "\nSunday: " + convertTime(businessDetailed.getHours().get(0).getOpen().get(i).getStart(), businessDetailed.getHours().get(0).getOpen().get(i).getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
            tvHours.setText(hoursString);
        }

        tvAddress.setText(String.format("%s %s", businessDetailed.getLocation().getDisplayAddress().get(0), businessDetailed.getLocation().getDisplayAddress().get(1)));
        tvPhone.setText(businessDetailed.getDisplayPhone());
        String transaction1;
        String transaction2;
        String transaction3;
        switch (businessDetailed.getTransactions().size()) {
            case 0:
                tvDiningOptions.setText("No dining options provided");
                break;
            case 1:
                transaction1 = businessDetailed.getTransactions().get(0);
                if (transaction1.startsWith("r")) {
                    transaction1 = "reservations";
                }
                tvDiningOptions.setText(transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1));
                break;
            case 2:
                transaction1 = businessDetailed.getTransactions().get(0);
                transaction2 = businessDetailed.getTransactions().get(1);
                if (transaction1.startsWith("r")) {
                    transaction1 = "reservations";
                }
                if (transaction2.startsWith("r")) {
                    transaction2 = "reservations";
                }
                String result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1)
                        + " and " + transaction2.substring(0, 1).toUpperCase() + transaction2.substring(1);
                tvDiningOptions.setText(result);
                break;
            default: // 3 items
                transaction1 = businessDetailed.getTransactions().get(0);
                transaction2 = businessDetailed.getTransactions().get(1);
                transaction3 = businessDetailed.getTransactions().get(2);
                if (transaction1.startsWith("r")) {
                    transaction1 = "reservations";
                }
                if (transaction2.startsWith("r")) {
                    transaction2 = "reservations";
                }
                if (transaction3.startsWith("r")) {
                    transaction3 = "reservations";
                }
                result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1)
                        + ", " + transaction2.substring(0, 1).toUpperCase() + transaction2.substring(1)
                        + ", and " + transaction3.substring(0, 1).toUpperCase() + transaction3.substring(1);
                tvDiningOptions.setText(result);
        }

        tvViewYelp.setText(businessDetailed.getUrl());

        if (businessDetailed.getSpecialHours() == null) {
            tvSpecialHours.setText("No special hours");
        } else {
            String specialHoursString = "";
            for (SpecialHour specialHour : businessDetailed.getSpecialHours()) {
                specialHoursString += String.format("%s: %s - %s", specialHour.getDate(), specialHour.getStart(), specialHour.getEnd());
            }
            tvSpecialHours.setText(specialHoursString);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String convertTime(String start, String end) throws ParseException {
        String newStart = "";
        String newEnd = "";

        SimpleDateFormat input = new SimpleDateFormat("HHmm");
        SimpleDateFormat output = new SimpleDateFormat("h:mm aa"); // 12 hour format

        Date dateStart = null;
        Date dateEnd = null;

        try {
            dateStart = input.parse(start);
            newStart = output.format(dateStart);
            dateEnd = input.parse(end);
            newEnd = output.format(dateEnd);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return newStart + " - " + newEnd;
    }
}
