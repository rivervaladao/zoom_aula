package dynareport;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample {

	public static void main(String[] args) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/academico", "java", "123456");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// cod_curso, nom_curso, tot_cred
		FontBuilder defaultFont = stl.font().setFontName("FreeUniversal");

		JasperReportBuilder report = DynamicReports.report();// a new report
		
		

		report.setDefaultFont(defaultFont).setTemplate(Templates.reportTemplate)
				.title(cmp.horizontalList(						
						cmp.text("RELATÓRIO DE CURSOS").setStyle(stl.style().setFontSize(16))
						.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)),
						cmp.text(new Date())
						)
				.columns(col.column("Curso", "cod_curso", DataTypes.integerType()),
						col.column("Nome", "nom_curso", DataTypes.stringType()),
						col.column("\u2211Total", "tot_cred", DataTypes.integerType())
						.setStyle(stl.style(stl.font().setFontName("DejaVu Sans"))))
				
				.pageFooter(Components.pageXofY())// show page number on the
												// page footer
				.setDataSource("SELECT cod_curso, nom_curso, tot_cred FROM cursos", connection);

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
}