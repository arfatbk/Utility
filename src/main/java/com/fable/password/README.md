## Usage

Add this `JAR` to classpath of your application.

```java
import com.fable.password.Password;

class Main {
    //this will pick a random encoder
    PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
    String hash = passwordEncoder.encode("password");
    
    //this will pick right encoder based on prefix
    boolean res = passwordEncoder.matches("password", hash);
}
```

```java
import com.fable.password.Password;
import com.fable.password.PasswordEncoder;

class Main {
    //this will pick a BCrypt encoder always. Not recommended
    PasswordEncoder passwordEncoder = PasswordEncoder.withBCrypt();
    //OR
    //this will pick a SHA512 encoder always. Not recommended
    PasswordEncoder passwordEncoder = PasswordEncoder.withSHA512();
    
    String hash = passwordEncoder.encode("password");

    //this will pick right encoder based on prefix
    boolean res = passwordEncoder.matches("password", hash);
}
```