package mx.com.terco.luis.appmiguel.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.com.terco.luis.appmiguel.R;

/**
 * Created by luisarias on 03/06/16.
 */
public class DeportesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.deportes_fragment, container, false);
        return rootView;
    }
}
