package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    private static final int FX_TIMEOUT_SECONDS = 5;

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> startupThrowable = new AtomicReference<>();
        try {
            Platform.startup(() -> {
                try {
                    // no-op
                } catch (Throwable throwable) {
                    startupThrowable.set(throwable);
                } finally {
                    latch.countDown();
                }
            });
        } catch (IllegalStateException e) {
            latch.countDown();
        } catch (UnsupportedOperationException e) {
            startupThrowable.set(e);
            latch.countDown();
        } catch (RuntimeException e) {
            // JavaFX initialization may fail in environments without GUI support.
            startupThrowable.set(e);
            latch.countDown();
        }
        assertTrue(latch.await(FX_TIMEOUT_SECONDS, TimeUnit.SECONDS), "Timed out waiting for JavaFX toolkit startup.");
        Throwable throwable = startupThrowable.get();
        assumeTrue(throwable == null, () ->
            "Skipping PersonCardTest because JavaFX is unavailable: " + throwable.getClass().getSimpleName());
    }

    @Test
    public void constructor_personWithAddressAndStarred_showsStarAndAddress() throws InterruptedException {
        Person person = new PersonBuilder().withStarred(true).build();

        runFxAndWait(() -> {
            PersonCard card = assertDoesNotThrow(() -> new PersonCard(person, 1));
            attachToStage(card);

            Label starredIndicator = (Label) card.getRoot().lookup("#starredIndicator");
            Label addressLabel = (Label) card.getRoot().lookup("#address");

            assertNotNull(starredIndicator);
            assertTrue(starredIndicator.isVisible());
            assertTrue(starredIndicator.isManaged());
            assertFalse(starredIndicator.getText().isEmpty());

            assertNotNull(addressLabel);
            assertEquals(person.getAddress().toString(), addressLabel.getText());
        });
    }

    @Test
    public void constructor_personWithoutAddressAndUnstarred_hidesStarAndAddress() throws InterruptedException {
        Person person = new PersonBuilder().withNoAddress().withStarred(false).build();

        runFxAndWait(() -> {
            PersonCard card = assertDoesNotThrow(() -> new PersonCard(person, 1));
            attachToStage(card);

            Label starredIndicator = (Label) card.getRoot().lookup("#starredIndicator");
            Label addressLabel = (Label) card.getRoot().lookup("#address");

            assertNotNull(starredIndicator);
            assertFalse(starredIndicator.isVisible());
            assertFalse(starredIndicator.isManaged());

            assertNotNull(addressLabel);
            assertTrue(addressLabel.getText().isEmpty());
        });
    }

    @Test
    public void constructor_personWithTags_doesNotThrow() throws InterruptedException {
        Person person = new PersonBuilder().withTags("friends", "teammate").build();

        runFxAndWait(() -> {
            assertDoesNotThrow(() -> new PersonCard(person, 1));
        });
    }

    private static void runFxAndWait(ThrowingRunnable runnable) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> throwable = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                runnable.run();
            } catch (Throwable t) {
                throwable.set(t);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(FX_TIMEOUT_SECONDS, TimeUnit.SECONDS), "Timed out waiting for JavaFX task completion.");
        assertFxThreadSucceeded(throwable);
    }

    private static void attachToStage(PersonCard card) {
        Stage stage = new Stage();
        Scene scene = new Scene(card.getRoot());
        stage.setScene(scene);
        scene.getRoot().applyCss();
        scene.getRoot().layout();
    }

    private static void assertFxThreadSucceeded(AtomicReference<Throwable> throwableRef) {
        Throwable throwable = throwableRef.get();
        if (throwable != null) {
            throw new AssertionError("Exception on JavaFX thread", throwable);
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
