package dialogs;
import android.app.*;
import android.os.*;
import android.content.*;
import pokazaniya.timofeev.com.pokazaniya.R;

public class AddCountDialog extends AddTpDialog
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.init();
		/**
         * массив строк для списка внутри диалогового окна
         */
		String[] countList = getResources().getStringArray(R.array.count);
		/**
         * установка заголовка окна
         */
		builder.setTitle(R.string.selectCount);
		/**
         * заполнить диалоговое окно списком с одиночным выбором типа radiobutton:
         * countList - массив строк с номерами счетчиков из ресурса R.strings
         * -1 - не устанавливать ни один из пунктов списка как активный
         * addCountListener - слушатель нажатия на пункт списка
         */
		builder.setSingleChoiceItems(countList, -1, addCountListener);
		builder.setNeutralButton(R.string.ok, okListener);
		return super.createDialog();
	}
	/**
     * объект интерфейса будет установлен в качестве слушателя действия "добавить счетчик" из меню настрек
     */
	DialogInterface.OnClickListener addCountListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface p1, int position) {
            switch (position) {
                case 0:
                    db.addRecord(tp309, count0);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 1:
                    db.addRecord(tp309, count1);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 2:
                    db.addRecord(tp310, count2);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 3:
                    db.addRecord(tp310, count3);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 4:
                    db.addRecord(tp310, count4);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 5:
                    db.addRecord(tp310, count5);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 6:
                    db.addRecord(tp311, count6);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 7:
                    db.addRecord(tp311, count7);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 8:
                    db.addRecord(tp312, count8);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 9:
                    db.addRecord(tp312, count9);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 10:
                    db.addRecord(tp313, count10);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
                case 11:
                    db.addRecord(tp314, count11);
					uiUpdater.update("Счетчик добавлен");//синхронизировать интерфейс с базой данных
                    break;
            }
        }
    };
}
