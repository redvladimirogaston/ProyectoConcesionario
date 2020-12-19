package presentacion.views.gerente;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

public class ReporteAutosVendidosFormView extends JDialog {

	private static final long serialVersionUID = 1L;
	private static ReporteAutosVendidosFormView instance;

	public static ReporteAutosVendidosFormView getInstance() {
		if (instance == null) {
			instance = new ReporteAutosVendidosFormView();
		}
		return instance;
	}

	private final JPanel contentPanel = new JPanel();

	private JButton btnGenerarReporte;

	private JDateChooser fechaDesde;
	private JDateChooser fechaHasta;

	public ReporteAutosVendidosFormView() {
		setTitle("Reporte Autos Vendidos");
		setBounds(100, 100, 380, 280);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panelCentral = new JPanel();
		panelCentral.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblDesde = new JLabel("Desde");
		panelCentral.add(lblDesde, "4, 4");
				
						fechaDesde = new JDateChooser();
						panelCentral.add(fechaDesde, "8, 4, 3, 1");
		
				JLabel lblHasta = new JLabel("Hasta");
				panelCentral.add(lblHasta, "4, 6");
				
						fechaHasta = new JDateChooser();
						panelCentral.add(fechaHasta, "8, 6, 3, 1");

		JPanel panelInferior = new JPanel();
		contentPanel.add(panelInferior, BorderLayout.SOUTH);

		btnGenerarReporte = new JButton("GenerarReporte");
		panelInferior.add(btnGenerarReporte);

	}

	public void clearData() {
		this.btnGenerarReporte.setVisible(true);
	}

	public void display() {
		setVisible(true);
	}

	public void setActionGenerarReporte(ActionListener listener) {
		this.btnGenerarReporte.addActionListener(listener);
	}

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporte;
	}

	public Date getFechaDesde() {
		return fechaDesde.getDate();
	}

	public Date getFechaHasta() {
		return fechaHasta.getDate();
	}

}
