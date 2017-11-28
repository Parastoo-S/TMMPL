package segproject.tmmpl;


import java.util.Collection;

public class User {

    private String _id;
    private String _username;
    private String _avatar_id;
    private static User activeUser;

    private Collection<Task> createdTasks;
    private Collection<Task> assignedTasks;

    public User() {
    }

    public User(String id, String username) {
        _id = id;
        _username = username;
//        _avatar_id = avatar_id;
    }

    public User(String username) {
        _username = username;
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
        return (activeUser);
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


    public Collection<Task> getCreatedTasks(){
        return createdTasks;
    }

    public Collection<Task> getAssignedTasks(){
        return assignedTasks;
    }


}