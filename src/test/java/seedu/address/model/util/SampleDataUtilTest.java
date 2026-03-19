package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonEmptyPersonsWithFields() {
        Person[] persons = SampleDataUtil.getSamplePersons();

        assertNotNull(persons);
        assertEquals(6, persons.length);
        for (Person person : persons) {
            assertNotNull(person);
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getRemark());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyAddressBook sample = SampleDataUtil.getSampleAddressBook();
        Person[] expected = SampleDataUtil.getSamplePersons();
        assertEquals(expected.length, sample.getPersonList().size());
        for (Person person : expected) {
            assertTrue(sample.getPersonList().contains(person));
        }
    }

    @Test
    public void getTagSet_deduplicatesAndCreatesTags() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "friends", "colleagues");
        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("friends")));
        assertTrue(tags.contains(new Tag("colleagues")));
    }
}

