package com.riyol.aidl.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id;
    private String name;
    private int age;
    private int sex = 0;
    private int avatar;

    public User() {
    }

    public User(int id, String name, int age, int sex, int avatar) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void readFromParcel(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.age = in.readInt();
        this.sex = in.readInt();
        this.avatar = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.sex);
        dest.writeInt(this.avatar);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.age = in.readInt();
        this.sex = in.readInt();
        this.avatar = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
