package pokazaniya.timofeev.com.pokazaniya;

import android.hardware.Camera;

/**
 * Класс фонарика
 */
public class LED {
    Camera camera;
    Camera.Parameters parameters;
    boolean ledIsChecked = false;

    /**
     * инициализация камеры
     */
    void getCamera() {
        if (camera == null) {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }
    /**
     * включение led
     */
    void ledOn() {
        if (camera == null || parameters == null)
            return;//если камера не иницализирована - прервать выполнение метода
        parameters = camera.getParameters();//иницализация параметров камеры
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//включить led
        camera.setParameters(parameters);//установить камере данные параметры
        camera.startPreview();//стартануть камеру
        ledIsChecked = true;// установить флаг в значение true
    }
    /**
     * выключение led
     */
    void ledOff() {
        if (camera == null || parameters == null)
            return;//если камера не инициализирована - превать метод
        parameters = camera.getParameters();//инициализация параметров камеры
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//выключить led
        camera.setParameters(parameters);//устанавить камере данные параметры
        camera.stopPreview();//остановить камеру
        ledIsChecked = false;//установить флаг в значение false
    }
    /**
     * выгрузка камеры
     */
    void cameraRelease(){
        if (camera != null) {
            camera.release();//освободить камеру
            camera = null;
        }
    }
}
