package segproject.tmmpl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User {

    private String _id;
    private String _username;
    private String _avatar_id;
    private String _role;
//    private
    private static User activeUser;


    private ArrayList<Task> createdTasks = new ArrayList<>();
    private ArrayList<String> _assignedTasks = new ArrayList<>();

    public User() {
    }

    public User(String id, String username, String role) {
        this._id = id;
        this._username = username;
        this._role = role;
//        _avatar_id = avatar_id;
    }

        public User(String username, String role) {
        this._username = username;
//        _avatar_id = avatar_id;
            this._role = role;
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

    public void addCreatedTask(Task task) {
        this.createdTasks.add(task);
    }

    void removeCreatedTask(Task task) {
        createdTasks.remove(task);
    }

    public void addAssignedTask(String id){
        _assignedTasks.add(id);
    }

    void removeAssignedTask(Task task){
        _assignedTasks.remove(task);
    }


    public List<Task> getCreatedTasks(){
        return createdTasks;
    }

    public List<String> getAssignedTasks(){
        return _assignedTasks;
    }


    public String getRole(){
        return _role;
    }

    public void setRole(String role){
        _role = role;
    }



}