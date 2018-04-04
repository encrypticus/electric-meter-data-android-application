package dialogs;

import android.content.Context;
import pokazaniya.timofeev.com.pokazaniya.UiUpdater;
/**
 * Суперкласс для всех диалоговых окон приложения
 */
public class DialogWindow extends SimpleDialogWindow
{
    UiUpdater uiUpdater;

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
