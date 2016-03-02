package garbagemgr;

import me.anchora.garbage.utils.JsonUtil;

public class JsonTest {

    public static void main(String[] args) {
        Long id = 12L;
        System.out.println(JsonUtil.toJson(id));
                
    }

}
