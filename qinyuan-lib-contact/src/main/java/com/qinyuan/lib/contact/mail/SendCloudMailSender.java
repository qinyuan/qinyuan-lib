package com.qinyuan.lib.contact.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * send email by sendcloud
 * Created by qinyuan on 15-10-25.
 */
public class SendCloudMailSender implements MailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(SendCloudMailSender.class);
    private final static String url = "http://sendcloud.sohu.com/webapi/mail.send.json";
    private final String user;
    private final String apiKey;
    private final String domainName;

    public SendCloudMailSender(String user, String domainName, String apiKey) {
        this.user = user;
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void send(String recipient, String subject, Object content) {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("api_user", user));
        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("to", recipient));
        params.add(new BasicNameValuePair("from", user + "@" + domainName));
        params.add(new BasicNameValuePair("fromname", user));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", content.toString()));
        params.add(new BasicNameValuePair("resp_email_id", "true"));

        try {
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = HttpClientBuilder.create().build().execute(post);

            // response
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                @SuppressWarnings("unchecked")
                Map<String, Object> map = new ObjectMapper().readValue(result, Map.class);
                if (!"success".equals(map.get("message"))) {
                    @SuppressWarnings("unchecked")
                    List errors = (List) map.get("errors");
                    throw new Exception(errors.get(0).toString());
                }
            } else {
                throw new Exception("http request error");
            }
            post.releaseConnection();
        } catch (Exception e) {
            LOGGER.error("fail to send sendcloud email, info: {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(List<String> recipients, String subject, Object content) {
        send(Joiner.on(";").join(recipients), subject, content);
    }
}
