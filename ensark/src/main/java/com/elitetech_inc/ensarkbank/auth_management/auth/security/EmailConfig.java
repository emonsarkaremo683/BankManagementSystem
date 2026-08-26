package com.elitetech_inc.ensarkbank.auth_management.auth.security;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConfig {

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            log.warn("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    // ── Email verification ───────────────────────────────────────
    public void sendVerificationEmail(String to, String name, String token) {

        String link = frontendUrl + "/verify-email?token=" + token;

        String body = """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Email Verification</title>
</head>
<body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">

<table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
<tr>
<td align="center">

<table width="600" cellpadding="0" cellspacing="0"
       style="background:#ffffff;border-radius:16px;overflow:hidden;
       box-shadow:0 4px 20px rgba(0,0,0,0.08);">

<tr>
<td style="background:#0f172a;padding:30px;text-align:center;">
<h1 style="color:#ffffff;margin:0;font-size:28px;">
🏦 Your Bank Name
</h1>
<p style="color:#cbd5e1;margin-top:8px;">
Secure Digital Banking
</p>
</td>
</tr>

<tr>
<td style="padding:40px;">

<h2 style="margin-top:0;color:#0f172a;">
Verify Your Email Address
</h2>

<p style="color:#475569;font-size:15px;line-height:24px;">
Dear <strong>%s</strong>,
</p>

<p style="color:#475569;font-size:15px;line-height:24px;">
Thank you for creating your banking account.
To activate online banking services, please verify your email address.
</p>

<div style="text-align:center;margin:35px 0;">
<a href="%s"
   style="
      background:#2563eb;
      color:#ffffff;
      padding:14px 35px;
      border-radius:10px;
      text-decoration:none;
      font-weight:600;
      display:inline-block;
      font-size:16px;">
Verify Email
</a>
</div>

<div style="
background:#eff6ff;
border-left:4px solid #2563eb;
padding:15px;
border-radius:8px;">
<p style="margin:0;color:#1e3a8a;font-size:14px;">
🔒 This verification link will expire in <strong>1 hour</strong>.
</p>
</div>

<p style="margin-top:25px;color:#64748b;font-size:14px;">
If you did not create this account, please ignore this email.
No action is required.
</p>

</td>
</tr>

<tr>
<td style="background:#f8fafc;padding:25px;text-align:center;">
<p style="font-size:12px;color:#64748b;margin:0;">
© 2026 Your Bank Name. All Rights Reserved.
</p>
<p style="font-size:12px;color:#94a3b8;">
This is an automated security email.
</p>
</td>
</tr>

</table>

</td>
</tr>
</table>

</body>
</html>
""".formatted(name, link);

        sendEmail(to, "Verify your RNRBank account", body);
    }


    // ── Password reset ────────────────────────────────────────────
    public void sendPasswordResetEmail(String to, String name, String token) {

        String link = frontendUrl + "/reset-password?token=" + token;

        String body = """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Password Reset</title>
</head>
<body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">

<table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
<tr>
<td align="center">

<table width="600" cellpadding="0" cellspacing="0"
       style="background:#ffffff;border-radius:16px;
       overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">

<tr>
<td style="background:#991b1b;padding:30px;text-align:center;">
<h1 style="color:#ffffff;margin:0;">
🔐 Password Reset Request
</h1>
</td>
</tr>

<tr>
<td style="padding:40px;">

<h2 style="color:#0f172a;">
Reset Your Password
</h2>

<p style="color:#475569;font-size:15px;line-height:24px;">
Hello <strong>%s</strong>,
</p>

<p style="color:#475569;font-size:15px;line-height:24px;">
We received a request to reset your online banking password.
Click the button below to continue.
</p>

<div style="text-align:center;margin:35px 0;">
<a href="%s"
   style="
      background:#dc2626;
      color:#ffffff;
      padding:14px 35px;
      border-radius:10px;
      text-decoration:none;
      font-weight:600;
      display:inline-block;
      font-size:16px;">
Reset Password
</a>
</div>

<div style="
background:#fef2f2;
border-left:4px solid #dc2626;
padding:15px;
border-radius:8px;">
<p style="margin:0;color:#991b1b;font-size:14px;">
⚠ This password reset link expires in <strong>15 minutes</strong>.
</p>
</div>

<div style="
margin-top:20px;
background:#f8fafc;
padding:15px;
border-radius:8px;">
<p style="margin:0;font-size:14px;color:#334155;">
Security Tips:
</p>
<ul style="color:#64748b;font-size:13px;line-height:22px;">
<li>Never share your password.</li>
<li>Never share OTP codes.</li>
<li>Bank employees will never ask for credentials.</li>
</ul>
</div>

<p style="margin-top:20px;color:#64748b;font-size:14px;">
If you did not request a password reset,
please ignore this email and contact support immediately if needed.
</p>

</td>
</tr>

<tr>
<td style="background:#f8fafc;padding:25px;text-align:center;">
<p style="font-size:12px;color:#64748b;margin:0;">
© 2026 Your Bank Name. All Rights Reserved.
</p>
<p style="font-size:12px;color:#94a3b8;">
This is an automated security email.
</p>
</td>
</tr>

</table>

</td>
</tr>
</table>

</body>
</html>
""".formatted(name, link);

        sendEmail(to, "Reset your RNRBank password", body);
    }

}
