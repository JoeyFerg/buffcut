# BuffCut Software Requirements Specification

## Overview

BuffCut is a clipboard manager for Android devices. BuffCut's primary function is to save a history of
copied texts to be selected and pasted later by the user. There are three main screens to BuffCut: the preferences
screen, the clipboard buffer overlay, and the status bar icon. Users can use the preferences screen to change different
settings and personalize their BuffCut experience. The clipboard buffer overlay will be used to paste the text that the
user had previously copied, and the icon is used to access the buffer overlay when the user wants to paste an item in the buffer.

In summary, the goal of BuffCut is to be a sleek, fast, and intuitive clipboard buffer that will better the copy and paste
skills of users around the world.

---

## Scope

The scope of this project simply includes a working clipboard buffer as described in the following requirements. Anything beyond
that should be run by both the client and the team before it is added to this document and inevitably to this app.

---

## Screens

### Preferences

The preference screen will be accessible through the app icon. It will be split up into multiple tabs that allow the user to both set and manage a number of different settings. One tab will allow the user to change settings like how many copied items can be in the buffer at one time.
Another tab will allow the user to manage history and pinned items. Both being able to delete items from the buffer and pin/unpin items. The third tab will allow the user to change which programs use the buffer.

### Clipboard Buffer Overlay

The clipboard buffer overlay will be displayed over any currently running application. It will allow the user to select an item
to paste from a list displaying all of the copied items in the buffer. The user will also be able to switch to the pinned menu and
paste one of his pinned items.

### Status Icon

The status icon will be displayed in the status bar as long as BuffCut is running. When a user taps the icon, the clipboard
buffer overlay is displayed.

---

## Requirements

### Preferences Screen

1. The user should access the preferences screen through the app icon.
    - Unlike the buffer overlay, the preferences screen should take focus.

1. The user should see multiple tabs that can be selected.
    - The Settings tab
    - The Buffer tab
    - The Pinned tab
    - The Blacklist tab

1. The user should be able to set the size of the buffer in the Settings tab.

1. The user should be able to manage the buffer history in the Buffer tab.
    - Remove a specific copied text.
    - Remove all copied text in the buffer.
    - Pin copied text in the history.

1. The user should be able to manage pinned items in the Pinned tab.
    - Unpin items.
    - Reorder items.

1. The user should be able to add and remove applications to the blacklist in the blacklist tab.
    - Add any installed applications to blacklist.
    - Removed applications from blacklist.

### Clipboard Buffer Overlay

1. The clipboard buffer overlay should not take focus away from the currently running program.
    - Should simply overlay the current program.

1. The clipboard buffer overlay should display a list of the most recently copied items.
    - Should only display up to N items, where N is the max buffer size set by the user.
    - Should display the items like a queue, with the newest item on top and the oldest on the bottom.
    - Each item should only take up one line, regardless of the length of the item.
    - The list should be scrollable.

1. The user can press a star next to an item to pin it.
    - Pinned items don't count towards the max buffer size.
    - Pinned items persist until they are unpinned.

1. The user can switch to a pinned list that displays all the items that have been pinned by hitting a tab on top of the buffer.
    - Should display the items with the most frequently used on top.

1. The user can select any item in either list and that item will be available to be pasted.
    - Selecting an item doesn't affect the order of the buffer or make that item disappear.
        - Unless it is a favorite, which is affected by the frequency of pastes.
    - Selecting an item makes the overlay close.

1. The user can tap off the overlay to make it close.

### Status Icon

1. The status icon should always display in the status bar as long as the app is running.

1. The user can select the icon and it should bring up the clipboard buffer overlay.

### Runtime

1. When a user copies text, that text is added to the top of the clipboard buffer.
    - Duplicates are preserved in the order that they are copied.
    - Should not add to buffer if copied within blacklisted applications.

1. When a user wants to paste text, it should take three touches to get the text out of the clipboard buffer.

1. Should run on Android 4.2 and higher.

1. Should not ask for any permissions that aren't expressly needed.

1. Should be as efficient as possible. Preferably with near zero load times.
    - Avoid unneeded features.
    - Avoid inefficient code.

1. Should not take a noticeable amount of battery.

---

## Client Requirements

### Performance

The responsiveness of the application will remain virtually immediate to user’s request throughout the entire experience. While this is not a requirement we see it as necessary for a good user experience and so that any operation performed on the site is fast and simple. Being able to copy and paste several different lines of texts should be consistent in its’ accountability to perform the correct actions as immediate as possible for all users.

### Usability

In comparison to the normal methods of copying and pasting android users were previously using, our interface will require less time pasting multiple lines of texts. This is purposely how we designed it. We wanted to comply to the client’s requirements to be as fast and intuitive as possible. So when trying to pastes several lines of text a user can simply select the text from the clipboard buffer that will be neatly displayed on screen.

### Ease of Use

Ease of use was a high priority when designing this system. This is shown can by the way we implemented the way of being able to select and paste text. A simple click of the “buffer” button after holding down where you would like to paste your previously copied text will allow the user  to quickly perform their tasks. We also made sure that searching, adding, deleting, and editing information was easy and gave clear feedback to the user within the preferences screen.

---

## Non-Requirements

1. This app should **not** replace the Android system clipboard.

1. Should not support non-string copies.
    - Pictures.
    - Videos.

1. Does not need to support tablets.
    - This might be needed in the future.

1. Does not need to support android versions under 4.2.

1. Does not need to support anything other than Android.
    - IOS.
    - Windows.

1. Should not have a widget.

---

## Developer Requirements

1. Use Android Studio to develop project using the following languages.
    - Java.
    - XML.

1. Use GitLab for version control.
    - Refer to "Version Control Standards" for standard use.

---

## Dream Features

These features are only to be pursued once all previously stated functionality is reached.

- The Clipboard Buffer overlay provides list of common strings.
- Provide additional ordering formats for favorites (i.e. alphabetical).
- Provide configuration option for removing duplicates.

---

## Use Examples

### Case 1: Tommy

Tommy needs to send his mother a sweet new recipe he found online, but he had recently got a new phone and hadn't been
able to recover his contacts. Thankfully, his sister texted him his mother's email, but now he needs to both copy the email and
the recipe into his mail app. Thankfully again, instead of tediously manually switching between apps, this smart user had downloaded
BuffCut, the best clipboard buffer for Android. Now, Tommy can simply copy his mother's address from his messaging app and
the recipe from his browser. Tommy then switches to his mailing app and taps the BuffCut icon in his status bar, which
brings up the clipboard buffer. Now Tommy selects his mother's email from the buffer. The buffer disappears and Tommy cleanly
and efficiently pastes the address in. Again, Tommy presses the icon, but this time he selects the recipe, which he then pastes
into the body of the email. Mission accomplished, Tommy can now go upstairs and enjoy the homecooked meal that his mother has
prepared.

### Case 2: Sally

Sally is a very spontaneous person, and because of this, she always forgets her passwords. Sometimes she types Cats123, but
sometimes she types Cats321, or D0gs423!, or Antidisestablishmentarianism57! Sally writes these passwords down on Notepad++
sometimes, but it is always a pain to open up a separate program, copy, and paste, each time she needs to check her Facebook.
Thankfully there is a solution to Sally's problem. Sally can use BuffCut. All Sally has to do is copy her password, then tap
the icon in her status bar and pin her password to the buffer by pressing a cute little star. Now, whenever she needs to log in
to Facebook, she can simply press the icon, switch to the pinned list, and select her password. Plus, if she ever wants to loan
her computer to someone else, she can go to the preferences and remove her password from the buffer, effectively keeping her
Facebook from being hacked.

### Case 3: Roberto

Roberto is a very cautious man. As a cautious man, he really wants all his passwords to be safely encrypted and stored in an awesome
little program called LastPass. So every time he needs to log in myFitnessPal.com or SororitySquat.edu, he opens up LastPass and copies
the passwords to his clipboard and then logs in. Roberto is also a smart man. Because he often found himself having to copy the same
E-mail addresses and names into different E-mails over and over again on his phone, he got BuffCut, the best clipboard buffer this side
of the Atlantic. However, because he is a cautious man, he doesn't want all his passwords to persist as plaintext in the clipboard buffer.
Fortunately, BuffCut has just the right feature for Roberto's needs. Roberto can open up the Buffcut app, switch to the blacklist tab, and
simply add LastPass to the blacklist. Now, every time Roberto copies a password from LastPass, he can rest easy knowing that his information
is safe.

---

## Notes

The client has based the ideas for this app on other clipboard managers; it might be useful to Investigate them.

- Klipper for Linux.
- Jumpcut for Mac OS X.

As for the look and feel of this app, the customer pointed us towards system apps and the Android style guide.

[Link to project proposal.](https://repo.cse.taylor.edu/group-work/monkey/blob/master/README.md)

---

## Known Issues/Questions

1. We need to come up with a default buffer size.

1. We need to determine what to do if the user sets the buffer size smaller than the current size of the buffer.

1. We need to look into letting the user decide what to do with duplicate copies.

1. When a user pins an item, does it disappear from the buffer?

1. Can items be unpinned?

1. Is it okay that pinned items take four touches to get to?

---

## Version Control Standards

All code at the end of each sprint should end up in the Master branch. During sprints, all code should be in the develop branch. This means that when a sprint is done, develop should be reviewed for errors and then merged into Master.

When you start work on a feature you will create a branch off of develop named after the feature that you are going to work on. For example, if your feature is to add the Clipboard Buffer Overlay, then you should name your branch "BufferOverlay" or some variation thereof. Once the branch is checked out you can do all your work in that branch. When you are done, you should create a merge request into develop on GitLab. Do NOT create a merge request until you can demonstrate a working version of your requirement, develop should NEVER contain known broken code.

Commits should be descriptive of the work done and should be written as present tense verbs. As in the previous example, if you commit code that adds the Clipboard Buffer Overlay the commit message should be "Add Clipboard Buffer Overlay" or some variation thereof. You should commit and push code often, because you might not be the only member of the team working on a feature. Also, because all team members will be working in the repository, always make sure to pull changes before starting work on any branch or creating any new branch.

When you see a merge request, you should review the changes to make sure that the code does what it is supposed to do with no errors, and doesn't accidentally break code that you or another team member has written. Once done reviewing, give the request a thumbs up. When a merge request has three thumbs up (including the person who created it), then it can be merged into develop.

---

## Disclaimer

This Software Requirements Specification is by no means complete and is intended to be modified as requirements are
discovered and discarded throughout the software development process. This is simply my best effort to put into writing the
requirements that we currently have for this project.

![alt text][xkcd]

[xkcd]: https://imgs.xkcd.com/comics/tasks_2x.png "Funny XKCD comic"

Credit: [XKCD](https://imgs.xkcd.com/comics/tasks_2x.png)

---

This SRS was painfully made with the blood and tears of members of Team Monkey.

"We may all be monkeys, but we aren't dumb."