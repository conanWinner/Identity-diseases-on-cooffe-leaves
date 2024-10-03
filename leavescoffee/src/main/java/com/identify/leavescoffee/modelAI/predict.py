import sys
import numpy as np
from PIL import Image
import tensorflow as tf
import io
import os
os.environ['TF_ENABLE_ONEDNN_OPTS'] = '1'

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Tải mô hình
MODEL = tf.keras.models.load_model("D:/_DEV/Springboot/leavescoffee/leavescoffee/src/main/java/com/identify/leavescoffee/modelAI/coffee_densenet_3.keras")


# MODEL.summary()

CLASS_NAMES = ["miner", "nodisease", "phoma", "rust"]

# Hàm đọc ảnh
def read_file_as_image(filename):
    image = Image.open(filename)
    image = image.resize((512, 256))
    image = np.expand_dims(np.array(image), axis=0)
    return image

# Lấy đường dẫn file ảnh từ tham số dòng lệnh
image_path = sys.argv[1]
image = read_file_as_image(image_path)

# Dự đoán
predictions = MODEL.predict(image)
predicted_class = CLASS_NAMES[np.argmax(predictions[0])]
confidence = np.max(predictions[0])

# In ra kết quả (để Java có thể nhận)
print("=======")
print(f"{predicted_class},{confidence}")
