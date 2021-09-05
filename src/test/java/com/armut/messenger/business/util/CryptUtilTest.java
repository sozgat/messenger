package com.armut.messenger.business.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptUtilTest {

    @Test
    void encrypt() {
        String password = "123456";
        String encyptPassword = "xUTcEa5dDxIw2tIlUeGaqg==";
        String returnPassword = CryptUtil.encrypt(password);
        Assert.assertEquals(encyptPassword,returnPassword);
    }

    @Test
    void decrypt() {
        String encyptPassword = "xUTcEa5dDxIw2tIlUeGaqg==";
        String password = "123456";
        String returnPassword = CryptUtil.decrypt(encyptPassword);
        Assert.assertEquals(password,returnPassword);
    }
}