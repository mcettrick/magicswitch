package magicswitch.jetty.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/*
 * 
 * USB controlled relay board featuring an addressable SPDT relay rated 10A @ 250VAC. Onboard microcontroller & CH340T USB - serial controller chips. 
Power: 5V USB Port
Control Input: USB Type A Male
Output: 3 Position Terminal Strip
LED Power Indicator
LED Relay Status
Communication: Serial 9600bps,8 Data + 1 Stop bit,
No parity 
Commands are HEX code
1 Start: A0 
2 Switch code: 01 Default is 0 (first Relay)
3 On/Off: 01/00 
4 Check: A1
Close Relay: A0 01 00 A1
Open Relay: A0 01 01 A2
L: 2-1/2”	W: 5/8”	HT: 3/4” WT: .04

 */

@Component
public class Relay {

	private static final Logger log = LoggerFactory.getLogger(Relay.class);

	private static final String PORT = "/dev/ttyUSB0";

	private boolean on;

	public boolean isOn() {
		return on;
	}

	public synchronized void toggle() {
		if (on) {
			turnOff();
		} else {
			turnOn();
		}
	}

	public synchronized void turnOn() {

		SerialPort serialPort = openPort();

		try {
			log.debug("sending on command");

			serialPort.writeByte((byte)0xa0);
			serialPort.writeByte((byte)0x01);
			serialPort.writeByte((byte)0x01);
			serialPort.writeByte((byte)0xa2);

			on = true;

			serialPort.closePort();
		} catch (Exception e) {
			log.error("error turning on", e);
		}
	}

	public synchronized void turnOff() {

		SerialPort serialPort = openPort();

		try {
			log.debug("sending off command");

			serialPort.writeByte((byte)0xa0);
			serialPort.writeByte((byte)0x01);
			serialPort.writeByte((byte)0x00);
			serialPort.writeByte((byte)0xa1);

			on = false;

			serialPort.closePort();
		} catch (Exception e) {
			log.error("error turning off", e);
		}
	}

	@JsonIgnore
	public List<String> getPortNames() {
		String[] portNames = SerialPortList.getPortNames();

		for (String port : portNames) {
			System.out.println("port: " + port);
		}

		return Lists.newArrayList(portNames);
	}

	private SerialPort openPort() {
		
		SerialPort serialPort = new SerialPort(PORT);
		
		try {
			serialPort.openPort();//Open serial port
			serialPort.setParams(
					SerialPort.BAUDRATE_9600, 
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
		} catch (SerialPortException ex) {
			throw new RuntimeException(ex);
		}

		return serialPort;
	}

	private List<String> getSerialPortNames() {
		return Lists.newArrayList(SerialPortList.getPortNames());
	}
	
	@Override
	public String toString() {
		return "Switch [on=" + on + "]";
	}
}
