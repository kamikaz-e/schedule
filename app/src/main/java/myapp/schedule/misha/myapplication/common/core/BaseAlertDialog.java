package myapp.schedule.misha.myapplication.common.core;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.core.snack.SnackBehavior;
import myapp.schedule.misha.myapplication.common.core.snack.SnackBehaviorInterface;

public abstract class BaseAlertDialog extends BaseDialog implements BaseDialogView {

    private SnackBehaviorInterface snackBehavior;

    @Override
    public void showError(@StringRes int message) {
        ScheduleApp.showToast(message);
    }

    @Override
    public void showError(String message) {
        ScheduleApp.showToast(message);
    }

    @Override
    public void showGlobalError(@StringRes int message) {
        // do nothing
    }


    @Override
    public void showGlobalError(String message) {
        //do nothing
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        snackBehavior = new SnackBehavior(getView());
        //noinspection unchecked
        getPresenter().setRoot(getContext());
        //noinspection unchecked
        getPresenter().setView(this);
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, true);
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean animate) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().getBackStackEntryCount() >= 1 && animate) {
            transaction.setCustomAnimations(R.anim.tr_child_up, R.anim.tr_exit_left,
                    R.anim.tr_parent_back, R.anim.tr_child_back);
        }
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }


    @Override
    public void showSnack(String message) {
        snackBehavior.showSnack(message);
    }

    @Override
    public void showSnack(String message, String button, View.OnClickListener callback) {
        snackBehavior.showSnack(message, button, callback);
    }

    @Override
    public void showSnack(@StringRes int message) {
        snackBehavior.showSnack(message);
    }


    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().setRoot(null);
        getPresenter().getCompositeDisposable().dispose();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();

    }

    @NonNull
    protected abstract BasePresenter getPresenter();
}
