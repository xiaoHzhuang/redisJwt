package com.inspur.mail.service;

import java.util.List;

public interface IMailService {
    /**
     * 发送邮件
     *
     * @param mailSubject   主题
     * @param mailContent   内容
     * @param mailSender    发件人
     * @param mailReceivers 收件人[多个]
     */
    void sendMail(String mailSubject, String mailContent, String mailSender, String[] mailReceivers) throws Exception;

    /**
     * 发送邮件
     *
     * @param mailSubject   主题
     * @param mailContent   内容
     * @param mailSender    发件人
     * @param mailCopiers   抄送人[多个]
     * @param mailReceivers 收件人[多个]
     */
    void sendMail(String mailSubject, String mailContent, String mailSender, String[] mailCopiers, String[] mailReceivers) throws Exception;

    /**
     * 发送邮件
     *
     * @param mailSubject   主题
     * @param mailContent   内容
     * @param html          是否为html格式
     * @param mailSender    发件人
     * @param mailCopiers   抄送人[多个]
     * @param mailReceivers 收件人[多个]
     */
    void sendMail(String mailSubject, String mailContent, boolean html, String mailSender, String[] mailCopiers, String[] mailReceivers) throws Exception;

    /**
     * 发送邮件
     *
     * @param mailSubject    主题
     * @param mailContent    内容
     * @param html           是否为html格式
     * @param mailSender     发件人
     * @param mailCopiers    抄送人[多个]
     * @param mailReceivers  收件人[多个]
     * @param attachPathList 附件路径
     */
    void sendMail(String mailSubject, String mailContent, boolean html, String mailSender, String[] mailCopiers, String[] mailReceivers, List<String> attachPathList) throws Exception;

}
