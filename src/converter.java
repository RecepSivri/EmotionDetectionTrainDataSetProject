/**
 * Created by Recep Sivri on 29.10.2017.
 */
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

import java.io.File;

public class converter {
    /**
     * takes 2 arguments:
     * - CSV input file
     * - ARFF output file
     */
    public static void convertArrf(String filename1,String filename2) throws Exception {



        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filename1));
        Instances data = loader.getDataSet();

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(filename2));
        saver.writeBatch();



    }
}