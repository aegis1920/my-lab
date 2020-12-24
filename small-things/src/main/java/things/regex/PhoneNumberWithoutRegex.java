package things.regex;

public class PhoneNumberWithoutRegex {

    private final String phoneNumber;

    public PhoneNumberWithoutRegex(String input) {
        String[] inputSegments = input.split("-");
        validateSegmentsLength(inputSegments);
        validateFirstSegment(inputSegments[0]);
        validateSecondSegment(inputSegments[1]);
        validateThirdSegment(inputSegments[2]);
        this.phoneNumber = input;
    }

    private void validateSegmentsLength(String[] inputSegments) {
        if (inputSegments.length != 3 ) {
            throw new IllegalArgumentException();
        }
    }

    private void validateFirstSegment(String inputSegment) {
        if (!inputSegment.startsWith("010")) {
            throw new IllegalArgumentException();
        }
    }

    private void validateSecondSegment(String inputSegment) {
        if (isInvalidLength(inputSegment) && isNotNumber(inputSegment)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateThirdSegment(String inputSegment) {
        if (isInvalidLength(inputSegment) && isNotNumber(inputSegment)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isInvalidLength(String input) {
        return input.length() != 4;
    }

    private boolean isNotNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
