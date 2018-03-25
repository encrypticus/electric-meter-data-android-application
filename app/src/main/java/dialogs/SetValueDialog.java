package dialogs;
import android.os.*;
import android.app.*;
import pokazaniya.timofeev.com.pokazaniya.R;
import android.view.*;
import android.widget.*;
import android.content.DialogInterface;

public class SetValueDialog extends DialogWindow
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
     * текстовое поле из R.layout.setvalue, служащее для редактирования значений показаний
     */
	EditText et;
	/**
	 * этой переменной будет присвоено значение AdapterView.AdapterContextMenuInfo.id,
	 * которое будет передано в диалоговое окно из MainActivity через объект класса Bundle
	 * при нажатии на пункт контекстного меню "Изменить"
	 * Свойство id этого класса содержит
	 * _id записи из базы данных - primary key значение. Переменная используется при удалении или изменении записи базы данных
	 * */
	long id;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		//инициализация
		super.init();
		//получение id пунктв меню из объекта Bundle из MainActivity
		id = getArguments().getLong("id");
		//установка заголовка окна
		builder.setTitle(R.string.insert);
		//установка иконки окна
		builder.setIcon(R.drawable.edit);
		inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.setvalue, null);
		et = (EditText) view.findViewById(R.id.setvalue);
		//установка содержимого окна
		builder.setView(view);
		//установка кнопок и слушателей окна
		builder.setPositiveButton(R.string.insert, setValueListener);//установить кнопки для диалогового
		builder.setNegativeButton(R.string.cancel, setValueListener);//окна и слушатели на них
		return super.createDialog();
	}
	/**
     * объект данного интерфейса устанавливается в качестве слушателя события выбора пункта контекстного
     * меню "Изменить"
     */
    DialogInterface.OnClickListener setValueListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE://если нажата кнопка "Изменить"
                    String value = et.getText().toString();//получить данные, введенные в текстовое поле
                    db.changeRecord(id, value);//изменить данные в базе данных
					uiUpdater.update("Значение изменено");//обновить
                    break;
                case Dialog.BUTTON_NEGATIVE://если нажата кнопка "Оменить" - ничего не делать
                    break;
            }
        }


    };
}
