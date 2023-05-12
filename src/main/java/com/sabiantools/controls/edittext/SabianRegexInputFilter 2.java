package com.sabiantools.controls.edittext;

import android.text.InputFilter;
import android.text.Spanned;

import com.sabiantools.utilities.SabianUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SabianRegexInputFilter implements InputFilter {

    private String regex = "[a-zA-Z\\s+-_]+";

    public SabianRegexInputFilter() {
    }

    public SabianRegexInputFilter(String regex) {
        if (SabianUtilities.IsStringEmpty(regex)) {
            return;
        }
        this.regex = regex;
    }

    @Override
    public CharSequence filter(CharSequence cs, int start, int end, Spanned dest, int dstart, int dend) {
        // TODO Auto-generated method stub
        if (cs.equals("")) { // for backspace
            return cs;
        }
        if (cs.toString().matches(regex)) {
            return cs;
        }
        return "";
    }

    private boolean isCharAllowed(char c) {
        Pattern ps = Pattern.compile(regex);
        Matcher ms = ps.matcher(String.valueOf(c));
        return ms.matches();
    }
}
