package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import pokazaniya.timofeev.com.pokazaniya.DbHelper;
import pokazaniya.timofeev.com.pokazaniya.R;
import pokazaniya.timofeev.com.pokazaniya.UiUpdater;
/**
 * Суперкласс для всех диалоговых окон приложения
 */
public class DialogWindow extends DialogFragment
{
	DbHelper db;//объект базы данных приложения
    /**
     * объект интерфейса, через который происходит взаимодействие диалогового окна с главной Activity.
     * При нажатии на пункт списка, из которого состоит диалоговое окно, в объекте addListener в методе onClick
     * происходит вставка записей в базу данных и для того, чтобы ListView активности был синхронизирован с базой, вызывается
     * метод update интерфейса UiUpdater, реализованного в MainActivity. В методе update как раз и вызывается метод
     * getSupportLoaderManager().getLoader(0).forceLoad(), синхронизирующий интерфейс активности с базой данных
     */
    UiUpdater uiUpdater;
	/**
	 * объект непосредственно диалогового окна
	 */
	AlertDialog.Builder builder;
	AlertDialog dialog;
	//инициализация		
	void init(){
		db = new DbHelper(getActivity());
		builder = new AlertDialog.Builder(getActivity());
		//builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
	}

	AlertDialog createDialog(){
		dialog = builder.create();
		dialog.getWindow().getAttributes().windowAnimations = R.style.ScaleRotateAnimation;
		return dialog;
	}
	/**
     * основной метод, в котором создается диалоговое окно. Для создания непосредственно диалогового окна внутри метода
     * применяется класс AlertDialog.Builder
     *
     * @param savedInstanceState
     * @return
     */
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// инициализация
		init();
		//return builder.create();
		return null;
	}

	/**
     * Метод onAttach() вызывается в начале жизненного цикла фрагмента, и именно здесь мы можем получить контекст фрагмента,
     * в качестве которого выступает класс MainActivity. Так как MainActivity реализует интерфейс UiUpdater,
     * то мы можем преобразовать контекст к данному интерфейсу.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        uiUpdater = (UiUpdater) context;
    } 
}
