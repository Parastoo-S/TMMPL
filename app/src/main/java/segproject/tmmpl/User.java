package segproject.tmmpl;


public class User {

    private int _id;
    private String _username;
    private String _avatar_id;

    public User() {
    }

    public User(int id, String username, String avatar_id) {
        _id = id;
        _username = username;
        _avatar_id = avatar_id;
    }

    public User(String username, String avatar_id) {
        _username = username;
        _avatar_id = avatar_id;
    }

    public void setID(int id) {
        _id = id;
    }

    public int getID() {
        return _id;
    }
    public void setUsername(String username) {
        _username = username;
    }
    public String getUsername() {
        return _username;
    }
    public void setAvatarId(String avatar_id) {
        _avatar_id = avatar_id;
    }
    public String getAvatarId() {
        return _avatar_id;
    }


}
