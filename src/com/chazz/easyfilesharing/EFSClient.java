/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chazz.easyfilesharing;

import java.io.File;

/**
 *
 * @author chazz
 */
public class EFSClient {
	private EFSClientAliveThread aliveThread ;
	private EFSClientFileReceiverThread fileReciverThread;
	
	

	public EFSClient(MainActivity mv) {
		
		aliveThread = 	new EFSClientAliveThread();
		aliveThread.start();
		
		
		mv.writeDebug("lol");
	}
	
	
	
	

	
	
	
}

