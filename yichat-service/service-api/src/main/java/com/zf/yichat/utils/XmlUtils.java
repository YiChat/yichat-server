package com.zf.yichat.utils;

import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:34 2019/8/14 2019
 */
public class XmlUtils {
    private static final String LEFT = "<";
    private static final String LEFT_X = "</";
    private static final String RIGHT = ">";
    private static final String CDATA_LEFT = "";
    private static final String CDATA_RIGHT = "";

    public XmlUtils() {
    }

    public static String mapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXML(map, sb);
        sb.append("</xml>");
        return sb.toString();
    }

    private static void mapToXML(Map map, StringBuffer sb) {
        Set set = map.keySet();
        Iterator it = set.iterator();

        while (true) {
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = map.get(key);
                if (null == value) {
                    value = "";
                }

                if (value.getClass().getName().equals("java.util.ArrayList")) {
                    ArrayList list = (ArrayList) map.get(key);
                    sb.append("<" + key + ">");

                    for (int i = 0; i < list.size(); ++i) {
                        HashMap hm = (HashMap) list.get(i);
                        mapToXML(hm, sb);
                    }

                    sb.append("</" + key + ">");
                } else if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXML((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + ">" + "" + value + "" + "</" + key + ">");
                }
            }

            return;
        }
    }

    public static Map<String, String> xmlToMap(Document document) {
        Element root = document.getRootElement();
        List<Element> listElement = root.elements();
        Map map = Maps.newHashMap();
        Iterator var4 = listElement.iterator();

        while (var4.hasNext()) {
            Element e = (Element) var4.next();
            if (!"sign".equals(e.getName())) {
                map.put(e.getName(), e.getTextTrim());
            }
        }

        return map;
    }


}
