

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.supportVector.CachedKernel;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by Alper on 25.12.2017.
 */
public class LandUseClassifier {
    private String trainPath;
    private String  testPath;
    private int numOfFEV;

    public  LandUseClassifier(String  trainPath , String testPath, int numOfFEV){
        this.testPath = testPath;
        this.trainPath = trainPath;
        this.numOfFEV = numOfFEV;

    }


    public void doClassification() throws Exception {
//        // TODO: 26.12.2017 KAPPA
//        DecimalFormat df = new DecimalFormat("#.####");
//        double kappa1 = computeKappa(trainPath, testPath,numOfFEV);
//        System.err.println("KAPPA : " + df.format(kappa1));

        RandomForest randomForest = new RandomForest();
        randomForest.setNumFeatures(5);
        randomForest.setNumIterations(100);
        randomForest.setSeed(1);

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("train2.csv"));
        Instances trainInstance = loader.getDataSet();
        trainInstance.setClassIndex(trainInstance.numAttributes() -1 );
        loader.setSource(new File("test2.csv"));
        Instances unlabeledData = loader.getDataSet();
        unlabeledData.setClassIndex(unlabeledData.numAttributes() -1 );
        randomForest.buildClassifier(trainInstance);


        Instances result = new Instances(unlabeledData);

        // label instances
        for (int i = 0; i < unlabeledData.numInstances(); i++) {
            //get actual instance
            Instance actualIns = unlabeledData.instance(i);
            //get the actual class
            double actualClass = actualIns.classValue();
            String actualVal = unlabeledData.classAttribute().value((int) actualClass);
            //do classification
            double clsLabel = randomForest.classifyInstance(unlabeledData.instance(i));
            result.instance(i).setClassValue(clsLabel);
            //get the result class
            double resultClass = result.instance(i).classValue();
            String resultVal = unlabeledData.classAttribute().value((int) resultClass);

            //show the result
            System.err.println(result.instance(i));
            System.err.println("ACTUAL : " + actualVal + "  PREDICT : " + resultVal);
        }


        // save labeled data
        BufferedWriter writer = new BufferedWriter(
                new FileWriter("result.arff"));
        writer.write(result.toString());
        writer.newLine();
        writer.flush();
        writer.close();



    }
    private  double computeKappa(String trainPath, String testPath, int numberOfRFFeatures) {
        // 1NN
        //String[] options = {"-t", trainPath, "-T", testPath, "-o", "-v"};				// 1NN

        // Random Forest
        String[] options = {"-t", trainPath, "-T", testPath, "-o", "-v", "-I", "100", "-K", String.valueOf(numberOfRFFeatures), "-S", "1"};

        try{
            //String results = Evaluation.evaluateModel(new weka.classifiers.lazy.IBk(), options);							// 1NN
            String results = Evaluation.evaluateModel(new weka.classifiers.trees.RandomForest(), options);				// RF

            String[] lines = results.split("\n");
            double kappa = 0.0;

            for(int j = 0; j < lines.length; j++){

                if(lines[j].startsWith("Kappa")){
                    String[] tokens = lines[j].split("\\s+");
                    kappa = Double.parseDouble(tokens[2]);
                    //System.err.println();
                    return kappa;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    private RandomForest buildClassifier(String trainPath,int numberOfRFFeatures) throws Exception {
//        String[] options = {"-t", trainPath, "-T", testPath, "-o", "-v", "-I", "100", "-K", String.valueOf(numberOfRFFeatures), "-S", "1"};
//        RandomForest randomForest = new RandomForest();
//        randomForest.setOptions(options);
        RandomForest randomForest = new RandomForest();
        randomForest.setNumFeatures(numberOfRFFeatures);
        randomForest.setNumIterations(100);
        randomForest.setSeed(1);

        Instances trainInstance = new Instances(new BufferedReader( new FileReader(trainPath)));
        trainInstance.setClassIndex(trainInstance.numAttributes() -1 );

        randomForest.buildClassifier(trainInstance);


        return randomForest;
    }

    private Instances loadUnlabeledData(String testPath) throws IOException {
        Instances unlabeledIns = new Instances( new BufferedReader(new FileReader(testPath)));
        unlabeledIns.setClassIndex(unlabeledIns.numAttributes() -1 );
        return  unlabeledIns;
    }
}