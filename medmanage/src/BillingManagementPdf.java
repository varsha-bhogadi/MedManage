import java.io.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class BillingManagementPdf {

    // Get the current date
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public String formattedDate = currentDate.format(formatter);


    public BillingManagementPdf(){

        String name = BillingManagement.patientName;
        String gender = BillingManagement.patientDateOfBirth;
        String contact = BillingManagement.patientContactNumber;
        String mode = BillingManagement.mode;
        String amount = BillingManagement.amountTextField.getText();
        String date = formattedDate;
        String paymentId = BillingManagement.paymentId;

        
        String pdfFolderPath = new File("PaymentReciepts").getAbsolutePath();
        new File(pdfFolderPath).mkdirs();
        String pdfFileName = pdfFolderPath + File.separator + paymentId + ".pdf";

        // Multiline HTML content as a single string
        String htmlContent = 
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<style>" +
            "body {" +
            "    font-family: Arial, sans-serif;" +
            "    margin: 0;" +
            "    padding: 0;" +
            "    text-align: center;" +
            "}" +
            ".container {" +
            "    margin: 50px;" +
            "}" +
            ".header {" +
            "    font-size: 36px;" + 
            "    font-weight: bold;" +
            "    margin-bottom: 20px;" + 
            "}" +
            ".underline {" +
            "    border-bottom: 2px solid black;" +
            "    margin-bottom: 30px;" +
            "}" +
            ".details {" +
            "    text-align: left;" +
            "    margin-bottom: 30px;" + 
            "}" +
            ".details p {" +
            "    margin: 15px 0;" + 
            "}" +
            ".table-container {" +
            "    width: 100%;" +
            "    text-align: left;" +
            "    margin-bottom: 30px;" + 
            "}" +
            "table {" +
            "    width: 100%;" +
            "    border-collapse: collapse;" +
            "}" +
            "th, td {" +
            "    border: 1px solid black;" +
            "    padding: 8px;" +
            "    text-align: left;" +
            "}" +
            ".footer {" +
            "    text-align: left;" +
            "}" +
            ".footer p {" +
            "    margin: 15px 0;" + 
            "}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class=\"container\">" +
            "<div class=\"header\">" +
            "Med Manage" +
            "</div>" +
            "<div class=\"underline\"></div>" +
            "<div class=\"details\">" +
            "<p>Name: " + name + "</p>" +
            "<p>DOB: " + gender + "</p>" +
            "<p>Contact: " + contact + "</p>" +
            "</div>" +
            "<div class=\"table-container\">" +
            "<table>" +
            "<thead>" +
            "<tr>" +
            "<th>S.No</th>" +
            "<th>Particulars</th>" +
            "<th>Amount</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>" +
            "<tr>" +
            "<td>1</td>" +
            "<td>Total Amount</td>" +
            "<td>" + amount + "</td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "</div>" +
            "<div class=\"footer\">" +
            "<p>Date: " + date + "</p>" +
            "<p>Payment ID: " + paymentId + "</p>" +
            "<p>Mode: " + mode + "</p>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>";
        
        Document document = new Document(PageSize.A4);
        try{
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));

            document.open();

          
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(htmlContent));

            document.close();

            File pdfFile = new File(pdfFileName);

            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Desktop is not supported. Cannot open the file automatically.");
                }
            } else {
                System.out.println("The file does not exist.");
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new BillingManagementPdf();
    }
}
