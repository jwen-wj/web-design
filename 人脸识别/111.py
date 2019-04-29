
# -*- coding: UTF-8 -*-  

 
import dlib
from aip import AipFace

import cv2

import base64
from json import JSONDecoder
import sys

 

sys.setrecursionlimit(1000000) 

 

#初始化aipFace

 

APP_ID = '11222528'

API_KEY = 'l1CAmQTvS5IAgWeurQv2WPyF'

SECRET_KEY = 'dmZ9zDvoVqImWkoSxGdYKHaF6Zwh4vaG'

 

aipFace = AipFace(APP_ID, API_KEY, SECRET_KEY)

 

 

#人脸检测部分

def get_file_content(filePath):

    with open(filePath, 'rb') as fp:
        date = str(base64.b64encode(fp.read()), encoding="utf-8")
        return date

 

options = {

    'max_face_num': 1, # 人脸数量

    'face_fields': "expression,faceshape",

}		

		

result1 = aipFace.detect(get_file_content('1.jpg'), options)

# 解析位置信息1

location1=result1['result']['face_list'][0]['location']

left_top1=(int(location1['left']),int(location1['top']))

right_bottom1=(int(left_top1[0]+location1['width']),int(left_top1[1]+location1['height']))

 

img1=cv2.imread('1.jpg')

cv2.rectangle(img1,left_top1,right_bottom1,(0,255,0),2)

cv2.imshow('img1',img1)	

 

result2 = aipFace.detect(get_file_content('11.jpg'), options)

# 解析位置信息2

location2=result2['result']['face_list'][0]['location']

left_top2=(int(location2['left']),int(location2['top']))

right_bottom2=(int(left_top2[0]+location2['width']),int(left_top2[1]+location2['height']))

 

img2=cv2.imread('11.jpg')

cv2.rectangle(img2,left_top2,right_bottom2,(0,255,0),2)

cv2.imshow('img2',img2)		

		

		

		

#人俩比对部分		

result = aipFace.match([

    {

        'image' : base64.b64encode(open('1.jpg', 'rb').read()).decode(),

        'image_type': 'BASE64',

    },

    {

       'image' : base64.b64encode(open('11.jpg', 'rb').read()).decode(),

        'image_type': 'BASE64',

    }

])

 

#print(result)

print('\n')

aaa=result['result']['score']

print ("相似得分为："),print (aaa)

if aaa>80:

	print ("图1和图2是同一个人.\n")

else:

	print ("图1和图2不是同一个人.\n")

cv2.waitKey(100000)
