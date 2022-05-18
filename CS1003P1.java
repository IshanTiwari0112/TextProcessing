import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class CS1003P1{
    public static void main(String[] args)throws IOException{ 
        //testDice();methods which were used in testing
        //testBigrams();methods which were used in testing
        
        if(args.length == 0){ //checks length of input in command line parameter in quotes-if no input then message displayed and program terminated.
            System.out.println("Invalid input: enter a sentence as a quoted command line parameter");
            System.exit(0);
        } 
        ArrayList<String> finalSentence=new ArrayList<String>();//Creates an arraylist to store the final sentence required after checking spelling of each word
        StringTokenizer st = new StringTokenizer(args[0], " ");//defines the string tokenizer to parse the entered string into words
        while (st.hasMoreTokens()) {//while loop makes sue all words in the sentence are parsed
            String data = st.nextToken();//words is defined as data needed to use 
            data = data.toLowerCase();//converts to lower case to help string matching
            List<String> lines = dictionary("words_alpha.txt"); //dictionary to check spelling
            float similarity; //float to store how similar strings are after calculation of the Sorenson-dice coefficient
            float mostSimilarity = -1; //keeps track of the closest spelled word in the dictionary
            String mostSimilarWord = ""; //stores the closest spelled word in the dictionary
            /*the following loop loops through the dictionary while performing all required bigram and string similarity 
            calculations to check the spelling of each word that has been input*/
            for( String line :  lines){ 
                Set<String>bigramsOfLine=bigrams(line);//creates the bigram set for each line in the dictionary                    
                similarity=diceCoefficient(bigrams(data),bigramsOfLine); //calculates the string similarity using the sorenson-dice coefficient and stores it in a float value
                if(similarity>mostSimilarity){//loop to keep track of most similar word
                    mostSimilarity=similarity;
                    mostSimilarWord=line;      
                }
            } 
            finalSentence.add(mostSimilarWord);  //adds the most similar string to the arrayList finalSentence
        }
        String delim = " "; //delimiter definition to print out final sentence from the arrayList
        String sentence = String.join(delim,finalSentence); //forms the sentence with each checked word and adds a space in between them
        System.out.println(sentence);   
    }
    
        public static List<String> dictionary(String Path){ //method to create a list of all the words in the given dictionary
            try {
                List<String> lines = Files.readAllLines(Paths.get(Path)); //reads all lines from the file
                return lines;
            } catch(IOException e) {//catches exception if file is not found and asks user to check path given
                System.out.println("file not found : check path");
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    
        public static Set<String> bigrams(String word){//method to calculate bigrams of any word
        
            word="^"+word+"$";// generates the top and tail functionality in the bigram
            Set<String> set=new HashSet<>(); //set to store the generated bigrams
            
            for(int i=0; i< word.length()-1;i++) {
               
                set.add(""+word.charAt(i)+word.charAt(i+1));//adding the bigrams on each iteration to the set
            }
            return set;
        }

        public static float diceCoefficient(Set<String> word1,Set<String> word2){//method to calculate sorenson-dice coefficient to calculate string similarity
            
            Set<String> intersection= new HashSet<>(word1); //stores the set in a new hashset to be used to calculate the intersection of both sets
            intersection.retainAll(word2);//finding intersection of the 2 sets
            
            if(intersection.size()==0){
                return 0;
            }
            else{
                return((float) (2*intersection.size())/(word1.size()+word2.size())); //calculates the sorenson-dice coefficient and returns the value
            }
        }
    }
/* methods below were used extensively in testing
        public static void testDice(){
            float test1=diceCoefficient(bigrams("work"), bigrams("job"));
            System.out.println(test1);
        }
        public static void testBigrams(){
            System.out.println(bigrams("crisps"));
        }
    }
*/


       