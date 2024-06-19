package sg.edu.np.mad.madpractical5;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String description;
    private Boolean followed;

    public User(int id, String name, String description, boolean followed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.followed = followed;
    }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setFollowed(Boolean followed) { this.followed = followed; }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean getFollowed() { return followed; }
}
