package com.bbdig.util;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.model.v20160308.ImageDetectionRequest;
import com.aliyuncs.green.model.v20160308.ImageDetectionResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hyliu on 16/3/2.
 * 同步图片检测结果调用示例，调用会实时返回检测结果
 */
public class OcrUtil {

    public static void main(String[] args) {
        //请替换成你自己的accessKeyId、accessKeySecret
        IClientProfile profile = DefaultProfile.getProfile("cn-beijing", "ZKXOPU4vIcUfVmHP", "TVYzzXQdLLZH8UKotNu2e7bjuuhMH4");
        IAcsClient client = new DefaultAcsClient(profile);

        ImageDetectionRequest imageDetectionRequest = new ImageDetectionRequest();

        /**
         * 是否同步调用
         * false: 同步
         */
        imageDetectionRequest.setAsync(false);


        /**
         * 同步图片检测支持多个场景:
         * porn:  黄图检测
         * ocr:  图文识别
         */
        imageDetectionRequest.setScenes(Arrays.asList("ocr"));


        /**
         * 同步图片检测一次只支持单张图片进行检测
         */
        imageDetectionRequest.setImageUrls(Arrays.asList("http://cas.baidu.com/?action=image&key=1461243904"));

        try {
            ImageDetectionResponse imageDetectionResponse = client.getAcsResponse(imageDetectionRequest);

            System.out.println(new Gson().toJson(imageDetectionResponse));
            if("Success".equals(imageDetectionResponse.getCode())){
                List<ImageDetectionResponse.ImageResult> imageResults = imageDetectionResponse.getImageResults();
                if(imageResults != null && imageResults.size() > 0) {
                    //同步图片检测只有一个返回的ImageResult
                    ImageDetectionResponse.ImageResult imageResult = imageResults.get(0);

                    //porn场景对应的检测结果放在pornResult字段中
                    /**
                     * 黄图检测结果
                     */
                    ImageDetectionResponse.ImageResult.PornResult pornResult = imageResult.getPornResult();
                    if(pornResult != null) {
                        /**
                         * 绿网给出的建议值, 0表示正常，1表示色情，2表示需要review
                         */
                        Integer label = pornResult.getLabel();

                        /**
                         * 黄图分值, 0-100
                         */
                        float rate = pornResult.getRate();

                        // 根据业务情况来做处理
                    }
                    /**
                     *ocr检测结果
                     **/
                    System.out.println(new Gson().toJson( imageResult.getOcrResult())); 

                }

            }else{
                /**
                 * 检测失败
                 */
            }

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}