import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by Recep Sivri on 31.10.2017.
 */
public class TrainKxKLBP {


    public int testForSystem(int value, int split,int flag)
    {
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        imagesValue.put(1,"KA");
        imagesValue.put(2,"KL");
        imagesValue.put(3,"KM");
        imagesValue.put(4,"KR");
        imagesValue.put(5,"MK");
        imagesValue.put(6,"NA");
        imagesValue.put(7,"NM");
        imagesValue.put(8,"TM");
        imagesValue.put(9,"UY");
        imagesValue.put(10,"YM");
        int []lbpform;
        int []lbpform2;
        Lbp lbp1=new Lbp();
        Image img;
        Image img2;
        InverseUniform un=new InverseUniform();
        int j=0;
        int [][]res;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("test.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        UniformValues uniform=new UniformValues();
        System.out.println("uniform size="+uniform.uniforms.size());
        int i;
        if(flag==1)
        {
            for( i=0;i<256;++i)
            {
                sb.append(i);
                sb.append(',');
            }
            sb.append("emotion");
            sb.append("\n");
        }
        else
        if(flag==2)
        {
            for( i=0;i<un.inverseUniforms.size();++i)
            {
                sb.append("u"+un.inverseUniforms.get(i));
                sb.append(',');
            }
            sb.append("emotion");
            sb.append("\n");
        }

        File folder = new File("src//data");
        File[] listOfFiles = folder.listFiles();

        int k=0;
        int index = 0;

        for ( i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&listOfFiles[i].getName().substring(0,2).equals(imagesValue.get(value))) {

                System.out.println(listOfFiles[i].getName()+"------"+listOfFiles[i].getName().substring(3,5)+
                        "------"+listOfFiles[i].getName().substring(0,2));
                img = Load.invoke("src//data//"+listOfFiles[i].getName());
                res=ImageSplitter.returnsplitresult(img,split);
                //Display2D.invoke(img,returnEmotion(listOfFiles[i].getName().substring(3,5)));
                lbpform=res[0];
                lbpform2=res[1];
                if(flag==1)
                {
                    for( int l=0;l<lbpform.length;++l)
                    {
                        sb.append(lbpform[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");

                }
                else
                if(flag==2)
                {
                    for( int l=0;l<lbpform2.length;++l)
                    {
                        sb.append(lbpform2[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");
                }

            }
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println(k);
        return index;
    }
    public int trainForSystem(int value, int split, int flag)
    {
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        imagesValue.put(1,"KA");
        imagesValue.put(2,"KL");
        imagesValue.put(3,"KM");
        imagesValue.put(4,"KR");
        imagesValue.put(5,"MK");
        imagesValue.put(6,"NA");
        imagesValue.put(7,"NM");
        imagesValue.put(8,"TM");
        imagesValue.put(9,"UY");
        imagesValue.put(10,"YM");
        int []lbpform;
        int []lbpform2;
        Lbp lbp1=new Lbp();
        Image img;
        Image img2;
        int [][]res;
        InverseUniform un=new InverseUniform();
        int j=0;

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("train.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        int i;

        UniformValues uniform=new UniformValues();

        if(flag==1)
        {
            for( i=0;i<256;++i)
            {
                sb.append(i);
                sb.append(',');
            }
            sb.append("emotion");
            sb.append("\n");
        }
        else
        if(flag==2)
        {
            for( i=0;i<un.inverseUniforms.size();++i)
            {
                sb.append("u"+un.inverseUniforms.get(i));
                sb.append(',');
            }
            sb.append("emotion");
            sb.append("\n");
        }



        File folder = new File("src//data");
        File[] listOfFiles = folder.listFiles();

        int k=0;
        int index = 0;

        for ( i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&!listOfFiles[i].getName().substring(0,2).equals(imagesValue.get(value))) {

                System.out.println(listOfFiles[i].getName()+"------"+listOfFiles[i].getName().substring(3,5)+
                        "------"+listOfFiles[i].getName().substring(0,2));
                img = Load.invoke("src//data//"+listOfFiles[i].getName());
                res=ImageSplitter.returnsplitresult(img,split);
                //Display2D.invoke(img,returnEmotion(listOfFiles[i].getName().substring(3,5)));
                lbpform=res[0];
                int total=0;

                for(int c=0;c<lbpform.length;++c)
                    total=total+lbpform[c];

                System.out.println("total===>"+total);
                lbpform2=res[1];

                if(flag==1)
                {
                    for( int l=0;l<lbpform.length;++l)
                    {
                        sb.append(lbpform[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");

                }
                else
                if(flag==2)
                {
                    for( int l=0;l<lbpform2.length;++l)
                    {
                        sb.append(lbpform2[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");
                }
            }
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println(k);
        return index;
    }

    private String returnEmotion(String em)
    {
        if(em.equals("AN"))
            return "ANGRY";
        else
        if(em.equals("DI"))
            return "DISTINGUISH";
        else
        if(em.equals("FE"))
            return "FEAR";
        else
        if(em.equals("HA"))
            return "HAPPY";
        else
        if(em.equals("NE"))
            return "NEUTRAL";
        else
        if(em.equals("SA"))
            return "SAD";
        else
        if(em.equals("SU"))
            return "SUPRISED";
        else
            return "NULL";
    }
}
