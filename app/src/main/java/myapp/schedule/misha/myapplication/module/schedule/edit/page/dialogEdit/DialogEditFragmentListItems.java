package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseAlertDialog;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.entity.SimpleItem;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit.addData.DialogFragmentAddData;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;
import static myapp.schedule.misha.myapplication.Constants.LIST_ITEMS;

public class DialogEditFragmentListItems extends BaseAlertDialog implements DialogEditFragmentListItemsView {

    private DialogEditFragmentPresenter presenter;
    private RecyclerView rvItems;
    private DialogEditFragmentListItemsAdapter dialogFragmentListItemsAdapter;

    public static DialogEditFragmentListItems newInstance(Bundle args) {
        DialogEditFragmentListItems fragment = new DialogEditFragmentListItems();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showAddDataDialog(ArrayList<? extends SimpleItem> items, int fragmentCode) {
        DialogFragmentAddData dialogFragment = null;
        if (fragmentCode == FRAGMENT_SUBJECTS) {
            dialogFragment = DialogFragmentAddData.newInstance(new EditDataModel(R.string.error_subject, R.string.hint_subject, R.string.title_subject, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, 60, FRAGMENT_SUBJECTS, 0, ""));
        }
        if (fragmentCode == FRAGMENT_AUDIENCES) {
            dialogFragment = DialogFragmentAddData.newInstance(new EditDataModel(R.string.error_audience, R.string.hint_audience, R.string.title_audience, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, 14, FRAGMENT_AUDIENCES, 0, ""));
        }
        if (fragmentCode == FRAGMENT_EDUCATORS) {
            dialogFragment = DialogFragmentAddData.newInstance(new EditDataModel(R.string.error_educator, R.string.hint_educator, R.string.title_educator, InputType.TYPE_TEXT_FLAG_CAP_WORDS, 60, FRAGMENT_EDUCATORS, 0, ""));
        }
        if (fragmentCode == FRAGMENT_TYPELESSONS) {
            dialogFragment = DialogFragmentAddData.newInstance(new EditDataModel(R.string.error_typelesson, R.string.hint_typelesson, R.string.title_typelesson, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, 20, FRAGMENT_TYPELESSONS, 0, ""));
        }
        dialogFragment.show(getChildFragmentManager(), DialogFragmentAddData.class.getSimpleName());
    }


    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int fragmentCode = getArguments().getInt(FRAGMENT_CODE);
        ArrayList<SimpleItem> listItems = getArguments().getParcelableArrayList(ITEMS);
        presenter = new DialogEditFragmentPresenter(fragmentCode);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_rv_list, null);
        TextView title_dialog = view.findViewById(R.id.dialog_textView);
        if (fragmentCode == FRAGMENT_SUBJECTS) {
            title_dialog.setText(getString(R.string.dialog_fragment_title_subject));
        }
        if (fragmentCode == FRAGMENT_AUDIENCES) {
            title_dialog.setText(getString(R.string.dialog_fragment_title_audience));
        }
        if (fragmentCode == FRAGMENT_EDUCATORS) {
            title_dialog.setText(getString(R.string.dialog_fragment_title_educator));
        }
        if (fragmentCode == FRAGMENT_TYPELESSONS) {
            title_dialog.setText(getString(R.string.dialog_fragment_title_typelesson));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        rvItems = view.findViewById(R.id.rv_dialog_list);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        updateItemsAdapter(listItems);
        Button button_add = view.findViewById(R.id.btn_add);
        button_add.setOnClickListener(v -> presenter.onItemClick(fragmentCode));
        Button button_cancel = view.findViewById(R.id.btn_close);
        button_cancel.setOnClickListener(v -> dismiss());
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        ArrayList<SimpleItem> itemList = presenter.getItemList();
        dialogFragmentListItemsAdapter.setLessonList(itemList);
        dialogFragmentListItemsAdapter.notifyDataSetChanged();
        updateItemsAdapter(itemList);
    }

    public void updateItemsAdapter(ArrayList<SimpleItem> listItems) {
        int fragmentCode = getArguments().getInt(FRAGMENT_CODE);
        int clickedPosition = getArguments().getInt(POSITION);
        dialogFragmentListItemsAdapter = new DialogEditFragmentListItemsAdapter(listItems, (position, view1) -> {
            Intent intent = new Intent();
            intent.putExtra(POSITION, clickedPosition);
            intent.putExtra(LIST_ITEMS, listItems.get(position));
            getParentFragment().onActivityResult(fragmentCode, Activity.RESULT_OK, intent);
            dismiss();
        });

        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
