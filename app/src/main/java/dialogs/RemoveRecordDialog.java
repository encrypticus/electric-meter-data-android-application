package dialogs;
import android.app.*;
import android.os.*;
import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;
import pokazaniya.timofeev.com.pokazaniya.R;

public class RemoveRecordDialog extends DialogWindow
{
	/**
     * создает layout из ресурса R.layout.setvalue.
	 * Этот layout содержит текстовое поле ввода,
	 * отображаемое в диалоговом окне, которое служит для
     * редактирования значения показаний
     */
	LayoutInflater inflater;
	/**
     * layout из R.layout.setvalue, который содержит
	 * текстовое поле для редактирования значения показаний
     */
	View view;
	/**
	 * этой переменной будет присвоено значение AdapterView.AdapterContextMenuInfo.id,
	 * которое будет передано в диалоговое окно из MainActivity через объект класса Bundle
	 * при нажатии на пункт контекстного меню "Удалить"
	 * Свойство id этого класса содержит
	 * _id записи из базы данных - primary key значение. Переменная используется при удалении или изменении записи базы данных
	 * */
	 long[] ids;
	 
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.init();
		//получение id пунктв меню из объекта Bundle из MainActivity
		ids = getArguments().getLongArray("ids");
		//установка заголовка окна
		builder.setTitle(R.string.alert);
		//установка иконки окна
		builder.setIcon(R.drawable.alert);
		//установка сообщения диалогового окна
		builder.setMessage(R.string.removeRecord);
		//установка кнопок и слушателей
		builder.setPositiveButton(R.string.remove, removeRecordListener);
		builder.setNegativeButton(R.string.no, removeRecordListener);
		return builder.create();
	}
	/**
     * объект данного интерфейса устанавливается в качестве слушателя события выбора контекстного меню "Удалить"
     */
    DialogInterface.OnClickListener removeRecordListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface p1, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE://если нажата кнопка "Удалить"
                    db.removeRecords(ids);//удалить запись с указанным id из базы
                    uiUpdater.update("Выбранные записи удалены");//обновить интерфейс
                    break;
                case Dialog.BUTTON_NEGATIVE:// если нажата кнопка "Отмена"
                    break;//ничего не делать и просто скрыть диалоговое окно
            }
        }

    };
}
