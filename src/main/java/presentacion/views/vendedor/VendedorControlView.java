package presentacion.views.vendedor;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.List;

import javax.swing.JTabbedPane;

import dto.ClienteDTO;
import dto.VehiculoParaVentaDTO;
import dto.temporal.ConsultaVehiculoParaVentaDTO;
import dto.temporal.OutputConsultaVehiculoEnVentaDTO;
import presentacion.views.supervisor.ClientePanelView;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;

public class VendedorControlView extends JInternalFrame {
	  	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1308877516578945407L;

	private static VendedorControlView instance;
	
	private PanelBusquedaPorDniView busquedaPanel;
	
	private ClientePanelView clientePanel;
	
	private BusquedaVehiculoPanel busquedaVehiculoPanel;
	
	private DatosVentaVehiculo datosVentaVehiculoPanel;

	private TablePanel<OutputConsultaVehiculoEnVentaDTO> tableView;
	
	private CaracteristicaDeVehiculoPanel caracteristicaVehiculoPanel;
	
	private JButton btnRegistrarVenta;
	
	public static VendedorControlView getInstance() {
		if (instance == null)
			instance = new VendedorControlView();
		return instance;
	}

	private VendedorControlView() {
		setClosable(false);
		setTitle("Vendedor Control View");
		setMaximizable(true);
		setIconifiable(true);
		setBounds(100, 100, 1055, 739);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Venta de vehículos", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		busquedaPanel = new PanelBusquedaPorDniView();
		panel_1.add(busquedaPanel);
		clientePanel = new ClientePanelView();
		panel_1.add(clientePanel);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		btnRegistrarVenta = new JButton("Registrar venta");
		panel_3.add(btnRegistrarVenta);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		busquedaVehiculoPanel = new BusquedaVehiculoPanel();
		panel_2.add(busquedaVehiculoPanel, BorderLayout.NORTH);
		
		datosVentaVehiculoPanel = new DatosVentaVehiculo();
		panel_2.add(datosVentaVehiculoPanel, BorderLayout.SOUTH);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(tabbedPane_1, BorderLayout.CENTER);
		
		tableView = new TablePanel<OutputConsultaVehiculoEnVentaDTO>(new String [] {"Código", "Marca", "Familia", "Linea", "Cilindrada", "Color", "Precio"}) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6912872259496249346L;

			@Override
			public void setData(List<OutputConsultaVehiculoEnVentaDTO> data) {
				for(OutputConsultaVehiculoEnVentaDTO dto : data) {
					Object [] row = { dto.getCodigo(), dto.getMarca(), dto.getFamilia(), dto.getLinea(), dto.getCilindrada(), dto.getColor(), dto.getPrecio() };
					model.addRow(row);	
				}
			}
		};
		
		tabbedPane_1.addTab("Listado de vehículos", null, tableView, null);
		caracteristicaVehiculoPanel = new CaracteristicaDeVehiculoPanel();
		tabbedPane_1.addTab("Caracteristica del Vehículo", null, caracteristicaVehiculoPanel, null);
	}

	public void display() {
		try {
			setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}

	public void close() {
		setVisible(false);
	}

	public void clearData() {
		
	}

	public void setActionConsultarCliente(ActionListener listener) {
		this.busquedaPanel.setActionBuscar(listener);
	}
	
	public String getData() {
		return this.busquedaPanel.getData();
	}

	public void clearDataCliente() {
		this.clientePanel.clearData();
	}

	public void setData(ClienteDTO cliente) {
		this.clientePanel.setData(cliente);
	}

	public void setActionConsultarVehiculo(ActionListener listener) {
		this.busquedaVehiculoPanel.setActionBuscar(listener);
	}

	public ConsultaVehiculoParaVentaDTO getDataConsultaVehiculo() {
		return this.busquedaVehiculoPanel.getData();
	}

	public void clearDataVehiculos() {
		this.tableView.clearData();
	}

	public void setData(List<OutputConsultaVehiculoEnVentaDTO> vehiculos) {
		this.tableView.setData(vehiculos);
	}

	public void addTiposBusqueda(String[] tipos) {
		this.busquedaVehiculoPanel.addTipos(tipos);
	}

	public void addSucursalesBusqueda(String[] suc) {
		this.busquedaVehiculoPanel.addSucursales(suc);
	}
}
