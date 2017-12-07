package com.team_monkey.team_monkeysetup;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Buffer {
    private ClipboardManager clipboardManager;
    private LinkedList<String> clipDataBuffer;
    private LinkedList<String> favBuffer;
    private int maxSize;
    private boolean listenerRegistered;
    private SharedPreferences preferences;
    private Gson gson;
    private String PREF_NAME = "BuffClip";
    private String BUFFER = "Buffer";
    private BufferCallback callback;

    public interface BufferCallback{
        void callBack();
    }

    private ClipboardManager.OnPrimaryClipChangedListener onChangeListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if(IsValidClip(clipData))
            {
                    String clipText = ExtractTextFromClipData(clipData);
                    if(clipText.length() > 0) {
                        AddItem(clipText);
                        android.util.Log.d("====ClipAdded====", ExtractTextFromClipData(clipData));
                        LogBuffer();
                    }
                    else
                        android.util.Log.d("====ClipNotAdded====", "Empty string ignored");

                    if(callback != null)
                        callback.callBack();
            }
            else
                android.util.Log.d("InvalidClip","Invalid clip ignored");
            }
    };

    Buffer(Context context)
    {
        clipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        clipDataBuffer = new LinkedList<String>();
        favBuffer = new LinkedList<String>();
        maxSize = 50;
        listenerRegistered = false;

        preferences = context.getSharedPreferences(PREF_NAME, 0);
        gson = new Gson();
        callback = null;

        AddClipboardEventListener();
    }

    Buffer(Context context, LinkedList<String> buf)
    {
        clipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        clipDataBuffer = buf;
        favBuffer = new LinkedList<String>();
        maxSize = 50;
        listenerRegistered = false;

        preferences = context.getSharedPreferences(PREF_NAME, 0);
        gson = new Gson();
        callback = null;

        AddClipboardEventListener();
    }

    Buffer(Context context, LinkedList<String> buf, LinkedList<String> favBuf)
    {
        clipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        clipDataBuffer = buf;
        favBuffer = favBuf;
        maxSize = 50;
        listenerRegistered = false;

        preferences = context.getSharedPreferences(PREF_NAME, 0);
        gson = new Gson();
        callback = null;

        AddClipboardEventListener();
    }

    public void RegisterBufferCallback(BufferCallback buffCallBack)
    {
        callback = buffCallBack;
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
            if(clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
            {
                return true;
            }
        }
        return false;
    }

    private String ExtractTextFromClipData(ClipData clipData)
    {
        int numItems = clipData.getItemCount();
        String extractedText = "";
        for(int i = 0; i < numItems; i++)
        {
            CharSequence charSeq = clipData.getItemAt(i).getText();
            if(charSeq != null)
                extractedText += charSeq.toString();
        }
        return extractedText;
    }

    private void AddItem(String item)
    {
        RemoveItemFromList(clipDataBuffer, item);
        clipDataBuffer.add(0, item);
        TrimBuffer();
        SaveBuffer();
    }

    public void SetMaxSize(int maxSize)
    {
        Assert.assertTrue("INVALID BUFFER SIZE!", maxSize > 0);

        this.maxSize = maxSize;
        TrimBuffer();
    }

    public void AddToFavorites(int index)
    {
        String item = clipDataBuffer.get(index);
        RemoveItemFromList(favBuffer, item);
        favBuffer.add(item);
    }

    public LinkedList<String> Data()
    {
        return clipDataBuffer;
    }

    public LinkedList<String> DataFavorites()
    {
        return favBuffer;
    }

    public String[] DataArray() {
        return clipDataBuffer.toArray(new String[clipDataBuffer.size()]);
    }

    public String[] DataFavArray() {
        return favBuffer.toArray(new String[favBuffer.size()]);
    }

    public void FillClipboardWithElementAtIndex(int index)
    {
        Assert.assertTrue("INVALID BUFFER INDEX!", IsValidIndex(clipDataBuffer, index));

        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", clipDataBuffer.get(index)));
    }

    public void FillClipboardWithFavoritesElementAtIndex(int index)
    {
        Assert.assertTrue("INVALID BUFFER INDEX!", IsValidIndex(favBuffer, index));

        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", favBuffer.get(index)));
    }

    public void ApplyTestClip(String message)
    {
        clipboardManager.setPrimaryClip(ClipData.newPlainText("test", message));
        android.util.Log.d("test", message);
    }

    public void Clear() {
        clipDataBuffer = new LinkedList<String>();
        favBuffer = new LinkedList<String>();
    }

    public void LogBuffer()
    {
        android.util.Log.d("BufferLog", "Buffer size: " + Integer.toString(clipDataBuffer.size()));
        ListIterator<String> iClipData = clipDataBuffer.listIterator();
        while(iClipData.hasNext())
        {
            android.util.Log.d("BufferLog", iClipData.next());
        }

        android.util.Log.d("BufferLog", "Favorites Buffer size: " + Integer.toString(favBuffer.size()));
        ListIterator<String> iFav = favBuffer.listIterator();
        while(iFav.hasNext())
        {
            android.util.Log.d("BufferLog", iFav.next());
        }
    }

    private void TrimBuffer()
    {
        int numToTrim = clipDataBuffer.size() - maxSize;
        if(numToTrim <= 0)
            return;

        ListIterator<String> iClipData = clipDataBuffer.listIterator(clipDataBuffer.size());
        while(iClipData.hasPrevious() && numToTrim > 0)
        {
            iClipData.previous();
            iClipData.remove();
            numToTrim--;
        }
    }

    private static boolean IsValidIndex(List<String> list, int index)
    {
        return index >= 0 && index < list.size();
    }

    private static int RemoveItemFromList(List<String> list, String item)
    {
        int numRemoved = 0;

        ListIterator<String> iClipData = list.listIterator();
        while(iClipData.hasNext())
        {
            if(iClipData.next().equals(item)) {
                iClipData.previous();
                iClipData.remove();
                numRemoved++;
            }
        }
        return numRemoved;
    }

    public void ClearBuffer()
    {
        this.clipDataBuffer = new LinkedList<String>();
        this.favBuffer = new LinkedList<String>();
    }

    public void LoadBuffer()
    {
        String bufferString = preferences.getString(BUFFER, "[]");
        android.util.Log.d("bufferstring", bufferString);
        if (bufferString != "[]") {
            LinkedList<String> bufferList = new LinkedList<>((ArrayList<String>)gson.fromJson(bufferString, new TypeToken<List<String>>(){}.getType()));
            clipDataBuffer = bufferList;
        }
    }

    public void SaveBuffer()
    {
        String BufferString;
        BufferString = gson.toJson(clipDataBuffer);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BUFFER, BufferString);
        editor.commit();
    }
}
