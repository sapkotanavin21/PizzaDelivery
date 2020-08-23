package com.example.pizzadelivery.bll;

public class LoginBLL {
    private String username;
    private String password;
    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {

//        UserAPI usersAPI = Url.getInstance().create(UserAPI.class);
//        Call<SignUpResponse> usersCall = usersAPI.checkUser(username, password);
//        try {
//            Response<SignUpResponse> loginResponse = usersCall.execute();
//            if (loginResponse.isSuccessful() &&
//                    loginResponse.body().getStatus().equals("Login success!")) {
//
//                Url.token += loginResponse.body().getToken();
//                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
//                isSuccess = true;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return isSuccess;
    }
}

