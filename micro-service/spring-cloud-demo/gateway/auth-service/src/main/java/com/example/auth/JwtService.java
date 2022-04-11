package com.example.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {

    // 生产环境不能这么用
    private static final String KEY = "changeIt";
    private static final String ISSUER = "www";


    private static final long TOKEN_EXP_TIME = 600000;
    private static final String USER_NAME = "username";

    /**
     * 生成Token
     *
     * @param acct
     * @return
     */
    public String token(Account acct) {
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create()
                // 颁发者
                .withIssuer(ISSUER)
                // 颁发时间
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXP_TIME))
                // 追加自定义声明
                .withClaim(USER_NAME, acct.getUsername())
//                .withClaim("ROLE", "")
                .sign(algorithm);

        log.info("jwt generated user={}", acct.getUsername());
        return token;
    }

    /**
     * 校验Token
     *
     * @param token
     * @param username
     * @return
     */
    public boolean verify(String token, String username) {
        log.info("verifying jwt - username={}", username);

        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            // 构建验证器，使用颁发的时候同一个算法
            JWTVerifier verifier = JWT.require(algorithm)
                    // 这里可以验证颁发的时候放进去的所有信息
                    .withIssuer(ISSUER)
                    .withClaim(USER_NAME, username)
                    .build();

            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("auth failed", e);
            return false;
        }

    }

}
