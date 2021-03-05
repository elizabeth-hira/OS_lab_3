//
//
// import java.util.concurrent.ConcurrentHashMap;
//
//public class IntegralTask {
//    static ConcurrentHashMap<Integer, Task> tasksMap = new ConcurrentHashMap<>();
//
//
//    public static void processingRequest(Request request,{
//        String answer;
//        switch (request.type){
//            case GET -> answer = getProcessing(request);
//            case POST -> answer =
//        }
//        if (request.type == RequestType.GET) {
//            getProcessing(task)
//
//        }else if (request.type == RequestType.POST) {
//
//        }
//    }
//    public static String getProcessing(Request request){
//        Task task = tasksMap.get(Integer.parseInt(request.params.get("id")));
//        if(task != null) {
//            if (task.getStatus() != Status.DONE) {
//                return "Status: " + task.getStatus().name();
//            } else {
//                return task.getResult().toString();
//            }
//        } else {
//            return "Error: No such id found";
//        }
//    }
//    public static String postProcessing(Request request){
//        Task task = new Task(Integer.parseInt(request.params.get("number")));
//        tasksQueue.add(task);
//        int id = Task.getID();
//        tasksMap.put(id, task);
//        out.println(id);
//    }
//}
