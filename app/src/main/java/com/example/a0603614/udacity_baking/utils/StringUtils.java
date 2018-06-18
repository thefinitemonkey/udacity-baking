package com.example.a0603614.udacity_baking.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String toProperCase(String input) {
        // A pattern for all (UNICODE-) lower case characters preceded by a word boundary
        // From http://www.codecodex.com/wiki/Convert_a_string_to_proper_case

        Pattern p = Pattern.compile("\\b([\\p{javaLowerCase}])", Pattern.UNICODE_CASE);
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer(input.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
