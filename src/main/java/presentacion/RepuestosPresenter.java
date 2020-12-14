package presentacion;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import business_logic.RepuestosController;
import dto.taller.RepuestoDTO;
import dto.temporal.AltaRepuestoDTO;
import dto.validators.StringValidator;
import presentacion.views.supervisor.EditorRepuestosDialog;
import presentacion.views.supervisor.NuevosRepuestosFormView;
import presentacion.views.supervisor.PanelGestionRepuestos;
import presentacion.views.utils.InputDialog;
import presentacion.views.utils.MessageDialog;
import services.DatabaseGraphRepuesto;

public class RepuestosPresenter {

	private PanelGestionRepuestos gestionRepuestos;
	private RepuestosController repuestosController;
	private NuevosRepuestosFormView nuevosRepuestosView;
	private DatabaseGraphRepuesto repuestosGraph;
	private static final String All= "Todas";
	private String marca;
	private String descripcion;

	public RepuestosPresenter(RepuestosController controller) {
		this.repuestosController = controller;
		this.nuevosRepuestosView = NuevosRepuestosFormView.getInstance();
		this.gestionRepuestos = PanelGestionRepuestos.getInstance();
		this.gestionRepuestos.setActionOnBuscar(a -> onBuscarRepuesto(a));
		this.gestionRepuestos.setActionOnIngresarStock(a -> onIngresarStock(a));
		this.gestionRepuestos.setActionOnCargarArchivo(a -> onCargarArchivo(a));
		
		this.nuevosRepuestosView.setActionOnValidadCarga(a -> onValidarCarga(a));
		this.nuevosRepuestosView.setActionOnCancelarCarga(a -> onCancelarCarga(a));
		this.gestionRepuestos.setActionOnEditarStock(a -> editarRepuesto(a));
		this.gestionRepuestos.setActionBajoStock(a -> onMostrarRepuestosSinStock(a));
		
		EditorRepuestosDialog.getInstance().setActionOnAceptar(a ->guardarRepuesto(a));
		cargarMarcas();
	}
	
	private void editarRepuesto(ActionEvent a) {
		int id = this.gestionRepuestos.getIdRepuesto();
		if (id >= 0) {
			RepuestoDTO repuesto = repuestosController.readById(id);
			EditorRepuestosDialog.getInstance().setData(repuesto);
			EditorRepuestosDialog.getInstance().display();
		} else {
			new MessageDialog().showMessages("Seleccione un repuesto");
		}
	}
	
	private void guardarRepuesto(ActionEvent a) {
		AltaRepuestoDTO altaRepuesto = EditorRepuestosDialog.getInstance().getData();
		List<String> error = altaRepuesto.validate();
		if (error.isEmpty()) {
			int id = this.gestionRepuestos.getIdRepuesto();
			RepuestoDTO repuesto = new RepuestoDTO(altaRepuesto);
			repuesto.setIdRepuesto(id);
			repuestosController.update(repuesto);
			refrescar();
			EditorRepuestosDialog.getInstance().close();
		} else {
			new MessageDialog().showMessages(error);
		}
	}

	private void onCancelarCarga(ActionEvent a) {
		this.nuevosRepuestosView.cerrar();//cerrar
		this.nuevosRepuestosView.clear();//limpiar la vista tambn
	}
	
	private void onCargarArchivo(ActionEvent a) {
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.YML", "yml");//filtrar archivos
		chooser.setFileFilter(filtro);
		chooser.setAcceptAllFileFilterUsed(false);//quita opcion todos los archivos
		int seleccion = chooser.showOpenDialog(null);
		
		if(seleccion==JFileChooser.APPROVE_OPTION) {
			try {
				LogManager.getLogger(this.getClass()).log(Level.INFO,"Seed database operation status: [INITIALIZED - LOADING .YML]");
				Yaml yaml = new Yaml(new Constructor(DatabaseGraphRepuesto.class));
				InputStream inputStream = new FileInputStream(chooser.getSelectedFile().getAbsolutePath());//FileInputStream cambio
				repuestosGraph = yaml.load(inputStream);
				
				if(validarRepuestos(repuestosGraph.getRepuestos())) {
					this.nuevosRepuestosView.cargarTabla(repuestosGraph.getRepuestos());
					this.nuevosRepuestosView.mostrar();
				}else {
					new MessageDialog().showMessages("El archivo contiene datos erroneos, por favor revisar el contenido del archivo\nEjemplo de formato recomendado:\nrepuestos:\n   - codigoRepuesto: 1122\r\n" + 
							"      precioRepuesto: 3000\r\n" + 
							"      marcaRepuesto: Renault\r\n" + 
							"      descripcionRepuesto: Volante\r\n" + 
							"      stockRepuesto: 20\r\n" + 
							"      fabricante: Autopartes Argentinas\r\n" + 
							"      stockMinimo: 13");
					}
			} catch (Exception e) {
				LogManager.getLogger(this.getClass()).log(Level.ERROR,"Seed database operation status: [ABORT - ERROR LOADING .YML, " + e.getMessage() + "]");
				new MessageDialog().showMessages("El archivo esta vacío, por favor revisar el contenido del archivo\nEjemplo de formato recomendado:\nrepuestos:\n   - codigoRepuesto: 1122\r\n" + 
						"      precioRepuesto: 3000\r\n" + 
						"      marcaRepuesto: Renault\r\n" + 
						"      descripcionRepuesto: Volante\r\n" + 
						"      stockRepuesto: 20\r\n" + 
						"      fabricante: Autopartes Argentinas\r\n" + 
						"      stockMinimo: 13");
			}
		}
	}

	private boolean validarRepuestos(List<RepuestoDTO> repuestos) {
		boolean flag=true;
		for (RepuestoDTO repuesto : repuestos) {
			if(repuesto.getCodigoRepuesto()==null || repuesto.getPrecioRepuesto()==null || repuesto.getMarcaRepuesto()==null ||
					repuesto.getDescripcionRepuesto()==null || repuesto.getStockRepuesto()==null || repuesto.getFabricante()==null || repuesto.getStockMinimo()==null) {
				flag=false;
			}
		}
		return flag;
	}

	private void onValidarCarga(ActionEvent a) {
		int j=0;
		List<Integer> idRepuestosNoAceptados = this.nuevosRepuestosView.getIdRepuestosNoAceptados();//obtengo id de repuestos que estoy leyendo que no fueron aceptados para cargar
		int codigo;
		List<RepuestoDTO> repuestosCargados = this.repuestosGraph.getRepuestos();//lista con todos los repuestos del archivo
		ListIterator<RepuestoDTO> it = repuestosCargados.listIterator();
		RepuestoDTO repuestoAuxiliar;
		
		while(it.hasNext()&&j<idRepuestosNoAceptados.size() ) {//quito los que selecciono
			codigo=it.next().getCodigoRepuesto();
			if(codigo==idRepuestosNoAceptados.get(j)) {
				j++;
				it.remove();
			}
		}
		
		it = repuestosCargados.listIterator();
		while(it.hasNext()) {//busco los que ya existen y actualizo la cantidad de repuestos
			repuestoAuxiliar=it.next();			
			if(repuestosController.readByCodigo(repuestoAuxiliar.getCodigoRepuesto()) != null) {
				repuestoAuxiliar.setStockRepuesto(repuestoAuxiliar.getStockRepuesto()+repuestosController.readByCodigo(repuestoAuxiliar.getCodigoRepuesto()).getStockRepuesto());
			}
		}
		
		it = repuestosCargados.listIterator();
		while(it.hasNext()) {//inserto en la base de datos.
			repuestoAuxiliar=it.next();
			repuestoAuxiliar.setDescripcionRepuesto(repuestoAuxiliar.getDescripcionRepuesto().toLowerCase());
			repuestosController.saveByCodigo(repuestoAuxiliar);
		}
		
		this.nuevosRepuestosView.cerrar();//cerrar
		this.nuevosRepuestosView.clear();//limpiar la vista tambn
		cargarMarcas();//actualizo combobox
		List<RepuestoDTO> repuestos = repuestosController.readAll();
		this.gestionRepuestos.setData(repuestos);//actualizo tabla repuestos
	}
	
	private void onIngresarStock(ActionEvent a) {
		int id = this.gestionRepuestos.getIdRepuesto();
		if (id >= 0) {
			String stock = new InputDialog().title("Agregar Stock").setLabel("Cantidad").open();
			if (stock != null) {
				List<String> error = new StringValidator(stock).notBlank("Debe ingresar un valor")
						.number("El valor debe ser numérico").validate();
				if (error.isEmpty()) {
					int cantidad = Integer.parseInt(stock);
					RepuestoDTO repuesto = repuestosController.readById(id);
					repuesto.setStockRepuesto(repuesto.getStockRepuesto() + cantidad);
					repuestosController.update(repuesto);
					refrescar();
				} else {
					new MessageDialog().showMessages(error);
				}
			}
		} else {
			new MessageDialog().showMessages("Seleccione un repuesto");
		}
	}

	private void refrescar() {
		List<RepuestoDTO> repuestos;
		if (descripcion.isEmpty()) {
			if (marca == All) {
				repuestos = repuestosController.readAll();
			} else {
				repuestos = repuestosController.readByMarca(marca);
			}
		} else {
			if (marca == All) {
				repuestos = repuestosController.readByDescripcion(descripcion);
			} else {
				repuestos = repuestosController.readbyMarcaYDescripcion(marca, descripcion);
			}
		}
		gestionRepuestos.setData(repuestos);
	}

	public void cargarMarcas() {
		List<String> marcas = new ArrayList<String>();
		marcas.add(All);
		marcas.addAll(repuestosController.readMarcas());
		this.gestionRepuestos.setDataMarcas(marcas);
	}

	// Busca repuestos segun criterio seleccionado
	private void onBuscarRepuesto(ActionEvent a) {
		marca = gestionRepuestos.getMarca();
		descripcion = gestionRepuestos.getDescripcion();
		refrescar();
	}
	
	private void onMostrarRepuestosSinStock(ActionEvent a) {
		marca = All;
		descripcion = "";
		gestionRepuestos.resetBuscador();
		gestionRepuestos.setData(repuestosController.readRepuestosSinStock());
	}
}
