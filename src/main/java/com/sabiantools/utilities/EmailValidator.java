package com.sabiantools.utilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private final Pattern pattern;

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    private String usePattern = EMAIL_PATTERN;

    public EmailValidator() {
        pattern = Pattern.compile(usePattern);
    }

    public EmailValidator(String usePattern) {
        this.usePattern = usePattern;
        pattern = Pattern.compile(usePattern);
    }

    /**
     * Validate email with regular expression
     *
     * @param email for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validate(@NonNull String email, @Nullable String[] formats) {
        if (formats == null || formats.length == 0) {
            return this.validate(email);
        }
        for (String format : formats) {
            EmailValidator validator = new EmailValidator(format);
            if (validator.validate(email))
                return true;
        }
        return false;

    }
}
