package main;

import javax.swing.JOptionPane;
import java.io.*;
import SoundSystem.SoundManager;

public class WifiEstablishment {
	private ProcessBuilder builder;
	private Process process, process2;
	private SoundManager sm;
	private App app;
	private String windir = System.getenv("SystemRoot");
	public String status;
	   
	public WifiEstablishment(SoundManager sm, App app) {
	    this.sm = sm;	
	    this.app = app;
	}
	@SuppressWarnings("deprecation")
	public void establishConnection(String SSID, String password) {
	   try {
       	 process = Runtime.getRuntime().exec("netsh wlan connect name=\"" + SSID + "\"");
       	 process.waitFor();
       	 
       	 if(new File("setting.txt").exists()) {
       		 try(BufferedReader br = new BufferedReader(new FileReader("setting.txt"))) {
       			 status = br.readLine();
       			 br.close();
       		 }catch(IOException ex) {
       			 System.out.println("Can't find the Writing status: " + ex.getMessage());
       		 }
       	 }
       	 if(process.waitFor() == 0) {
       		 if(status != null) {
       			 if(status.equals("Enabled")) {
       				 writeSave(SSID, password);
           			 app.hide();
           			 JOptionPane.showMessageDialog(null, "Connected successfully!", "WConnect", JOptionPane.INFORMATION_MESSAGE);
           			 app.AboutWifi.setEnabled(true);
           			 app.AboutWifi.setToolTipText("");
           			 app.DisconnectButton.setEnabled(true);
           			 app.DisconnectButton.setToolTipText("");
           			 app.window.dispose();
           			 new App();
           			 sm.playSound();
       			 }else if(status.equals("Disabled")) {
           			 app.hide();
           			 JOptionPane.showMessageDialog(null, "Connected successfully!", "WConnect", JOptionPane.INFORMATION_MESSAGE);
           			 app.AboutWifi.setEnabled(true);
           			 app.AboutWifi.setToolTipText("");
           			 app.DisconnectButton.setEnabled(true);
           			 app.DisconnectButton.setToolTipText("");
           			 app.window.dispose();
           			 new App();
           			 sm.playSound();
       			 }
       		 }
       	 }else {
       		 app.hide();
       		 JOptionPane.showMessageDialog(null, "Wi-Fi connection has failed, Please make sure that SSID Or Password Box isn't blank, or try typing again correctly.", "WConnect", JOptionPane.ERROR_MESSAGE);
       		 sm.playSound();
       	 }
        }catch(Exception e) {
       	   JOptionPane.showMessageDialog(null, "Problem with connecting, Please consider showing this error to Developer:" + e.getMessage(), "WConnect", JOptionPane.ERROR_MESSAGE);
        }
	}
	public void resetNetworkSettings() {
	    try {
	        if(windir == null) {
	        	windir = "C:\\Windows";
	        }
	        builder = new ProcessBuilder(windir + "\\System32\\WindowsPowerShell\\v1.0\\powershell.exe", "-Command", "Start-Process powershell -ArgumentList '-Command netsh int ip reset; netsh winsock reset' -Verb RunAs -Wait -WindowStyle Hidden");
	        process2 = builder.start();
	        
	        if(process2.waitFor() == 0) {
	           JOptionPane.showMessageDialog(null, "After doing Network Reset, computer needs to be restarted for noticeable changes. Please Restart your computer.", "WConnect", JOptionPane.INFORMATION_MESSAGE);
	        }else {
	           JOptionPane.showMessageDialog(null, "Network Reset either failed or has been rejected.", "WConnect", JOptionPane.ERROR_MESSAGE);
	        }
	    }catch(IOException | InterruptedException e) {
	        JOptionPane.showMessageDialog(null, "Network seems to be malfunctioning, please inform Developer: " + e.getMessage(), "WConnect", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public void Disconnect() {
		try {
			Runtime.getRuntime().exec("netsh wlan disconnect");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void writeSave(String SSID, String password) {
		 try(BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"))) { 
				 writer.write("Network Name - " + SSID);
				 writer.newLine();
				 writer.write("Network Password - " + password);
				 writer.close();
		   }catch(IOException e) {
			 System.out.println("Can't save Wi-Fi details: " + e.getMessage());
	    }
	}
}
