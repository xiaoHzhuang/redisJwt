package com.inspur.mail.service.impl;

import com.inspur.mail.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String mailSubject, String mailContent, String mailSender, String[] mailReceivers) throws Exception {
        sendMail(mailSubject, mailContent, mailSender, null, mailReceivers);
    }

    @Override
    public void sendMail(String mailSubject, String mailContent, String mailSender, String[] mailCopiers, String[] mailReceivers) throws Exception {
        sendMail(mailSubject, mailContent, false, mailSender, mailCopiers, mailReceivers);
    }

    @Override
    public void sendMail(String mailSubject, String mailContent, boolean html, String mailSender, String[] mailCopiers, String[] mailReceivers) throws Exception {
        sendMail(mailSubject, mailContent, html, mailSender, mailCopiers, mailReceivers, null);
    }

    @Override
    public void sendMail(String mailSubject, String mailContent, boolean html, String mailSender, String[] mailCopiers, String[] mailReceivers, List<String> attachPathList) throws Exception {
        try {
            MimeMessage parentMimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(parentMimeMessage, true, "utf-8");
            mimeMessageHelper.setSubject(mailSubject);
            mimeMessageHelper.setFrom(new InternetAddress(mailSender));
            mimeMessageHelper.setSentDate(new Date());
            // 设置收件人地址
            mimeMessageHelper.setTo(mailReceivers);
            // 设置抄送人
            if (!StringUtils.isEmpty(mailCopiers)) {
                mimeMessageHelper.setCc(mailCopiers);
            }
            // 设置正文
            mimeMessageHelper.setText(mailContent, html);
            // 设置附件
            if (!CollectionUtils.isEmpty(attachPathList)) {
                Multipart multipart = new MimeMultipart();
                for (String attachPath : attachPathList) {
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    DataHandler dataHandler = new DataHandler(new FileDataSource(attachPath));
                    mimeBodyPart.setDataHandler(dataHandler);
                    mimeBodyPart.setFileName(dataHandler.getName());
                    multipart.addBodyPart(mimeBodyPart);
                }
                parentMimeMessage.setContent(multipart);
            }
            // 正式发送邮件
            javaMailSender.send(parentMimeMessage);
        } catch (Exception e) {
            log.error("发送邮件失败：{}", e);
            throw e;
        }
    }
}
