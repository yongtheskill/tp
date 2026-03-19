package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
    public void getImage_resourceExists_illegalArgumentException_returnsNull() {
        String iconPath = "/images/address_book_32.png";
        AppUtil.setImageLoaderForTesting(unused -> {
            throw new IllegalArgumentException("bad url");
        });
        try {
            var image = assertDoesNotThrow(() -> AppUtil.getImage(iconPath));
            assertNull(image);
        } finally {
            AppUtil.resetImageLoaderForTesting();
        }
    }

    @Test
    public void getImage_resourceExists_runtimeException_dueToJavaFxUnavailable_returnsNull() {
        String iconPath = "/images/address_book_32.png";
        AppUtil.setImageLoaderForTesting(unused -> {
            throw new RuntimeException("No toolkit found");
        });
        try {
            var image = assertDoesNotThrow(() -> AppUtil.getImage(iconPath));
            assertNull(image);
        } finally {
            AppUtil.resetImageLoaderForTesting();
        }
    }

    @Test
    public void getImage_resourceExists_runtimeException_unexpected_rethrows() {
        String iconPath = "/images/address_book_32.png";
        RuntimeException expected = new RuntimeException("boom");
        AppUtil.setImageLoaderForTesting(unused -> {
            throw expected;
        });
        try {
            RuntimeException actual = org.junit.jupiter.api.Assertions.assertThrows(
                    RuntimeException.class, () -> AppUtil.getImage(iconPath));
            assertSame(expected, actual);
        } finally {
            AppUtil.resetImageLoaderForTesting();
        }
    }

    @Test
    public void getImage_resourceExists_linkageError_dueToJavaFxUnavailable_returnsNull() {
        String iconPath = "/images/address_book_32.png";
        AppUtil.setImageLoaderForTesting(unused -> {
            throw new LinkageError("Graphics Device initialization failed");
        });
        try {
            var image = assertDoesNotThrow(() -> AppUtil.getImage(iconPath));
            assertNull(image);
        } finally {
            AppUtil.resetImageLoaderForTesting();
        }
    }

    @Test
    public void getImage_resourceExists_linkageError_unexpected_rethrows() {
        String iconPath = "/images/address_book_32.png";
        LinkageError expected = new LinkageError("boom");
        AppUtil.setImageLoaderForTesting(unused -> {
            throw expected;
        });
        try {
            LinkageError actual = org.junit.jupiter.api.Assertions.assertThrows(
                    LinkageError.class, () -> AppUtil.getImage(iconPath));
            assertSame(expected, actual);
        } finally {
            AppUtil.resetImageLoaderForTesting();
        }
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
