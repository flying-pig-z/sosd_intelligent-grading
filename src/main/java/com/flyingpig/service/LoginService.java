package com.flyingpig.service;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.entity.User;

public interface LoginService {
    Result login(User user);
    Result logout();

    void updateUserWithPassword(String no,String newPassword);

    String getPasswordByNo(String no);
}
