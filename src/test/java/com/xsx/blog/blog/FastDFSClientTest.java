package com.xsx.blog.blog;

import com.xsx.blog.util.FastDFSClient;
import org.junit.Test;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 21:36
 */
public class FastDFSClientTest extends BlogApplicationTests {

    @Test
    public void removeTest(){
        try {
            String[] deleFiles = {"M00/00/00/rBHmHV0-5U2AcKwUAAxzsT01dFk90..jpg",
                    "M00/00/00/rBHmHV0-5ziAc2VEAB-Pm7dC5ZM47..jpg",
                    "M00/00/00/rBHmHV0-50WAQrHgAAxzsT01dFk18..jpg",
                    "M00/00/00/rBHmHV0-53yAKiHtAB-Pm7dC5ZM31..jpg",
                    "M00/00/01/rBHmHV0-54CAFZaXAAxzsT01dFk59..jpg",
                    "M00/00/01/rBHmHV0-582ACQzlAB-Pm7dC5ZM94..jpg",
                    "M00/00/01/rBHmHV0-59CAO1xUAAxzsT01dFk40..jpg",
                    "M00/00/01/rBHmHV0-6giAXVxhAB-Pm7dC5ZM17..jpg",
                    "M00/00/01/rBHmHV0-6m2AMUodAAxzsT01dFk87..jpg",
                    "M00/00/01/rBHmHV0-7GaAEXffAB-Pm7dC5ZM72..jpg",
                    "M00/00/01/rBHmHV0-77-Aa194AB-Pm7dC5ZM16..jpg",
                    "M00/00/01/rBHmHV0-8WSAEqJJAAxzsT01dFk25..jpg",
                    "M00/00/01/rBHmHV0-8iqACG7WAB-Pm7dC5ZM22..jpg",
                    "M00/00/01/rBHmHV0-8leAT-wAAAxzsT01dFk94..jpg",
                    "M00/00/01/rBHmHV0-8oGANT4pAAxzsT01dFk72..jpg",
                    "M00/00/01/rBHmHV0-8tWAF17EAB-Pm7dC5ZM12..jpg",
                    "M00/00/01/rBHmHV0-82-AKpHJAB-Pm7dC5ZM93..jpg",
                    "M00/00/01/rBHmHV0-9ZqAD-n_AAxzsT01dFk47..jpg",
                    "M00/00/01/rBHmHV0-9ZuAVwLaAB-Pm7dC5ZM49..jpg"};
            for(String str : deleFiles){
                FastDFSClient.deleteFile("group1",str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
