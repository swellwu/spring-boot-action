package com.swellwu.service;

/**
 * 邮件服务
 *
 * @author swellwu
 * @create 2017-08-13-22:23
 */
public interface MailService {

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    boolean sendSimpleMail(String to, String subject, String content);

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    boolean sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    boolean sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    boolean sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
