import dlib
import cv2
import glob
import numpy as np

class face_recognition:
    '''
    模型路径
    predictor_path = "shape_predictor_68_face_landmarks.dat"
    face_rec_model_path = "dlib_face_recognition_resnet_model_v1.dat"
    '''

    def __init__(self,predictor_path,face_rec_model_path):
        self.predictor_path = predictor_path
        self.face_rec_model_path = face_rec_model_path
        
        self.detector = dlib.get_frontal_face_detector()
        self.shape_predictor = dlib.shape_predictor(self.predictor_path)
        self.face_rec_model = dlib.face_recognition_model_v1(self.face_rec_model_path)

    def face_detection(self,url_img_1,url_img_2):
        img_path_list = [url_img_1,url_img_2]
        dist = []
        for img_path in img_path_list:
            img = cv2.imread(img_path)
            # 转换rgb顺序的颜色。
            b, g, r = cv2.split(img)
            img2 = cv2.merge([r, g, b])
            # 检测人脸
            faces = self.detector(img, 1)
            if len(faces):
                for index, face in enumerate(faces):
                    # # 提取68个特征点
                    shape = self.shape_predictor(img2, face)
                    # 计算人脸的128维的向量
                    face_descriptor = self.face_rec_model.compute_face_descriptor(img2, shape)

                    dist.append(list(face_descriptor))
            else:
                pass
        return dist

    # 欧式距离
    def dist_o(self,dist_1,dist_2):
        dis = np.sqrt(sum((np.array(dist_1)-np.array(dist_2))**2))
        return dis

    def score(self,url_img_1,url_img_2):
        url_img_1 = glob.glob(url_img_1)[0]
        url_img_2 = glob.glob(url_img_2)[0]
        data = self.face_detection(url_img_1,url_img_2)
        goal = self.dist_o(data[0],data[1])
        # 判断结果，若goal小于0.6的话是同一个人，否则不是。此处所用的是欧式距离判别
        return 1-goal
# 调用 模型下载地址：http://dlib.net/files/
predictor_path = "shape_predictor_68_face_landmarks.dat"
face_rec_model_path = "dlib_face_recognition_resnet_model_v1.dat"
face_ = face_recognition(predictor_path,face_rec_model_path)
img_1 = 'p1.png'
img_2 = 'p2.png'
goal = face_.score(img_1,img_2)
print("相似度为:"+str(goal))

if goal >0.6:
     print("是同一个人")  #大于百分之60为同一个人
else:
 print("不是同一个人")
