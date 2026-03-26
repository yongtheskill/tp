---
layout: page
title: John Doe's Project Portfolio Page
---

## Project: TaskNest

TaskNest is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

- **New Feature**: Added the ability to undo/redo previous commands.
    - What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
    - Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
    - Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

- **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

- **Code contributed**: [RepoSense dashboard](https://nus-cs2103-ay2526s2.github.io/tp-dashboard/?search=johndoe&breakdown=true)

- **Project management**:
    - Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

- **Enhancements to existing features**:
    - Updated the GUI color scheme (Pull requests [#33](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/33), [#34](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/34))
    - Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [#36](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/36), [#38](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/38))

- **Documentation**:
    - User Guide:
        - Added documentation for the features `delete` and `find`.
        - Did cosmetic tweaks to existing documentation of features `clear`, `exit`.
    - Developer Guide:
        - Added implementation details of the `delete` feature.

- **Community**:
    - PRs reviewed (with non-trivial review comments): [#12](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/12), [#32](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/32), [#19](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/19), [#42](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/42)
    - Contributed to forum discussions ([example discussion](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/32)).
    - Reported bugs and suggestions for other teams in the class ([example review report](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/19)).
    - Some parts of the history feature I added were adopted by several other classmates ([feature PR reference](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/42)).

- **Tools**:
    - Integrated a third-party library (Natty) to the project ([#42](https://github.com/AY2526S2-CS2103T-T17-2/tp/pull/42))
    - Integrated a new GitHub plugin (CircleCI) to the team repo

