package segproject.tmmpl;


import java.util.Collection;
import java.util.List;

public class User {

    private String _id;
    private String _username;
    private String _avatar_id;
    private static User activeUser;


    private List<Task> createdTasks;
    private List<Task> assignedTasks;

    public User() {
    }

    public User(String id, String username) {
        this._id = id;
        this._username = username;
//        _avatar_id = avatar_id;
    }

    public User(String username) {
        this._username = username;
//        _avatar_id = avatar_id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }
    public void setUsername(String username) {
        _username = username;
    }
    public String getUsername() {
        return _username;
    }
//    public void setAvatarId(String avatar_id) {
//        _avatar_id = avatar_id;
//    }
//    public String getAvatarId() {
//        return _avatar_id;
//    }

    public static void setActiveUser(User user){
        activeUser = user;
    }

    public static User getActiveUser(){
        return activeUser;
    }

    void addCreatedTask(Task task) {
        createdTasks.add(task);
    }

    void removeCreatedTask(Task task) {
        createdTasks.remove(task);
    }

    void addAssignedTask(Task task){
        assignedTasks.add(task);
    }

    void removeAssignedTask(Task task){
        assignedTasks.remove(task);
    }


    public List<Task> getCreatedTasks(){
        return createdTasks;
    }

    public List<Task> getAssignedTasks(){
        return assignedTasks;
    }


}