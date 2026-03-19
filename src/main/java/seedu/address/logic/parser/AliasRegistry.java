package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registry for command aliases.
 */
public class AliasRegistry {
    private final Map<String, String> aliasMap = new HashMap<>();

    /**
     * Adds an alias mapping. Returns true if successful, false if conflict or invalid input.
     */
    public boolean addAlias(String alias, String commandWord, Set<String> reservedWords) {
        if (alias == null || alias.isBlank() || commandWord == null || commandWord.isBlank()) {
            return false;
        }
        Set<String> reserved = (reservedWords != null) ? reservedWords : Set.of();
        if (reserved.contains(alias) || aliasMap.containsKey(alias)) {
            return false;
        }
        aliasMap.put(alias, commandWord);
        return true;
    }

    /**
     * Clears all stored aliases.
     */
    public void clear() {
        aliasMap.clear();
    }

    /**
     * Removes an alias. Returns true if removed, false if not found.
     */
    public boolean removeAlias(String alias) {
        return aliasMap.remove(alias) != null;
    }

    /**
     * Gets the command word for an alias, or null if not found.
     */
    public String getCommandWord(String alias) {
        return aliasMap.get(alias);
    }

    /**
     * Returns all aliases.
     */
    public Map<String, String> getAllAliases() {
        return new HashMap<>(aliasMap);
    }
}
