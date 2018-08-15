package id.co.asyst.yeni.movieyeni.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.asyst.yeni.movieyeni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends BottomSheetDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView yearTV, filterTV;
    Button filterBtn;
    Spinner filterSp, yearSp;
    String selectedFilter;
    String selectedYear;
    ArrayList<String> listSort = new ArrayList<>();
    OnSubmitButtonListener listener;


    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String year, String sort) {
        FilterFragment filterFragment = new FilterFragment();

        Bundle bundle = new Bundle();
        bundle.putString("year", year);
        bundle.putString("sort", sort);

        filterFragment.setArguments(bundle);

        return filterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        yearSp = view.findViewById(R.id.year_sp);
        filterSp = view.findViewById(R.id.filter_sp);

        yearSp.setOnItemSelectedListener(this);
        filterSp.setOnItemSelectedListener(this);

        filterBtn = view.findViewById(R.id.filter_btn);


        ArrayList<String> years = new ArrayList<String>();
        years.add("None");

        for (int i = 2020; i > 1900; i--) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        yearSp.setAdapter(yearadapter);
        filterBtn.setOnClickListener(this);


        listSort.add("popularity.asc");
        listSort.add("popularity.desc");
        listSort.add("release_date.asc");
        listSort.add("release_date.desc");

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSort);
        filterSp.setAdapter(sortAdapter);

//supaya datanya masih nyangkut
        if (getArguments() != null) {
            selectedYear = getArguments().getString("year", "");
            selectedFilter = getArguments().getString("sort", "");

            int posisiYear = yearadapter.getPosition(selectedYear);
            yearSp.setSelection(posisiYear);

            int posisiFilter = sortAdapter.getPosition(selectedFilter);
            filterSp.setSelection(posisiFilter);
        }

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_btn:
                selectedYear = yearSp.getSelectedItem().toString();
                selectedFilter = filterSp.getSelectedItem().toString();
                if (selectedYear == "None") {
                    listener.onSubmitButton("", selectedFilter);
                } else {
                    listener.onSubmitButton(selectedYear, selectedFilter);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedFilter = filterSp.getSelectedItem().toString();
        selectedYear = yearSp.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttach(Context context) {

        if (context instanceof OnSubmitButtonListener) {
            listener = (OnSubmitButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement interface");
        }
        super.onAttach(context);
    }

    //    FilterFragment.OnSubmitButtonListener listener;
    public interface OnSubmitButtonListener {
        void onSubmitButton(String year, String sort);
    }


}
