public class TaskThread implements Runnable {

    Task task;

    public TaskThread(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.setStatus(Status.PROCESSING);
        task.setResult(Task.countResult(task.getNumber()));
        task.setStatus(Status.DONE);
    }
}
