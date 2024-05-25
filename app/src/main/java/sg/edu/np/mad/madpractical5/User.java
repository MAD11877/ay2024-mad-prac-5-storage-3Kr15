package sg.edu.np.mad.madpractical5;

public class User {
    private int id;
    private String name;
    private String password;
    private Boolean followed;
    public void setID(int id) { this.id = id; }
    public void setName(String username) { this.name = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFollowed(Boolean followed) {this.followed = followed; }
    public int getID() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public boolean getFollowed() { return followed; }
    public User(int id, String name, String password, boolean followed) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.followed = followed;
    }
}
