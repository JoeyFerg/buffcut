package com.team_monkey.team_monkeysetup;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import junit.framework.Assert;
import java.util.*;

public class Buffer {
    private ClipboardManager clipboardManager;
    private List<android.content.ClipData> clipDataBuffer;
    private int maxSize;

    Buffer(Context context)
    {
        clipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        clipDataBuffer = new LinkedList<ClipData>();
        maxSize = 50;

        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipboardManager.getPrimaryClip();
                if(clipData != null) {
                    if(clipData.getDescription().hasMimeType(android.content.ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        AddClipDataItem(clipData);
                        android.util.Log.d("ClipAdded", clipData.getItemAt(0).getText().toString());
                        LogBuffer();
                    }
                }
                else
                    android.util.Log.d("NullClip","null clip was ignored");
            }
        });
    }

    private void AddClipDataItem(ClipData item)
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

    public List<ClipData> Data()
    {
        return clipDataBuffer;
    }

    public void FillClipboardWithElementAtIndex(int index)
    {
        Assert.assertTrue("INVALID BUFFER INDEX!", IsValidIndex(index));

        clipboardManager.setPrimaryClip(clipDataBuffer.get(index));
    }

    public void ApplyTestClip(String message)
    {
        clipboardManager.setPrimaryClip(ClipData.newPlainText("test", message));
        android.util.Log.d("test", message);
    }

    public void LogBuffer()
    {
        ListIterator<ClipData> iClipData = clipDataBuffer.listIterator();
        while(iClipData.hasNext())
        {
            android.util.Log.d("BufferLog", iClipData.next().getItemAt(0).getText().toString());
        }
    }

    private void TrimBuffer()
    {
        int numToTrim = clipDataBuffer.size() - maxSize;
        if(numToTrim <= 0)
            return;

        ListIterator<ClipData> iClipData = clipDataBuffer.listIterator(clipDataBuffer.size());
        while(iClipData.hasPrevious() && numToTrim > 0)
        {
            iClipData.previous();
            iClipData.remove();
            numToTrim--;
        }
    }

    private boolean IsValidIndex(int index)
    {
        return index >= 0 && index < clipDataBuffer.size();
    }
}
