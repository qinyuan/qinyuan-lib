package com.qinyuan.lib.mvc.tag;

/**
 * Base Class of Css and Js
 * Created by qinyuan on 15-6-14.
 */
class CssJsBase extends MyTagSupport {
    private final static long VERSION = System.currentTimeMillis();
    private boolean version;
    protected final static String PREFIX = "resources";
    protected final static String CSS_PREFIX = PREFIX + "/css/";
    protected final static String JS_PREFIX = PREFIX + "/js/";

    public void setVersion(boolean version) {
        this.version = version;
    }

    protected void printVersion() {
        if (version) {
            print("?=" + VERSION);
        }
    }
}
