package myapp.schedule.misha.myapplication.data.preferences;

public interface PreferencesInterface {

    boolean isHintsOpened();

    void setHintsOpened();

    void setSemesterStart(String date);

    String getSemestrStart();

    boolean isCallsOpened();

    void setCallsOpened(boolean state);

    int getSelectedWeekSchedule();

    void setSelectedWeekSchedule(int position);

    int getSelectedPositionTabDays();

    void setSelectedPositionTabDays(int position);

    boolean getFabOpen();

    void setFabOpen(boolean state);

    int getSelectedPositionLesson();

    void setSelectedPositionLesson(int position);

    String getSelectDate();

    void setSelectDate(String selectDate);

    String getSelectedTheme();

    void setSelectedTheme(String stringTheme);

    String getSelectedLesson();

    String getSelectedWeek();

    String getSelectedDate();

    String getSelectedCurrentLesson();

    void setSelectedCurrentLesson(String currentLesson);

    Boolean getWeek();

    void selectWeek(boolean select);
}
