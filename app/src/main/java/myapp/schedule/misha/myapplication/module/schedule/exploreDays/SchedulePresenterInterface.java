package myapp.schedule.misha.myapplication.module.schedule.exploreDays;

public interface SchedulePresenterInterface {

    void onWeekSelected(int position);

    void onButtonClicked();

    void onPageSwiped(int position);

    void onPageSelected(int position);

    void selectDefaultWeek();
}
