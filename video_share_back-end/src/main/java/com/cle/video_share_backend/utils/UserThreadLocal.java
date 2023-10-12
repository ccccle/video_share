package com.cle.video_share_backend.utils;


import com.cle.video_share_backend.pojo.User;

//统一处理token
public class UserThreadLocal {

    private  static  ThreadLocal<User>LOCAL=new ThreadLocal<>();

   public   UserThreadLocal(){

    }
    
    public   static void set(User user){
        LOCAL.set(user);
    }

    public   static  User get(){
        return  LOCAL.get();
    }

 }

