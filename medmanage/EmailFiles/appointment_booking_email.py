import sys
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

# Get doctor details
doctor_name = sys.argv[1]
to_email = sys.argv[2]
appointment_date = sys.argv[3]
doctor_name = sys.argv[4]
patient_name = sys.argv[5]

# User credentials
service_email = 'medmanage.service@gmail.com'
service_email_pass = 'pncz oskh jffd ttny'

# Send confirmation email
def sendConfirmationEmail(to_email):
    msg = MIMEMultipart()
    msg['From'] = service_email
    msg['To'] = to_email
    msg['Subject'] = 'MedManage Appointment Booking Successful'

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

            .imgback {{
                width: 100%;
                height: auto;
                position: relative;
                background-color: white;
                display: flex;
                justify-content: center;
                align-items: center;
                border-top-left-radius: 20px;
                border-top-right-radius: 20px;
                overflow: hidden;
                padding: 20px;
            }}

            .imgback h1 {{
                position: absolute;
                color: darkblue;
                font-size: 24px;
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
                z-index: 2;
                text-align: center;
                line-height: 1.2;
                padding: 0 10px;
                box-sizing: border-box;
            }}

            .content {{
                padding: 20px;
                box-sizing: border-box;
            }}

            .head1, .head2, .head3, .head4, .head5, .head6, .head7, .head8, .head9, .head10, table {{
                color: #000;
            }}

            .head1 {{
                font-size: 22px;
                font-weight: 400;
                margin-bottom: 1.5%;
            }}

            .head2, .head5, .head7, .head8, .head9, .head10 {{
                font-size: 16px;
                margin-bottom: 1.5%;
            }}

            .head3, .head4, .head6 {{
                font-size: 18px;
                margin-bottom: 1.5%;
                font-weight: 600;
            }}

            .head3 {{
                color: rgba(60, 124, 0, 0.669);
            }}

            .head4 {{
                color: rgb(255, 211, 37);
            }}

            .head6 {{
                color: rgb(3, 154, 200);
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

            .bullet_points {{
                padding-left: 20px;
            }}

            .bullet_points li {{
                margin-bottom: 10px;
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

                .imgback h1 {{
                    font-size: 20px;
                }}

                .head1 {{
                    font-size: 18px;
                }}

                .head2, .head5, .head7, .head8, .head9, .head10 {{
                    font-size: 14px;
                }}

                .head3, .head4, .head6 {{
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

                .imgback h1 {{
                    font-size: 18px;
                }}

                .head1 {{
                    font-size: 16px;
                }}

                .head2, .head5, .head7, .head8, .head9, .head10 {{
                    font-size: 12px;
                }}

                .head3, .head4, .head6 {{
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
                <div class="head2">Thank you for choosing MedManage Service for your healthcare needs. We are pleased to confirm your appointment as per the details below:</div>
                <div class="line"></div>
                <div class="head3">Appointment Details :</div>
                <div class="bold">
                    <table>
                        <tr>
                            <td>Patient Name:</td>
                            <td>{patient_name}</td>
                        </tr>
                        <tr>
                            <td>Appointment Date:</td>
                            <td>{appointment_date}</td>
                        </tr>
                        <tr>
                            <td>Doctor Name:</td>
                            <td>{doctor_name}</td>
                        </tr>
                        <tr>
                            <td>Location:</td>
                            <td>MedManage Hospital, {'hospital_address'}</td>
                        </tr>
                    </table> 
                </div>   
                <div class="line"></div>
                <div class="head4">Additional Information:</div>
                <div class="head5">
                    <ul class="bullet_points">
                        <li>Please arrive at least 15 minutes before your scheduled appointment time.</li>
                        <li>Bring your identification documents and any relevant medical records.</li>
                        <li>If you need to reschedule or cancel your appointment, kindly contact us at least 24 hours in advance.</li>
                    </ul>
                </div>
                <div class="line"></div>
                <div class="head6">Contact Us:</div>
                <div class="head7">If you have any questions or need further assistance, please feel free to contact us:</div>
                <div class="head8">
                    Phone: currently - unavailable<br>
                    Email: <a href="mailto:medmanage.service@gmail.com">medmanage.service@gmail.com</a><br>
                    We look forward to serving you and ensuring you receive the best possible care.
                </div>
                <div class="head9">Best regards,</div>
                <div class="head10">
                    MedManage Team<br>
                   <br>
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

# Call the function to send the email
sendConfirmationEmail(to_email)
