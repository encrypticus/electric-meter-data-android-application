package dialogs;
import android.app.*;
import android.os.*;
import pokazaniya.timofeev.com.pokazaniya.R;
import android.content.Intent;
import android.content.DialogInterface;
import pokazaniya.timofeev.com.pokazaniya.StatisticByCountActivity;

public class StatisticByCountDialog extends DialogWindow
{
	String[] countList;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		init();
		countList = getResources().getStringArray(R.array.count);
		builder.setTitle(R.string.statisticByCount);
		builder.setItems(countList, statisticByCountListener);
		builder.setNeutralButton(R.string.cancel, okListener);
		return super.createDialog();
	}
	/**
     * метод инкапсулирует отправку сообщения с помощью параметра intent
     * в сообщении передается номер счетчика в параметре countNumber
     * @param intent
     * @param countNumber
     */
    private void sendMessage(Intent intent, int countNumber) {
        //отправить сообщение с именем "countNumber" и значением параметра countNumber
        intent.putExtra("countNumber", countNumber);
        //перейти на активность intent
        startActivity(intent);
		getActivity().overridePendingTransition(R.anim.activity_rotate_left, R.anim.activity_rotate_right);
    }
	/**
     * объект данного интерфейса устанавливается в качестве слушателя выбора пункта меню настроек
     * "Статистика по счетчику"
     */
    DialogInterface.OnClickListener statisticByCountListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int position) {
            /**
             * intent для перехода на StatisticByCountActivity
             * эта активность отображает ListView со статистикой по выбранному номеру счетчика
             */
            Intent statisticByCountIntent = new Intent(getActivity(), StatisticByCountActivity.class);
            switch (position) {
                case 0:
                    sendMessage(statisticByCountIntent, 100964);
                    break;
                case 1:
                    sendMessage(statisticByCountIntent, 160000);
                    break;
                case 2:
                    sendMessage(statisticByCountIntent, 215110);
                    break;
                case 3:
                    sendMessage(statisticByCountIntent, 995258);
                    break;
                case 4:
                    sendMessage(statisticByCountIntent, 19250);
                    break;
                case 5:
                    sendMessage(statisticByCountIntent, 114489);
                    break;
                case 6:
                    sendMessage(statisticByCountIntent, 215933);
                    break;
                case 7:
                    sendMessage(statisticByCountIntent, 516465);
                    break;
                case 8:
                    sendMessage(statisticByCountIntent, 820943);
                    break;
                case 9:
                    sendMessage(statisticByCountIntent, 835057);
                    break;
                case 10:
                    sendMessage(statisticByCountIntent, 20297549);
                    break;
                case 11:
                    sendMessage(statisticByCountIntent, 20309187);
                    break;
            }
        }
    };

	DialogInterface.OnClickListener okListener =new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface p1, int p2)
		{
			// TODO: Implement this method
		}
	};
}
