package com.amigos.awsuploadimage.repository;

import com.amigos.awsuploadimage.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

//    static {
//        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "lancat", null));
//        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "nhibui", null));
//    }
//    public List<UserProfile> getUserProfiles(){
//        return USER_PROFILES;
//    }
}
