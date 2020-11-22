
package presentacion.views.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteViewImpl {

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	private Logger log = Logger.getLogger(ReporteViewImpl.class);
	private String file;

	public ReporteViewImpl(String file) {
		this.file = file;
	}

	public <T> void setData(List<T> dto) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		try {
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reportes" + File.separator + file);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(dto));
			log.info("Se cargó correctamente el reporte");
		} catch (JRException ex) {
			log.error("Ocurrió un error mientras se cargaba el archivo ProyectoReporteAgenda.Jasper", ex);
		}
	}

	public void open() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}
}