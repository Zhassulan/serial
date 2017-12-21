package kz.ugs.callisto.mis.device.hlp5.serial;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Класс для записи результатов чтения порта в файл
 * @author ZTokbayev
 *
 */
public class ResultWriter {
	
	private static Logger logger = LogManager.getLogger(ResultWriter.class);
	private static volatile ResultWriter _instance = null;

	public static synchronized ResultWriter getInstance() {
        if (_instance == null)
        	 synchronized (ResultWriter.class) {
                 if (_instance == null)
                     _instance = new ResultWriter();
             }
        return _instance;
    }
	
	public void writeToFile(String fileName, String data)	{
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(fileName, true));
			//append
			writer.append(' ');
			writer.append(data.toString());
			//writer.write(data); overwrite
		    writer.close();
		    logger.info("Save data {" + data + "} to file \"" + fileName + "\" is finished.");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	   
	}
	
}
