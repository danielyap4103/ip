Atlas User Guide

📌 Introduction

Atlas is a lightweight task management chatbot with a JavaFX GUI.
It helps you manage todos, deadlines, and events efficiently using simple text commands.

Atlas automatically saves your tasks after every change, so you never need to manually save your work.

🚀 Quick Start

Download the latest JAR file.

Open a terminal in the folder containing the JAR.

Run:

java -jar atlas.jar


Start typing commands in the input box at the bottom of the window.

🧭 Features
1. Add a Todo

Adds a simple task without a date.

Format:

todo DESCRIPTION


Example:

todo Submit tutorial

2. Add a Deadline

Adds a task with a due date.

Format:

deadline DESCRIPTION /by YYYY-MM-DD


Example:

deadline Submit assignment /by 2026-02-20

3. Add an Event

Adds a task with a start and end date.

Format:

event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD


Example:

event CS2103 Workshop /from 2026-02-15 /to 2026-02-16

4. List Tasks

Displays all tasks currently stored.

Format:

list

5. Mark Task as Done

Marks a task as completed.

Format:

mark INDEX


Example:

mark 1

6. Unmark Task

Marks a completed task as not done.

Format:

unmark INDEX

7. Delete Task

Deletes a task from the list.

Format:

delete INDEX

8. Find Tasks

Finds tasks containing a keyword.

Format:

find KEYWORD


Example:

find tutorial

9. Update Task (Extension Feature)

Allows editing an existing task without deleting it first.

Update Todo
update INDEX todo NEW_DESCRIPTION


Example:

update 1 todo Revise lecture notes

Update Deadline
update INDEX deadline NEW_DESCRIPTION /by YYYY-MM-DD


Example:

update 2 deadline Submit report /by 2026-03-01

Update Event
update INDEX event NEW_DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD


Example:

update 3 event Team meeting /from 2026-02-22 /to 2026-02-22

10. Exit Program
    bye


Closes Atlas.