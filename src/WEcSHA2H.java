import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WEcSHA2H {

	private JFrame frmShaPerformanceTest;
	private JLabel lblStatus;
	private JTextField txtElapse;
	private JTextField txtTimes;
	private JComboBox cbxSha2;
	private JTextField txtFilesize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WEcSHA2H window = new WEcSHA2H();
					window.frmShaPerformanceTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WEcSHA2H() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmShaPerformanceTest = new JFrame();
		frmShaPerformanceTest.setTitle("SHA2 Performance Test");
		frmShaPerformanceTest.setBounds(100, 100, 450, 300);
		frmShaPerformanceTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmShaPerformanceTest.getContentPane().add(panel,  BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblFilesize = new JLabel("Filesize");
		lblFilesize.setBounds(22, 32, 46, 15);
		panel.add(lblFilesize);
		
		txtFilesize = new JTextField();
		txtFilesize.setBounds(114, 29, 213, 21);
		panel.add(txtFilesize);
		txtFilesize.setColumns(10);
		
		JLabel lblBytes = new JLabel("Bytes");
		lblBytes.setBounds(358, 32, 46, 15);
		panel.add(lblBytes);
		
		JLabel lblSha2 = new JLabel("SHA2:");
		lblSha2.setBounds(22, 78, 46, 15);;
		panel.add(lblSha2);
		
		cbxSha2 = new JComboBox();
		cbxSha2.setModel(new DefaultComboBoxModel(new String[] {"SHA2-224", "SHA2-256", "SHA2-384", "SHA2-512"}));
		cbxSha2.setBounds(114, 75, 213, 21);
		panel.add(cbxSha2);
		
		JLabel lblTimes = new JLabel("Times");
		lblTimes.setBounds(22, 128, 46, 15);
		panel.add(lblTimes);
		
		txtTimes = new JTextField();
		txtTimes.setBounds(114, 125, 213, 21);
		panel.add(txtTimes);
		txtTimes.setColumns(10);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread execute = new Thread() {
					public void run() {
						try {
							lblStatus.setText("Running");
							int fileSize = Integer.parseInt(txtFilesize.getText());
							int times = Integer.parseInt(txtTimes.getText());
							int select = cbxSha2.getSelectedIndex();
							
							SecureRandom sr = new SecureRandom();
							byte[] original = new byte[fileSize];
							
							sr.nextBytes(original);
							MessageDigest digest = null;
							
							switch(select) {
							case 0:
								digest = MessageDigest.getInstance("SHA-224");
								break;
							case 1:
								digest = MessageDigest.getInstance("SHA-256");
								break;
							case 2:
								digest = MessageDigest.getInstance("SHA-384");
								break;
							case 3:
								digest = MessageDigest.getInstance("SHA-512");
								break;
							}
							
							long msbefore;
							long msafter;
							long total = 0;
							
							for (int i = 0; i < times; i++) {
								msbefore = getCurrentTime();
								byte[] resultbyte = digest.digest(original);
								msafter = getCurrentTime();
								total = total + (msafter - msbefore);
							}
							
							String result = "AVHS: " + (float)(total/times) + " ms";
							txtElapse.setText(result);
							
							lblStatus.setText("Ready");
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				execute.start();
			}
		});

		btnExecute.setBounds(337, 228, 87, 23);
		panel.add(btnExecute);
		
		JLabel lblElapse = new JLabel("Elapse");
		lblElapse.setBounds(22, 176, 46, 15);
		panel.add(lblElapse);
		
		txtElapse = new JTextField();
		txtElapse.setBounds(114, 173, 213, 21);;
		panel.add(txtElapse);
		txtElapse.setColumns(10);
		
		lblStatus = new JLabel("Ready");
		lblStatus.setBounds(22, 232, 46, 15);
		panel.add(lblStatus);
		
	}
	
	public static long getCurrentTime() {
		Date today;
		today = new Date();
		return today.getTime();
	}

	public JLabel getLblStatus() {
		return lblStatus;
	}
	public JTextField getTxtElapse() {
		return txtElapse;
	}
	public JTextField getTxtTimes() {
		return txtTimes;
	}
	public JComboBox getCbxSha2() {
		return cbxSha2;
	}
	public JTextField getTxtFilesize() {
		return txtFilesize;
	}
}
