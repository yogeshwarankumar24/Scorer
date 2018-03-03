package com.scorer.ModalClass;

import java.util.List;

/**
 * Created by impowersolutionspvtltd on 31/05/17.
 */

public class MailModel {

    public List<String> getSendmailto() {
        return Sendmailto;
    }

    public void setSendmailto(List<String> sendmailto) {
        Sendmailto = sendmailto;
    }

    public String getMailSubject() {
        return MailSubject;
    }

    public void setMailSubject(String mailSubject) {
        MailSubject = mailSubject;
    }

    public String getMailBody() {
        return MailBody;
    }

    public void setMailBody(String mailBody) {
        MailBody = mailBody;
    }

    public String getMailAttachment() {
        return MailAttachment;
    }

    public void setMailAttachment(String mailAttachment) {
        MailAttachment = mailAttachment;
    }

    private List<String> Sendmailto;
    private String MailSubject;
    private String MailBody;
    private String MailAttachment;

    public MailModel() {
    }

    public MailModel(List<String> Sendmailto,String MailSubject, String MailBody, String MailAttachment) {
        this.Sendmailto = Sendmailto;
        this.MailSubject = MailSubject;
        this.MailBody = MailBody;
        this.MailAttachment = MailAttachment;
    }
    public MailModel(MailModel objmodel) {
        this.Sendmailto = objmodel.getSendmailto();
        this.MailSubject = objmodel.getMailSubject();
        this.MailBody = objmodel.getMailBody();
        this.MailAttachment = objmodel.getMailAttachment();
    }
}
