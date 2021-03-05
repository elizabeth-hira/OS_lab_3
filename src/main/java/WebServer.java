import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebServer {

    ConcurrentLinkedQueue<Task> tasksQueue;
    ConcurrentHashMap<Integer, Task> tasksMap;
    ArrayList<TaskThread> threads;

    Thread tasksThread;

    public WebServer() {
        this.tasksQueue = new ConcurrentLinkedQueue<>();
        this.tasksMap = new ConcurrentHashMap<>();
        this.tasksThread = new Thread() {
            @Override
            public void run() {
                //very stupid i know
                while (true){
                    if(!tasksQueue.isEmpty()) {
                        Task task = tasksQueue.poll();
                        task.setStatus(Status.PROCESSING);
                        task.setResult(Task.countResult(task.getNumber()));
                        task.setStatus(Status.DONE);
                    }
                }
            }
        };
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(8080)) {
            tasksThread.start();
            while (true){
                Socket client = server.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());

                // Start sending our reply, using the HTTP 1.1 protocol
                out.print("HTTP/1.1 200 \r\n"); // Version & status code
                out.print("Content-Type: text/plain\r\n"); // The type of data
                out.print("Connection: close\r\n"); // Will close stream
                out.print("\r\n"); // End of headers

                String line;
                ArrayList<String> lines = new ArrayList<>();
                while ((line = in.readLine()) != null) {
                    if (line.length() == 0)
                        break;
                    //out.print(line + "\r\n");
                    //System.out.println(line);
                    lines.add(line);
                }
                //it is stupid too
                if (lines.size() == 0) {
                    continue;
                }
                Request request = Request.parse(lines.get(0));
                if (request.type == RequestType.GET) {
                    Task task;
                    if((task = tasksMap.get(Integer.parseInt(request.params.get("id")))) != null) {
                        if (task.getStatus() != Status.DONE) {
                            out.println("Status: " + task.getStatus().name());
                        } else {
                            out.println(task.getResult().toString());
                        }
                    } else {
                        out.println("Error: No such id found");
                    }

                }else if (request.type == RequestType.POST) {
                    Task task = new Task(Integer.parseInt(request.params.get("number")));
                    tasksQueue.add(task);
                    int id = Task.getID();
                    tasksMap.put(id, task);
                    out.println(id);
                }
                out.close();
                in.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
