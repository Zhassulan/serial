package kz.ugs.callisto.mis.device.hlp5;

import kz.ugs.callisto.mis.device.hlp5.serial.SerialService;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		SerialService ss = new SerialService();
		ss.init();
	}
}
