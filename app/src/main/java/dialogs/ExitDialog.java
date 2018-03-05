package dialogs;
import android.app.*;
import android.os.*;
import pokazaniya.timofeev.com.pokazaniya.R;
import android.content.*;

public class ExitDialog extends DialogWindow
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.init();
		builder.setTitle(R.string.exit);
		builder.setMessage(R.string.exitFromApp);//установить в качестве содержимого окна сообщение
		builder.setIcon(R.drawable.exit);
		builder.setPositiveButton(R.string.yes, exitListener);
		builder.setNegativeButton(R.string.no, exitListener);
		return builder.create();
	}
	
	/**
     * объект этого интерфейса устанавливается в качестве слушателя пункта меню настроек "Выход" или
     * нажатия системной кнопки "Назад"
     */
    DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE://если нажата кнопка "Выйти"
                    uiUpdater.exit();// завершить работу приложения
                    break;
                case Dialog.BUTTON_NEGATIVE:// если нажата кнопка "Отмена"
                    break;// просто скрыть диалоговое окно и остаться в приложении
            }
        }
    };
}
