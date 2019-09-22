package challenge.alejandroburdio.pokemonapi.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import challenge.alejandroburdio.pokemonapi.R;

/**
 * Created by Burdío on 22/09/2019.
 */

public class FragmentHandler extends Fragment{

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(FragmentManager mFragmentManager, Fragment destFragment) {
        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout, destFragment);
        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }
}
