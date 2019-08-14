package myapp.schedule.misha.myapplication.activity;

import android.os.Bundle;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.DialogCopyFragment;

public class ActivityCopyLesson extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lesson lesson = getIntent().getParcelableExtra(EditSchedulePageFragmentView.CURRENT_LESSON);
        setCurrentTitle(getString(R.string.title_copy_lesson));
        replaceFragment(DialogCopyFragment.newInstance(lesson));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }
}