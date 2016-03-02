package garbagemgr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GenerateDictionaryJSP {

    public static void main(String[] args) throws Exception {
        String dictionary = "src/main/webapp/dictionary.jsp";
        String properties = "src/main/resources/i18n/messages.properties";
        
        StringBuffer sb = new StringBuffer("<%@ taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\"%>\n"
                    + "<%@ taglib uri=\"http://java.sun.com/jsp/jstl/fmt\" prefix=\"fmt\"%>\n"
                    + "<c:if test=\"${!empty cookie.lang}\">\n"
                    + "    <fmt:setLocale value=\"${cookie.lang.value}\" />\n"
                    + "</c:if>\n"
                    + "<fmt:setBundle basename=\"i18n/messages\" />\n"
                    + "<script language=\"javascript\">\n"
                    + "    dictionary = {\n");
        
        String s = "";
        BufferedReader input = new BufferedReader(new FileReader(properties));
        int i = 0;
        while ((s = input.readLine()) != null) {
            if(s.indexOf("=") == -1) {
                continue;
            }
            s = s.split("=")[0];
            if(i == 0) {
                sb.append("    '" + s + "':'<fmt:message key=\"" + s + "\"/>'\n");
            } else {
                sb.append("    ,'" + s + "':'<fmt:message key=\"" + s + "\"/>'\n");
            }
            i++;
        }
        
        sb.append("};\n"
                   + "</script>");
        
        
        BufferedWriter output = new BufferedWriter(new FileWriter(dictionary));
        output.write(sb.toString());
        
        output.close();
        
        input.close();
    }

}
