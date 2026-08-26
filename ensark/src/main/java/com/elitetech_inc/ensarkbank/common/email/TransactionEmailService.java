package com.elitetech_inc.ensarkbank.common.email;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionEmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendOtpEmail(String toEmail, String otpCode, BigDecimal amount, String transactionType) {
        log.info("Sending OTP email for {} to {}", transactionType, toEmail);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Your Verification OTP Code - Ensark Bank");

            // `amount` is null for non-monetary OTPs (e.g. beneficiary verification),
            // so it must be handled here — calling null.toPlainString() would throw
            // and (previously, silently) swallow the email send.
            String amountPhrase = amount != null ? amount.toPlainString() + " BDT" : "your request";

            String body = """
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
                <h2 style="margin-top:0;color:#0f172a;">Verification Code</h2>
                <p style="color:#475569;font-size:15px;line-height:24px;">Dear Customer,</p>
                <p style="color:#475569;font-size:15px;line-height:24px;">You are requesting a <strong>%s</strong> verification for <strong>%s</strong>. Please use the following OTP code to continue:</p>
                <div style="text-align:center;margin:35px 0;">
                <div style="background:#0f172a;color:#ffffff;padding:20px 40px;border-radius:10px;font-size:32px;font-weight:700;letter-spacing:8px;display:inline-block;">%s</div>
                </div>
                <div style="background:#fffbeb;border-left:4px solid #f59e0b;padding:15px;border-radius:8px;">
                <p style="margin:0;color:#92400e;font-size:14px;">This code expires in <strong>5 minutes</strong>. Do not share this code with anyone, including bank staff.</p>
                </div>
                <p style="margin-top:25px;color:#64748b;font-size:14px;">If you did not initiate this request, please contact us immediately.</p>
                </td></tr>
                <tr><td style="background:#f8fafc;padding:25px;text-align:center;">
                <p style="font-size:12px;color:#64748b;margin:0;">&copy; 2026 Ensark Bank. All Rights Reserved.</p>
                <p style="font-size:12px;color:#94a3b8;">This is an automated security email.</p>
                </td></tr>
                </table>
                </td></tr></table>
                </body></html>
                """.formatted(transactionType, amountPhrase, otpCode);

            helper.setText(body, true);
            javaMailSender.send(message);
            log.info("OTP email sent to {}", toEmail);
        } catch (Exception e) {
            // Do NOT swallow — a failed OTP email must surface to the caller so the
            // UI does not falsely report "OTP sent" while nothing was delivered.
            log.error("Failed to send OTP email to {}", toEmail, e);
            throw new RuntimeException("Unable to send OTP email to " + toEmail, e);
        }
    }

    @Async
    public void sendTransactionSuccessEmail(String toEmail, AccountTransactionResponse response) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Transaction Successful - Ensark Bank");

            String fullAccount = response.getSenderAccountNumber();
            String maskedAccount = fullAccount.length() > 4
                    ? "****" + fullAccount.substring(fullAccount.length() - 4)
                    : fullAccount;

            var txnResponse = response.getResponse();
            String direction = response.getDirection() != null ? response.getDirection() : "OUT";

            String body = """
                <!DOCTYPE html>
                <html>
                <head><meta charset="UTF-8"></head>
                <body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
                <tr><td align="center">
                <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">
                <tr><td style="background:#0f172a;padding:30px;text-align:center;">
                <h1 style="color:#ffffff;margin:0;font-size:28px;">&#127974; Ensark Bank</h1>
                <p style="color:#cbd5e1;margin-top:8px;">Transaction Receipt</p>
                </td></tr>
                <tr><td style="padding:40px;">
                <h2 style="margin-top:0;color:#16a34a;">Transaction Successful</h2>
                <p style="color:#475569;font-size:15px;line-height:24px;">Dear Customer,</p>
                <p style="color:#475569;font-size:15px;line-height:24px;">Your transaction has been completed successfully. Here are the details:</p>
                <table width="100%%" cellpadding="12" cellspacing="0" style="border:1px solid #e2e8f0;border-radius:8px;margin:20px 0;">
                <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Transaction Type</td><td style="color:#475569;">%s</td></tr>
                <tr><td style="font-weight:600;color:#334155;">Direction</td><td style="color:#475569;">%s</td></tr>
                <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Amount</td><td style="color:#475569;">%s BDT</td></tr>
                <tr><td style="font-weight:600;color:#334155;">Account</td><td style="color:#475569;">%s</td></tr>
                <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Reference No</td><td style="color:#475569;">%s</td></tr>
                <tr><td style="font-weight:600;color:#334155;">Status</td><td style="color:#16a34a;font-weight:600;">%s</td></tr>
                </table>
                <div style="background:#fef2f2;border-left:4px solid #dc2626;padding:15px;border-radius:8px;">
                <p style="margin:0;color:#991b1b;font-size:14px;">If this transaction was not made by you, please contact us immediately.</p>
                </div>
                </td></tr>
                <tr><td style="background:#f8fafc;padding:25px;text-align:center;">
                <p style="font-size:12px;color:#64748b;margin:0;">&copy; 2026 Ensark Bank. All Rights Reserved.</p>
                <p style="font-size:12px;color:#94a3b8;">This is an automated notification email.</p>
                </td></tr>
                </table>
                </td></tr></table>
                </body></html>
                """.formatted(
                    txnResponse.getTransactionType(),
                    direction,
                    txnResponse.getAmount().toPlainString(),
                    maskedAccount,
                    txnResponse.getReferenceNo(),
                    txnResponse.getStatus()
            );

            helper.setText(body, true);
            javaMailSender.send(message);
            log.info("Transaction success email sent to {}", toEmail);
        } catch (Exception e) {
            log.warn("Failed to send transaction success email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendMonthlyStatementEmail(String toEmail, String customerName,
                                           String monthName, int year,
                                           byte[] pdfAttachment, int transactionCount) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Monthly Transaction Statement - " + monthName + " " + year + " - Ensark Bank");

            String body = """
                <!DOCTYPE html>
                <html>
                <head><meta charset="UTF-8"></head>
                <body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
                <tr><td align="center">
                <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">
                <tr><td style="background:#0f172a;padding:30px;text-align:center;">
                <h1 style="color:#ffffff;margin:0;font-size:28px;">&#127974; Ensark Bank</h1>
                <p style="color:#cbd5e1;margin-top:8px;">Monthly Transaction Statement</p>
                </td></tr>
                <tr><td style="padding:40px;">
                <h2 style="margin-top:0;color:#0f172a;">Your %s %d Statement is Ready</h2>
                <p style="color:#475569;font-size:15px;line-height:24px;">Dear %s,</p>
                <p style="color:#475569;font-size:15px;line-height:24px;">Please find attached your transaction statement for <strong>%s %d</strong>. The statement contains <strong>%d transaction(s)</strong>.</p>
                <div style="background:#fffbeb;border-left:4px solid #f59e0b;padding:15px;border-radius:8px;margin:20px 0;">
                <p style="margin:0;color:#92400e;font-size:14px;">This PDF is password-protected. Your password is your date of birth in <strong>dd/MM/yyyy</strong> format.</p>
                </div>
                <p style="margin-top:25px;color:#64748b;font-size:14px;">If you have any questions about your statement, please contact us.</p>
                </td></tr>
                <tr><td style="background:#f8fafc;padding:25px;text-align:center;">
                <p style="font-size:12px;color:#64748b;margin:0;">&copy; 2026 Ensark Bank. All Rights Reserved.</p>
                <p style="font-size:12px;color:#94a3b8;">This is an automated monthly statement email.</p>
                </td></tr>
                </table>
                </td></tr></table>
                </body></html>
                """.formatted(monthName, year, customerName, monthName, year, transactionCount);

            helper.setText(body, true);
            helper.addAttachment("Statement_" + monthName + "_" + year + ".pdf",
                    new org.springframework.core.io.ByteArrayResource(pdfAttachment));

            javaMailSender.send(message);
            log.info("Monthly statement email sent to {}", toEmail);
        } catch (Exception e) {
            log.warn("Failed to send monthly statement email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendCashierTransactionEmail(String toEmail, String customerName,
                                             String transactionType, String amount,
                                             String accountNumber) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(transactionType + " of $" + amount + " - Ensark Bank");

            String body = """
                <!DOCTYPE html>
                <html>
                <head><meta charset="UTF-8"></head>
                <body style="margin:0;padding:0;background:#f1f5f9;font-family:Segoe UI,Arial,sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background:#f1f5f9;padding:40px 0;">
                <tr><td align="center">
                <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">
                <tr><td style="background:#0f172a;padding:30px;text-align:center;">
                <h1 style="color:#ffffff;margin:0;font-size:28px;">&#x1F3E6; Ensark Bank</h1>
                <p style="color:#cbd5e1;margin-top:8px;">Transaction Receipt</p>
                </td></tr>
                <tr><td style="padding:40px;">
                <h2 style="margin-top:0;color:#16a34a;">%s Successful</h2>
                <p style="color:#475569;font-size:15px;line-height:24px;">Dear <strong>%s</strong>,</p>
                <p style="color:#475569;font-size:15px;line-height:24px;">Your %s transaction has been processed successfully.</p>
                <table width="100%%" cellpadding="12" cellspacing="0" style="border:1px solid #e2e8f0;border-radius:8px;margin:20px 0;">
                <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Transaction Type</td><td style="color:#475569;">%s</td></tr>
                <tr><td style="font-weight:600;color:#334155;">Amount</td><td style="color:#475569;">$%s</td></tr>
                <tr style="background:#f8fafc;"><td style="font-weight:600;color:#334155;">Account</td><td style="color:#475569;">%s</td></tr>
                <tr><td style="font-weight:600;color:#334155;">Status</td><td style="color:#16a34a;font-weight:600;">SUCCESS</td></tr>
                </table>
                <div style="background:#fef2f2;border-left:4px solid #dc2626;padding:15px;border-radius:8px;">
                <p style="margin:0;color:#991b1b;font-size:14px;">If this transaction was not made by you, please contact us immediately.</p>
                </div>
                </td></tr>
                <tr><td style="background:#f8fafc;padding:25px;text-align:center;">
                <p style="font-size:12px;color:#64748b;margin:0;">&copy; 2026 Ensark Bank. All Rights Reserved.</p>
                </td></tr>
                </table>
                </td></tr></table>
                </body></html>
                """.formatted(transactionType, customerName, transactionType.toLowerCase(), transactionType, amount, accountNumber);

            helper.setText(body, true);
            javaMailSender.send(message);
            log.info("Cashier transaction email sent to {}", toEmail);
        } catch (Exception e) {
            log.warn("Failed to send cashier transaction email to {}: {}", toEmail, e.getMessage());
        }
    }
}
