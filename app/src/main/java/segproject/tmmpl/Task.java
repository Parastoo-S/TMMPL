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
    private String _taskname;
    private User _creator;
    private User _assignedUser;
    private String _description;
    private long _dueDate;
    private List<String> _equipment;
    private Boolean _completed;

    private Collection<User> assignedUser;
    private User creatorUser;

    private static Task activeTask;


    public Task() {
    }

    public Task(String id, String taskname, String description, User assignedUser, long dueDate, List<String> equipment){

        this._id = id;
        this._taskname = taskname;
        this._description = description;
        _assignedUser = assignedUser;
        this._dueDate = dueDate;
        this._completed = false;
        this._creator = User.getActiveUser();
        this._equipment = equipment;
    }

    public Task(String id, String taskname, User assignedUser, String description, long dueDate){

        this._id = id;
        this._taskname = taskname;
        this._assignedUser = assignedUser;
        this._description = description;
        this._dueDate = dueDate;
    }
    public Task(String id, String taskname, User creator, User assignedUser, String description, long dueDate, Boolean completed){

        this._id = id;
        this._taskname = taskname;
        this._creator = creator;
        this._assignedUser = assignedUser;
        this._description = description;
        this._dueDate = dueDate;
        this._completed = completed;
    }
    public Task(String id, String taskname, User creator, User assignedUser, String description, long dueDate, List<String> equipments, Boolean completed){

        this._id = id;
        this._taskname = taskname;
        this._creator = creator;
        this._assignedUser = assignedUser;
        this._description = description;
        this._dueDate = dueDate;
        this._equipment = equipments;
        this._completed = completed;
    }

    public void setTaskId(String id){
        _id = id;
    }

    public String getTaskId(){
        return _id;
    }

    public void setTaskName(String taskName){
        _taskname = taskName;
    }

    public String getTaskName(){
        return _taskname;
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

    public List<String> getequipments(){
        return _equipment;
    }

    public void setStatus(Boolean status){
        _completed = status;
    }

    public Boolean getStatus(){
        return _completed;
    }


    void addAssignedUser(User user) {
        assignedUser.add(user);
    }

    void removeAssignedUser(User user) {
        assignedUser.remove(user);
    }

    public static void setActiveTask(Task task){
        activeTask = task;
    }

    public static Task getActiveTask(){
        return activeTask;
    }
    void setCreatorUser(User user){
        creatorUser = user;
    }

    public User getCreatorUser(){
        return creatorUser;
    }
    //Don't need a deleteCreatorUser method, because task and user are dependent
}
