package com.zinco.react_back.utils;

import org.apache.commons.text.StringEscapeUtils;

public class utils {
    public static String decodeHtmlEntities(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }
}