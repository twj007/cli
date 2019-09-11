package com.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.constant.CommonConstants;
import com.common.pojo.SysUser;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.*;

/***
 **@project: base
 **@description: include jwt, aes etc
 **@Author: twj
 **@Date: 2019/06/20
 **/
public class EncryptUtils {

//    private static String JWT_KEY = "twj_jien2018";
//
//    private static String AES_KEY = "twl_jien2018_007";
//
//    private static String IV = "1234567812345678";




    public static void main(String[] args) {
        List<Integer> body = Arrays.asList(1, 2, 3);
//        String token = EncryptUtils.encode(body, "twj", "subject");
        SysUser user = new SysUser("jien", "");
        user.setCreatedate(new Date());
        String token = EncryptUtils.encode(user, "jien");
        System.out.println(token);
        System.out.println(EncryptUtils.getIssuer(token));
        System.out.println(decode("jien", token, "username"));


    }
/***********************************************************************
 *
 *             J W T
 *
 ***********************************************************************/
    /***
     * iss (issuer)：签发人
     * exp (expiration time)：过期时间
     * sub (subject)：主题
     * aud (audience)：受众
     * nbf (Not Before)：生效时间
     * iat (Issued At)：签发时间
     * jti (JWT ID)：编号
     * @Param issuer
     * @Param 自定义的字段，保存的一些信息
     */
    public static String encode(Object data, String issuer, String subject, String jti, String... audience){

        String token = null;
        Calendar now = Calendar.getInstance();
        Date iatDate = now.getTime(); //颁发时间
        now.add(Calendar.MINUTE, 100);
        Date expireDate = now.getTime();//过期时间（100分钟后过期）
        try{
            Algorithm algorithm = Algorithm.HMAC256(CommonConstants.JWT_KEY);
            if(data instanceof String){
                token = JWT.create()
                        .withClaim("userVal", (String) data)
                        .withIssuedAt(iatDate)
                        .withSubject(subject)
                        .withExpiresAt(expireDate)
                        .withIssuer(issuer)
                        .withAudience(audience)
                        .withJWTId(jti)
                        .withSubject(subject)
                        .sign(algorithm);
            }else if(data instanceof List){
                if(((List)data).size() == 0){
                    return null;
                }
                Object type = ((List)data).get(0);
                switch(type.getClass().getTypeName()){
                    case "java.lang.String":
                        token = JWT.create()
                                .withArrayClaim("userArr", (String[]) ((List<String>)data).toArray())
                                .withIssuedAt(iatDate)
                                .withSubject(subject)
                                .withExpiresAt(expireDate)
                                .withIssuer(issuer)
                                .withAudience(audience)
                                .withJWTId(jti)
                                .withSubject(subject)
                                .sign(algorithm);
                        break;
                    case "java.lang.Integer":
                        token = JWT.create()
                                .withArrayClaim("userArr", (Integer[]) ((List<Integer>)data).toArray())
                                .withIssuedAt(iatDate)
                                .withSubject(subject)
                                .withExpiresAt(expireDate)
                                .withIssuer(issuer)
                                .withAudience(audience)
                                .withJWTId(jti)
                                .withSubject(subject)
                                .sign(algorithm);
                        break;
                    case "java.lang.Long":
                        token = JWT.create()
                                .withArrayClaim("userArr", (Long[]) ((List<Long>)data).toArray())
                                .withIssuedAt(iatDate)
                                .withSubject(subject)
                                .withExpiresAt(expireDate)
                                .withIssuer(issuer)
                                .withAudience(audience)
                                .withJWTId(jti)
                                .withSubject(subject)
                                .sign(algorithm);
                        break;
                    default:

                        break;
                }

            }else{
                JWTCreator.Builder builder =  JWT.create();
                Field[] fields = data.getClass().getDeclaredFields();
                for(Field field : fields){
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(data);
                    if(value instanceof String  && value != null){
                        builder.withClaim(name, (String) value);
                    }
                    else if(value instanceof Long && value != null){
                        builder.withClaim(name, (Long) value);
                    }
                    else if(value instanceof  Double && value != null){
                        builder.withClaim(name, (Double)value);
                    }
                    else if(value instanceof  Integer && value != null){
                        builder.withClaim(name, (Integer) value);
                    }
                    else if(value instanceof  Date && value != null){
                        builder.withClaim(name, (Date) value);
                    }
                    else if(value instanceof  Boolean && value != null){
                        builder.withClaim(name, (Boolean) value);
                    }

                }
                token = builder
                        .withIssuedAt(iatDate)
                        .withSubject(subject)
                        .withExpiresAt(expireDate)
                        .withIssuer(issuer)
                        .withAudience(audience)
                        .withJWTId(jti)
                        .withSubject(subject)
                        .sign(algorithm);

            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return token;
    }


    public static String encode(Object data, String issuer, String subject, String jti){
        return EncryptUtils.encode(data, issuer, subject, jti, "");
    }

    public static String encode(Object data, String issuer, String subject){
        return EncryptUtils.encode(data, issuer, subject, "");
    }

    public static String encode(Object data, String issuer){
        return EncryptUtils.encode(data, issuer, "");
    }

    /***
     * 通过请求头的token验证合法性
     * @param token
     * @return
     */
    public static boolean verify(String token){
        if(token == null || "".equals(token)){
            return false;
        }
        Algorithm algorithm = Algorithm.HMAC256(CommonConstants.JWT_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        if(jwt == null){
            return false;
        }
        return true;
    }


    public static String decode(String issuer, String token, String val){
        String jwtStr = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(CommonConstants.JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            jwtStr = jwt.getClaim(val).asString();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }
        return jwtStr;
    }

    public static String getIssuer(String token){
        String jwtStr = null;
        if(token == null || "".equals(token)){
            return jwtStr;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(CommonConstants.JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            jwtStr = jwt.getIssuer();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }
        return jwtStr;
    }

    public static String decode(String token){
        String jwtStr = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(CommonConstants.JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            jwtStr = jwt.getClaim("userVal").asString();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }
        return jwtStr;
    }


    /************************************************************************
     *
     *
     *                  A E S
     *
     *
     *************************************************************************/

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

//    public static void main(String args[]) throws Exception{
//        String content = "rio-front-dev";
//        //加密
//        byte[] encrypted = AESEncrypt(content.getBytes());
//        //解密/PZmZgE0H+iVs40r17/P7g==
//        byte[] decrypted = AESDecrypt(decryptBASE64(encryptBASE64(encrypted)));
//
//        System.out.println("加密后："+encryptBASE64(encrypted));
//        System.out.println("解密后："+byteToString(decrypted));
//    }

    /****
     * 通用解密
     * @param baes64Encrypt
     * @return
     */
    public static String AESDecode(String baes64Encrypt){
        return byteToString(AESDecrypt(decryptBASE64(baes64Encrypt)));
    }

    public static String AESEncode(String content){
        return encryptBASE64(AESEncrypt(content.getBytes()));
    }

    private static String byteToString(byte[] bytes){
        return new String(bytes);
    }


    /***
     * 获取加密数据
     * @param content
     * @return
     */
    private static byte[] AESEncrypt(byte[] content){
        byte[] keyBytes = CommonConstants.AES_KEY.getBytes();
        //byte[] iv = CommonConstants.IV.getBytes();
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取解密数据
     * @param content
     * @return
     */
    private static byte[] AESDecrypt(byte[] content){
        try {
            byte[] keyBytes = CommonConstants.AES_KEY.getBytes();
           // byte[] iv = CommonConstants.IV.getBytes();
            SecretKeySpec key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //字符串装换成base64
    private static byte[] decryptBASE64(String key)  {
        return Base64.getDecoder().decode(key.getBytes());
    }
    //base64装换成字符串
    private static String encryptBASE64(byte[] key)  {
        return new String(Base64.getEncoder().encode(key));
    }


    /************************************************************
     *
     *
     *                  M D 5
     *
     *
     ************************************************************/

    private static final String HEXDIGITS[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultString;
    }


    private static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }



}
