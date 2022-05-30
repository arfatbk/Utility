## Usage

Add this `JAR` to classpath of your application.

```java
import com.fable.password.PasswordEncoders;
import com.fable.password.PasswordEncoder;

class Main {
    //this will pick a random encoder and generate Hash
    PasswordEncoder passwordEncoder = PasswordEncoders.delegatingPasswordEncoder();
    String hash = passwordEncoder.encode("password");

    //this will pick right encoder based on hash prefix
    boolean res = passwordEncoder.matches("password", hash);
}
```

```java
import com.fable.password.PasswordEncoders;
import com.fable.password.PasswordEncoder;

class Main {
    //this will pick a BCrypt encoder always. Not recommended
    PasswordEncoder passwordEncoder = PasswordEncoders.withBCrypt();
 
    //OR
    
    //this will pick a SHA512 encoder always. Not recommended
    PasswordEncoder passwordEncoder = PasswordEncoders.withSHA512();

    String hash = passwordEncoder.encode("password");

    //this will pick right encoder based on hash prefix
    boolean res = passwordEncoder.matches("password", hash);
}
```

### Oneliner 😁

```java
import com.fable.password.PasswordEncoders;

class Main {
    //generate hash and store in storage
    String hash = PasswordEncoders.encode("password");
    
    //match raw Password with hash 
    boolean matches = PasswordEncoders.matches("password",hash);
}
```