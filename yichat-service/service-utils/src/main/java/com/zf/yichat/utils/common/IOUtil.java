package com.zf.yichat.utils.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:03 2019/5/30 2019
 */
public class IOUtil {

    public static InputStream str2Stream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public static String stream2str(InputStream input) {

        StringBuffer out = new StringBuffer();
        try {
            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
