package myapp.schedule.misha.myapplication.module.exploreList;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static myapp.schedule.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.CustomSpinnerAdapterWeeks;
import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.EditScheduleFragment;
import myapp.schedule.misha.myapplication.util.DataUtil;

public class ScheduleListFragment extends BaseMainFragment implements ScheduleListFragmentView, AdapterView.OnItemSelectedListener, View.OnTouchListener {

	private Spinner spinner;
	private CustomSpinnerAdapterWeeks weeksAdapter;
	private ScheduleListPresenter presenter;
	private ScheduleListFragmentAdapter scheduleListAdapter;
	private TextView titleDay;
	private TextView textView;
	private ArrayList<Lesson> lessons;
	private RecyclerView rvLessons;
	private String dateDay = "";

	@Override
	public void onPause() {
		super.onPause();
		getContext().getToolbar().removeView(textView);
		getContext().getToolbar().removeView(spinner);
	}

	@Override
	public void onResume() {
		super.onResume();
		hideToolbarIcon();
		setHasOptionsMenu(true);
		textView = new TextView(getContext());
		textView.setBackgroundColor(Color.TRANSPARENT);
		spinner = new Spinner(getContext());
		spinner.setBackgroundColor(Color.TRANSPARENT);
		spinner.setAdapter(weeksAdapter);
		spinner.setOnItemSelectedListener(this);
		presenter.init();
		getContext().getToolbar().addView(textView);
		textView.getLayoutParams().width = 70;
		getContext().getToolbar().addView(spinner);
		getContext().setCurrentTitle(null);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (Preferences.getInstance().getWeek()) {
			int searchListLength = lessons.size();
			for (int i = 0; i < searchListLength; i++) {
				if (lessons.get(i).getNumber_week().equals(String.valueOf(position + 1))) {
					((LinearLayoutManager) rvLessons.getLayoutManager()).scrollToPositionWithOffset(position == 0 ? 0 : i + 1, 0);
					break;
				}
			}
			Preferences.getInstance().selectWeek(false);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new ScheduleListPresenter();
		scheduleListAdapter = new ScheduleListFragmentAdapter();
		weeksAdapter = new CustomSpinnerAdapterWeeks(getContext());
		DataUtil.hintKeyboard(getContext());
	}

	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_schedule_list, container, false);
		rvLessons = fragmentView.findViewById(R.id.rv_lessons_edit);
		rvLessons.setAdapter(scheduleListAdapter);
		titleDay = fragmentView.findViewById(R.id.titleDay);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		rvLessons.setLayoutManager(linearLayoutManager);
		rvLessons.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				LinearLayoutManager layoutManager = (LinearLayoutManager) rvLessons.getLayoutManager();
				int firstVisiblePosition = layoutManager != null ? (layoutManager.findFirstVisibleItemPosition() == 0 ? layoutManager.findFirstVisibleItemPosition() : layoutManager.findFirstVisibleItemPosition() - 1) : 0;
				if (!dateDay.equals(DataUtil.dateDay(lessons, firstVisiblePosition))) {
					presenter.onWeekSelected(Integer.parseInt(lessons.get(firstVisiblePosition).getNumber_week()) - 1);
				}
				dateDay = DataUtil.dateDay(lessons, firstVisiblePosition);
				titleDay.setText(dateDay);
			}
		});
		return fragmentView;
	}

	@Override
	public void selectWeek(int position) {
		spinner.setSelection(position);
	}

	@Override
	public void openEditor() {
		getContext().replaceFragment(new EditScheduleFragment());
	}

	@Override
	public void selectCurrentWeek(int currentWeek) {
		spinner.setSelection(currentWeek);
	}

	@NonNull
	@Override
	protected BasePresenter getPresenter() {
		return presenter;
	}

	@Override
	public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_empty, menu);
		if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
			menu.findItem(R.id.btn_edit).setIcon(R.drawable.ic_edit_white);
		}
		if (Preferences.getInstance().getSelectedTheme().equals(LIGHT_THEME)) {
			menu.findItem(R.id.btn_edit).setIcon(R.drawable.ic_edit_black);
		}
	}

	public void updateList(ArrayList<Lesson> lessonList) {
		this.lessons = lessonList;
		scheduleListAdapter.setLessonList(lessonList);
		scheduleListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onOptionsItemSelected(@NotNull MenuItem item) {
		if (item.getItemId() == R.id.btn_edit) {
			presenter.onButtonClicked();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Preferences.getInstance().selectWeek(event.getAction() == MotionEvent.ACTION_UP);
		return false;
	}
}
