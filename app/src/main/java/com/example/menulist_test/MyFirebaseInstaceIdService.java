package com.example.menulist_test;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstaceIdService extends FirebaseInstanceIdService {
    // 디바이스에서 앱이 최초 실행되어 디바이스 토큰이 생성되거나 재생성 될 시 에 동작을 작성할 클래스
    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("IDService","Refreshed token : "+refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token){
        //디바이스 토큰이 생성되거나 재생성 될 시 동작할 코드 작성
    }
}
