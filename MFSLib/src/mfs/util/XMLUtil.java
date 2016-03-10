package mfs.util;

public class XMLUtil {
	
	public static String getResultFromXmlTag(String reply, String xmlTag){
        try{
            String result = "";
            int idx = reply.indexOf("<" + xmlTag + ">");
            if (idx >= 0) {
                int endIdx = reply.indexOf("</" + xmlTag, idx);
                result = reply.substring(idx + xmlTag.length() + 2, endIdx);
            }
            return result.trim();
        }catch(Exception e){
            return "";
        }
    }
}
