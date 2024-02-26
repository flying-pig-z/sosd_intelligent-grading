import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    public static void main(String[] args){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("zzgdm");
        System.out.println(encodedPassword); // 输出编码后的哈希值

        boolean isMatch = bCryptPasswordEncoder.matches("zzgdm", encodedPassword);
        System.out.println(isMatch); // 输出 true
    }
}
