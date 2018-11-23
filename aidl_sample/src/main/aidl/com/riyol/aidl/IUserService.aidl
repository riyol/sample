// IUserService.aidl
package com.riyol.aidl;
// Declare any non-default types here with import statements
import com.riyol.aidl.entity.User;
interface IUserService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void initUsers();
    List<User> getAllUsers();
    List<User> filter(int sex);
    boolean deleteUser(int id);
    boolean addUser(in User user);
    boolean getUserInfo(int id, out User user);
    boolean updateUser(int id, inout User user);
}
