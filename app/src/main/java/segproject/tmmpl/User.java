package segproject.tmmpl;


public class User {

    private String _id;
    private String _username;
    private String _avatar_id;

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


}