package MyBusinessDb.db;

import org.apache.ibatis.annotations.*;
import MyBusinessDb.db.User;

public interface UserMapper 
{
	public User getUser(@Param("uid") String uid);
	public boolean addUser(User user);
	public boolean deleteUser(@Param("uid") String uid);
	public boolean updateUser(@Param("uid") String uid, @Param("count")int count, @Param("date") String date);
}