package com.application.paramountfinefoods;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


public class BasePreferenceHelper {

    // region TAG.
    private static final String TAG = BasePreferenceHelper.class.getSimpleName();
    // endregion

    // region Static Member(s).
    private static Context currentContext;
    // endregion

    // region Context Helpers.
    public static Context getCurrentContext() {
        return currentContext;
    }

    public static void setCurrentContext(Context currentContext) {

        BasePreferenceHelper.currentContext = currentContext;
    }
    // endregion

    // region Clear all user prefs.
    public static void clearUserPrefs() {
//        UserPhone.getInstance().clear();
    }
    // endregion

    // region Access to Preference.
    public SharedPreferences accessSharedPreferences(String preferenceName) {
        return accessSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public SharedPreferences accessSharedPreferences(String preferenceName, Integer mode) {
        return getCurrentContext().getSharedPreferences(preferenceName, mode);
    }
    // endregion

    // region Access Editor for Preference.
    public SharedPreferences.Editor accessEditorForPreferences(String preferenceName) {
        return accessEditorForPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor accessEditorForPreferences(String preferenceName, Integer mode) {
        return accessSharedPreferences(preferenceName, mode).edit();
    }
    // endregion

    // region Add object to preference.
    public void add(String preferenceName, String key, Object value) {
        add(accessEditorForPreferences(preferenceName), key, value);
    }

    public void add(SharedPreferences.Editor editor, String key, Object value) {
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float || value instanceof Double) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            editor.putString(key, (String) value);
        }
        editor.commit();
    }
    // endregion

    // region Get Value from Preference.
    public Object get(@NonNull String preferenceName, @NonNull String key) {
        return get(preferenceName, key, null);
    }

    public Object get(@NonNull String preferenceName, @NonNull String key, @NonNull Object defValue) {
        return get(preferenceName, key, defValue, false);
    }

    public Object get(@NonNull String preferenceName, @NonNull String key, @Nullable Object defValue, @Nullable Boolean create) {
        final SharedPreferences prefs = accessSharedPreferences(preferenceName);
        final Map<String, ?> prefValues = prefs.getAll();
        if (prefValues.containsKey(key)) {
            return prefValues.get(key);
        } else if (create == null || !create) {
            return defValue;
        } else {
            // Need to create the value, using the defValue.
            add(preferenceName, key, defValue);
            return defValue;
        }
    }
    // endregion
}
