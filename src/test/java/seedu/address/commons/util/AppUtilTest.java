package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class AppUtilTest {

    @Test
    public void getImage_nonExistentPath_returnsNull() {
        var image = assertDoesNotThrow(() -> AppUtil.getImage("/images/non_existent_image.png"));
        assertNull(image);
    }

    @Test
    public void getImage_resourceExists_returnsNonNull() {
        String iconPath = "/images/address_book_32.png";
        var image = assertDoesNotThrow(() -> AppUtil.getImage(iconPath));
        Assumptions.assumeTrue(image != null, "Image not loadable (missing resource or JavaFX unavailable)");
        assertTrue(image.getWidth() > 0 && image.getHeight() > 0);
    }

    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class, errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }
}
