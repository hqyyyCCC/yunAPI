package com.hqy.yunapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtil {
    /**
     *
     * @param body 获取签名的 参数
     * @param secretKey 不参与传递
     * @return 返回摘要算法加密的签名
     */
    public static String  getSign(String body , String secretKey){
        // 摘要算法不可逆 固定长度
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);

// 5393554e94bf0eb6436f240a4fd71282
        String digestHex = sha256.digestHex(body+secretKey);

        return digestHex;
    }
}
