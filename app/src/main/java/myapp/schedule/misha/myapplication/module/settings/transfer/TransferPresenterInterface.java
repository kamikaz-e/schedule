package myapp.schedule.misha.myapplication.module.settings.transfer;

public interface TransferPresenterInterface {

    void onClickImport();

    void onClickExport();

    void import_data(String file);
}
