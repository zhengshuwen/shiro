package sys.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EncryptionMD5 {
	/**加密名称*/
	String hashAlgorithmName = "MD5";
    /**加密次数*/
    int hashIterations = 1024;
    /**加密的盐*/
    //String salt = null;
    /**
     * 加密凭证，即用户密码之类的
     * @param String credentials需要加密的凭证(密码)
     * @return String 加密后的字符串
     * */
    public String toMD5(String credentials){
    	SimpleHash str = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
		return str.toString();
    }
    
    /**
     * 加密凭证，即用户密码之类的
     * @param String credentials:需要加密的凭证(密码) 
     * 		  String salt:加密的盐，不同用户出现相同密码时，加盐加密的结果依然不同(salt一般使用username)
     * @return String 加密后的字符串
     * */
    public String toMD5(String credentials,String salt){
    	SimpleHash str = new SimpleHash(hashAlgorithmName, credentials, ByteSource.Util.bytes(salt), hashIterations);
		return str.toString();
    }
}
