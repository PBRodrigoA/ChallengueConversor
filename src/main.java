import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;


public class main extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JComboBox convertfrom;
	private JComboBox convertTo;
	private JLabel lblNewLabel_2;
	private JTextArea MCantidad;
	private JTextField cant;
	private JButton btnNewButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 14));
		setForeground(Color.BLACK);
		setTitle("Conversor de monedas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Convertir de :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(37, 36, 87, 13);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Convertir a :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(37, 84, 87, 13);
		contentPane.add(lblNewLabel_1);
		
		convertfrom = new JComboBox();
		convertfrom.setModel(new DefaultComboBoxModel(new String[] {"Soles", "Dolar", "Euro", "Libras esterlinas", "Yen Japones", "Won surcoreano"}));
		convertfrom.setBounds(175, 29, 142, 21);
		contentPane.add(convertfrom);
		
		convertTo = new JComboBox();
		convertTo.setModel(new DefaultComboBoxModel(new String[] {"Soles", "Dolar", "Euro", "Libras esterlinas", "Yen Japones", "Won surcoreano"}));
		convertTo.setBounds(175, 77, 142, 21);
		contentPane.add(convertTo);
		
		lblNewLabel_2 = new JLabel("Cantidad :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(37, 136, 87, 13);
		contentPane.add(lblNewLabel_2);
		
		MCantidad = new JTextArea();
		MCantidad.setEnabled(false);
		MCantidad.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
		MCantidad.setBounds(72, 191, 425, 143);
		contentPane.add(MCantidad);
		
		cant = new JTextField();
		cant.setBounds(175, 130, 142, 19);
		contentPane.add(cant);
		cant.setColumns(10);
		
		btnNewButton = new JButton("Calcular");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(383, 59, 114, 38);
		contentPane.add(btnNewButton);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			actionPerformedBtnNewButton(e);
		}
	}
	protected void actionPerformedBtnNewButton(ActionEvent arg0) {
		int convertFrom,convertTo;
		BigDecimal quantity;
		String knowCoinTo,knowCoinFrom;
		
		convertFrom=getMonedaFrom();
		convertTo=getMonedaTo();
		quantity=getCant();
		
		knowCoinFrom=sabermonedaFrom(convertFrom);
		knowCoinTo=sabermonedaTo(convertTo);
		
		
		
		try {
			
			URL url =new URL("https://api.exchangerate.host/latest?base="+knowCoinFrom);
			HttpURLConnection conn= (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responseCode=conn.getResponseCode();
			if (responseCode!=200) {
				throw new RuntimeException("ocurrio un error "+responseCode);
			} else {
				StringBuilder information=new StringBuilder();
				Scanner scannner =new Scanner(url.openStream());
				
				while (scannner.hasNext()) {
					information.append(scannner.nextLine());
				}
				scannner.close();
				
				
				JSONObject jsonobject= new JSONObject(information.toString());
				JSONObject ratesObject = jsonobject.getJSONObject("rates");
				
				BigDecimal resultado=(ratesObject.getBigDecimal(knowCoinTo)).multiply(quantity);
				
				MCantidad.setText("El resultado es "+String.format("%.2f", resultado));
				
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	//obtiene la cantidad
	
	BigDecimal getCant() {
		return BigDecimal.valueOf(Double.parseDouble(cant.getText()));
	}
	
	
	//retorna el tipo de moneda
	int getMonedaFrom() {
		int xt;
		xt = convertfrom.getSelectedIndex();
		return xt;
	}
	
	
	int getMonedaTo() {
		int xt;
		xt = convertTo.getSelectedIndex();
		return xt;
	}
	
	String sabermonedaFrom(int monedaFrom) {
		String moneda="";
		switch(monedaFrom) {
			case 0: moneda="PEN";break;
			case 1: moneda="USD";break;
			case 2: moneda="EUR";break;
			case 3: moneda="GBP";break;
			case 4: moneda="JPY";break;
			case 5: moneda="KRW";break;
		}
		return moneda;
	}
	String sabermonedaTo(int monedaTo) {
		String moneda="";
		switch(monedaTo) {
			case 0: moneda="PEN";break;
			case 1: moneda="USD";break;
			case 2: moneda="EUR";break;
			case 3: moneda="GBP";break;
			case 4: moneda="JPY";break;
			case 5: moneda="KRW";break;
		}
		return moneda;
	}
	
}
