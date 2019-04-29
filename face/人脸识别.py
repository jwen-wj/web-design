
import base64

import json

import requests

 

class BaiduPicIndentify:

    def __init__(self,img):

        self.AK = "Y2NVpwyaQroVUzBXm5lgPu1K"

        self.SK = "dLUGZQafmxChDP6D5XadVmLdf0MVpAMc"

        self.img_src = img

        self.headers = {

            "Content-Type": "application/json; charset=UTF-8"

        }

 

    def get_accessToken(self):

        host = 'https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=' + self.AK + '&client_secret=' + self.SK

        response = requests.get(host, headers=self.headers)

        json_result = json.loads(response.text)

        return json_result['access_token']

 

    def img_to_BASE64(slef,path):

        with open(path,'rb') as f:

            base64_data = base64.b64encode(f.read())

            return base64_data

 

    def detect_face(self):

        # 人脸检测与属性分析

        img_BASE64 = self.img_to_BASE64(self.img_src)

        request_url = "https://aip.baidubce.com/rest/2.0/face/v3/detect"

        post_data = {

            "image": img_BASE64,

            "image_type": "BASE64",

            "face_field": "gender,age,beauty,gender,race,expression",

            "face_type": "LIVE"

        }

        access_token = self.get_accessToken()

        request_url = request_url + "?access_token=" + access_token

        response = requests.post(url=request_url, data=post_data, headers=self.headers)

        json_result = json.loads(response.text)

        if json_result['error_msg']!='pic not has face':

            print("图片中包含人脸数：", json_result['result']['face_num'])

            print("图片中包含人物年龄：", json_result['result']['face_list'][0]['age'])

            print("图片中包含人物颜值评分：", json_result['result']['face_list'][0]['beauty'])

            print("图片中包含人物性别：", json_result['result']['face_list'][0]['gender']['type'])

            print("图片中包含人物种族：", json_result['result']['face_list'][0]['race']['type'])

            print("图片中包含人物表情：", json_result['result']['face_list'][0]['expression']['type'])

 

if __name__=='__main__':

    img_src='11.jpg'

    baiduDetect = BaiduPicIndentify(img_src)

    baiduDetect.detect_face()

 
