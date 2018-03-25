package pokazaniya.timofeev.com.pokazaniya;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import dialogs.*;
import pokazaniya.timofeev.com.pokazaniya.services.LEDService;

import android.widget.AbsListView.*;
import android.view.*;
import java.util.*;

/**
 * главный класс приложения, точка входа приложения
 */
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, UiUpdater {
    /**
     * главный ListView приложения
     */
    ListView thisListView;
    /**
     * класс базы данных
     */
    DbHelper db = new DbHelper(this);//

    long[] idsArray;
    /**
     * массив строк номеров счетчиков из R.strings
     */
    String[] countList;
    /**
     * Адаптер, к которому привязаны данные из базы
     */
    SimpleCursorAdapter simpleCursorAdapter;
    LED led = new LED();
    boolean ledIsChecked = false;
    //Intent ledServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        countList = getResources().getStringArray(R.array.count);
        //ledServiceIntent = new Intent(this, LEDService.class);

        thisListView = (ListView) findViewById(R.id.mainListView1);
        thisListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        MultiChoiceModeListener listener = new MultiChoiceModeListener() {

            long id;
            ArrayList<Long> ids = new ArrayList<Long>();

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode p1, Menu p2) {
                // TODO: Implement this method
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_remove://если выбран пункт "Удалить" - вызвать соотвествующий диалог
                        RemoveRecordDialog removeRecordDialog = new RemoveRecordDialog();
                        Bundle ids_args = new Bundle();
                        ids_args.putLongArray("ids", idsArray);
                        removeRecordDialog.setArguments(ids_args);
                        removeRecordDialog.show(getSupportFragmentManager(), "removeRecordDialog");
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode p1) {
                // TODO: Implement this method
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                this.id = id;
                if (ids.contains(id)) ids.remove(id);
                else ids.add(id);
                idsArray = new long[ids.size()];
                for (int i = 0; i < ids.size(); i++) {
                    idsArray[i] = ids.get(i);
                }
            }
        };

        thisListView.setMultiChoiceModeListener(listener);
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View ViewTreeObserver, int position, long id) {
                SetValueDialog setValueDialog = new SetValueDialog();
                Bundle id_args = new Bundle();
                id_args.putLong("id", id);
                setValueDialog.setArguments(id_args);
                setValueDialog.show(getSupportFragmentManager(), "setValueDialog");
            }
        };
        thisListView.setOnItemClickListener(clickListener);
        /**
         * массив строк, содержащих имена столбцов в базе данных. Используется в SimpleCursorAdapter
         */
        String[] from = {DbHelper.TP_NUMBER, DbHelper.COUNT_NUMBER, DbHelper.VALUE, DbHelper.DATE};
        /**
         * массив элементов из R.layout.table, в которые будут вставляться значения из базы данных.
         * Используется в SimpleCursorAdpater
         */
        int[] to = {R.id.tableTextView1, R.id.tableTextView2, R.id.tableTextView3, R.id.tableTextView4};
        /**
         * адаптер, связанный с базой данных
         */
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.table, null, from, to, 0);
        /**
         * установка адаптера на ListView
         */
        thisListView.setAdapter(simpleCursorAdapter);
        /**
         * LoaderManager, связанный с базой и адаптером, предназначенные для чтения данных из базы
         */
        getSupportLoaderManager().initLoader(0, null, this);
    }

    /**
     * метод оболочка для вывода Toast сообщений
     *
     * @param message String выводимое сообщение
     */
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /**
     * включение или отключение камеры. Метод вызывается из R.menu.settings
     *
     * @param item
     */
    public void turnLed(MenuItem item) {
        if (!led.ledIsChecked) {//если фонарик выключен
            led.ledOn();//включить фонарик
            item.setTitle("LED ON");//изменить надпись пункта меню настроек
            item.setIcon(R.drawable.lampon);//сменить иконку пункта меню настроек
            //startService(ledServiceIntent);
            //ledIsChecked = true;
        } else {//иначе
            led.ledOff();//выключить фонарик
            item.setTitle("LED OFF");//изменить надпись пункта меню настроек
            item.setIcon(R.drawable.lampoff);//сменить иконку пункта меню настроек
            //stopService(ledServiceIntent);
            //ledIsChecked = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (led.ledIsChecked) led.ledOn();// включить фонарик, если флаг установлен в true
        invalidateOptionsMenu();//обновить пункт меню списка настроек
    }

    @Override
    protected void onStart() {
        super.onStart();
        led.getCamera();//инициализировать камеру
    }

    @Override
    protected void onStop() {
        super.onStop();
        //led.cameraRelease();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        led.cameraRelease();//
//        if (ledServiceIntent != null) stopService(ledServiceIntent);
//        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLongArray("longArray", idsArray);
        outState.putBoolean("ledIsChecked", led.ledIsChecked);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        idsArray = savedInstanceState.getLongArray("longArray");
        led.ledIsChecked = savedInstanceState.getBoolean("ledIsChecked");
    }

    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog();
        exitDialog.show(getSupportFragmentManager(), "exitDialog"); //вывести диалоговое окно при нажатии кнопки назад
    }

    /**
     * создание меню настроек
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * обновление меню настроек. Вызывается перед показом меню настроек
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.getItem(2);//пункт меню управления фонариком
        if (!led.ledIsChecked) {//если false
            item.setTitle("LED OFF");//сменить название пункта
            item.setIcon(R.drawable.lampoff);//изменить иконку пункта
        } else {//иначе
            item.setTitle("LED ON");//сменить название пункта на другое
            item.setIcon(R.drawable.lampon);//сменить иконку на другую
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * создание контекстного меню
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context, menu);
    }

    //метод вызыватся при нажатии на пункт меню настроек "Добавить ТП"
    public void showSelectTpDialog(MenuItem item) {
        AddTpDialog tpDialog = new AddTpDialog();
        tpDialog.show(getSupportFragmentManager(), "addTpDialog");
    }

    //метод вызыватся при нажатии на пункт меню настроек "Добавить счетчик"
    public void showSelectCountDialog(MenuItem item) {
        AddCountDialog addCountDialog = new AddCountDialog();
        addCountDialog.show(getSupportFragmentManager(), "addCountDialog");
    }

    //метод вызыватся при нажатии на пункт меню настроек "выход" или на системную кнопку "Назад"
    public void showExitDialog(MenuItem item) {
        ExitDialog exitDialog = new ExitDialog();
        exitDialog.show(getSupportFragmentManager(), "exitDialog"); //вывести диалоговое окно при нажатии кнопки назад
    }

    //метод вызывается при нажатии на пункт меню настроек "Статистика по номеру счетчика"
    public void showStatisticByCountDialog(MenuItem item) {
        StatisticByCountDialog statisticDialog = new StatisticByCountDialog();
        statisticDialog.show(getSupportFragmentManager(), "statisticDialog");
    }
    //в следующих трех методах происхдит работа с асинхронным манипулированием данных из базы

    /**
     * Именно в этом методе создается инициализированный в строке 158 загрузчик
     *
     * @param i
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new DbCursorLoader(this, db);
    }

    /**
     * вызывается, когда созданный загрузчик завершил загрузку
     *
     * @param loader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        simpleCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * метод-оболочка над методом загрузчика forceLoad(), который при вызове заново читает данные из связанного с ним источника
     */
    public void update(String message) {
        getSupportLoaderManager().getLoader(0).forceLoad();
        showMessage(message);
    }

    public void exit() {
        //if (ledServiceIntent != null) stopService(ledServiceIntent);
        finish();
		overridePendingTransition(R.anim.activity_rotate_right, R.anim.activity_rotate_left);
    }

    /**
     * наследник класса CursorLoader. В нем переопределен метод loadInBackground(), в котором происходит получение
     * курсора, с помощью которого читаются данные из базы
     */
    static class DbCursorLoader extends CursorLoader {
        DbHelper db;

        public DbCursorLoader(Context context, DbHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllRecords();
            return cursor;
        }
    }

}
