package com.application.paramountfinefoods;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Pair;


import java.util.Map;

/**
 * Created by awright on 7/14/16.
 */
public class FragmentController {

    // region TAG.
    private static final String TAG = FragmentController.class.getSimpleName();
    // endregion

    // region Constant(s).
    protected static final Integer DEFAULT_LAYOUT = R.id.flContent;
    // endregion

    // region Static Member(s).
    private static FragmentController controller = new FragmentController();
    // endregion

    // region Ivar(s).
    private FragmentManager manager;
    // endregion

    // region Singleton - Eager.
    public static FragmentController getInstance() {
        return controller;
    }

    // region Constructor(s).
    private FragmentController() {
    }
    // endregion

    // region Manager Controls.
    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }
    // endregion

    // region Push Fragment.
    public Boolean push(Fragment fragment) {
        return push(fragment, DEFAULT_LAYOUT, false);
    }

    public Boolean push(Fragment fragment, Boolean addToStack) {
        return push(fragment, DEFAULT_LAYOUT, addToStack);
    }

    public Boolean push(Fragment fragment, Integer res, Boolean addToStack) {
        return push(res, fragment, addToStack, false);
    }

    public Boolean pushStateless(Fragment fragment) {
        return pushStateless(fragment, false);
    }

    public Boolean pushStateless(Fragment fragment, Boolean addToStack) {
        return pushStateless(fragment, DEFAULT_LAYOUT, addToStack);
    }

    public Boolean pushStateless(Fragment fragment, Integer resId, Boolean addToStack) {
        return push(resId, fragment, addToStack, true);
    }

    public Boolean push(Map<Integer, Pair<Fragment, Boolean>> fragments) {
        if (fragments == null || fragments.isEmpty()) {
            return false;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        for (Map.Entry<Integer, Pair<Fragment, Boolean>> entry : fragments.entrySet()) {
            transaction.replace(entry.getKey(), entry.getValue().first);
            if (entry.getValue().second) {
                transaction.addToBackStack(entry.getValue().first.getClass().getSimpleName());
            }
        }
        transaction.commit();
        return true;
    }

    private Boolean push(Integer resId, Fragment fragment, Boolean addToStack, Boolean stateless) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return null;
            }
        }

        FragmentTransaction transaction = transaction();
        transaction.replace(resId, fragment, fragment.getClass().getSimpleName());
        if (addToStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        if (stateless) {
            transaction.commitAllowingStateLoss();
            return true;
        }
        try {
            transaction.commit();
        } catch (IllegalStateException ise) {
            return false;
        }
        return true;
    }
    // endregion

    // region Find Fragment.
    public Fragment find(String tag) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return null;
            }
        }
        Log.d(TAG, "Searching for fragment with TAG: " + tag);
        return manager.findFragmentByTag(tag);
    }

    public Fragment find(Integer id) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return null;
            }
        }

        return manager.findFragmentById(id);
    }
    // endregion

    // region Remove Fragment.
    public Boolean remove(String tag) {
        // Return null if the FragmentManager is not available.
        if (manager == null) {
            bindSupportManager();
            if (manager == null) {
                return null;
            }
        }

        // Return false if the Fragment was not found on the stack.
        Fragment fragment = find(tag);
        if (fragment == null) {
            return false;
        }

        return remove(fragment);
    }

    public Boolean remove(Fragment fragment) {
        // Return true if the Fragment was found, and scheduled for removal.
        FragmentTransaction transaction = transaction();
        transaction.remove(fragment).commit();
        return true;
    }
    // endregion

    // region Pop Stack.
    public void pop() {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }

        manager.popBackStack();
    }

    public void pop(Integer index) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }

        manager.popBackStack(manager.getBackStackEntryAt(index).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void pop(String tag) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }

        manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popNow() {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }

        manager.popBackStackImmediate();
    }

    public void popNow(Integer index) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }

        manager.popBackStackImmediate(manager.getBackStackEntryAt(index).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popNow(String tag) {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }
        manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    // endregion

    // region Clear Stack.
    public void clear() {
        if (manager == null) {
            if (!bindSupportManager()) {
                return;
            }
        }
        final Integer fragmentCount = manager.getBackStackEntryCount();
        Log.d(TAG, "Current back stack count: " + fragmentCount);
        if (fragmentCount > 0) {
            for (int i = fragmentCount - 1; i >= 0; i--) {
                pop(i);
            }
        }
    }
    // endregion

    // region Generate Transaction.
    public FragmentTransaction transaction() {
        if (manager == null) {
            if (!bindSupportManager()) {
                return null;
            }
        }

        return manager.beginTransaction();
    }
    // endregion

    // region Manager Binding.
    private Boolean bindSupportManager() {
        if (manager == null) {
            Context context = BasePreferenceHelper.getCurrentContext();
            if (context instanceof HomeActivity) {
                manager = ((HomeActivity) context).getSupportFragmentManager();
                return true;
            }
        }
        return false;
    }
    // endregion

    //region
    public Fragment getCurrentFragment() {
        Fragment fragment = getInstance().getManager().findFragmentById(DEFAULT_LAYOUT);
        return fragment;
    }
}