package com.riyol.aidl.impl;

import android.content.Context;
import android.os.RemoteException;

import com.riyol.aidl.IUserService;
import com.riyol.aidl.entity.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class UserServiceImpl extends IUserService.Stub {

    private final static int DEFAULT_SIZE = 30;
    private static int ID = 0;
    private Context context;

    private List<User> userList;

    public UserServiceImpl(Context context) {
        this.context = context;
        try {
            initUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initUsers() throws RemoteException {
        userList = new ArrayList<>(DEFAULT_SIZE);
        Random random = new Random();
        while (userList.size() < DEFAULT_SIZE) {
            String name = Cheeses.sCheeseStrings[random.nextInt(Cheeses.sCheeseStrings.length)];
            userList.add(new User(ID++, name, Cheeses.getRandomAge(), Cheeses.getRandomSex(), Cheeses
                    .getRandomCheeseDrawable()));
        }

    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        return userList;
    }

    @Override
    public List<User> filter(final int sex) throws RemoteException {
        List<User> result = new ArrayList<>();
        for (User user : userList) {
            if (user.getSex() == sex) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public boolean deleteUser(int id) throws RemoteException {
        Iterator<User> it = userList.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (id == user.getId()) {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public boolean addUser(User user) throws RemoteException {
        user.setId(ID++);
        userList.add(user);
        return true;
    }

    @Override
    public boolean getUserInfo(int id, User user) throws RemoteException {
        User find = null;
        for (User u : userList) {
            if (u.getId() == id) {
                find = u;
                break;
            }
        }
        if (find != null) {
            user.setId(find.getId());
            user.setName(find.getName());
            user.setSex(find.getSex());
            user.setAge(find.getAge());
            user.setAvatar(find.getAvatar());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(int id, User user) throws RemoteException {
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            User temp = userList.get(i);
            if (temp.getId() == id) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            userList.set(index, user);
            return true;
        }
        return false;
    }
}
