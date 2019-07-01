package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface DialogSelectLessonFragmentView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";

    void showAddDataDialog(ArrayList<? extends SimpleItem> items, int fragmentCode);
}
