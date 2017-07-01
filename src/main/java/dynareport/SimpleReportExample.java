package dynareport;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample {

  public static void main(String[] args) {
	Connection connection = null;
	try {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/academico","java", "123456");
	} catch (SQLException e) {
		e.printStackTrace();
		return;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		return;
	}
	
	//cod_curso, nom_curso, tot_cred

	JasperReportBuilder report = DynamicReports.report();//a new report
	report
	  .setTemplate(Templates.reportTemplate)
	  .title(cmp.text("RELATÓRIO DE CURSOS").setStyle(stl.style().setFontSize(18)))
	  .columns(
	      Columns.column("Curso", "cod_curso", DataTypes.integerType()),
	      Columns.column("Nome", "nom_curso", DataTypes.stringType()),
	      Columns.column("Total", "tot_cred", DataTypes.integerType()))
	  .title(//title of the report
	      Components.text("SimpleReportExample")
		  .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT cod_curso, nom_curso, tot_cred FROM cursos",
                                  connection);

	try {
                //show the report
		report.show();

                //export the report to a pdf file
		//report.toPdf(new FileOutputStream("c:/report.pdf"));
	} catch (DRException e) {
		e.printStackTrace();
	/*} catch (FileNotFoundException e) {
		e.printStackTrace();*/
	}
  }
}