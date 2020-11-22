package presentacion.views.cajero;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import dto.AltaClienteDTO;
import dto.ClienteDTO;
import dto.DatosPersonalesDTO;
import dto.TarjetaCreditoDTO;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class TarjetaCreditoFormView extends JDialog {

	private static final long serialVersionUID = -6293870932970697649L;
	private final JPanel contentPanel = new JPanel();

	private static TarjetaCreditoFormView instance;
	private JButton btnSalvar;
	private JTextField textNumeroTarjeta;
	private JTextField textNombreYApellido;
	private JTextField textFechaExpiracion;
	private JTextField textCod;
	private JTextField textDni;
	private JTextField textCuotas;

	private TarjetaCreditoFormView() {
		setBounds(100, 100, 506, 253);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("14px"),
				ColumnSpec.decode("right:max(37dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(80dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:89px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("26px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblNumeroTarjeta = new JLabel("Numero tarjeta");
		panel.add(lblNumeroTarjeta, "2, 2, right, default");

		textNumeroTarjeta = new JTextField();
		textNumeroTarjeta.setColumns(10);
		panel.add(textNumeroTarjeta, "4, 2, fill, default");

		JLabel lblNombreYApellido = new JLabel("Nombre y apellido");
		panel.add(lblNombreYApellido, "2, 4, right, default");

		textNombreYApellido = new JTextField();
		textNombreYApellido.setColumns(10);
		panel.add(textNombreYApellido, "4, 4, fill, default");

		JLabel lblFechaExpiracion = new JLabel("Fecha expiracion");
		panel.add(lblFechaExpiracion, "2, 6, right, default");

		textFechaExpiracion = new JTextField();
		textFechaExpiracion.setColumns(10);
		panel.add(textFechaExpiracion, "4, 6, fill, default");

		JLabel lblCod = new JLabel("Cod Seguridad");
		panel.add(lblCod, "6, 6, right, default");

		textCod = new JTextField();
		textCod.setColumns(10);
		panel.add(textCod, "8, 6, fill, default");

		JLabel lblDni = new JLabel("DNI");
		panel.add(lblDni, "2, 8, right, default");

		textDni = new JTextField();
		textDni.setColumns(10);
		panel.add(textDni, "4, 8, fill, default");

		JLabel lblCuotas = new JLabel("Cuotas");
		panel.add(lblCuotas, "2, 10, right, default");

		textCuotas = new JTextField();
		textCuotas.setColumns(10);
		panel.add(textCuotas, "4, 10, fill, default");

		JPanel panel_1 = new JPanel();
		contentPanel.add(panel_1, BorderLayout.SOUTH);

		btnSalvar = new JButton("Pagar");
		panel_1.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		panel_1.add(btnCancelar);

		setVisible(false);
		clearData();
	
	}

	public static TarjetaCreditoFormView getInstance() {
		if (instance == null) {
			instance = new TarjetaCreditoFormView();
		}
		return instance;
	}

	public TarjetaCreditoDTO getData() {
		TarjetaCreditoDTO datosTarjeta = new TarjetaCreditoDTO();
				
		datosTarjeta.setNumeroTarjeta(textNumeroTarjeta.getText());
		datosTarjeta.setNombreyapellido(textNombreYApellido.getText());
		datosTarjeta.setFechaExpiracion(textFechaExpiracion.getText());
		datosTarjeta.setCodSeguridad(textCod.getText());
		datosTarjeta.setDni(textDni.getText());
		datosTarjeta.setCuotas(textCuotas.getText());
				
		return datosTarjeta;

	}

	public void setData(TarjetaCreditoDTO tarjeta) {
		
		TarjetaCreditoDTO datosTarjeta = new TarjetaCreditoDTO();
	//	TarjetaCreditoDTO datosTarjeta = tarjeta.getDatosPersonalesDTO();
		
		textNumeroTarjeta.setText(datosTarjeta.getNumeroTarjeta());		
		textNombreYApellido.setText(datosTarjeta.getNombreyapellido() + "");
		textFechaExpiracion.setText(datosTarjeta.getFechaExpiracion());		
		textCod.setText(datosTarjeta.getCodSeguridad());
		textDni.setText(datosTarjeta.getDni() + "");		
		textCuotas.setText(datosTarjeta.getCuotas());
		

	//	this.btnSalvar.setVisible(false);
		
	}

	//public void setActionOnSave(ActionListener listener) {
	//	this.btnSalvar.addActionListener(listener);
//	}

	public void setActionOnRegistrar(ActionListener listener) {
		this.btnSalvar.addActionListener(listener);
		
	}

	public void clearData() {
		textNumeroTarjeta.setText("");
		textNombreYApellido.setText("");
		textFechaExpiracion.setText("");
		textCod.setText("");
		textDni.setText("");
		textCuotas.setText("");

		this.btnSalvar.setVisible(true);
	}

	public void display() {
		setVisible(true);
	}

	public void close() {
		setVisible(false);
	}
}