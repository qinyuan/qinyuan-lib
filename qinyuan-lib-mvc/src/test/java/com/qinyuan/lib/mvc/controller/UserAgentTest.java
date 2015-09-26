package com.qinyuan.lib.mvc.controller;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAgentTest {
    @Test
    public void testGetOS() throws Exception {
        String str = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
        assertThat(new UserAgent(str).getOS()).isEqualTo(UserAgent.OS.LINUX);

        str = "Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0";
        assertThat(new UserAgent(str).getOS()).isEqualTo(UserAgent.OS.LINUX);

        str = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4";
        assertThat(new UserAgent(str).getOS()).isEqualTo(UserAgent.OS.IOS);

        str = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
        assertThat(new UserAgent(str).getOS()).isEqualTo(UserAgent.OS.IOS);

        str = "Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36";
        assertThat(new UserAgent(str).getOS()).isEqualTo(UserAgent.OS.ANDROID);

        str = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; QQBrowser/8.2.3638.400)";
        assertThat(new UserAgent(str).getOS()).isEqualTo(UserAgent.OS.WINDOWS);
    }

    @Test
    public void testGetBrowser() throws Exception {
        String str = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.CHROME);

        str = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.CHROME);

        str = "Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.FIREFOX);

        str = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0)";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.IE);

        str = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; QQBrowser/8.2.3638.400)";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.IE);

        str = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.SAFARI);

        str = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.SAFARI);

        str = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36 OPR/29.0.1795.60";
        assertThat(new UserAgent(str).getBrowser()).isEqualTo(UserAgent.Browser.OPERA);
    }
}
