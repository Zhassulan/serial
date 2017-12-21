package kz.ugs.callisto.mis.device.hlp5.serial;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialService {
	
	private static Logger logger = LogManager.getLogger(SerialService.class);
	private static SerialPort serialPort;
	private static String data;
	private static String port; 
	private static Properties props = new Properties();
	
	/**
	 * 
	 */
	public SerialService() {
		super();
		try {
			props.load(PortReader.class.getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}	
	}
	
	public String getData() {
		return data;
	}

	public void init() {
		port = props.getProperty("PORT");
		  //Передаём в конструктор имя порта
        serialPort = new SerialPort(port);
        try {
            //Открываем порт
            serialPort.openPort();
            //Выставляем параметры
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            //Включаем аппаратное управление потоком
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                                          SerialPort.FLOWCONTROL_RTSCTS_OUT);
            //Устанавливаем ивент лисенер и маску
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            //Отправляем запрос устройству
            serialPort.writeString("Get data");
        }
        catch (SerialPortException ex) {
            logger.error(ex.getMessage(), ex);
        }
	}
	
	 private static class PortReader implements SerialPortEventListener {

	        public void serialEvent(SerialPortEvent event) {
	            if(event.isRXCHAR() && event.getEventValue() > 0){
	                try {
	                    //Получаем ответ от устройства, обрабатываем данные и т.д.
	                    data = serialPort.readString(event.getEventValue());
	                    logger.info(data);
	                    if (props.getProperty("MODE").equals("FILE"))	{
	                    	ResultWriter.getInstance().writeToFile(props.getProperty("FILEPATH"), data);
	                    }	
	                    //И снова отправляем запрос
	                    serialPort.writeString("Get data");
	                }
	                catch (SerialPortException ex) {
	                	logger.error(ex.getMessage(), ex);
	                }
	            }
	        }
	    }

}
