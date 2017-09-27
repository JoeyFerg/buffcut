# Project Proposal

EVERGREEN (codename)  
Version 1.0  
Android Application  


## Executive Summary

This document is a project proposal for an Android application codenamed EVERGREEN. EVERGREEN is a clipboard buffering application for Android. The purpose of EVERGREEN is to provide quick and non-intrusive access to the operating system clipboard history.


## Overview

Typing on mobile devices can be cumbersome compared to typing on a full-size desktop keyboard. Often a mobile user may wish to cut and paste long excerpts of text from one application to another. For example, if a user wishes to send a web address to a friend by text message, he could memorize and retype the URL. However, typing a full web address could take many keystrokes and is prone to typos. Rather than retyping, and possibly mistyping, a URL, the user can simply touch and hold the address displayed in their web browser, then touch the copy icon. Once the web address is stored in the operating system clipboard it can be pasted into other apps that accept text input, such as the text messaging application.

The cut-and-paste paradigm is not new. However, as is the case with the typical Android operating system, the clipboard only gives you access to the most recently copied text. Often times it would be convenient to have access to more than just one item in the clipboard. For example, when texting a web address to someone, you may also want to include the title of the web page. The only option you have, aside from retyping one or both the address and the title, is to switch back and forth between the web browser application and text messaging application multiple times using the clipboard one clip at a time.

Klipper for Linux and Jumpcut for Mac OS X are desktop applications that serve as inspiration for this Android project. Developers should investigate these desktop applications for foundational understanding. Clipboard management applications exist for Android. However, EVERGREEN is distinct from these in that it is NOT a clipboard manager; it is a clipboard buffer. Clipboard managers are often full applications that steal application focus from the currently running application. EVERGREEN and Jumpcut/Klipper run in the background and are accessed by a status bar icon. When the icon is clicked the user interface is not hijacked by the clipboard application. Instead, a simple and non-intrusive desktop menu appears over the top of any running applications with a list of the most recent clippings. Once a clipping is selected, the menu disappears and the paste buffer contains the selected clipping (not necessarily the most recently copied item).


## High-Level Requirements

EVERGREEN has two primary user interfaces: the clipboard buffer (BUFFER) and a configuration interface (PREFS). The BUFFER should be presented to the user after at most three touches from any application that doesn't hide the status bar (fewer the better). The first touch will likely be on the status bar. It may be possible to reuse a physical or capacitive button (such as the menu button) to access the BUFFER. However, this is not a requirement and may not even be a good idea.

The name EVERGREEN is merely a codename for the project. The Android application that is a result of EVERGREEN should be named and assigned a launcher icon. Though the icon and name are secondary priorities to functionality, the name and icon are important. When the app is released to the Android Market we want it to be findable and professionally styled.

EVERGREEN should run on modern Android devices with Android version 4.2 and higher. Support for older devices is not necessary. For version 1.0 we are interested in supporting phone-sized devices. At some later time, support for tablet devices will likely be added. Whenever possible, code should be created that enables the application to work on both types of devices.

When the BUFFER is accessed, EVERGREEN should not steal Activity focus from the currently running Android activity. This is one of the primary distinguishing features of EVERGREEN. It should instead reuse the system notification menu or create a floating menu that presents the user with a list of most recent clippings. Though the Android system clipboard may have the capability to store non-string items in the clipboard, the BUFFER should only represent strings that have been copied (not images, for example). The strings should be truncated and formatted to fit in the BUFFER menu.

EVERGREEN should run in the background and not incur any undue resource costs that would result in excessive battery drain or system slowdown. Developers should investigate using the ClipboardManager Android interface, added in API 11, that includes event listener registration. This should allow EVERGREEN to register a callback that is executed any time the contents of the clipboard are changed.

The PREFS configuration interface may be an Android Activity and steal application focus (unlike BUFFER). EVERGREEN should have, at minimum, one configuration option. The one configuration option should allow the user to select how many clippings to save in the buffer at one time (NUMCLIPS). A reasonable default value for NUMCLIPS should be determined by the developers; it may be influenced by the type of BUFFER menu displayed to the user.

The EVERGREEN interface should be as simple as possible and adhere to common Android style and user interface guidelines. The configuration interface should closely mimic the configuration interface provided by other applications.

EVERGREEN is an Android-only application that is tightly coupled with the Android user interface. It should be developed using the native Android libraries. It should not be created in a cross-platform framework such as Titamium. It should not be created in HTML and wrapped in a browser window; for example, a list of items should be an Android ListView (or derivative) and not an HTML UL tag rendered by an HTML parser.

EVERGREEN is a client-only application. It does not require access to the network. It should require very few permissions from the Android operating system. EVERGREEN should request only the permissions needed by the application.

One additional feature that may or may not be included in version 1.0 of EVERGREEN is the ability to "pin" certain strings to the BUFFER. This feature would allow users to designate one or more entries for the buffer that would be persistent. For example, a user may want to store their email address as one of the persistent BUFFER entries to save time typing and retyping their email address into multiple applications.

