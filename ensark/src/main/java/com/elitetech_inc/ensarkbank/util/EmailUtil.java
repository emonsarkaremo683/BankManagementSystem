package com.elitetech_inc.ensarkbank.util;

import com.elitetech_inc.ensarkbank.auth_management.auth.security.EmailConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtil {

    private final EmailConfig emailConfig;

    public void sendAccountStatusEmail(String toEmail, String customerName, String accountNumber, String status) {
        try {
            String subject = "Account " + status + " - Ensark Bank";
            String body = buildStatusEmail(customerName, "Account", accountNumber, status);
            emailConfig.sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            log.error("Failed to send account status email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendCardStatusEmail(String toEmail, String customerName, String cardNumber, String status) {
        try {
            String subject = "Card " + status + " - Ensark Bank";
            String body = buildStatusEmail(customerName, "Card", cardNumber, status);
            emailConfig.sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            log.error("Failed to send card status email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendLoanStatusEmail(String toEmail, String customerName, String loanId, String status) {
        try {
            String subject = "Loan " + status + " - Ensark Bank";
            String body = buildStatusEmail(customerName, "Loan", loanId, status);
            emailConfig.sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            log.error("Failed to send loan status email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendKycStatusEmail(String toEmail, String customerName, String status) {
        try {
            String subject = "KYC Verification " + status + " - Ensark Bank";
            String body = buildKycStatusEmail(customerName, status);
            emailConfig.sendEmail(toEmail, subject, body);
        } catch (Exception e) {
            log.error("Failed to send KYC status email to {}: {}", toEmail, e.getMessage());
        }
    }

    private String buildStatusEmail(String customerName, String entityType, String entityId, String status) {
        String color = status.equalsIgnoreCase("ACTIVE") || status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("VERIFIED") ? "#16a34a" : "#dc2626";
        return """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
            <tr><td align="center">
            <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">
            <tr><td style="background:#0f172a;padding:30px;text-align:center;">
            <h1 style="color:#ffffff;margin:0;font-size:28px;">&#127974; Ensark Bank</h1>
            <p style="color:#cbd5e1;margin-top:8px;">Secure Digital Banking</p>
            </td></tr>
            <tr><td style="padding:40px;">
            <h2 style="margin-top:0;color:%s;">%s %s</h2>
            <p style="color:#475569;font-size:15px;line-height:24px;">Dear <strong>%s</strong>,</p>
            <p style="color:#475569;font-size:15px;line-height:24px;">Your %s has been <strong>%s</strong>.</p>
            <table width="100%%" cellpadding="12" cellspacing="0" style="border:1px solid #e2e8f0;border-radius:8px;margin:20px 0;">
            <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Type</td><td style="color:#475569;">%s</td></tr>
            <tr><td style="font-weight:600;color:#334155;">Reference</td><td style="color:#475569;">%s</td></tr>
            <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Status</td><td style="color:%s;font-weight:600;">%s</td></tr>
            </table>
            <p style="margin-top:25px;color:#64748b;font-size:14px;">If you have any questions, please contact us.</p>
            </td></tr>
            <tr><td style="background:#f8fafc;padding:25px;text-align:center;">
            <p style="font-size:12px;color:#64748b;margin:0;">&copy; 2026 Ensark Bank. All Rights Reserved.</p>
            <p style="font-size:12px;color:#94a3b8;">This is an automated notification email.</p>
            </td></tr>
            </table>
            </td></tr></table>
            </body></html>
            """.formatted(color, entityType + " " + status, "", customerName, entityType, status.toLowerCase(), entityType, entityId, color, status);
    }

    private String buildKycStatusEmail(String customerName, String status) {
        String color = status.equalsIgnoreCase("VERIFIED") ? "#16a34a" : "#dc2626";
        return """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
            <tr><td align="center">
            <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">
            <tr><td style="background:#0f172a;padding:30px;text-align:center;">
            <h1 style="color:#ffffff;margin:0;font-size:28px;">&#127974; Ensark Bank</h1>
            <p style="color:#cbd5e1;margin-top:8px;">Secure Digital Banking</p>
            </td></tr>
            <tr><td style="padding:40px;">
            <h2 style="margin-top:0;color:%s;">KYC Verification %s</h2>
            <p style="color:#475569;font-size:15px;line-height:24px;">Dear <strong>%s</strong>,</p>
            <p style="color:#475569;font-size:15px;line-height:24px;">Your KYC documents have been <strong>%s</strong>.</p>
            <div style="background:%s;border-left:4px solid %s;padding:15px;border-radius:8px;margin:20px 0;">
            <p style="margin:0;color:#ffffff;font-size:14px;">Status: <strong>%s</strong></p>
            </div>
            <p style="margin-top:25px;color:#64748b;font-size:14px;">If you have any questions, please contact us.</p>
            </td></tr>
            <tr><td style="background:#f8fafc;padding:25px;text-align:center;">
            <p style="font-size:12px;color:#64748b;margin:0;">&copy; 2026 Ensark Bank. All Rights Reserved.</p>
            <p style="font-size:12px;color:#94a3b8;">This is an automated notification email.</p>
            </td></tr>
            </table>
            </td></tr></table>
            </body></html>
            """.formatted(color, status, customerName, status.toLowerCase(), color, color, status);
    }
}
