package things.regex;

import java.util.regex.Pattern;

public class PhoneNumberWithRegex {

    private final String phoneNumber;

    public PhoneNumberWithRegex(String input) {
        validateRegex(input);
        this.phoneNumber = input;
    }

    private void validateRegex(String input) {
        if (!Pattern.matches("010-\\d{4}-\\d{4}", input)) {
            throw new IllegalArgumentException();
        }
    }
}
