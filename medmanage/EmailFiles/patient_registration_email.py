import sys
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

# Get patient details
patient_name = sys.argv[1]
contact_number = sys.argv[2]
to_email = sys.argv[3]
registration_date = sys.argv[4]
patient_id = sys.argv[5]

# User credentials
service_email = 'medmanage.service@gmail.com'
service_email_pass = 'pncz oskh jffd ttny'

# Send confirmation email
def sendConfirmationEmail(to_email):
    msg = MIMEMultipart()
    msg['From'] = service_email
    msg['To'] = to_email
    msg['Subject'] = 'MedManage Patient Registration Successful'

    body = f"""
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body {{
                    background-color: #858a8d;
                    margin: 0;
                    padding: 0;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                }}
                .mid_background {{
                    width: 90%;
                    max-width: 700px;
                    margin: 0 auto;
                    background-color: snow;
                    border-radius: 20px;
                    overflow: hidden;
                }}
                .imgback img {{
                    max-width: 100%;
                    height: auto;
                }}
                .content {{
                    padding: 20px;
                    box-sizing: border-box;
                }}
                .head1, .head2, .head3, .head4, .head5, .head6, .head7, .head8, .head9, .head10,.imp, .note, table {{
                    color: #000;
                }}
                .head1 {{
                    font-size: 22px;
                    font-weight: 400;
                    margin-bottom: 1.5%;
                }}
                .head2 {{
                    font-size: 16px;
                    margin-bottom: 1.5%;
                }}
                .imp {{
                    font-size: 16px;
                    margin-bottom: 1.5%;
                }}
                .head3 {{
                    font-size: 18px;
                    margin-bottom: 1.5%;
                    font-weight: 600;
                    color: rgba(60, 124, 0, 0.669);
                }}
                .head6 {{
                color: rgb(3, 154, 200);
                font-size: 16px;
                }}
                .note {{
                color: rgb(3, 154, 200);
                font-size: 16px;
                }}
                .line {{
                    width: 100%;
                    height: 1px;
                    background-color: #ccc;
                    margin-bottom: 0.5%;
                }}
                .bold {{
                    font-size: 16px;
                    font-weight: bold;
                }}
                @media screen and (max-width: 1024px) {{
                    .mid_background {{
                        width: 80%;
                    }}
                }}
                @media screen and (max-width: 768px) {{
                    .mid_background {{
                        width: 90%;
                    }}
                    .head1 {{
                        font-size: 20px;
                    }}
                    .head2 {{
                        font-size: 14px;
                    }}
                    .imp {{
                        font-size: 14px;
                    }}
                    .head3 {{
                        font-size: 16px;
                    }}
                    table td {{
                        font-size: 14px;
                    }}
                }}
                @media screen and (max-width: 480px) {{
                    .mid_background {{
                        width: 95%;
                    }}
                    .head1 {{
                        font-size: 16px;
                    }}
                    .head2 {{
                        font-size: 12px;
                    }}
                    .imp {{
                        font-size: 12px;
                    }}
                    .head3 {{
                        font-size: 14px;
                    }}
                    table td {{
                        font-size: 12px;
                    }}
                    .content {{
                        padding: 15px;
                    }}
                }}
            </style>
        </head>
        <body>
            <div class="mid_background">
                <div class="content">
                    <div class="head1">Dear {patient_name},</div>
                    <div class="head2">We are pleased to inform you that your registration with MedManage Service has been successfully completed. Below are the details we have recorded:</div>
                    <div class="line"></div>
                    <div class="head3">Patient Registration Details :</div>
                    <div class="bold">
                        <table>
                            <tr>
                                <td>Patient ID:</td>
                                <td>{patient_id}</td>
                            </tr>
                            <tr>
                                <td>Patient Name:</td>
                                <td>{patient_name}</td>
                            </tr>
                            <tr>
                                <td>Registration Date:</td>
                                <td>{registration_date}</td>
                            </tr>
                            <tr>
                                <td>Contact Number:</td>
                                <td>{contact_number}</td>
                            </tr>
                        </table> 
                    </div>   
                    <div class="line"></div>
                    <div class="note"> Note: </div>
                    <div class="imp">Please remember the patient's ID as it is essential for future reference.</div>
                    <div class="line"></div>
                    <div class="head6">Contact Us:</div>
                    <div class="head2">If you have any questions or need further assistance, <br> please feel free to contact us:</div>
                    <div class="head2">
                        Phone: currently unavailable<br>
                        Email: <a href="mailto:medmanage.service@gmail.com">medmanage.service@gmail.com</a><br>
                        We look forward to serving you and ensuring you receive the best possible care.
                    </div>
                    <div class="head2">Best regards,</div>
                    <div class="head2">
                        MedManage Team
                    </div>
                </div>
            </div>
        </body>
        </html>
        """
    msg.attach(MIMEText(body, 'html'))
    with smtplib.SMTP('smtp.gmail.com', 587) as server:
        server.starttls()
        server.login(service_email, service_email_pass)
        server.sendmail(service_email, to_email, msg.as_string())
sendConfirmationEmail(to_email)