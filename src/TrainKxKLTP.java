import vpt.Image;
import vpt.algorithms.io.Load;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Recep Sivri on 18.11.2017.
 */
public class TrainKxKLTP {
    public int testForSystem(int value, int split,int flag,int threshold)
    {
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        ArrayList<Image> images;
        ArrayList<int[]> ltpform=new ArrayList<>();
        int []total=new int[256];
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
        LTP ltp=new LTP();
        Image img;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("test.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
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
            for( i=0;i<256;++i)
            {
                sb.append(i);
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
                images=ImageSplitter.splitImage(img,split);
                for( int l=0;l<total.length;++l)
                    total[l]=0;
                if(flag==1)
                {
                    for(int m=0;m<images.size();++m)
                        ltpform.add(new int[256]);
                    for(int m=0;m<images.size();++m)
                        ltpform.set(m,ltp.returnUpperHistogram(images.get(m),threshold));
                    for( int m=0;m<ltpform.size();++m)
                        for( int l=0;l<total.length;++l)
                            total[l]=total[l]+ltpform.get(m)[l];

                    for( int l=0;l<total.length;++l)
                    {
                        sb.append(total[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");

                }
                else
                if(flag==2)
                {
                    for(int m=0;m<images.size();++m)
                        ltpform.add(new int[256]);
                    for(int m=0;m<images.size();++m)
                        ltpform.set(m,ltp.returnLowerHistogram(images.get(m),threshold));
                    for( int m=0;m<ltpform.size();++m)
                        for( int l=0;l<total.length;++l)
                            total[l]=total[l]+ltpform.get(m)[l];

                    for( int l=0;l<total.length;++l)
                    {
                        sb.append(total[l]);
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
    public int trainForSystem(int value, int split, int flag,int threshold)
    {
        HashMap<Integer,String> imagesValue=new HashMap<Integer,String>();
        ArrayList<Image> images;
        ArrayList<int[]> ltpform=new ArrayList<>();
        int []total=new int[256];
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
        LTP ltp=new LTP();
        Image img;
        int [][]res;

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("train.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
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
            for( i=0;i<256;++i)
            {
                sb.append(i);
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


                images=ImageSplitter.splitImage(img,split);
                for( int l=0;l<total.length;++l)
                    total[l]=0;
                if(flag==1)
                {
                    for(int m=0;m<images.size();++m)
                        ltpform.add(new int[256]);
                    for(int m=0;m<images.size();++m)
                        ltpform.set(m,ltp.returnUpperHistogram(images.get(m),threshold));
                    for( int m=0;m<ltpform.size();++m)
                        for( int l=0;l<total.length;++l)
                            total[l]=total[l]+ltpform.get(m)[l];

                    int total2=0;
                    for( int l=0;l<total.length;++l)
                        total2=total2+total[l];
                    System.out.println("total="+total2);

                    for( int l=0;l<total.length;++l)
                    {
                        sb.append(total[l]);
                        sb.append(',');
                    }
                    sb.append(returnEmotion(listOfFiles[i].getName().substring(3,5)));
                    sb.append("\n");

                }
                else
                if(flag==2)
                {
                    for(int m=0;m<images.size();++m)
                        ltpform.add(new int[256]);
                    for(int m=0;m<images.size();++m)
                        ltpform.set(m,ltp.returnLowerHistogram(images.get(m),threshold));
                    for( int m=0;m<ltpform.size();++m)
                        for( int l=0;l<total.length;++l)
                            total[l]=total[l]+ltpform.get(m)[l];

                    for( int l=0;l<total.length;++l)
                    {
                        sb.append(total[l]);
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
