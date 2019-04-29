import base64
from aip import AipFace

APP_ID = '11222528'
API_KEY = 'l1CAmQTvS5IAgWeurQv2WPyF'
SECRET_KEY = 'dmZ9zDvoVqImWkoSxGdYKHaF6Zwh4vaG'
client = AipFace(APP_ID, API_KEY, SECRET_KEY)
IMAGE_TYPE='BASE64'

def judge(pathone,pathtwo):
    f1 = open(pathone,'rb')
    f2 = open(pathtwo,'rb')
    img1 = base64.b64encode(f1.read())
    img2 = base64.b64encode(f2.read())
    params = [{"image":str(img1,'utf-8'),"image_type":IMAGE_TYPE},{"image":str(img2,'utf-8'),"image_type":IMAGE_TYPE}]
    result = client.match(params);
    print(result)
    score=result['result']['score'];
    print('相似度为:'+str(score));
    if score > 80:
        print("同一個人")
    else:
        print("不是同一個人")
        

judge('1.jpg','2.jpg')
