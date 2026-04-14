---
layout: page-with-sidebar
title: User Guide
---

## Welcome to PingBook

Hi there! Whether you are brand new to command-line tools or already comfortable in a terminal, this guide has you covered. If you have never typed a command before, every step is explained from scratch. If you already know your way around a terminal, you can skim the Quick Start and jump straight to the feature you need.

**PingBook** is a desktop app for managing contacts. You type short text commands to add, search, and organise people in your phone book. Once you get the hang of it, typing is actually much faster than hunting around with a mouse.

As a CS student, your network grows fast: project teammates, lab partners, teaching assistants, professors, and internship contacts all pile up. PingBook is built for exactly that: quickly adding a new teammate when a group project starts, tagging your tutors and TAs so you can filter them in one step, starring the recruiter you are actively emailing, and archiving old contacts at the end of each semester so your active list stays clean.

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
5. [Creating/Editing Contacts](#creatingediting-contacts)
   - [Adding a contact: `add`](#adding-a-contact-add)
   - [Editing a contact: `edit`](#editing-a-contact-edit)
   - [Adding a remark: `remark`](#adding-a-remark-remark)
   - [Deleting a contact: `delete`](#deleting-a-contact-delete)
6. [Finding Contacts](#finding-contacts)
   - [Listing all contacts: `list`](#listing-all-contacts-list)
   - [Searching contacts: `find`](#searching-contacts-find)
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
13. [Planned Enhancements](#planned-enhancements)
14. [Command Summary](#command-summary)

## Product Description

PingBook is a desktop application for managing contacts quickly using typed commands.

You can add, edit, search, organise, and archive contacts without ever touching the mouse. Starred contacts appear at the top of your list, archived contacts stay out of sight until you need them, and command aliases let you create shortcuts for the commands you use most.

If you can type quickly, PingBook lets you manage hundreds of contacts faster than any click-based app.

##### Built for student life

| Situation | How PingBook helps |
|---|---|
| A new CS project starts and you need to store your teammates' details quickly | `add` a contact in one line; tag them `t/cs2103` so the whole team is one `filter` away |
| You want your internship recruiter always at hand | `star` the contact so they float to the top every time you open the app |
| The semester ends and old TA contacts clutter your list | `archive` them — they disappear from the active list but are never deleted |
| You picked up a conference contact but only have their name, phone number, and email right now | Add them with `n/`, `p/`, and `e/`; fill in the rest with `edit` later |

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

> A programming language and platform that PingBook is built on. Your computer needs Java installed for PingBook to work, just as you need a PDF reader to open a PDF. You need **Java 17** specifically.

##### JDK (Java Development Kit)

> The full package that installs Java on your computer. When this guide says "install Java 17," it means installing the JDK version 17.

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

This section walks you through installing and launching PingBook for the first time. Follow the steps for your operating system (**Windows**, **macOS**, or **Linux**).

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

##### On Linux

1. Press **Ctrl + Alt + T** on your keyboard. This shortcut opens a terminal on most popular Linux distributions (such as Ubuntu, Debian, and Fedora).
2. A terminal window will open. This is your terminal.

> Alternatively, right-click on your desktop and look for an option such as **Open Terminal** or **Open in Terminal**. The exact option depends on your Linux desktop environment.

You will see a prompt like `C:\Users\yourname>` (Windows), `yourname@MacBook ~ %` (macOS), or `yourname@hostname:~$` (Linux). This is normal; it means the terminal is ready for your input.

### Step 2: Check Java is installed

PingBook needs **Java 17** (a specific version of the Java platform) to run. Let's check if you have it.

1. In your terminal, type the following exactly and press **Enter**:

   ```shell
   java -version
   ```

2. Look at the output:
   - If you see something like `java version "17.x.x"` or `openjdk version "17.x.x"`, you have the right version. Move to Step 3.
   - If the version number starts with something other than `17` (e.g. `11`, `21`), you need to install Java 17.
   - If you see `'java' is not recognized` (Windows) or `command not found` (macOS/Linux), Java is not installed yet.

##### To install Java 17

Rather than include installation steps that may become outdated, this guide links to official installation guides for each platform:

- **Windows:** Follow the [Adoptium Java 17 installation guide for Windows](https://adoptium.net/installation/windows/).

- **macOS:** Follow the [macOS Java installation guide](https://se-education.org/guides/tutorials/javaInstallationMac.html). macOS requires a specific JDK variant (Azul JDK 17 with JavaFX support) for PingBook to display correctly.

- **Linux:** Follow the [Adoptium Java 17 installation guide for Linux](https://adoptium.net/installation/linux/) for your distribution.

Once installed, close and reopen your terminal, then run `java -version` again to confirm.

### Step 3: Download PingBook

**1. Download the file**

1. Go to the [PingBook releases page](https://github.com/AY2526S2-CS2103T-T17-2/tp/releases) in your web browser.
2. Under the latest release, click `pingbook.jar` to download it. This is the JAR file (the runnable application file) that contains PingBook.
3. Your browser will save it to your **Downloads** folder automatically.

**2. Create a dedicated PingBook folder**

<div markdown="block" class="alert alert-info">
💡 <strong>Why a dedicated folder?</strong> PingBook automatically creates a <code>data</code> folder next to <code>pingbook.jar</code> to save your contacts. Keeping <code>pingbook.jar</code> in its own folder keeps everything tidy.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Understanding file paths:</strong> A file path is the address of a folder or file on your computer (see <a href="#key-terms-read-this-first">Key Terms</a> for a full explanation). In the examples below, replace <code>yourname</code> with your actual login username — the name that appears when you log into your computer. The <code>~</code> symbol is a shortcut for your home folder, so <code>~/Documents</code> and <code>/Users/yourname/Documents</code> are the same place.
</div>

##### On Windows

1. Press **Windows key + E** to open **File Explorer**.
2. Click **Documents** in the left sidebar (or navigate to any location you prefer).
3. Right-click in an empty area and choose **New → Folder**.
4. Name the folder `PingBook` and press **Enter**.

Your new folder's path will be: `C:\Users\yourname\Documents\PingBook`

##### On macOS

1. Open **Finder** (the smiley-face icon in your Dock).
2. Click **Documents** in the left sidebar (or navigate to any location you prefer).
3. Right-click (or Control-click) in an empty area and choose **New Folder**.
4. Name the folder `PingBook` and press **Return**.

Your new folder's path will be: `~/Documents/PingBook`

##### On Linux

1. Open your **Files** application (also known as Nautilus, Dolphin, or Thunar, depending on your desktop environment).
2. Navigate to your home folder or **Documents**.
3. Right-click in an empty area and choose **New Folder** (or **Create Folder**).
4. Name the folder `PingBook` and press **Enter**.

Your new folder's path will be: `~/Documents/PingBook`

**3. Move `pingbook.jar` into the PingBook folder**

##### On Windows

1. In **File Explorer**, navigate to your **Downloads** folder (`C:\Users\yourname\Downloads`).
2. Right-click `pingbook.jar` and choose **Cut** (or press **Ctrl + X**).
3. Navigate to your new `PingBook` folder.
4. Right-click in an empty area and choose **Paste** (or press **Ctrl + V**).

##### On macOS

1. In **Finder**, click **Downloads** in the left sidebar.
2. Drag `pingbook.jar` from Downloads into your new **PingBook** folder.

> Alternatively: right-click `pingbook.jar`, choose **Move to…**, then select the `PingBook` folder.

##### On Linux

1. In your **Files** application, navigate to your **Downloads** folder.
2. Drag `pingbook.jar` into your new `PingBook` folder.

> Alternatively: right-click `pingbook.jar`, choose **Move to…** (or **Cut**), then navigate to `PingBook` and **Paste**.

### Step 4: Navigate to your folder

Now you need to tell your terminal to "go to" the folder where `pingbook.jar` lives. You do this with the `cd` command (short for **c**hange **d**irectory, which means changing which folder you're in).

##### On Windows

```shell
cd C:\Users\yourname\Documents\PingBook
```

Replace `yourname` with your actual Windows username. Press **Enter**.

##### On macOS

```shell
cd ~/Documents/PingBook
```

Press **Enter**.

##### On Linux

```shell
cd ~/PingBook
```

Press **Enter**.

<div markdown="block" class="alert alert-warning">
⚠️ <strong>If your folder path contains spaces:</strong> (e.g. <code>My PingBook</code>), wrap the whole path in quotation marks: <code>cd "C:\Users\yourname\My PingBook"</code>
</div>

After pressing Enter, your terminal prompt will update to show the new folder. For example, on Windows you might see `C:\Users\yourname\PingBook>`.

To confirm `pingbook.jar` is there, you can list the files in the folder:

- **Windows:** type `dir` and press **Enter**
- **macOS/Linux:** type `ls` and press **Enter**

You should see `pingbook.jar` in the list.

### Step 5: Launch the app

With your terminal open and the PingBook folder set as the current directory (as done in Step 4 above), type the following and press **Enter**:

```shell
java -jar pingbook.jar
```

This tells Java to run the `pingbook.jar` file.

![Terminal flow showing the Quick Start commands](images/TerminalExample.png)
*Screenshot of the entire process to launch PingBook.*

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

![Screenshot of app](images/LabelledUi.png)
*The PingBook window with the menu bar, command box, result display, and contact list labelled.*

The PingBook window (the user interface, or UI, meaning all the visual elements you interact with) contains four areas, arranged vertically from top to bottom:

| Area | Description |
|---|---|
| **Menu bar** | The row of menus (File, Help) at the very top of the window. |
| **Command box** | The full-width text field directly below the menu bar where you type commands. It is highlighted with a blue outline when active. Click it before typing. |
| **Result display** | The full-width panel directly below the command box. It shows the outcome of each command — a success message, an error message, or a list of results. |
| **Contact list** | The large panel filling the rest of the window, below the result display. Each contact appears as a card showing their name, phone number, email, address, and tags. |

### Your First Command

Let's add your first contact to make sure everything is working.

1. Click inside the **Command box** (the text field near the top of the window).
2. Type the following command exactly as shown, then press **Enter**:

   ```text
   add n/Wei Jie p/91234567 e/weijie@u.nus.edu
   ```

   Here, `n/` is the prefix for name, `p/` is the prefix for phone number, and `e/` is the prefix for email.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: add your first contact</strong></span>
<pre class="example-command">add n/Wei Jie p/91234567 e/weijie@u.nus.edu</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">The result display shows New contact added: Wei Jie; ... and Wei Jie appears in the contact list below the result display.</pre>
</div>

Congratulations! You've just used your first PingBook command!

[↑ Back to Table of Contents](#table-of-contents)

## Understanding Commands

All PingBook commands follow the same basic structure: a **command word** (what you want to do) followed by **parameters** (the details).

Here are the rules for reading command formats throughout this guide:

- Words in `UPPER_CASE` are placeholders for values you provide. For example, in `add n/NAME`, you replace `NAME` with an actual name: `add n/Alex Tan`.
- Parameters inside `[square brackets]` are **optional**; you can include them or leave them out. For example, `[a/ADDRESS]` means you may omit the address.
- Parameters followed by `...` can be **repeated** as many times as you like. For example, `[t/TAG]...` means you can add zero or more tags: `t/groupmate t/cs2103`.
- Parameters can be typed in **any order**. `n/Alex p/91234567` and `p/91234567 n/Alex` produce the same result.
- Commands that take no parameters at all (such as `list`, `help`, `exit`, `sort`, and `listarchived`) require only the command word. If you add extra text, PingBook shows an error instead of guessing what you meant.
- The only exception is `clear`, which optionally accepts the confirmation word `confirm`.

### Index-based command safety

Commands that use `INDEX` always refer to the **currently displayed list**, not a fixed global ordering.
After commands like `find`, `filter`, or `listarchived`, the currently displayed contacts may appear at different indices.
**Note:** The `sort` command is special — it reorders **all** contacts globally (both active and archived), so indices can change even for contacts not currently on your screen.
To avoid editing/deleting the wrong contact, run `list` (or `listarchived`) and confirm the index immediately before running `edit`, `delete`, `star`, `unstar`, `archive`, or `unarchive`.

### If a command fails

- Read the result display first — it tells you exactly what went wrong.
- Most failures come from missing prefixes, invalid index values, or wrong field formats.
- Correct the command and run it again; your existing data is not changed by failed commands.
- If PingBook cannot save a write command (for example due to file permissions), the command is treated as failed and any in-memory changes are rolled back immediately.

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Copying from a PDF?</strong> If you copy a command from a PDF version of this guide and paste it into the command box, check that no extra spaces or line breaks were inserted. If the command does not work, try typing it out manually instead.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Creating/Editing Contacts

### Adding a contact: `add`

Adds a new contact to your address book.

##### Format: `add n/NAME p/PHONE e/EMAIL [a/ADDRESS] [t/TAG]...`

Each prefix (`n/`, `p/`, `e/`, etc.) tells PingBook what type of information follows it. The table below explains what is required and what rules apply:

##### Field constraints

| Field | Required? | Rules |
|---|---|---|
| `n/NAME` | Yes | Letters (including accented characters), digits, spaces, hyphens, apostrophes, and periods allowed (e.g. `Anne-Marie`, `O'Brien`, `Dr. Lee`, `David Jr.`). Must start with a letter or digit, punctuation cannot appear consecutively, and a period may appear at the end of a token (such as `Jr.`). |
| `p/PHONE` | Yes | Must contain 3 to 15 digits. You may include spaces, `+`, hyphens, parentheses, and annotation letters (e.g. `+65 9123 4567`, `91234567 (Telegram)`). PingBook stores the phone value as entered. |
| `e/EMAIL` | Yes | Must follow `localpart@domain` (e.g. `alex@email.com`). The domain must contain period-separated labels (at least one `.`), each label may contain letters/digits and internal hyphens only, and the final label must be at least 2 characters (so `a@localhost` and `doc@doc` are invalid). |
| `a/ADDRESS` | No | Any text, but **extends only until the next prefix is encountered** (e.g. in `a/Block 1 t/friend`, the address is `Block 1` and `t/friend` starts a new tag field). If you need address text that looks like a prefix (e.g. `p/o Box`), use the `edit` command instead to add/modify the address. |
| `t/TAG` | No | Letters and digits are required in each segment; single hyphens may appear between segments, with no spaces or leading, trailing, or consecutive hyphens. Use separate `t/` prefixes for multiple tags. Tag text is stored as entered, so `friends` and `Friends` are treated as different tag labels in the UI. |

##### Steps

1. Click the **Command box**.
2. Type `add` followed by the contact's details using the prefixes shown above.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example:</strong></span>
<pre class="example-command">add n/Priya Sharma p/87654321 e/priya@u.nus.edu a/COM1-02-12 t/ta</pre>
This adds your CS2103T TA Priya Sharma with her phone number, email, office room number, and the tag "ta."

<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">New contact added: Priya Sharma; Phone: 87654321; Email: priya@u.nus.edu; Address: COM1-02-12; Tags: [ta] (7 contacts total)</pre>
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> You can add a free-text note to a contact after creating them using the <code>remark</code> command. See <a href="#adding-a-remark-remark">Adding a remark</a>.
</div>

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Important:</strong> PingBook identifies contacts by their exact name, so you can't have two contacts with identical names. Names are case-sensitive and order-sensitive, meaning <code>Alex Yeoh</code>, <code>alex yeoh</code>, and <code>Yeoh Alex</code> are all considered different names. This uniqueness check applies to the whole address book, including archived contacts. If you try to add a contact with a name that matches any existing contact exactly (active or archived), PingBook will reject the addition. If you need to store two people with the same name, try variations like adding a middle initial (e.g. <code>Alex T. Yeoh</code> vs <code>Alex R. Yeoh</code>) or use tags to distinguish them.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Note:</strong> Unlike names, phone numbers and emails do <strong>not</strong> need to be unique. You can have multiple contacts sharing the same phone number or email address without any warning. This is useful if, for example, you store multiple people who share a work phone line or a shared email account.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Editing a contact: `edit`

Changes one or more details of an existing contact.

##### Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

- `INDEX` is the number shown next to the contact in the current list (e.g. if Priya is contact number 3, use `edit 3 ...`).
- You must change at least one field; you cannot run `edit 3` with nothing after it.
- Any field you leave out stays unchanged, **except tags**: if you include any `t/` value, it **replaces all** existing tags. To remove all tags entirely, use `t/` with nothing after it.
- To clear the address field, use `a/` with nothing after it.
- `edit` only works on active contacts. To edit an archived contact, first [unarchive](#restoring-an-archived-contact-unarchive) it.
- If you edit a name to one that already exists anywhere in PingBook (including archived contacts), the edit is rejected with the duplicate-person error. If this happens, run `listarchived` to check for a name conflict in archived contacts.

##### Steps

1. Type `list` and press **Enter** to see all contacts and their index numbers.
2. Type `edit` followed by the index number and the fields you want to update.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: update phone and email for contact 3</strong></span>
<pre class="example-command">edit 3 p/99887766 e/priya@comp.nus.edu</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Edited Person: Priya Sharma; Phone: 99887766; ...</pre>
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: remove all tags from contact 3</strong></span>
<pre class="example-command">edit 3 t/</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Edited Person: Priya Sharma; Tags: (none)</pre>
</div>

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Watch out with tags:</strong> Editing tags replaces <em>all</em> existing tags at once. For example, <code>edit 3 t/cs2103ta</code> removes every previous tag and leaves only <code>cs2103ta</code>. Tag casing is also preserved, so entering <code>friends</code> and <code>Friends</code> creates two visually different tag labels.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>See also:</strong> To add a free-text note rather than changing structured fields, use <a href="#adding-a-remark-remark"><code>remark</code></a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Adding a remark: `remark`

Adds, updates, or removes a free-text note attached to a contact. This is useful for personal reminders like "Prefers email" or "Met at orientation week."

##### Format: `remark INDEX r/REMARK`

- To remove an existing remark, type `r/` with nothing after it.
- Supply `r/` exactly once. Commands with multiple `r/` prefixes are rejected.
- `remark` only works on active contacts. To change a remark for an archived contact, first [unarchive](#restoring-an-archived-contact-unarchive) the contact.

##### Steps

1. Type `list` and press **Enter** to find the active contact's index number.
2. Type `remark INDEX r/` followed by your note.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: add a remark to contact 2</strong></span>
<pre class="example-command">remark 2 r/Holds consultation every Thursday 3-4pm at COM1-02-12.</pre>
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

Permanently removes a contact from your address book.

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
⚠️ <strong>Warning:</strong> Deletion is permanent and cannot be undone. If you just want to hide the contact for now without losing their data, use <a href="#archiving-a-contact-archive"><code>archive</code></a> instead.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Finding Contacts

### Listing all contacts: `list`

Shows all your active (non-archived) contacts.

##### Format: `list`

- Starred contacts appear before unstarred contacts.
- Archived contacts are not shown here. Use [`listarchived`](#listing-archived-contacts-listarchived) to see them.

##### Steps

1. Type `list`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: list all active contacts</strong></span>
<pre class="example-command">list</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Listed active contacts</pre>
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Note:</strong> Although the <code>list</code> command doesn't modify any data, the data file (and its containing folder) must be writable for the command to work. If you get an error when running <code>list</code>, check that your data folder is writable and that no other application has locked the file.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Searching contacts: `find`

Shows only the contacts that match any of the keywords you search for.

#### Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is **case-insensitive**, meaning it does not matter whether you use uppercase or lowercase letters. `alex` matches `Alex`.
- **Partial matching** is supported. Searching `Al` will find `Alex`, `Alice`, etc.
- If you type multiple keywords, contacts matching **any one** of them are shown. For example, `find wei priya` shows everyone named Wei and everyone named Priya.
- The search looks across **all fields**: name, phone number, email, address, remark, and tags. Matching is substring-based. For example, `find 9123` finds contacts whose phone number contains `9123`, and `find ta` can match a tag like `teaching-assistant` because it contains `ta`.
- The search respects your current view. If you are viewing archived contacts (via `listarchived`), `find` searches only within archived contacts. If you are in the active view (via `list`), it searches only active contacts.
- `find` takes plain keywords only. If you type a command-style prefix such as `n/John`, PingBook treats it as invalid input instead of searching for the literal text.

##### Steps

1. Type `find` followed by one or more keywords.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: find all contacts named Wei or Priya</strong></span>
<pre class="example-command">find wei priya</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">2 persons listed!</pre>
The matching contacts appear in the list below.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> You can search by any field — name, phone, email, address, remark, or tags. To filter exclusively by tag, use <a href="#filtering-by-tag-filter"><code>filter</code></a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Filtering by tag: `filter`

Shows only the contacts that have a specific tag attached to them.

##### Format: `filter t/TAG [t/MORE_TAGS]`

- Tag matching is **case-insensitive** and requires a **full tag match**. For example, `filter t/ta` matches a tag named exactly `ta`, but does not match `teaching-assistant`.
- Because filtering is case-insensitive, `filter t/friends` will match contacts tagged `friends` and `Friends`.
- If you provide multiple tags, contacts that have **any one** of those tags are shown.
- The filter respects your current view. If you are viewing archived contacts (via `listarchived`), `filter` searches only within archived contacts. Otherwise, it searches only active contacts.

##### Steps

1. Type `filter t/` followed by the tag name.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: show all contacts tagged "ta" or "groupmate"</strong></span>
<pre class="example-command">filter t/ta t/groupmate</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">2 persons listed!</pre>
The number shown reflects how many contacts match the tags you searched for.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>See also:</strong> To search across names, phone numbers, emails, addresses, or tags, use <a href="#searching-contacts-find"><code>find</code></a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Sorting contacts: `sort`

Reorders your contact list so that starred contacts appear first, and all remaining contacts are sorted alphabetically by name.

##### Format: `sort`

- The `sort` command reorders **all** contacts in your address book (both active and archived) alphabetically, regardless of which view you are currently in.
- If you run `sort` while viewing archived contacts (via [`listarchived`](#listing-archived-contacts-listarchived)), the active contacts will also be reordered in the background. When you switch back to the active view (via [`list`](#listing-all-contacts-list)), you will see the newly sorted active contacts.
- If you run `sort` while viewing a filtered list (e.g. after `find` or `filter`), the success message still says "Sorted all persons" because sorting is global. However, the on-screen list may remain filtered; run `list` (or `listarchived`) to view the full reordered list.

##### Steps

1. Type `sort`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: sort the contact list</strong></span>
<pre class="example-command">sort</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Sorted all persons (starred first, then by name)</pre>
Sorting is applied to the whole address book. If your current view is filtered, you may still see only that filtered subset until you run <code>list</code> (or <code>listarchived</code>).
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Organising Contacts

### Starring a contact: `star`

Marks a contact as important so they always float to the top of the list.

##### Format: `star INDEX`

- You can only star **active** (non-archived) contacts. To star an archived contact, first [unarchive](#restoring-an-archived-contact-unarchive) it.
- After a successful `star` command, the contact's position updates immediately in the current list and moves into the starred section at the top.

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

![A starred contact shown at the top of the list](images/StarredContact.png)
*A starred contact is marked with a star icon and floats to the top of the active list.*

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> Starred contacts already move to the top automatically. Run <a href="#sorting-contacts-sort"><code>sort</code></a> when you want to alphabetize names within the starred group and within the unstarred group.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Removing a star: `unstar`

Removes the starred status from a contact.

##### Format: `unstar INDEX`

- You can only unstar **active** (non-archived) contacts. To unstar an archived contact, first [unarchive](#restoring-an-archived-contact-unarchive) it.
- After a successful `unstar` command, the contact's position updates immediately in the current list and moves out of the starred section.

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

<div markdown="block" class="alert alert-info">
💡 <strong>See also:</strong> To re-star a contact, use <a href="#starring-a-contact-star"><code>star</code></a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Archiving a contact: `archive`

Hides a contact from the main list without deleting them. Useful for people you rarely contact but still want to keep on record.

##### Format: `archive INDEX`

- Archived contacts are hidden from `list` results.
- To see archived contacts, use [`listarchived`](#listing-archived-contacts-listarchived). You can then use `find` and `filter` to search within archived contacts.

##### Steps

1. Type `list` and press **Enter** to find the contact's index number.
2. Type `archive` followed by the index number.
3. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: archive contact number 5</strong></span>
<pre class="example-command">archive 5</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Archived Person: ...</pre>
The contact no longer appears in the active list.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>See also:</strong> To bring an archived contact back to your active list, use <a href="#restoring-an-archived-contact-unarchive"><code>unarchive</code></a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Restoring an archived contact: `unarchive`

Moves an archived contact back to your active list.

##### Format: `unarchive INDEX`

##### Steps

1. Type `listarchived` and press **Enter** to see archived contacts and their index numbers.
2. Type `unarchive` followed by the index number.
3. Press **Enter**.

If you run `unarchive` while the active contact list is showing, PingBook will tell you to run `listarchived` first.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: restore the first archived contact</strong></span>
<pre class="example-command">unarchive 1</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Unarchived Person: ...</pre>
The contact disappears from the archived list and reappears when you run <code>list</code>.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>See also:</strong> To archive a contact, use <a href="#archiving-a-contact-archive"><code>archive</code></a>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Listing archived contacts: `listarchived`

Shows all contacts that have been archived.

##### Format: `listarchived`

##### Steps

1. Type `listarchived`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: view all archived contacts</strong></span>
<pre class="example-command">listarchived</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Listed archived contacts</pre>
All archived contacts appear in the list.
</div>

![The archived contacts list view](images/ListArchived.png)
*The contact list after running `listarchived`, showing only archived contacts.*

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> To go back to your active contacts, type <a href="#listing-all-contacts-list"><code>list</code></a> and press <strong>Enter</strong>.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Advanced Features

### Creating command aliases: `alias`

Creates a personal shortcut word that triggers a built-in command. For example, you could create `la` as a shortcut for `listarchived` so you type less.

##### Format

| What you want to do | Command |
|---|---|
| Create a new shortcut | `alias add ALIAS COMMAND_WORD` |
| Remove a shortcut | `alias remove ALIAS` |
| See all current shortcuts | `alias list` |

- `ALIAS` is the shortcut word you want to create. It must not clash with any existing command word or alias.
- `COMMAND_WORD` must be one of PingBook's built-in commands (e.g. `add`, `delete`, `find`).

##### Steps

1. Type `alias add` followed by your chosen shortcut word and the command it should trigger.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: create shortcut <code>la</code> for the <code>listarchived</code> command</strong></span>
<pre class="example-command">alias add la listarchived</pre>
After this, you can type <code>la</code> instead of <code>listarchived</code> to view your archived contacts.
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Alias 'la' added for command 'listarchived'.</pre>
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: see all your current shortcuts</strong></span>
<pre class="example-command">alias list</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Aliases:
la -> listarchived</pre>
</div>

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: remove the shortcut <code>la</code></strong></span>
<pre class="example-command">alias remove la</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">Alias 'la' removed.</pre>
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Editing the data file directly

PingBook automatically saves all your contacts to a JSON file (a plain-text data file) at `data/pingbook.json` inside the same folder as `pingbook.jar`. If you ever need to make many changes at once, you can edit this file in a plain-text editor (such as Notepad on Windows, TextEdit on macOS, or Gedit/nano on Linux) **only when PingBook is closed**.

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Important:</strong> Do not edit <code>pingbook.json</code> while PingBook is running. PingBook keeps data in memory during a session and writes that in-memory copy back to disk when commands run, which can overwrite your external file edits without warning. Safe workflow: close PingBook → edit and save <code>pingbook.json</code> → relaunch PingBook.
</div>

![The pingbook.json data file open in a text editor](images/SaveJSON.png)
*`pingbook.json` open in a plain-text editor, showing the raw contact data.*

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Warning:</strong> JSON files must follow a strict format. If you accidentally delete a comma, bracket, or quotation mark, the file becomes invalid. PingBook keeps a temporary backup file (`pingbook.json.bak`) only while saving; under normal use it is removed again after a successful save, so you usually will not see it in the folder. If an interrupted or failed save leaves that file behind, PingBook will try to recover the whole data snapshot from it at startup, restoring both contacts and aliases together. If the backup is also invalid or does not exist, PingBook starts with an empty address book and an empty alias registry. To avoid losing data, always make a backup copy of <code>pingbook.json</code> (e.g. save a copy as <code>pingbook_backup.json</code>) before editing it manually.
</div>

[↑ Back to Table of Contents](#table-of-contents)

## Utility Commands

### Viewing help: `help`

Opens the help window, which displays a link to this User Guide. You can click the **Copy** button in the window to copy the link, then paste it into a web browser to access the User Guide.

##### Format: `help`

##### Steps

1. Type `help`.
2. Press **Enter**.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: open the help window</strong></span>
<pre class="example-command">help</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
A help window opens displaying a link to this User Guide. Click the <strong>Copy</strong> button to copy the link to your clipboard, then paste it into your web browser to view the full User Guide.
</div>

<div markdown="block" class="alert alert-info">
💡 <strong>Tip:</strong> You can also press <strong>F1</strong> on your keyboard at any time to open the help window without typing a command. (Just press the F1 key, usually found in the top row of your keyboard.)
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Clearing all contacts: `clear`

Deletes every contact from your address book at once. Requires confirmation to prevent accidental data loss.

#### Format: `clear confirm`

##### Steps

1. Type `clear confirm`.
2. Press **Enter**.

If you type just `clear` without `confirm`, PingBook will display a reminder asking you to type `clear confirm` to proceed.

<div markdown="block" class="alert alert-success example-block">
<span class="example-label">📌 <strong>Example: clear all contacts</strong></span>
<pre class="example-command">clear confirm</pre>
<span class="example-result-label">✅ <strong>Expected result:</strong></span>
<pre class="example-result">PingBook has been cleared!</pre>
The contact list becomes empty.
</div>

<div markdown="block" class="alert alert-warning">
⚠️ <strong>Warning:</strong> This deletes all contacts permanently and cannot be undone. Before running this command, make a backup copy of <code>data/pingbook.json</code> in case you need the data later.
</div>

[↑ Back to Table of Contents](#table-of-contents)

### Exiting the app: `exit`

Closes PingBook. Your data is saved automatically before the app closes, so you do not need to do anything extra.

##### Format: `exit`

##### Steps

1. Type `exit`.
2. Press **Enter**.

[↑ Back to Table of Contents](#table-of-contents)

## Data and Storage

### How your data is saved

PingBook saves your contacts automatically once any data changes. You never need to press a "Save" button.

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

## Planned Enhancements

The following improvements are planned for future versions:

##### Expanded tag color palette: Currently, tags are assigned colors based on a limited palette, which can lead to multiple different tags appearing in the same color (hash collisions). This makes it harder to visually categorize and distinguish between tags. A future version will feature a larger color palette and improved color assignment algorithm to ensure better visual differentiation between tags.

[↑ Back to Table of Contents](#table-of-contents)

## Command Summary

Use this table as a quick reference once you are familiar with the app.

| Command | Format | Example |
|---|---|---|
| **add** | `add n/NAME p/PHONE e/EMAIL [a/ADDRESS] [t/TAG]...` | `add n/Priya Sharma p/87654321 e/priya@u.nus.edu t/ta` |
| **edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...` | `edit 2 n/Priya Nair e/priya.nair@comp.nus.edu` |
| **remark** | `remark INDEX r/REMARK` | `remark 2 r/Consultation Thu 3-4pm.` |
| **delete** | `delete INDEX` | `delete 3` |
| **list** | `list` | `list` |
| **find** | `find KEYWORD [MORE_KEYWORDS]` | `find wei priya` |
| **filter** | `filter t/TAG [t/MORE_TAGS]` | `filter t/ta t/groupmate` |
| **sort** | `sort` | `sort` |
| **star** | `star INDEX` | `star 2` |
| **unstar** | `unstar INDEX` | `unstar 2` |
| **archive** | `archive INDEX` | `archive 5` |
| **unarchive** | `unarchive INDEX` | `unarchive 1` |
| **listarchived** | `listarchived` | `listarchived` |
| **alias add** | `alias add ALIAS COMMAND_WORD` | `alias add la listarchived` |
| **alias remove** | `alias remove ALIAS` | `alias remove la` |
| **alias list** | `alias list` | `alias list` |
| **clear** | `clear confirm` | `clear confirm` |
| **help** | `help` | `help` |
| **exit** | `exit` | `exit` |

[↑ Back to Table of Contents](#table-of-contents)
