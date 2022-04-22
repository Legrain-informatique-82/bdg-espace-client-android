package fr.legrain.bdg.espaceclient;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

/**
 * Created by nicolas on 18/09/17.
 */

public class LgrFragment extends Fragment {

    /*
    https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    solution N°2
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setFocusAndShowKeyboard() {
//        EditText editText = (EditText) findViewById(R.id.myTextViewId);
//        editText.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public boolean isHardwareKeyboardAvailable() {
        return getResources().getConfiguration().keyboard != Configuration.KEYBOARD_NOKEYS;
    }
}
