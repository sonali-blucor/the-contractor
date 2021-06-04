package com.blucor.thecontractor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.blucor.thecontractor.database.TableNames;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TableNames.LOGIN)
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fname;
    public String lname;
    public String company_name = "";
    public String mobile;
    public String email;
    public String password;
    public String image_name;
    public String device_id;
    public int is_client;
    public String created_at;
    public int server_id;

    public User() {

    }

    protected User(Parcel in) {
        id = in.readInt();
        fname = in.readString();
        lname = in.readString();
        company_name = in.readString();
        mobile = in.readString();
        email = in.readString();
        password = in.readString();
        image_name = in.readString();
        device_id = in.readString();
        is_client = in.readInt();
        created_at = in.readString();
        server_id = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getIs_client() {
        return is_client;
    }

    public void setIs_client(int is_client) {
        this.is_client = is_client;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(company_name);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(image_name);
        dest.writeString(device_id);
        dest.writeInt(is_client);
        dest.writeString(created_at);
        dest.writeInt(server_id);
    }
}
