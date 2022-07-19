package lavaplayer;

import net.dv8tion.jda.api.entities.User;

/**
 * This class contains methods for processing track related user data
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 *
 * Please refer the Pied Piper Docs for more info
 */

public class RequestMetadata
{
    public static final RequestMetadata EMPTY = new RequestMetadata(null);

    public final UserInfo user;

    public RequestMetadata(User user)
    {
        this.user = user == null ? null : new UserInfo(user.getIdLong(), user.getName(), user.getDiscriminator(), user.getEffectiveAvatarUrl());
    }

    public long getOwner()
    {
        return user == null ? 0L : user.id;
    }

    public class RequestInfo
    {
        public final String query, url;

        private RequestInfo(String query, String url)
        {
            this.query = query;
            this.url = url;
        }
    }

    public class UserInfo
    {
        public final long id;
        public final String username, discrim, avatar;

        private UserInfo(long id, String username, String discrim, String avatar)
        {
            this.id = id;
            this.username = username;
            this.discrim = discrim;
            this.avatar = avatar;
        }
    }
}
