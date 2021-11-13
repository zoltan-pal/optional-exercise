package com.github.zoltanpal.prog2.exercise.optional.domain;

import java.util.Objects;

/**
 * Domain class to hold a single phonebook entry.
 */
public class PhoneBookEntry {

    private final String name;
    private final PhoneNumber phoneNumber;

    public PhoneBookEntry(String name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PhoneBookEntry entry = (PhoneBookEntry) o;

        if (!Objects.equals(name, entry.name))
            return false;
        return Objects.equals(phoneNumber, entry.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneBookEntry{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
