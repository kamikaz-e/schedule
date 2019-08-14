package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface EditSchedulePageFragmentView extends BaseView {

    String CURRENT_LESSON = "CURRENT_LESSON";

    String ITEMS = "ITEMS";

    String FRAGMENT_CODE = "FRAGMENT_CODE";

    String POSITION = "POSITION";


    void updateView(ArrayList<Lesson> arrayList);

    void showEditDialog(ArrayList<? extends SimpleItem> subjectList, int position, int subject);

    void showCopyDialog(Lesson currentLesson);

    void setWeek(int position);

    void animateFAB();
}
