package com.github.zoltanpal.prog2.exercise.optional.crawler;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.zoltanpal.prog2.exercise.optional.domain.PhoneBookEntry;
import com.github.zoltanpal.prog2.exercise.optional.domain.PhoneNumber;

/**
 * Unit test for {@link PhoneBookCrawler}.
 */
public class PhoneBookCrawlerTest {

    private static final String NAME_1 = "Person_1";
    private static final String NAME_2 = "Person_2";
    private static final String NAME_3 = "Person_3";
    private static final String INVALID_NAME = "InvalidPerson";

    private static final PhoneNumber PHONE_NUMBER_1 = new PhoneNumber("+36.201234567");
    private static final PhoneNumber PHONE_NUMBER_2 = new PhoneNumber("+36.302345678");
    private static final PhoneNumber PHONE_NUMBER_3 = new PhoneNumber("+36.403456789");

    private static final PhoneBookEntry PHONE_BOOK_ENTRY_1 = new PhoneBookEntry(NAME_1, PHONE_NUMBER_1);
    private static final PhoneBookEntry PHONE_BOOK_ENTRY_2 = new PhoneBookEntry(NAME_2, PHONE_NUMBER_2);
    private static final PhoneBookEntry DEFAULT_PHONE_BOOK_ENTRY = new PhoneBookEntry(NAME_3, PHONE_NUMBER_3);

    @Mock
    private Supplier<Collection<PhoneBookEntry>> phoneBookEntrySupplier;

    private PhoneBookCrawler underTest;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        underTest = new PhoneBookCrawler(phoneBookEntrySupplier);

        given(phoneBookEntrySupplier.get()).willReturn(List.of(PHONE_BOOK_ENTRY_1, PHONE_BOOK_ENTRY_2));
    }

    @Test
    public void findEntryByNameShouldReturnOptionalOfCorrectEntryWhenItIsFound() {
        // Given
        final Optional<PhoneBookEntry> expected = Optional.of(PHONE_BOOK_ENTRY_2);

        // When
        final Optional<PhoneBookEntry> actual = underTest.findEntryByName(NAME_2);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void findEntryByNameShouldReturnEmptyOptionalWhenItemIsNotFound() {
        // Given
        final Optional<PhoneBookEntry> expected = Optional.empty();

        // When
        final Optional<PhoneBookEntry> actual = underTest.findEntryByName(INVALID_NAME);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void findEntryByNameWithExternalFallbackShouldReturnCorrectEntryWhenItIsFound() {
        // Given
        final Supplier<PhoneBookEntry> phoneBookEntrySupplier = () -> DEFAULT_PHONE_BOOK_ENTRY;
        final PhoneBookEntry expected = PHONE_BOOK_ENTRY_1;

        // When
        final PhoneBookEntry actual = underTest.findEntryByNameWithExternalFallback(NAME_1, phoneBookEntrySupplier);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void findEntryByNameWithExternalFallbackShouldReturnFallbackEntryWhenItemIsNotFound() {
        // Given
        final Supplier<PhoneBookEntry> phoneBookEntrySupplier = () -> DEFAULT_PHONE_BOOK_ENTRY;
        final PhoneBookEntry expected = DEFAULT_PHONE_BOOK_ENTRY;

        // When
        final PhoneBookEntry actual = underTest.findEntryByNameWithExternalFallback(INVALID_NAME, phoneBookEntrySupplier);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void findEntryByNameWithFallbackShouldReturnCorrectEntryWhenItIsFound() {
        // Given
        final PhoneBookEntry expected = PHONE_BOOK_ENTRY_2;

        // When
        final PhoneBookEntry actual = underTest.findEntryByNameWithFallback(NAME_2);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void findEntryByNameWithFallbackShouldReturnFallbackEntryWhenItemIsNotFound() {
        // Given
        final PhoneBookEntry expected = PhoneBookCrawler.DEFAULT_ENTRY;

        // When
        final PhoneBookEntry actual = underTest.findEntryByNameWithFallback(INVALID_NAME);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void printPhoneNumberByNameShouldPrintCorrectPhoneNumberWhenItIsFound() {
        // Given
        PrintStream systemOut = System.out;
        PrintStream temporarySystemOut = Mockito.mock(PrintStream.class);
        System.setOut(temporarySystemOut);

        // When
        underTest.printPhoneNumberByName(NAME_1);
        System.setOut(systemOut);

        // Then
        then(temporarySystemOut).should().println(PHONE_NUMBER_1);
    }

    @Test
    public void printPhoneNumberByNameShouldPrintNotFoundMessageWhenWhenItemIsNotFound() {
        // Given
        PrintStream systemOut = System.out;
        PrintStream temporarySystemOut = Mockito.mock(PrintStream.class);
        System.setOut(temporarySystemOut);

        // When
        underTest.printPhoneNumberByName(INVALID_NAME);
        System.setOut(systemOut);

        // Then
        then(temporarySystemOut).should().println(PhoneBookCrawler.NOT_FOUND_MESSAGE);
    }
}
