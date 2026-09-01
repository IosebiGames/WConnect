package main;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import SoundSystem.SoundManager;
import com.formdev.flatlaf.FlatLightLaf;

public class App {
   public JFrame window;
   public JLabel titleLabel, quoteLabel, splashText, SSIDLabel, PasswordLabel, wifiNameLabel, IPLabel, LoopbackLabel, AuthorLabel;
   public JPanel panel;
   public JButton ConnectionButton, ConnectButton, AboutWifi, resetButton, DisconnectButton;
   public JPanel connectPanel;
   public JTextField passwordArea, textArea;
   public Random random;
   public JTextField nameArea;
   public SoundManager sm;
   private ActionListener connectListener, BackListener;
   private WifiEstablishment we;
   private String WifiName, WifiIP, LoopbackAddress;
   private boolean connected = false;
   public JToggleButton savingButton;
   private String status;
   private Path savePath;
	
   static {
	   FlatLightLaf.setup();
	   JOptionPane.showMessageDialog(null, "Important: WConnect is Java application which currently works on Windows. it won't operate on other OS. But Multi-OS connection might be implemented in future updates. for change log, click down below to open Github.", "WConnect", JOptionPane.WARNING_MESSAGE);
   }
   public App() {
	  this.random = new Random();
	  this.sm = new SoundManager();
	  this.we = new WifiEstablishment(sm, this);
	  this.savePath = Path.of("setting.txt");
	  
	  this.readSettingFile();
	   try {
		  this.WifiName = InetAddress.getLocalHost().getHostName();
	      this.WifiIP = InetAddress.getLocalHost().getHostAddress();
	      this.LoopbackAddress = String.valueOf(InetAddress.getLoopbackAddress());
	      connected = InetAddress.getByName("8.8.8.8").isReachable(1500);
	  }catch(IOException e) {
		  connected = false;
		  System.out.println("Can't determine a host: " + e.getMessage());
		  System.out.println("Connection status: " + connected);
	  }
	  window = new JFrame("WConnect");
      window.setResizable(false);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setPreferredSize(new Dimension(854, 480));
      window.pack();
      window.getContentPane().setLayout(null);
      window.setLocationRelativeTo(null);
      window.getContentPane().setBackground(Color.white);
      window.setIconImage(new ImageIcon(this.getClass().getResource("/icons/logo.png")).getImage());
      window.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
               case 27: System.exit(0); 
                  break;
            }
         }
      });
      titleLabel = new JLabel("WConnect");
      titleLabel.setFont(new Font("SimSun", 1, 30));
      titleLabel.setBounds(335, -2, 466, 46);
      
      quoteLabel = new JLabel("Wi-Fi isn't just a signal, it's your comfort.");
      quoteLabel.setFont(new Font("Consolas", Font.BOLD, 21));
      quoteLabel.setBounds(120, 26, 566, 46);
      
      window.getContentPane().add(titleLabel);
      window.getContentPane().add(quoteLabel);
    
      panel = new JPanel();
      panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4.0F)));
      panel.setBounds(192, 98, 425, 252);
      panel.setLayout(null);
      window.getContentPane().add(panel);
      
      SSIDLabel = new JLabel("SSIDD:");
      SSIDLabel.setFont(new Font("Trebuchet MS", 2, 17));
      SSIDLabel.setBounds(65, 38, 87, 14);
      SSIDLabel.setVisible(false);
      panel.add(SSIDLabel);
      
      connectPanel = new JPanel();
      connectPanel.setBounds(92, 21, 233, 149);
      connectPanel.setVisible(true);
      connectPanel.setLayout(null);
      
      nameArea = new JTextField();
      nameArea.setBounds(42, 11, 137, 28);
      connectPanel.add(nameArea);
      
      passwordArea = new JTextField();
      passwordArea.setBounds(42, 49, 137, 28);
      passwordArea.setVisible(true);
      connectPanel.add(passwordArea);
      
      ConnectButton = new JButton("Connect");
      ConnectButton.setBounds(69, 102, 89, 23);
      ConnectButton.setFocusable(false);
      ConnectButton.setBackground(Color.white);
      ConnectButton.setForeground(Color.black);
      connectPanel.add(ConnectButton);
      
      PasswordLabel = new JLabel("Password:");
      PasswordLabel.setFont(new Font("Trebuchet MS", 2, 17));
      PasswordLabel.setBounds(40, 76, 87, 14);
      PasswordLabel.setVisible(false);
      panel.add(PasswordLabel);
      
      splashText = new JLabel("Works Only on Windows!");
      splashText.setBounds(506, 66, 210, 22);
      splashText.setFocusable(false);
      splashText.setFont(new Font("Trebuchet MS", Font.ITALIC, 18));
      window.getContentPane().add(splashText);
      
      wifiNameLabel = new JLabel("Wi-Fi Name: " + WifiName);
	  wifiNameLabel.setBounds(25, 38, 467, 34);
      wifiNameLabel.setFocusable(false);
      wifiNameLabel.setVisible(false);
      wifiNameLabel.setFont(new Font("Consolas", Font.BOLD, 21));
      panel.add(wifiNameLabel);
      
      IPLabel = new JLabel("Wi-Fi IP: " + WifiIP);
	  IPLabel.setBounds(25, 68, 467, 34);
      IPLabel.setFocusable(false);
      IPLabel.setVisible(false);
      IPLabel.setFont(new Font("Consolas", Font.BOLD, 21));
      panel.add(IPLabel);
      
      LoopbackLabel = new JLabel("Loopback IP: " + LoopbackAddress);
      LoopbackLabel.setBounds(25, 98, 467, 34);
      LoopbackLabel.setFocusable(false);
      LoopbackLabel.setVisible(false);
      LoopbackLabel.setFont(new Font("Consolas", Font.BOLD, 21));
      panel.add(LoopbackLabel);
      
      AuthorLabel = new JLabel("<html>by IosebiGames. <span style='color: red;'>Click to see Github.</span></html>");
      AuthorLabel.setBounds(530, 380, 497, 34);
      AuthorLabel.setFocusable(false);
      AuthorLabel.setFont(new Font("Consolas", Font.BOLD, 15));
      AuthorLabel.addMouseListener(new MouseAdapter() {
    	  @Override
    	  public void mouseClicked(MouseEvent e) {
    		  if(e.getSource() == AuthorLabel) {
    			  try {
					Desktop.getDesktop().browse(new URI("https://github.com/IosebiGames"));
				  }catch(IOException | URISyntaxException ex) {
			          System.out.println("Can't open Github page: " + ex.getMessage());
				  }
    		  }
    	  }
      });
      window.add(AuthorLabel);
      
      BackListener = e -> {
    	 if(e.getSource() == ConnectionButton) {
    		sm.playSound();
   		    SSIDLabel.setVisible(false);
   		    passwordArea.setVisible(false);
   		    PasswordLabel.setVisible(false);
   		    connectPanel.setVisible(false);
   		    SSIDLabel.setVisible(false);
   		    passwordArea.setVisible(false);
   		    wifiNameLabel.setVisible(false);
   		    IPLabel.setVisible(false);
   		    AboutWifi.setVisible(true);
   		    DisconnectButton.setVisible(true);
   		    resetButton.setVisible(true);
  		    savingButton.setVisible(true);
   		    LoopbackLabel.setVisible(false);
   		    ConnectionButton.setBounds(46, 41, 174, 37);
   	        DisconnectButton.setBounds(46, 91, 174, 37);
   		    ConnectionButton.setText("Connect to Wi-Fi");
   		    ConnectionButton.removeActionListener(BackListener);
   		    ConnectionButton.addActionListener(connectListener);
    	 }
      };
      ConnectionButton = new JButton("Connect to Wi-Fi");
      ConnectionButton.setBounds(46, 41, 174, 37);
      ConnectionButton.setBackground(Color.white);
      ConnectionButton.setForeground(Color.black);
      ConnectionButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
      ConnectionButton.setFocusable(false);
      
      DisconnectButton = new JButton("Disconnect from Wi-Fi");
      if(!connected) {
    	  DisconnectButton.setEnabled(false);
    	  DisconnectButton.setToolTipText("You are already disconnected from Wi-Fi.");
      }else {
    	  DisconnectButton.setEnabled(true);
    	  DisconnectButton.setToolTipText("");
      }
      DisconnectButton.setBounds(46, 91, 174, 37);
      DisconnectButton.setBackground(Color.white);
      DisconnectButton.setForeground(Color.black);
      DisconnectButton.setFocusable(false);
      DisconnectButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
      DisconnectButton.addActionListener(e -> {
          if(e.getSource() == DisconnectButton) {
        	  we.Disconnect();
        	  DisconnectButton.setEnabled(false);
        	  DisconnectButton.setToolTipText("You are already disconnected from Wi-Fi.");
          }
      });
      AboutWifi = new JButton("About my Wi-Fi");
      AboutWifi.setBounds(46, 191, 177, 37);
      AboutWifi.setBackground(Color.white);
      AboutWifi.setForeground(Color.black);
      AboutWifi.setFont(new Font("Times New Roman", Font.BOLD, 15));
      AboutWifi.setFocusable(false);
      
      resetButton = new JButton("Reset Network");
      resetButton.setBounds(46, 141, 174, 37);
      resetButton.setBackground(Color.white);
      resetButton.setForeground(Color.black);
      resetButton.setFocusable(false);
      resetButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
      resetButton.addActionListener(e -> {
            if(e.getSource() == resetButton) {
            	we.resetNetworkSettings();
            }
      });
      savingButton = new JToggleButton("Save Wi-Fi details");
      savingButton.setBounds(250, 180, 160, 40);
      savingButton.setBackground(Color.white);
      savingButton.setForeground(Color.black);
      savingButton.setFocusable(false);
      savingButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        
         if(status != null) {
        	 if(status.equals("Enabled")) {
        		 savingButton.setSelected(true);
        		 savingButton.setBackground(Color.green);
        	 }else if(status.equals("Disabled")) {
        		 savingButton.setSelected(false);
        	 }
         }
    	 savingButton.addActionListener(e -> {
    	  if(e.getSource() == savingButton) {
    		  if(savingButton.isSelected()) {
    		 	  savingButton.setBackground(Color.green);
    			  try {
					Files.writeString(savePath, "Enabled");
				 }catch(IOException ex) {
					System.out.println("Can't save status: " + ex.getMessage());
				}
    		  }else {
    			 savingButton.setBackground(Color.white);
    			 if(Files.exists(savePath)) {
    				 try {
						Files.delete(savePath);
					 }catch(IOException ex) {
					    System.out.println("Can't delete the file: " + ex.getMessage());
					 }
    			 }
    		  }
    	  }
      });
      if(connected) {
    	  AboutWifi.setEnabled(true);
      }else {
    	  AboutWifi.setEnabled(false);
    	  AboutWifi.setToolTipText("No Wi-Fi connection is established yet.");
      }
      AboutWifi.addActionListener(e -> {
    	  if(e.getSource() == AboutWifi) {
              sm.playSound();
              AboutWifi.setVisible(false);
              resetButton.setVisible(false);
              DisconnectButton.setVisible(false);
    		  savingButton.setVisible(false);
              ConnectionButton.setBounds(116, 171, 174, 37);
              ConnectionButton.setText("Back");
              ConnectionButton.removeActionListener(connectListener);
              ConnectionButton.addActionListener(BackListener);
              ConnectionButton.setVisible(true);
              wifiNameLabel.setVisible(true);
              IPLabel.setVisible(true);
              LoopbackLabel.setVisible(true);
    	  }
      });
      connectListener = e -> {
    	  if(e.getSource() == ConnectionButton) {
    		  sm.playSound();
    		  panel.add(connectPanel);
    		  SSIDLabel.setVisible(true);
    		  passwordArea.setVisible(true);
    		  PasswordLabel.setVisible(true);
    		  connectPanel.setVisible(true);
    		  SSIDLabel.setVisible(true);
    		  AboutWifi.setVisible(false);
    		  resetButton.setVisible(false);
    		  DisconnectButton.setVisible(false);
    		  savingButton.setVisible(false);
    		  passwordArea.setVisible(true);
    		  ConnectionButton.setBounds(116, 171, 174, 37);
    		  ConnectionButton.setText("Back");
    		  ConnectionButton.addActionListener(BackListener);
    		  ConnectionButton.removeActionListener(connectListener);
    	  }
      };
      ConnectionButton.addActionListener(connectListener);
      panel.add(ConnectionButton);
      panel.add(DisconnectButton);
      panel.add(AboutWifi);
      panel.add(resetButton);
      panel.add(savingButton);
    
      ConnectButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            sm.playSound();
            ConnectionButton.setText("Connect to Wi-Fi");
            ConnectionButton.setEnabled(true);
            DisconnectButton.setVisible(true);
            AboutWifi.setVisible(true);
            resetButton.setVisible(true);
  		    savingButton.setVisible(true);
            connectPanel.setVisible(false);
            SSIDLabel.setVisible(false);
            PasswordLabel.setVisible(false);
            ConnectionButton.setBounds(46, 41, 174, 37);
   	        DisconnectButton.setBounds(46, 91, 174, 37);
   	        we.establishConnection(nameArea.getText().trim(), passwordArea.getText().trim());
   	        nameArea.setText("");
            passwordArea.setText("");
         }
      });
      window.setVisible(true);
      new Timer(500, new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    		  splashText.setForeground(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
    	  }
      }).start();
   }
   public void hide() {
      SSIDLabel.setVisible(false);
	  passwordArea.setVisible(false);
	  PasswordLabel.setVisible(false);
	  connectPanel.setVisible(false);
	  SSIDLabel.setVisible(false);
	  passwordArea.setVisible(false);
	  AboutWifi.setVisible(true);
	  ConnectionButton.setText("Connect to Wi-Fi");
	  ConnectionButton.removeActionListener(BackListener);
	  ConnectionButton.addActionListener(connectListener);
   }
   private void readSettingFile() {
	   if(Files.exists(savePath)) {
		   try {
     		   status = Files.readString(savePath);
		   }catch(IOException ex) {
     			System.out.println("Can't find the Writing status: " + ex.getMessage());
     		}
     	}
   }
   public static void main(String[] args) {
	   SwingUtilities.invokeLater(App :: new);
   }
}
