package pokazaniya.timofeev.com.pokazaniya;

import android.app.AlertDialog;
import android.content.Context;
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
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;

import dialogs.AddTpDialog;
import dialogs.*;

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
     * объект для построения диалоговых окон
     */
    AlertDialog.Builder builder;
    /**
     * класс базы данных
     */
    DbHelper db = new DbHelper(this);

    /**
     * этой переменной будет присвоено значение AdapterView.AdapterContextMenuInfo.position. Свойство position этого класса
     * содержит номер пункта ListView, на котором было вызвано контекстное меню. Переменная используется для вывода
     * сообщения в Toast при удалении пункта записи из базы данных и пункта ListView соответственно.
     */
    int getItem;
    /**
     * этой переменной будет писвоено значение AdapterView.AdapterContextMenuInfo.id. Свойство id этого класса содержит
     * _id записи из базы данных - primary key значение. Переменная используется при удалении записи из базы данных
     */
    long id;
    /**
     * переменная используется при вызове контекстного меню на пункте списка и равна getItem + 1
     * нужна для корректного отображения номера удаленной записи в сообщении Toast, т.к. getItem начинается с 0,
     * а нужно, чтоб счет начинался с 1
     */
    int temp;
    /**
     * номера ТП и счетчиков из R.strings
     */
    private String tp309, tp310, tp311, tp312, tp313, tp314;
    private String count0, count1, count2, count3, count4,
            count5, count6, count7, count8, count9, count10, count11;
    /**
     * Камера
     */
    Camera camera;
    /**
     * параметры камеры
     */
    Parameters parameters;
    /**
     * флаг, указывающий - включена камера или нет
     */
    boolean ledIsChecked = false;
    /**
     * массив строк номеров счетчиков из R.strings
     */
    String[] countList;
    Cursor cursor;
    /**
     * Адаптер, к которому привязаны данные из базы
     */
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tp309 = getResources().getStringArray(R.array.tp)[0];
        tp310 = getResources().getStringArray(R.array.tp)[1];
        tp311 = getResources().getStringArray(R.array.tp)[2];
        tp312 = getResources().getStringArray(R.array.tp)[3];
        tp313 = getResources().getStringArray(R.array.tp)[4];
        tp314 = getResources().getStringArray(R.array.tp)[5];

        count0 = getResources().getStringArray(R.array.count)[0];
        count1 = getResources().getStringArray(R.array.count)[1];
        count2 = getResources().getStringArray(R.array.count)[2];
        count3 = getResources().getStringArray(R.array.count)[3];
        count4 = getResources().getStringArray(R.array.count)[4];
        count5 = getResources().getStringArray(R.array.count)[5];
        count6 = getResources().getStringArray(R.array.count)[6];
        count7 = getResources().getStringArray(R.array.count)[7];
        count8 = getResources().getStringArray(R.array.count)[8];
        count9 = getResources().getStringArray(R.array.count)[9];
        count10 = getResources().getStringArray(R.array.count)[10];
        count11 = getResources().getStringArray(R.array.count)[11];

        countList = getResources().getStringArray(R.array.count);

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
                        long[] idsArray = new long[ids.size()];
                        for (int i = 0; i < ids.size(); i++) {
                            idsArray[i] = ids.get(i);
                        }
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
         * регистрация контекстного меню на ListView
         */
        //registerForContextMenu(thisListView);
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
     * инициализация камеры
     */
    private void getCamera() {
        if (camera == null) {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }

    /**
     * включение led
     */
    private void ledOn() {
        if (!ledIsChecked) {//если камера выключена
            if (camera == null || parameters == null)
                return;//если камера не иницализирована - прервать выполнение метода
            parameters = camera.getParameters();//иницализация параметров камеры
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);//включить led
            camera.setParameters(parameters);//установить камере данные параметры
            camera.startPreview();//стартануть камеру
            ledIsChecked = true;// установить флаг в значение true
        }
    }

    /**
     * выключение led
     */
    private void ledOff() {
        if (ledIsChecked) {//если камера включена
            if (camera == null || parameters == null)
                return;//если камера не инициализирована - превать метод
            parameters = camera.getParameters();//инициализация параметров камеры
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);//выключить led
            camera.setParameters(parameters);//устанавить камере данные параметры
            camera.stopPreview();//остановить камеру
            ledIsChecked = false;//установить флаг в значение false
        }
    }

    /**
     * включение или отключение камеры. Метод вызывается из R.menu.settings
     *
     * @param item
     */
    public void turnLed(MenuItem item) {
        if (!ledIsChecked) {//если фонарик выключен
            ledOn();//включить фонарик
            item.setTitle("LED ON");//изменить надпись пункта меню настроек
            item.setIcon(R.drawable.lighton);//сменить иконку пункта меню настроек
        } else {//иначе
            ledOff();//выключить фонарик
            item.setTitle("LED OFF");//изменить надпись пункта меню настроек
            item.setIcon(R.drawable.lightoff);//сменить иконку пункта меню настроек
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ledOff();//выключить фонарик, если он включен
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ledIsChecked) ledOn();// включить фонарик, если флаг установлен в true
        invalidateOptionsMenu();//обновить пункт меню списка настроек
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCamera();//инициализировать камеру
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();//освободить камеру
            camera = null;
        }
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
        String title = item.getTitle().toString();// название пункта
        if (!ledIsChecked) {//если false
            item.setTitle("LED OFF");//сменить название пункта
            item.setIcon(R.drawable.lightoff);//изменить иконку пункта
        } else {//иначе
            item.setTitle("LED ON");//сменить название пункта на другое
            item.setIcon(R.drawable.lighton);//сменить иконку на другую
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

    /**
     * обработка нажатий на пункты контекстного меню
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        /**
         * в объекте данного класса хранится вся информация об элементе AdapterView, к которому
         * привязано данное контекстное меню
         */
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        /**
         * позиция (int) элемента в AdapterView. Счет начинаетс с нуля
         */
        getItem = info.position;
        /**
         * id элемента, который равен id записи в базе данных
         */
        id = info.id;
        /**
         * временная переменная для корректного вывода позиции элемента, т.к. счет элементов начинается с нуля
         */
        temp = getItem + 1;
        Bundle args = new Bundle();

        switch (item.getItemId()) {
            case R.id.item_remove://если выбран пункт "Удалить" - вызвать соотвествующий диалог
                RemoveRecordDialog removeRecordDialog = new RemoveRecordDialog();
                args.putLong("id", id);
                removeRecordDialog.setArguments(args);
                removeRecordDialog.show(getSupportFragmentManager(), "removeRecordDialog");
                return true;
        }
        return super.onContextItemSelected(item);
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

    //метод вызыватся при нажатии на пункт контекстного меню "Изменить"
    public void showSetValueDialog(MenuItem item) {
        SetValueDialog setValueDialog = new SetValueDialog();
        setValueDialog.show(getSupportFragmentManager(), "setValueDialog");
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

    private void update() {
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    /**
     * метод-оболочка над методом загрузчика forceLoad(), который при вызове заново читает данные из связанного с ним источника
     */
    public void update(String message) {
        getSupportLoaderManager().getLoader(0).forceLoad();
        showMessage(message);
    }

    public void exit() {
        finish();
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
