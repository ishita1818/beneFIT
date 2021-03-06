package tech.iosd.benefit;

import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser
{
    private String id;
    private String name;
    private String avatar;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public Author(String _id, String _name, String _avatar)
    {
        id = _id;
        name = _name;
        avatar = _avatar;
    }
    public Author(String _id, String _name)
    {
        id = _id;
        name = _name;
    }
}