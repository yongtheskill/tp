package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final boolean isArchived;
    private final boolean isStarred;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        this(name, phone, email, address, remark, false, tags, false);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Remark remark,
                  boolean isArchived, Set<Tag> tags) {
        this(name, phone, email, address, remark, isArchived, tags, false);
    }

    /**
     * Every field must be present and not null except address, which can be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags, boolean isStarred) {
        this(name, phone, email, address, remark, false, tags, isStarred);
    }

    /**
     * Every field must be present and not null except address, which can be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Remark remark,
                  boolean isArchived, Set<Tag> tags, boolean isStarred) {
        requireAllNonNull(name, phone, email, remark, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.isArchived = isArchived;
        this.isStarred = isStarred;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public boolean hasAddress() {
        return address != null;
    }

    public Remark getRemark() {
        return remark;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public boolean isStarred() {
        return isStarred;
    }

    /**
     * Returns a copy of this person with the given archived state.
     */
    public Person withArchived(boolean archived) {
        return new Person(name, phone, email, address, remark, archived, tags, isStarred);
    }

    /**
     * Returns a copy of this person with the given starred state.
     */
    public Person withStarred(boolean starred) {
        return new Person(name, phone, email, address, remark, isArchived, tags, starred);
    }

    /**
     * Returns a copy of this person with the given remark.
     */
    public Person withRemark(Remark newRemark) {
        return new Person(name, phone, email, address, newRemark, isArchived, tags, isStarred);
    }

    /**
     * Returns a copy of this person with the given name.
     */
    public Person withName(Name newName) {
        return new Person(newName, phone, email, address, remark, isArchived, tags, isStarred);
    }

    /**
     * Returns a copy of this person with the given phone.
     */
    public Person withPhone(Phone newPhone) {
        return new Person(name, newPhone, email, address, remark, isArchived, tags, isStarred);
    }

    /**
     * Returns a copy of this person with the given email.
     */
    public Person withEmail(Email newEmail) {
        return new Person(name, phone, newEmail, address, remark, isArchived, tags, isStarred);
    }

    /**
     * Returns a copy of this person with the given address.
     */
    public Person withAddress(Address newAddress) {
        return new Person(name, phone, email, newAddress, remark, isArchived, tags, isStarred);
    }

    /**
     * Returns a copy of this person with the given tags.
     */
    public Person withTags(Set<Tag> newTags) {
        return new Person(name, phone, email, address, remark, isArchived, newTags, isStarred);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().fullName.equalsIgnoreCase(getName().fullName);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
            && Objects.equals(address, otherPerson.address)
                && remark.equals(otherPerson.remark)
                && isArchived == otherPerson.isArchived
            && isStarred == otherPerson.isStarred
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, remark, isArchived, isStarred, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("remark", remark)
                .add("isArchived", isArchived)
                .add("isStarred", isStarred)
                .add("tags", tags)
                .toString();
    }

}
