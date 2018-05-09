/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chazz.easyfilesharing;

import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author chazz
 */
public class EFSClientAliveThread extends Thread {

	
	public DatagramSocket socket;
	public EFSClientAliveThread() {
		super("AliveThread");
		

	}

	@Override
	public void run() {
		try {
			socket = new DatagramSocket(20260);
			while (true) {

				try {
					byte[] buf = new byte[256];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					
					
					
					packet = new DatagramPacket(buf, buf.length, packet.getAddress(), 20261);
					socket.send(packet);
				} catch (IOException e) {
				}

			}


		} catch (SocketException e) {
			Log.e("chazz.easy", e.toString());
		}
	}

	public DatagramSocket getSocket() {
		return socket;
	}
	
	
}