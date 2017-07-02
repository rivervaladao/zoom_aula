package dynareport;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class SimpleReportExampleFont {

	public static void main(String[] args) {

		// cod_curso, nom_curso, tot_cred
		FontBuilder defaultFont = stl.font().setFontName("FreeUniversal");

		JasperReportBuilder report = DynamicReports.report();// a new report
		
		

		report.setDefaultFont(defaultFont).setTemplate(Templates.reportTemplate)
				.title(cmp.horizontalList(						
						cmp.text("RELATÓRIO DE PRODUTO").setStyle(stl.style().setFontSize(16))
						.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)),
						cmp.text(new Date())
						)
				.columns(col.column("Estado", "state", DataTypes.stringType()),
						col.column("Item", "item", DataTypes.stringType()),
						col.column("\u2211Total", "unitprice", DataTypes.bigDecimalType()))
				
				.pageFooter(Components.pageXofY())// show page number on the
												// page footer
				.setDataSource(createDataSource());

		try {
			// show the report
			report.show();

			// export the report to a pdf file
			report.toPdf(new FileOutputStream("/tmp/report.pdf"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("state", "item", "unitprice");
		dataSource.add("New York", "Notebook", new BigDecimal(500));
		dataSource.add("New York", "DVD", new BigDecimal(30));
		dataSource.add("New York", "DVD", new BigDecimal(45.6));
		dataSource.add("New York", "DVD", new BigDecimal(36));
		dataSource.add("New York", "DVD", new BigDecimal(41));
		dataSource.add("New York", "Book", new BigDecimal(11));
		dataSource.add("New York", "Book", new BigDecimal(9));
		dataSource.add("New York", "Book", new BigDecimal(14.8));

		dataSource.add("Washington", "Notebook", new BigDecimal(610));
		dataSource.add("Washington", "DVD", new BigDecimal(40));
		dataSource.add("Washington", "DVD", new BigDecimal(35));
		dataSource.add("Washington", "DVD", new BigDecimal(46.4));
		dataSource.add("Washington", "DVD", new BigDecimal(42));
		dataSource.add("Washington", "Book", new BigDecimal(12));
		dataSource.add("Washington", "Book", new BigDecimal(8));
		dataSource.add("Washington", "Book", new BigDecimal(14));
		dataSource.add("Washington", "Book", new BigDecimal(10));

		dataSource.add("Florida", "Notebook", new BigDecimal(460.7));
		dataSource.add("Florida", "DVD", new BigDecimal(49));
		dataSource.add("Florida", "DVD", new BigDecimal(32));
		dataSource.add("Florida", "DVD", new BigDecimal(47));
		dataSource.add("Florida", "Book", new BigDecimal(11));
		dataSource.add("Florida", "Book", new BigDecimal(6.1));
		dataSource.add("Florida", "Book", new BigDecimal(16));
		dataSource.add("Florida", "Book", new BigDecimal(18));
		return dataSource;
	}
}