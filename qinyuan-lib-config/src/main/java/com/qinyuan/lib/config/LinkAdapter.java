package com.qinyuan.lib.config;

import org.apache.commons.lang3.StringUtils;

/**
 * Class to adapt link
 * Created by qinyuan on 15-4-1.
 */
public class LinkAdapter {
    public String adjust(String link) {
        if (StringUtils.isBlank(link)) {
            return link;
        }

        link = link.trim();
        if (link.startsWith("www.")) {
            link = "http://" + link;
        }

        return link;
    }
}
