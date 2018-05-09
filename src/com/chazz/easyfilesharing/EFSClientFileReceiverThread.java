/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chazz.easyfilesharing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chazz
 */
public class EFSClientFileReceiverThread extends Thread {

	private ServerSocket serverSocket;
	private Socket socket;
	private MainActivity mv;

	public EFSClientFileReceiverThread(MainActivity mv) {
		super("FileReceiver");
		this.mv = mv;

	}

	@Override
	public void run() {
		boolean run = true;
		boolean successfullReceive = false;
		while (run) {
			try {
				serverSocket = new ServerSocket(20262);
				socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				ObjectInputStream oin = new ObjectInputStream(in);

				OutputStream out = socket.getOutputStream();
				ObjectOutputStream objectout = new ObjectOutputStream(out);

				FileModel fm = (FileModel) oin.readObject();

				mv.writeDebug("recieved:" + fm.toString());
				int choose = mv.showChoose("File: " + fm.getName() + "\n Size:" + (fm.getSize() / 1024) + " kB", "Accept this file");

				if (choose == 1) {
					mv.writeDebug("Accepted:" + fm.toString());
				} else {
					mv.writeDebug("Rejected:" + fm.toString());
				}


				if (choose == 1) {
					File reciveFilePath = mv.getRecivePath();
					String recivePath = reciveFilePath.getCanonicalPath() + File.separator + fm.getName();
					File reciveFile = new File(recivePath);
					int overwrite = 1;
					if (reciveFile.exists()) {
						overwrite = mv.showChoose("File " + recivePath + " exists.\nOverwrite ?", "File exists");
					}
					objectout.writeInt(overwrite);
					objectout.flush();
					if (overwrite == 1) {
						FileOutputStream fout = new FileOutputStream(recivePath);
						byte[] buffer = new byte[socket.getSendBufferSize()];
						int bytesRead;
						long bytesTotal = 0l;
						long bps = 0l;
						long start = System.currentTimeMillis();
						while ((bytesRead = in.read(buffer)) != -1) {
							fout.write(buffer, 0, bytesRead);
							bytesTotal += (long) bytesRead;
							double diff = (double) bytesTotal / (double) fm.getSize();
							if (System.currentTimeMillis() - start > 1000) {
								start = System.currentTimeMillis();
								mv.setSpeed((float) (bps / 1024f) / 1024f);
								bps = 0l;
							} else {
								bps += bytesRead;
							}
							mv.setProgressBarValue((int) (diff * 100));
						}
						fout.flush();
						fout.close();

						if (bytesTotal == fm.getSize()) {
							successfullReceive = true;
						}
					}
				} else {
					objectout.writeInt(choose);
					objectout.flush();
				}

				oin.close();
				in.close();
				objectout.close();
				out.close();

				socket.close();
				serverSocket.close();
				if (successfullReceive) {
					mv.showMessage("File transfer is successful");
					mv.setProgressBarValue(0);
					mv.setSpeed(-1);
				}
			} catch (BindException ex) {
				mv.showError("Program is already running!");
				System.exit(1);
			} catch (IOException ex) {
				run = false;
				Logger.getLogger(EFSClientFileReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				run = false;
				System.out.println("Class is invalid");
			}
			successfullReceive = false;
		}

	}
}

