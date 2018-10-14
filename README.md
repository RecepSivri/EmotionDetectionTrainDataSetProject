# EmotionDetectionTrainDataSetProject #
* This project is part of my graduation project which is Emotion detection from human facial expression.
* I train system using jaffe data set.
*  Use weka project to train system.
* To describing images (jaffe dataset and source image from camera) use lbp(local binary pattern) and its
derivatives (local ternary pattern, uniform local binary pattern and local directional pattern).
* I train data set with cross validation in train.java class( one selected women's pictures test system and other women's pictures used train system.)
* To provide %80 success rate I try another lbp derivates. so I get those success rates in below:

![ekran alintisi2](https://user-images.githubusercontent.com/16796421/46916407-130d2100-cfc3-11e8-9d8f-5403b5a3d267.PNG)

* Visualition of cross validation in below:

![ekran alintisi](https://user-images.githubusercontent.com/16796421/46916541-b0b52000-cfc4-11e8-9170-30f51d6f3f95.PNG)

* Then I use bag of visual words to provide %80 success rate then I implement K-means class and use weka api.
* I update Train classes in program. Then testing system for all clustering numbers in below:
![ekran alintisi3](https://user-images.githubusercontent.com/16796421/46917328-7ea8bb80-cfce-11e8-9e6f-2b816a1242ab.PNG)

* So I train sysytem with result of bag of visual words with cluster number 8 and rotational invariant uniform lbp as descriptor method.
