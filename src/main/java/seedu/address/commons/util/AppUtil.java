package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * A container for App specific utility functions
 */
public class AppUtil {
    private static final Logger logger = LogsCenter.getLogger(AppUtil.class);
    private static final Function<String, Image> DEFAULT_IMAGE_LOADER = Image::new;
    private static volatile Function<String, Image> imageLoader = DEFAULT_IMAGE_LOADER;

    /**
     * Gets an {@code Image} from the specified path.
     *
     * @return The image if it can be loaded, or {@code null} if the resource cannot be found on the classpath
     *         (via {@code MainApp.class.getResource(...)}) or if the JavaFX runtime is unavailable (e.g. headless
     *         unit tests with missing/incompatible JavaFX native libraries).
     */
    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        var url = MainApp.class.getResource(imagePath);
        if (url == null) {
            logger.warning("Image resource not found on classpath: " + imagePath);
            return null;
        }
        try {
            return imageLoader.apply(url.toExternalForm());
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid image URL for resource '" + imagePath + "': " + url + ". " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            if (isJavaFxUnavailable(e)) {
                logger.warning("JavaFX unavailable; cannot load image '" + imagePath + "': " + e.getMessage());
                return null;
            }
            logger.log(Level.SEVERE, "Unexpected failure loading image '" + imagePath + "' from URL: " + url, e);
            throw e;
        } catch (LinkageError e) {
            if (isJavaFxUnavailable(e)) {
                logger.warning("JavaFX unavailable; cannot load image '" + imagePath + "': " + e.getMessage());
                return null;
            }
            logger.log(Level.SEVERE, "Unexpected failure loading image '" + imagePath + "' from URL: " + url, e);
            throw e;
        }
    }

    static void setImageLoaderForTesting(Function<String, Image> loader) {
        imageLoader = requireNonNull(loader);
    }

    static void resetImageLoaderForTesting() {
        imageLoader = DEFAULT_IMAGE_LOADER;
    }

    /*
     * `isJavaFxUnavailable` uses pragmatic heuristics based on JavaFX's current exception messages.
     * It walks the cause chain and checks for known substrings like "No toolkit found",
     * "Error initializing QuantumRenderer", and "no suitable pipeline".
     *
     * If JavaFX changes these messages (or exposes a more robust signal), update these checks accordingly.
     */
    private static boolean isJavaFxUnavailable(Throwable t) {
        for (Throwable cur = t; cur != null; cur = cur.getCause()) {
            if (cur instanceof UnsatisfiedLinkError) {
                return true;
            }
            String msg = cur.getMessage();
            if (msg == null) {
                continue;
            }
            if (msg.contains("No toolkit found")
                    || msg.contains("Toolkit not initialized")
                    || msg.contains("Internal graphics not initialized")
                    || msg.contains("Error initializing QuantumRenderer")
                    || msg.contains("Graphics Device initialization failed")
                    || msg.contains("no suitable pipeline")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
