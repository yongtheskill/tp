package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Registry for command aliases.
 */
public class AliasRegistry {
    private final Map<String, String> aliasMap = new HashMap<>();

    /**
     * Summary of an alias load operation.
     */
    public static class LoadAliasesResult {
        private final int loadedCount;
        private final List<RejectedAliasEntry> rejectedEntries;

        LoadAliasesResult(int loadedCount, List<RejectedAliasEntry> rejectedEntries) {
            this.loadedCount = loadedCount;
            this.rejectedEntries = List.copyOf(rejectedEntries);
        }

        public int getLoadedCount() {
            return loadedCount;
        }

        public int getRejectedCount() {
            return rejectedEntries.size();
        }

        public boolean hasRejectedEntries() {
            return !rejectedEntries.isEmpty();
        }

        public List<RejectedAliasEntry> getRejectedEntries() {
            return rejectedEntries;
        }
    }

    /**
     * Details about a rejected alias entry during loading.
     */
    public static class RejectedAliasEntry {
        private final String alias;
        private final String commandWord;
        private final String reason;

        RejectedAliasEntry(String alias, String commandWord, String reason) {
            this.alias = alias;
            this.commandWord = commandWord;
            this.reason = reason;
        }

        public String getAlias() {
            return alias;
        }

        public String getCommandWord() {
            return commandWord;
        }

        public String getReason() {
            return reason;
        }

        public String toLogString() {
            return "alias='" + alias + "', command='" + commandWord + "' (" + reason + ")";
        }
    }

    /**
     * Adds an alias mapping. Returns true if successful, false if conflict or invalid input.
     */
    public boolean addAlias(String alias, String commandWord, Set<String> reservedWords) {
        String normalizedAlias = normalize(alias);
        String normalizedCommandWord = normalize(commandWord);
        if (normalizedAlias == null || normalizedAlias.isBlank()
                || normalizedCommandWord == null || normalizedCommandWord.isBlank()) {
            return false;
        }
        Set<String> reserved = (reservedWords != null) ? reservedWords : Set.of();
        if (reserved.contains(normalizedAlias) || aliasMap.containsKey(normalizedAlias)) {
            return false;
        }
        aliasMap.put(normalizedAlias, normalizedCommandWord);
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
        return aliasMap.remove(normalize(alias)) != null;
    }

    /**
     * Gets the command word for an alias, or null if not found.
     */
    public String getCommandWord(String alias) {
        return aliasMap.get(normalize(alias));
    }

    /**
     * Returns all aliases.
     */
    public Map<String, String> getAllAliases() {
        return new HashMap<>(aliasMap);
    }

    /**
     * Replaces all current aliases with validated entries from the given map.
     * Entries with blank keys/values or keys that clash with {@code reservedWords}
     * are rejected and returned to the caller.
     * If {@code aliases} is null the registry is simply cleared.
     */
    public LoadAliasesResult loadAliases(Map<String, String> aliases, Set<String> reservedWords) {
        aliasMap.clear();
        if (aliases == null) {
            return new LoadAliasesResult(0, List.of());
        }

        Set<String> reserved = (reservedWords != null) ? reservedWords : Set.of();
        List<RejectedAliasEntry> rejectedEntries = new java.util.ArrayList<>();
        aliases.forEach((alias, command) -> {
            String rejectionReason = getRejectionReason(alias, command, reserved);
            if (rejectionReason == null) {
                aliasMap.put(normalize(alias), normalize(command));
            } else {
                rejectedEntries.add(new RejectedAliasEntry(alias, command, rejectionReason));
            }
        });

        return new LoadAliasesResult(aliasMap.size(), rejectedEntries);
    }

    private String getRejectionReason(String alias, String commandWord, Set<String> reservedWords) {
        if (alias == null) {
            return "alias is null";
        }
        String normalizedAlias = normalize(alias);
        if (normalizedAlias.isBlank()) {
            return "alias is blank";
        }
        if (commandWord == null) {
            return "command word is null";
        }
        String normalizedCommandWord = normalize(commandWord);
        if (normalizedCommandWord.isBlank()) {
            return "command word is blank";
        }
        if (reservedWords.contains(normalizedAlias)) {
            return "alias is reserved";
        }
        if (!reservedWords.isEmpty() && !reservedWords.contains(normalizedCommandWord)) {
            return "command word is invalid";
        }
        if (aliasMap.containsKey(normalizedAlias)) {
            return "alias duplicates another entry";
        }
        return null;
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase(Locale.ROOT);
    }
}
