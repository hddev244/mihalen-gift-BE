package shop.mihalen.servive;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.var;
import shop.mihalen.entity.UserEntity;

@Service
public class UserService {
    private static final String EXISTING_EMAIL = "daivvh244@gmail.com";
    private static final String ANOTHER_EMAIL = "another@gmail.com";
    // public Optional<UserEntity> findByEmail(String username){
        
    //     if(EXISTING_EMAIL.equalsIgnoreCase(email)){
    //         var user = new UserEntity();
    //         user.setId(1L);
    //         user.setEmail(EXISTING_EMAIL);
    //         user.setPassword("$2a$12$hveBjksx942H3oj/SB9q4uaW8Wd9EwI..PTEZ2qXvAhFe1uqzqRt6");
    //         user.setRole("ROLE_ADMIN");
    //         user.setExtraInfo("hello");
    //         return Optional.of(user);
    //     } else if( ANOTHER_EMAIL.equalsIgnoreCase(email)){
    //         var user = new UserEntity();
    //         user.setId(11L);
    //         user.setEmail(ANOTHER_EMAIL);
    //         user.setPassword("$2a$12$hveBjksx942H3oj/SB9q4uaW8Wd9EwI..PTEZ2qXvAhFe1uqzqRt6");
    //         user.setRole("ROLE_USER");
    //         user.setExtraInfo("hello");
    //         return Optional.of(user);
    //     }
    //    return Optional.empty();
    // }
}
