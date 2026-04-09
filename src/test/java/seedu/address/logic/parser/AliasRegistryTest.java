package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AliasRegistryTest {

    private AliasRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new AliasRegistry();
    }

    @Test
    public void addAlias_valid_returnsTrue() {
        assertTrue(registry.addAlias("ls", "list", Set.of("list")));
    }

    @Test
    public void addAlias_reservedAlias_returnsFalse() {
        assertFalse(registry.addAlias("list", "list", Set.of("list")));
    }

    @Test
    public void addAlias_duplicateAlias_returnsFalse() {
        registry.addAlias("ls", "list", Set.of("list"));
        assertFalse(registry.addAlias("ls", "list", Set.of("list")));
    }

    @Test
    public void addAlias_nullAlias_returnsFalse() {
        assertFalse(registry.addAlias(null, "list", Set.of("list")));
    }

    @Test
    public void addAlias_blankAlias_returnsFalse() {
        assertFalse(registry.addAlias("   ", "list", Set.of("list")));
    }

    @Test
    public void addAlias_blankCommand_returnsFalse() {
        assertFalse(registry.addAlias("ls", "  ", Set.of("list")));
    }

    @Test
    public void addAlias_nullCommand_returnsFalse() {
        assertFalse(registry.addAlias("ls", null, Set.of("list")));
    }

    @Test
    public void addAlias_invalidCommandWord_returnsFalse() {
        assertFalse(registry.addAlias("ls", "missing", Set.of("list", "find")));
    }

    @Test
    public void addAlias_nullReservedWords_allowsAlias() {
        assertTrue(registry.addAlias("ls", "custom", null));
        assertEquals("custom", registry.getCommandWord("ls"));
    }

    @Test
    public void addAlias_mixedCaseAliasAndCommand_normalizesStoredEntry() {
        assertTrue(registry.addAlias("Ls", "LiSt", Set.of("list")));

        assertEquals("list", registry.getCommandWord("ls"));
        assertEquals("list", registry.getCommandWord("LS"));
        assertTrue(registry.getAllAliases().containsKey("ls"));
    }

    @Test
    public void removeAlias_existing_returnsTrue() {
        registry.addAlias("ls", "list", Set.of("list"));
        assertTrue(registry.removeAlias("ls"));
    }

    @Test
    public void removeAlias_nonExisting_returnsFalse() {
        assertFalse(registry.removeAlias("notfound"));
    }

    @Test
    public void getCommandWord_existing_returnsCommand() {
        registry.addAlias("ls", "list", Set.of("list"));
        assertEquals("list", registry.getCommandWord("ls"));
    }

    @Test
    public void getCommandWord_nonExisting_returnsNull() {
        assertNull(registry.getCommandWord("notfound"));
    }

    @Test
    public void getAllAliases_returnsDefensiveCopy() {
        registry.addAlias("ls", "list", Set.of("list"));
        Map<String, String> copy = registry.getAllAliases();
        copy.put("extra", "add");
        assertNull(registry.getCommandWord("extra"));
    }

    @Test
    public void loadAliases_replacesExistingEntries() {
        registry.addAlias("ls", "list", Set.of("list"));
        Map<String, String> newAliases = new HashMap<>();
        newAliases.put("a", "add");
        newAliases.put("d", "delete");

        AliasRegistry.LoadAliasesResult result = registry.loadAliases(newAliases, Set.of());

        assertNull(registry.getCommandWord("ls"));
        assertEquals("add", registry.getCommandWord("a"));
        assertEquals("delete", registry.getCommandWord("d"));
        assertEquals(2, result.getLoadedCount());
        assertEquals(0, result.getRejectedCount());
    }

    @Test
    public void loadAliases_emptyMap_clearsAll() {
        registry.addAlias("ls", "list", Set.of("list"));
        AliasRegistry.LoadAliasesResult result = registry.loadAliases(new HashMap<>(), Set.of());
        assertTrue(registry.getAllAliases().isEmpty());
        assertEquals(0, result.getLoadedCount());
        assertEquals(0, result.getRejectedCount());
    }

    @Test
    public void loadAliases_nullMap_clearsAll() {
        registry.addAlias("ls", "list", Set.of("list"));
        AliasRegistry.LoadAliasesResult result = registry.loadAliases(null, Set.of());
        assertTrue(registry.getAllAliases().isEmpty());
        assertEquals(0, result.getLoadedCount());
        assertEquals(0, result.getRejectedCount());
    }

    @Test
    public void loadAliases_reportsReservedAndBlankEntries() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("list", "list"); // reserved
        aliases.put("", "add"); // blank key
        aliases.put(null, "find"); // null key
        aliases.put("a", ""); // blank value
        aliases.put("b", null); // null command
        aliases.put("ls", "list"); // valid

        AliasRegistry.LoadAliasesResult result = registry.loadAliases(aliases, Set.of("list"));

        assertNull(registry.getCommandWord("list"));
        assertNull(registry.getCommandWord(""));
        assertNull(registry.getCommandWord(null));
        assertNull(registry.getCommandWord("a"));
        assertNull(registry.getCommandWord("b"));
        assertEquals("list", registry.getCommandWord("ls"));
        assertEquals(1, result.getLoadedCount());
        assertEquals(5, result.getRejectedCount());
    }

    @Test
    public void loadAliases_nullReservedWords_treatedAsEmpty() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");

        AliasRegistry.LoadAliasesResult result = registry.loadAliases(aliases, null);

        assertEquals("list", registry.getCommandWord("ls"));
        assertEquals(1, result.getLoadedCount());
        assertEquals(0, result.getRejectedCount());
    }

    @Test
    public void loadAliases_invalidTargetAndDuplicateAfterNormalization_rejected() {
        Map<String, String> aliases = new LinkedHashMap<>();
        aliases.put("Ls", "List");
        aliases.put("ls", "find");
        aliases.put("bad", "missing");

        AliasRegistry.LoadAliasesResult result = registry.loadAliases(aliases, Set.of("list", "find"));

        assertEquals("list", registry.getCommandWord("ls"));
        assertEquals(1, result.getLoadedCount());
        assertEquals(2, result.getRejectedCount());
    }

    @Test
    public void loadAliases_rejectedEntries_exposesDetails() {
        Map<String, String> aliases = new LinkedHashMap<>();
        aliases.put("bad", "missing");

        AliasRegistry.LoadAliasesResult result = registry.loadAliases(aliases, Set.of("list"));

        assertTrue(result.hasRejectedEntries());
        AliasRegistry.RejectedAliasEntry rejectedEntry = result.getRejectedEntries().get(0);
        assertEquals("bad", rejectedEntry.getAlias());
        assertEquals("missing", rejectedEntry.getCommandWord());
        assertEquals("command word is invalid", rejectedEntry.getReason());
        assertEquals("alias='bad', command='missing' (command word is invalid)", rejectedEntry.toLogString());
    }

    @Test
    public void clear_removesAllEntries() {
        registry.addAlias("ls", "list", Set.of("list"));
        registry.clear();
        assertTrue(registry.getAllAliases().isEmpty());
    }

}
