package datum.testdata;

import datum.authenticate.User;

public class UserData {
    public static User admin = User.builder()
            .id(1L)
            .email("admin@gmail.com")
            .password("admin")
            .build();
    public static  User userOwner = User.builder()
            .id(2L)
            .email("owner@gmail.com")
            .password("owner")
            .build();
}
