package id.co.asyst.yeni.movieyeni.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.co.asyst.yeni.movieyeni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    EditText searchEt;
    Button searchBtn;
    SearchFragment.OnSubmitButton listener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String search) {
        SearchFragment searchFragment = new SearchFragment();

        Bundle bundle = new Bundle();
        bundle.putString("search", search);

        searchFragment.setArguments(bundle);

        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchEt = view.findViewById(R.id.searc_et);
        searchBtn = view.findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:

                String search = searchEt.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    listener.onSubmitButtonListener(search);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Harus diisi", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof FilterFragment.OnSubmitButtonListener) {
            listener = (SearchFragment.OnSubmitButton) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement interface");
        }
        super.onAttach(context);
    }


    public interface OnSubmitButton {
        void onSubmitButtonListener(String search);
    }
}
