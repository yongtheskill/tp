---
layout: page-with-sidebar
title: User Guide
---

## Welcome to PingBook

Hi there! Whether you are brand new to command-line tools or already comfortable in a terminal, this guide has you covered. If you have never typed a command before, every step is explained from scratch. If you already know your way around a terminal, you can skim the Quick Start and jump straight to the feature you need.

**PingBook** is a desktop app for managing contacts. You type short text commands to add, search, and organise people in your phone book. Once you get the hang of it, typing is actually much faster than hunting around with a mouse.

Don't worry if some of this feels unfamiliar at first. Every technical term is explained the first time it appears, and each step is broken down so you can follow along without guessing.

You've got this!

<div markdown="block" class="alert alert-info">
💡 <strong>Navigation tip:</strong> Use the sidebar on wide screens, or tap <strong>Contents</strong> on smaller screens to open the table of contents and jump to any section.
</div>

## Who This Guide Is For

This guide is written for **Computer Science students at all levels**, from those who have never opened a terminal before to those who use one every day.

If you are new to command-line tools, everything is explained from scratch. If you are already experienced, the [Command Summary](#command-summary) and per-feature sections are quick to scan.

The only hard requirement: you can open files and folders on your computer and type on a keyboard. Everything else is covered here.

## Table of Contents

1. [Product Description](#product-description)
2. [Key Terms: Read This First](#key-terms-read-this-first)
3. [Quick Start](#quick-start)
   - [Step 1: Open your terminal](#step-1-open-your-terminal)
   - [Step 2: Check Java is installed](#step-2-check-java-is-installed)
   - [Step 3: Download PingBook](#step-3-download-pingbook)
   - [Step 4: Navigate to your folder](#step-4-navigate-to-your-folder)
   - [Step 5: Launch the app](#step-5-launch-the-app)
   - [UI Overview](#ui-overview)
   - [Your First Command](#your-first-command)
4. [Understanding Commands](#understanding-commands)
5. [Managing Contacts](#managing-contacts)
   - [Adding a contact: `add`](#adding-a-contact-add)
   - [Editing a contact: `edit`](#editing-a-contact-edit)
   - [Adding a remark: `remark`](#adding-a-remark-remark)
   - [Deleting a contact: `delete`](#deleting-a-contact-delete)
6. [Finding Contacts](#finding-contacts)
   - [Listing all contacts: `list`](#listing-all-contacts-list)
   - [Searching by name: `find`](#searching-by-name-find)
   - [Filtering by tag: `filter`](#filtering-by-tag-filter)
   - [Sorting contacts: `sort`](#sorting-contacts-sort)
7. [Organising Contacts](#organising-contacts)
   - [Starring a contact: `star`](#starring-a-contact-star)
   - [Removing a star: `unstar`](#removing-a-star-unstar)
   - [Archiving a contact: `archive`](#archiving-a-contact-archive)
   - [Restoring an archived contact: `unarchive`](#restoring-an-archived-contact-unarchive)
   - [Listing archived contacts: `listarchived`](#listing-archived-contacts-listarchived)
8. [Advanced Features](#advanced-features)
   - [Creating command aliases: `alias`](#creating-command-aliases-alias)
   - [Editing the data file directly](#editing-the-data-file-directly)
9. [Utility Commands](#utility-commands)
   - [Viewing help: `help`](#viewing-help-help)
   - [Clearing all contacts: `clear`](#clearing-all-contacts-clear)
   - [Exiting the app: `exit`](#exiting-the-app-exit)
10. [Data and Storage](#data-and-storage)
11. [FAQ](#faq)
12. [Known Issues](#known-issues)
13. [Command Summary](#command-summary)

## Product Description

PingBook is a desktop application for managing contacts quickly using typed commands.

You can add, edit, search, organise, and archive contacts without ever touching the mouse. Starred contacts appear at the top of your list, archived contacts stay out of sight until you need them, and command aliases let you create shortcuts for the commands you use most.

If you can type quickly, PingBook lets you manage hundreds of contacts faster than any click-based app.

[↑ Back to Table of Contents](#table-of-contents)

## Key Terms: Read This First

Before diving in, here are the technical terms you will encounter in this guide. They sound more complicated than they are!

##### Terminal (also called Command Prompt, Command Line, or CLI)

> A text window where you communicate with your computer by typing instructions. Instead of clicking icons, you type commands like `cd Documents` or `java -jar pingbook.jar`. Think of it like sending text messages to your computer.
>
> - On **Windows**, it is called **Command Prompt** (or **PowerShell**).
> - On **macOS**, it is called **Terminal**.
> - On **Linux**, it is also called **Terminal**.

##### Folder / Directory

> A folder on your computer. In terminal commands, folders are referred to as "directories." They are the same thing!

##### File path

> The full address of a file or folder on your computer, similar to a postal address but for files.
>
> - On **Windows**, it looks like: `C:\Users\yourname\Documents\PingBook`
> - On **macOS/Linux**, it looks like: `/Users/yourname/Documents/PingBook`
> The `~` symbol is a shortcut that means "your home folder." So `~/Documents` means `/Users/yourname/Documents`.

##### JAR file (`.jar`)

> A special file type that packages a runnable Java application. `pingbook.jar` is the file that contains and runs PingBook. You launch it with a terminal command rather than double-clicking it.

##### Java

> A programming language and platform that PingBook is built on. Your computer needs Java installed for PingBook to work, just as you need a PDF reader to open a PDF. We need **Java 17** specifically.

##### JDK (Java Development Kit)

> The full package that installs Java on your computer. When this guide says "install Java 17," it means install the JDK version 17.

##### Command

> An instruction you type into PingBook's command box. For example, `list` is a command that shows all your contacts.

##### Parameter

> Extra information you attach to a command to tell it what to do. For example, in `add n/Alex Tan`, the part `n/Alex Tan` is a parameter that tells the app the person's name is "Alex Tan."

##### Prefix

> The short code before each piece of information, like `n/` for name, `p/` for phone, or `e/` for email. Each prefix tells PingBook what type of information follows.

##### Index

> The number shown next to each contact in the list (1, 2, 3…). Commands like `delete` and `edit` use this number to know which contact you mean.

##### JSON file (`.json`)

> A plain-text file format used to store data in an organised way. PingBook saves all your contacts in a file called `pingbook.json`. You can open it in any text editor if you ever need to.

##### Alphanumeric

> Letters (A–Z, a–z) and digits (0–9) only. No spaces, punctuation, or special characters like `@` or `#`.

##### Case-insensitive

> The search does not care whether letters are uppercase or lowercase. For example, searching `alex` will also find `Alex` and `ALEX`.

[↑ Back to Table of Contents](#table-of-contents)

## Quick Start

This section walks you through installing and launching PingBook for the first time. Follow the steps for your operating system (**Windows** or **macOS**).

### Step 1: Open your terminal

Your **terminal** is the text window where you type commands. Here is how to open it:

##### On Windows

1. Click the **Start** button (the Windows logo in the bottom-left corner).
2. Type `cmd` in the search bar.
3. Click **Command Prompt** in the results.
4. A black window with white text will open. This is your terminal.

> Alternatively, press the **Windows key + R** (hold the Windows key and press R), type `cmd` into the box that appears, and press **Enter**.

##### On macOS

1. Press **Cmd + Space** (hold the Command key and press Space) to open Spotlight Search.
2. Type `Terminal` and press **Enter**.
3. A white (or dark) window with text will open. This is your terminal.

> Alternatively, open **Finder**, go to **Applications → Utilities**, and double-click **Terminal**.

You will see a prompt like `C:\Users\yourname>` (Windows) or `yourname@MacBook ~ %` (macOS). This is normal; it means the terminal is ready for your input.

### Step 2: Check Java is installed

PingBook needs **Java 17** (a specific version of the Java platform) to run. Let's check if you have it.

1. In your terminal, type the following exactly and press **Enter**:

   ```shell
   java -version
   ```

2. Look at the output:
   - If you see something like `java version "17.x.x"` or `openjdk version "17.x.x"`, you have the right version. Move to Step 3.
   - If the version number starts with something other than `17` (e.g. `11`, `21`), you need to install Java 17.
   - If you see `'java' is not recognized` (Windows) or `command not found` (macOS), Java is not installed yet.

##### To install Java 17

- **Windows / Linux:** Download and install the JDK (Java Development Kit, which is the package that installs Java) from [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/). Choose "Java 17" and pick the installer for your operating system. Run the installer and follow the on-screen instructions, then repeat the check above.

- **macOS:** Follow the [macOS Java installation guide](https://se-education.org/guides/tutorials/javaInstallationMac.html) to install the correct version. macOS requires a specific JDK variant (Azul JDK 17 with JavaFX support) for PingBook to display correctly.

Once installed, close and reopen your terminal, then run `java -version` again to confirm.

### Step 3: Download PingBook

1. Go to the [PingBook releases page](https://github.com/AY2526S2-CS2103T-T17-2/tp/releases) in your web browser.
2. Under the latest release, click `pingbook.jar` to download it. This is the JAR file (the runnable application file) that contains PingBook.
3. Create a new folder somewhere easy to remember, for example:
   - **Windows:** `C:\Users\yourname\PingBook`
   - **macOS:** `/Users/yourname/Documents/PingBook` (you can also just call it `PingBook` inside your Documents folder)
4. Move `pingbook.jar` into that folder.

<div markdown="block" class="alert alert-info">
💡 <strong>Why a dedicated folder?</strong> PingBook automatically creates a <code>data</code> folder next to <code>pingbook.jar</code> to save your contacts. Keeping <code>pingbook.jar</code> in its own folder keeps everything tidy.
</div>

### Step 4: Navigate to your folder

Now you need to tell your terminal to "go to" the folder where `pingbook.jar` lives. You do this with the `cd` command (short for **c**hange **d**irectory, which means change which folder you're in).

##### On Windows

```shell
cd C:\Users\yourname\PingBook
```

Replace `yourname` with your actual Windows username. Press **Enter**.

##### On macOS/Linux

```shell
cd ~/Documents/PingBook
```

Press **Enter**.

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> If your folder path contains spaces (e.g. <code>My PingBook</code>), wrap the whole path in quotation marks:<br>
<code>cd "C:\Users\yourname\My PingBook"</code>
</div>

After pressing Enter, your terminal prompt will update to show the new folder. For example, on Windows you might see `C:\Users\yourname\PingBook>`.

To confirm `pingbook.jar` is there, you can list the files in the folder:

- **Windows:** type `dir` and press **Enter**
- **macOS/Linux:** type `ls` and press **Enter**

You should see `pingbook.jar` in the list.

### Step 5: Launch the app

With your terminal open and pointing to the PingBook folder, type the following and press **Enter**:

```shell
java -jar pingbook.jar
```

This tells Java to run the `pingbook.jar` file.

<div markdown="block" class="alert alert-success result-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
The PingBook window opens with a set of sample contacts already loaded. You should see a text box at the top, a result display directly below it, and the contact list underneath.
</div>

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Nothing happened?</strong> Check that:
<ul>
<li>You are in the correct folder (the one that contains <code>pingbook.jar</code>).</li>
<li><code>java -version</code> shows version 17.</li>
<li>You typed the command exactly as shown, including the <code>-jar</code> part.</li>
</ul>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### UI Overview

![Screenshot of app](images/PingBookUi.png)

The PingBook window (the user interface, or UI, meaning all the visual elements you interact with) contains four areas:

| Area | Description |
|---|---|
| **Menu bar** | The row of menus (File, Help) at the very top of the window. |
| **Command box** | The text field where you type commands. Click here first before typing. |
| **Result display** | The panel below the command box that shows the outcome of each command, including success messages and error messages. |
| **Contact list** | The panel below the result display that shows all currently displayed contacts. |

### Your First Command

Let's add your first contact to make sure everything is working.

1. Click inside the **Command box** (the text field near the top of the window).
2. Type the following command exactly as shown, then press **Enter**:

   ```text
   add n/Alex Tan p/91234567 e/alex@email.com
   ```

   Here, `n/` is the prefix for name, `p/` is the prefix for phone number, and `e/` is the prefix for email.

<div markdown="block" class="alert alert-success example-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">The result display shows New contact added: Alex Tan; ... and Alex Tan appears in the contact list below the result display.</pre>
</div>

Congratulations! You've just used your first PingBook command!

[↑ Back to Table of Contents](#table-of-contents)

## Understanding Commands

All PingBook commands follow the same basic structure: a **command word** (what you want to do) followed by **parameters** (the details).

Here are the rules for reading command formats throughout this guide:

- Words in `UPPER_CASE` are placeholders for values you provide. For example, in `add n/NAME`, you replace `NAME` with an actual name: `add n/Alex Tan`.
- Parameters inside `[square brackets]` are **optional**; you can include them or leave them out. For example, `[a/ADDRESS]` means you may omit the address.
- Parameters followed by `...` can be **repeated** as many times as you like. For example, `[t/TAG]...` means you can add zero or more tags: `t/friend t/colleague`.
- Parameters can be typed in **any order**. `n/Alex p/91234567` and `p/91234567 n/Alex` produce the same result.
- Commands that take no parameters at all (such as `list`, `help`, `exit`, `clear`, `sort`, and `listarchived`) will simply ignore any extra text you type after the command word.

### Index-based command safety

Commands that use `INDEX` always refer to the **currently displayed list**, not a fixed global ordering.
After commands like `find`, `filter`, `sort`, or `listarchived`, the same contact may appear at a different index.
To avoid editing/deleting the wrong contact, run `list` (or `listarchived`) and confirm the index immediately before running `edit`, `delete`, `star`, `unstar`, `archive`, or `unarchive`.

### If a command fails

- Read the result display first — it tells you exactly what went wrong.
- Most failures come from missing prefixes, invalid index values, or wrong field formats.
- Correct the command and run it again; your existing data is not changed by failed commands.

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Copying from a PDF?</strong> If you copy a command from a PDF version of this guide and paste it into the command box, check that no extra spaces or line breaks were inserted. If the command does not work, try typing it out manually instead.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Managing Contacts

### Adding a contact: `add`

Add a new contact to your address book.

##### Format: `add n/NAME p/PHONE e/EMAIL [a/ADDRESS] [t/TAG]...`

Each prefix (`n/`, `p/`, `e/`, etc.) tells PingBook what type of information follows it. The table below explains what is required and what rules apply:

##### Field constraints

| Field | Required? | Rules |
|---|---|---|
| `n/NAME` | Yes | Letters, digits, spaces, and hyphens allowed (e.g. `Anne-Marie`). Must not be blank. |
| `p/PHONE` | Yes | Digits only, at least 3 digits long. |
| `e/EMAIL` | Yes | Must follow the format `localpart@domain` (e.g. `alex@email.com`). |
| `a/ADDRESS` | No | Any text. Can be added later using `edit`. |
| `t/TAG` | No | Letters and digits only, no spaces. Use separate `t/` prefixes for multiple tags. |

##### Steps

1. Click the **Command box**.
2. Type `add` followed by the contact's details using the prefixes shown above.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example:</strong></span>
<pre class="example-command">add n/Priya Sharma p/87654321 e/priya@company.com a/10 Orchard Road t/colleague</pre>
This adds a contact named Priya Sharma with a phone number, email, address, and the tag "colleague."

<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">New contact added: Priya Sharma; Phone: 87654321; Email: priya@company.com; Address: 10 Orchard Road; Tags: [colleague]</pre>
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> You can add a free-text note to a contact after creating them using the <code>remark</code> command. See <a href="#adding-a-remark-remark">Adding a remark</a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Editing a contact: `edit`

Change one or more details of an existing contact.

##### Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

- `INDEX` is the number shown next to the contact in the current list (e.g. if Priya is contact number 3, use `edit 3 ...`).
- You must change at least one field; you cannot run `edit 3` with nothing after it.
- Any field you leave out stays unchanged, **except tags**: if you include any `t/` value, it **replaces all** existing tags. To remove all tags entirely, use `t/` with nothing after it.

##### Steps

1. Type `list` and press **Enter** to see all contacts and their index numbers.
2. Type `edit` followed by the index number and the fields you want to update.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: update phone and email for contact 3</strong></span>
<pre class="example-command">edit 3 p/99887766 e/priya.new@company.com</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Edited Person: Priya Sharma; Phone: 99887766; ...</pre>
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: remove all tags from contact 3</strong></span>
<pre class="example-command">edit 3 t/</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Edited Person: Priya Sharma; Tags: []</pre>
</div>

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Watch out with tags:</strong> Editing tags replaces <em>all</em> existing tags at once. For example, <code>edit 3 t/vip</code> removes every previous tag and leaves only <code>vip</code>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Adding a remark: `remark`

Add, update, or remove a free-text note attached to a contact. This is useful for personal reminders like "Prefers email" or "Met at orientation week."

##### Format: `remark INDEX r/REMARK`

- To remove an existing remark, type `r/` with nothing after it.

##### Steps

1. Type `list` and press **Enter** to find the contact's index number.
2. Type `remark INDEX r/` followed by your note.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: add a remark to contact 2</strong></span>
<pre class="example-command">remark 2 r/Prefers contact by email only.</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Added remark to Person: ...</pre>
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: remove the remark from contact 2</strong></span>
<pre class="example-command">remark 2 r/</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Removed remark from Person: ...</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Deleting a contact: `delete`

Permanently remove a contact from your address book.

##### Format: `delete INDEX`

##### Steps

1. Type `list` and press **Enter** to find the contact's index number.
2. Type `delete` followed by the index number.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: delete contact number 4</strong></span>
<pre class="example-command">delete 4</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Deleted Person: ...</pre>
</div>

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Warning:</strong> Deletion is permanent and cannot be undone. If you just want to hide the contact for now without losing their data, use <code>archive</code> instead.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Finding Contacts

### Listing all contacts: `list`

Show all your active (non-archived) contacts.

##### Format: `list`

- Starred contacts appear before unstarred contacts.
- Archived contacts are not shown here. Use `listarchived` to see them.

##### Steps

1. Type `list`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Listed active contacts and all active contacts appear in the list.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Searching by name: `find`

Show only the contacts whose names contain the words you search for.

##### Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is **case-insensitive**, meaning it does not matter whether you use uppercase or lowercase letters. `alex` matches `Alex`.
- Only **complete words** are matched. Searching `Al` will not find `Alex`; you need to type the full word `Alex`.
- If you type multiple keywords, contacts matching **any one** of them are shown. For example, `find alex david` shows everyone named Alex and everyone named David.
- Only the contact's **name** is searched, not their phone number, email, or tags.

##### Steps

1. Type `find` followed by one or more name keywords.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: find all contacts named Alex or David</strong></span>
<pre class="example-command">find alex david</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">2 persons listed! and the matching contacts appear.</pre>
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> To search by tag instead of name, use the <code>filter</code> command.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Filtering by tag: `filter`

Show only the contacts that have a specific tag attached to them.

##### Format: `filter t/TAG [t/MORE_TAGS]`

- Tag matching is **case-insensitive**. `Friend` matches `friend`.
- If you provide multiple tags, contacts that have **any one** of those tags are shown.
- Archived contacts are never included in filter results.

##### Steps

1. Type `filter t/` followed by the tag name.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: show all contacts tagged "colleague" or "vip"</strong></span>
<pre class="example-command">filter t/colleague t/vip</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">N persons listed! where N is the number of matching contacts.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Sorting contacts: `sort`

Reorder your contact list so that starred contacts appear first, and all remaining contacts are sorted alphabetically by name.

##### Format: `sort`

##### Steps

1. Type `sort`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success result-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Sorted all persons (starred first, then by name) and the contact list reorders accordingly.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Organising Contacts

### Starring a contact: `star`

Mark a contact as important so they always float to the top of the list.

##### Format: `star INDEX`

##### Steps

1. Type `list` and press **Enter** to find the contact's index number.
2. Type `star` followed by the index number.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: star contact number 2</strong></span>
<pre class="example-command">star 2</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Starred Person: ...</pre>
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> After starring several contacts, run <code>sort</code> to group all starred contacts at the top of the list.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Removing a star: `unstar`

Remove the starred status from a contact.

##### Format: `unstar INDEX`

##### Steps

1. Type `list` and press **Enter** to find the contact's index number.
2. Type `unstar` followed by the index number.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: unstar contact number 2</strong></span>
<pre class="example-command">unstar 2</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Unstarred Person: ...</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Archiving a contact: `archive`

Hide a contact from the main list without deleting them. Useful for people you rarely contact but still want to keep on record.

##### Format: `archive INDEX`

- Archived contacts are hidden from `list`, `find`, and `filter` results.
- To see archived contacts, use the `listarchived` command.

##### Steps

1. Type `list` and press **Enter** to find the contact's index number.
2. Type `archive` followed by the index number.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: archive contact number 5</strong></span>
<pre class="example-command">archive 5</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Archived Person: ... and the contact no longer appears in the active list.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Restoring an archived contact: `unarchive`

Move an archived contact back to your active list.

##### Format: `unarchive INDEX`

##### Steps

1. Type `listarchived` and press **Enter** to see archived contacts and their index numbers.
2. Type `unarchive` followed by the index number.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: restore the first archived contact</strong></span>
<pre class="example-command">listarchived</pre>
<pre class="example-command">unarchive 1</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">The contact disappears from the archived list and reappears when you run list.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Listing archived contacts: `listarchived`

Show all contacts that have been archived.

##### Format: `listarchived`

##### Steps

1. Type `listarchived`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success result-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Listed archived contacts and all archived contacts appear in the list.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Advanced Features

### Creating command aliases: `alias`

Create a personal shortcut word that triggers a built-in command. For example, you could create `a` as a shortcut for `add` so you type less.

##### Format

| What you want to do | Command |
|---|---|
| Create a new shortcut | `alias add ALIAS COMMAND_WORD` |
| Remove a shortcut | `alias remove ALIAS` |
| See all current shortcuts | `alias list` |

- `ALIAS` is the shortcut word you want to create. It must not clash with any existing command word or alias.
- `COMMAND_WORD` must be one of PingBook's built-in commands (e.g. `add`, `delete`, `find`).

##### To create a shortcut

1. Type `alias add` followed by your chosen shortcut word and the command it should trigger.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: create shortcut <code>a</code> for the <code>add</code> command</strong></span>
<pre class="example-command">alias add a add</pre>
After this, you can type <code>a n/Alex p/91234567 e/alex@email.com</code> instead of the full <code>add</code> command.
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: see all your current shortcuts</strong></span>
<pre class="example-command">alias list</pre>
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: remove the shortcut <code>a</code></strong></span>
<pre class="example-command">alias remove a</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Editing the data file directly

PingBook automatically saves all your contacts to a JSON file (a plain-text data file) at `data/pingbook.json` inside the same folder as `pingbook.jar`. If you ever need to make many changes at once, you can open this file in any plain-text editor (such as Notepad on Windows or TextEdit on macOS) and edit it directly.

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Warning:</strong> JSON files must follow a strict format. If you accidentally delete a comma, bracket, or quotation mark, the file becomes invalid. When PingBook detects an invalid file, it discards all data and starts completely empty on the next launch. Always make a backup copy of <code>pingbook.json</code> (e.g. save a copy as <code>pingbook_backup.json</code>) before editing it manually.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Utility Commands

### Viewing help: `help`

Open the help window, which shows a link to this User Guide.

##### Format: `help`

##### Steps

1. Type `help`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success result-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
A help window opens showing a link to this User Guide.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> You can also press <strong>F1</strong> on your keyboard at any time to open the help window without typing a command. (Just press the F1 key, usually found in the top row of your keyboard.)
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Clearing all contacts: `clear`

Delete every single contact from your address book at once.

##### Format: `clear`

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Warning:</strong> This deletes all contacts permanently and cannot be undone. Before running this command, make a backup copy of <code>data/pingbook.json</code> in case you need the data later.
</div>

##### Steps

1. Type `clear`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success result-block">
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
The contact list becomes empty.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Exiting the app: `exit`

Close PingBook. Your data is saved automatically before the app closes, so you do not need to do anything extra.

##### Format: `exit`

##### Steps

1. Type `exit`.
2. Press **Enter**.

[↑ Back to Table of Contents](#table-of-contents)

## Data and Storage

### How your data is saved

PingBook saves your contacts automatically after every command that changes anything. You never need to press a "Save" button.

##### Where your data lives: `data/pingbook.json`, located inside the same folder as `pingbook.jar`.

### Transferring your data to another computer

Want to use PingBook on a different computer and bring your contacts with you? Here's how:

1. On your current computer, find and copy the file `data/pingbook.json`.
2. On the other computer, install Java 17 and follow this Quick Start guide to download `pingbook.jar` and launch it once. This creates the `data` folder automatically.
3. Replace the `data/pingbook.json` file on the new computer with the copy you brought over.
4. Launch PingBook on the new computer. All your contacts will appear.

[↑ Back to Table of Contents](#table-of-contents)

## FAQ

##### Q: I ran a command but the contact list did not change. What do I do?

Look at the **result display** panel just below the command box. It always shows what happened, including a specific error message if something went wrong. Common issues are a missing required field (the error message will name which field is missing), or using an index number that is larger than the number of contacts currently shown.

##### Q: I added a contact but they don't appear in the list. Why?

Your contact may have been accidentally archived. Type `listarchived` and press **Enter** to check. If they appear there, type `unarchive INDEX` (replacing INDEX with their number) and press **Enter** to bring them back to the active list.

##### Q: Can I have two contacts with the same name?

No. PingBook treats contacts with identical names as duplicates and will not add the second one. Use a slightly different name (like adding a middle initial) if you genuinely need two contacts with the same name.

##### Q: How do I undo a command?

PingBook does not have an undo command. For irreversible actions like `delete` and `clear`, make a backup copy of `data/pingbook.json` beforehand so you can restore it if needed.

##### Q: I get an error saying "Unknown command." What does that mean?

This means PingBook did not recognise the word you typed as a valid command. Check your spelling and make sure there are no extra spaces before the command word. Refer to the [Command Summary](#command-summary) for the full list of valid commands.

[↑ Back to Table of Contents](#table-of-contents)

## Known Issues

##### App window opens off-screen after disconnecting a monitor: If you moved PingBook to a second screen and then unplugged that screen, the app window might open in a position you cannot see the next time you launch it. To fix this: find and delete the file `preferences.json` from the folder containing `pingbook.jar`, then relaunch the app. This file stores the window position, and deleting it resets the window to the centre of your screen.

##### Help window does not reappear after being minimised: If you minimise the help window and then press **F1** or type `help` again, no new window appears. The help window is still open; it is just hidden at the bottom of your screen. Look for it in your **taskbar** (the bar of open apps along the bottom of your screen on Windows, or the Dock on macOS) and click it to bring it back.

[↑ Back to Table of Contents](#table-of-contents)

## Command Summary

Use this table as a quick reference once you are familiar with the app.

| Command | Format | Example |
|---|---|---|
| **add** | `add n/NAME p/PHONE e/EMAIL [a/ADDRESS] [t/TAG]...` | `add n/Priya Sharma p/87654321 e/priya@company.com t/colleague` |
| **edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...` | `edit 2 n/Priya Nair e/priya.nair@company.com` |
| **remark** | `remark INDEX r/REMARK` | `remark 2 r/Prefers email contact.` |
| **delete** | `delete INDEX` | `delete 3` |
| **list** | `list` | `list` |
| **find** | `find KEYWORD [MORE_KEYWORDS]` | `find priya alex` |
| **filter** | `filter t/TAG [t/MORE_TAGS]` | `filter t/colleague t/vip` |
| **sort** | `sort` | `sort` |
| **star** | `star INDEX` | `star 2` |
| **unstar** | `unstar INDEX` | `unstar 2` |
| **archive** | `archive INDEX` | `archive 5` |
| **unarchive** | `unarchive INDEX` | `unarchive 1` |
| **listarchived** | `listarchived` | `listarchived` |
| **alias add** | `alias add ALIAS COMMAND_WORD` | `alias add a add` |
| **alias remove** | `alias remove ALIAS` | `alias remove a` |
| **alias list** | `alias list` | `alias list` |
| **clear** | `clear` | `clear` |
| **help** | `help` | `help` |
| **exit** | `exit` | `exit` |

[↑ Back to Table of Contents](#table-of-contents)
