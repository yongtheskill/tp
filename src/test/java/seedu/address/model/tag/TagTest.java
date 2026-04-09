package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // valid tag names
        assertTrue(Tag.isValidTagName("friend"));
        assertTrue(Tag.isValidTagName("study-group"));
        assertTrue(Tag.isValidTagName("a"));
        assertTrue(Tag.isValidTagName("a-b-c"));
        assertTrue(Tag.isValidTagName("group1"));
        assertTrue(Tag.isValidTagName("class-2021"));

        // invalid tag names
        assertFalse(Tag.isValidTagName("tag-"));
        assertFalse(Tag.isValidTagName("tag--name"));
        assertFalse(Tag.isValidTagName("-tag"));
        assertFalse(Tag.isValidTagName(""));
        assertFalse(Tag.isValidTagName("tag_name"));
        assertFalse(Tag.isValidTagName("tag.name"));
        assertFalse(Tag.isValidTagName("tag@name"));
    }

    @Test
    public void equalsHashCodeAndToString() {
        Tag firstTag = new Tag("friend");
        Tag firstTagCopy = new Tag("friend");
        Tag secondTag = new Tag("colleague");

        assertTrue(firstTag.equals(firstTag));
        assertTrue(firstTag.equals(firstTagCopy));
        assertFalse(firstTag.equals(secondTag));
        assertFalse(firstTag.equals(1));
        assertFalse(firstTag.equals(null));
        assertEquals(firstTag.hashCode(), firstTagCopy.hashCode());
        assertEquals("[friend]", firstTag.toString());
    }

}
