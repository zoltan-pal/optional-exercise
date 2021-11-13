package com.github.zoltanpal.prog2.exercise.optional.domain;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Domain class to hold a single validated phone number.
 */
public class PhoneNumber {

    // Ref.: https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s03.html
    private static final String EPP_FORMAT_VALIDATOR_REGEX = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$";

    private final String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        if (Pattern.matches(EPP_FORMAT_VALIDATOR_REGEX, phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PhoneNumber that = (PhoneNumber) o;

        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber != null ? phoneNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
