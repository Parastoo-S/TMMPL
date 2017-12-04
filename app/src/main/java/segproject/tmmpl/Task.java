package segproject.tmmpl;

import android.widget.ImageView;


import java.security.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Parastoo on 11/25/2017.
 */

public class Task {
    private String _id;
    private String _taskName;
    private User _creator;
    private User _assignedUser;
    private String _description;
    private long _dueDate;
    private List<String> _equipment;
    private String _status;

    private Collection<User> assignedUser;
//    private User creatorUser;

    private static Task activeTask;


    public Task() {
    }

    public Task(String id, String taskName, String description, User creator,  User assignedUser, long dueDate, List<String> equipment){

        this._id = id;
        this._taskName = taskName;
        this._description = description;
        this._assignedUser = assignedUser;
        this._dueDate = dueDate;
        this._status = "inComplete";
        this._creator = creator;
        this._equipment = equipment;
    }


    public void setTaskId(String id){
        _id = id;
    }

    public String getTaskId(){
        return _id;
    }

    public void setTaskName(String taskName){
        _taskName = taskName;
    }

    public String getTaskName(){
        return _taskName;
    }

    public void setCreator(User newCreator){
        _creator = newCreator;
    }

    public User getcreator(){
        return _creator;
    }

    public void setAssignedUser(User assignedUser){
        _assignedUser = assignedUser;
    }

    public User getAssignedUser(){
        return _assignedUser;
    }


    public void setDescription(String description){
        _description = description;
    }

    public String getDescription(){
        return _description;
    }

    public void setDueDate(long dueDate){
        _dueDate = dueDate;
    }

    public long getDueDate(){
        return _dueDate;
    }

    public void setEquipments(List<String> equipments){
        _equipment = equipments;
    }

    public List<String> getEquipments(){
        return _equipment;
    }

    public void setStatus(String status){
        _status = status;
    }

    public String getStatus(){
        return _status;
    }


    void addAssignedUser(User user) {
        assignedUser.add(user);
    }

    public void removeAssignedUser() {
        _assignedUser = null;
    }

    public static void setActiveTask(Task task){
        activeTask = task;
    }

    public static Task getActiveTask(){
        return activeTask;
    }
    void setCreatorUser(User user){
        _creator = user;
    }

    public User getCreatorUser(){
        return _creator;
    }
    //Don't need a deleteCreatorUser method, because task and user are dependent
}
