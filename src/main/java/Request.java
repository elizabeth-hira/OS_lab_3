import java.util.HashMap;
import java.util.StringTokenizer;

enum RequestType { GET, POST }

public class Request {
    public RequestType type;
    public HashMap<String, String> params;

    public Request() {
        params = new HashMap<>();
    }

    public static Request parse(String line) {
        Request request = new Request();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        String strType = tokenizer.nextToken();
        setType(strType, request);
        String url = tokenizer.nextToken();
        tokenizer = new StringTokenizer(url, "/?&=");
        while(tokenizer.hasMoreTokens()) {
            String key = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            request.params.put(key, value);
        }
        return request;
    }
    public static void setType(String str, Request request){
        switch (str){
            case "GET":
                request.type = RequestType.GET;
                break;
            case "POST":{
                request.type = RequestType.POST;
                break;
            }
        }
    }
}
