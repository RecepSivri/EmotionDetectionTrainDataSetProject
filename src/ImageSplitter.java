import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

import java.util.ArrayList;

/**
 * Created by Recep Sivri on 30.10.2017.
 */
public class ImageSplitter {

    public  static int [][] returnsplitresult(Image image , int split )
    {
        int [][] returnValue = new int [2][];
        ArrayList<Image> img=splitImage(image,split);
        ArrayList<int[]> lbpform=new ArrayList<>();
        ArrayList<int[]> lbpform2=new ArrayList<>();

        Lbp lbp1=new Lbp();
        Image img2;
        int []totalUniform1= new int[256];
        int []temp1= new int[256];
        int []totalUniform2= new int[59];
        int []temp2= new int[59];
        for(int j=0;j<img.size();++j)
        {
            lbpform.add(new int[256]);
            lbpform2.add(new int[59]);
        }

        for(int i=0;i<img.size();++i)
        {
            img2 = lbp1.convolutionLbp(img.get(i));
            temp1 = lbp1.returnPropertyVectorNonUniform(img2);
            for (int h = 0; h < temp1.length; ++h)
                totalUniform1[h] = totalUniform1[h] + temp1[h];
            temp2 = lbp1.returnPropertyVectorUniform(img2);

            for (int h = 0; h < temp2.length; ++h)
                totalUniform2[h] = totalUniform2[h] + temp2[h];
        }


        returnValue[0]=totalUniform1;
        returnValue[1]=totalUniform2;
        return returnValue;
    }
    public static   ArrayList<Image> splitImage(Image img ,int split) {
        ArrayList<Image> images = new ArrayList<Image>();
        Image img2 = null;
        img2 = img.newInstance(img.getXDim() / split, img.getYDim() / split, 1);
        int counter = 0;
        for (int i = 0; i < split*(img.getXDim() / split); i = i + img.getXDim() / split) {
            for (int j = 0; j < split*(img.getYDim()/split); j = j + img.getYDim() / split) {
                ++counter;
                int number1 = 0, number2 = 0;
                if (i + img.getXDim() / split > img.getXDim())
                    number1 = img.getXDim();
                else
                    number1 = i + img.getXDim() / split;

                if (j + img.getYDim() / split > img.getYDim())
                    number2 = img.getYDim();
                else
                    number2 = j + img.getYDim() / split;
                for (int k = i; k < number1; ++k) {
                    for (int l = j; l < number2; ++l) {
                        img2.setXYByte(l % (img.getYDim() / split), k % (img.getXDim() / split), img.getXYByte(l, k));
                    }
                }
                images.add(img2.newInstance(true));

            }

        }
        return images;
    }

}
