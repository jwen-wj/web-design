
import face_recognition
first_image = face_recognition.load_image_file("1.jpg")
second_image = face_recognition.load_image_file("6.jpg")
first_encoding = face_recognition.face_encodings(first_image)[0]
second_encoding = face_recognition.face_encodings(second_image)[0]
results = face_recognition.compare_faces([first_encoding], second_encoding)
print("相似度为:"+str(results))
