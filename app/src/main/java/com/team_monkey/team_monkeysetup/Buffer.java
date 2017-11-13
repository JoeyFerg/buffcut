package com.team_monkey.team_monkeysetup;

import android.content.*;
import junit.framework.Assert;
import java.util.*;

public class Buffer {
    private ClipboardManager clipboardManager;
    private List<String> clipDataBuffer;
    private int maxSize;
    private boolean listenerRegistered;

    private ClipboardManager.OnPrimaryClipChangedListener onChangeListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if(IsValidClip(clipData)) {
                    AddClipDataItem(clipData.getItemAt(0).getText().toString());
                    android.util.Log.d("ClipAdded", clipData.getItemAt(0).getText().toString());
                    LogBuffer();
                }
                else
                    android.util.Log.d("InvalidClip","Invalid clip was ignored");
            }
    };

    Buffer(Context context)
    {
        clipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        clipDataBuffer = new LinkedList<>();
        maxSize = 50;
        listenerRegistered = false;

        AddClipboardEventListener();
    }

    Buffer(Context context, LinkedList<String> buf)
    {
        clipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        clipDataBuffer = buf;
        maxSize = 50;
        listenerRegistered = false;

        AddClipboardEventListener();
    }

    public void AddClipboardEventListener()
    {
        if(!listenerRegistered)
        {
            clipboardManager.addPrimaryClipChangedListener(onChangeListener);
            listenerRegistered = true;
        }
    }

    public void RemoveClipboardEventListener()
    {
        if(listenerRegistered)
        {
            clipboardManager.removePrimaryClipChangedListener(onChangeListener);
            listenerRegistered = false;
        }
    }

    public boolean IsValidClip(ClipData clipData)
    {
        if(clipData != null)
        {
            ClipDescription clipDescription = clipData.getDescription();
            if(clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    && clipDescription.getMimeTypeCount() == 1)
            {
                return true;
            }
        }
        return false;
    }

    private void AddClipDataItem(String item)
    {
        clipDataBuffer.add(0, item);
        TrimBuffer();
    }

    public void SetMaxSize(int maxSize)
    {
        Assert.assertTrue("INVALID BUFFER SIZE!", maxSize > 0);

        this.maxSize = maxSize;
        TrimBuffer();
    }

    public List<String> Data()
    {
        return clipDataBuffer;
    }

    public void FillClipboardWithElementAtIndex(int index)
    {
        Assert.assertTrue("INVALID BUFFER INDEX!", IsValidIndex(index));

        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", clipDataBuffer.get(index)));
    }

    public void ApplyTestClip(String message)
    {
        clipboardManager.setPrimaryClip(ClipData.newPlainText("test", message));
        android.util.Log.d("test", message);
    }

    public void LogBuffer()
    {
        android.util.Log.d("BufferLog", "Buffer size: " + Integer.toString(clipDataBuffer.size()));

        ListIterator<String> iClipData = clipDataBuffer.listIterator();
        while(iClipData.hasNext())
        {
            android.util.Log.d("BufferLog", iClipData.next());
        }
    }

    private void TrimBuffer()
    {
        int numToTrim = clipDataBuffer.size() - maxSize;
        if(numToTrim <= 0) { return; }

        ListIterator<String> iClipData = clipDataBuffer.listIterator(clipDataBuffer.size());
        while(iClipData.hasPrevious() && numToTrim > 0)
        {
            iClipData.previous();
            iClipData.remove();
            numToTrim--;
        }
    }

    public LinkedList<String> BufferToString()
    {
        LinkedList<String> stringList = new LinkedList<String>();

        ListIterator<String> iClipData = clipDataBuffer.listIterator();
        while(iClipData.hasNext())
        {
            stringList.addLast(iClipData.next());
        }

        return stringList;
    }

    private boolean IsValidIndex(int index)
    {
        return index >= 0 && index < clipDataBuffer.size();
    }
}
