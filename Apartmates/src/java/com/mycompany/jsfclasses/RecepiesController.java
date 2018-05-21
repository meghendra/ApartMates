package com.mycompany.jsfclasses;

import com.mycompany.entityclasses.GroceryItem;
import com.mycompany.entityclasses.Ingredient;
import com.mycompany.entityclasses.Recepie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author megh
 * Handles the Recipe queries to Edamam and Walmart Api
 * Extracts ingredients list from the retrieved json data
 */
@SessionScoped

@Named(value = "recepiesController")

public class RecepiesController implements Serializable {

    private final String edamam_url1 = "https://api.edamam.com/search?q=";
    private final String edamam_url2 = "&app_id=3f14c961&app_key=f2d00aa591142323e8875c24c3ec4e0d";

    private final String walmart_url1 = "http://api.walmartlabs.com/v1/search?query=";
    private final String walmart_url2 = "&format=json&categoryId=976759&apiKey=fyngq4hwugxbqev6v5v3dju8";

    private String searchItem1;
    private String searchItem2;
    private String searchItem3;
    private String searchItem4;
    private String searchItem5;

    private static final String[] stopwords = {
        "inch", "piece", "grated", "tablespoons", "tablespoon",
        "teaspoons", "teaspoon", "cup", "cups", "pint", "mashed", "g", "kg", "ml", "l", "beaten",
        "melted", "stirred", "sifted", "tbsp", "freshly", "fresh", "quartered", "oz", "tbs", "sliced",
        "chopped", "finely", "garnish", "pounds", "pound", "lb", "peeled", "smashed", "halves", "taste",
        "ounce", "left", "undisturbed", "hours", "hour", "minutes", "minute", "seconds", "second", "few",
        "small", "large", "baby",
        "a", "as", "able", "about", "above", "according",
        "accordingly", "across", "actually", "after", "afterwards", "again", "against",
        "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also",
        "although", "always", "am", "among", "amongst", "an", "and", "another", "any",
        "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart",
        "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask",
        "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because",
        "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe",
        "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by",
        "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly",
        "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering",
        "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently",
        "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing",
        "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else",
        "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody",
        "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff",
        "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth",
        "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes",
        "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt",
        "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter",
        "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully",
        "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in",
        "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into",
        "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept",
        "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let",
        "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe",
        "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself",
        "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new",
        "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere",
        "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other",
        "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular",
        "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que",
        "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively",
        "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing",
        "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven",
        "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone",
        "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying",
        "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx",
        "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter",
        "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think",
        "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together",
        "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately",
        "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various",
        "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve",
        "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where",
        "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while",
        "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within",
        "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve",
        "your", "yours", "yourself", "yourselves", "zero"};

    private static Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));

    private List<Recepie> foundRecipes;
    private List<GroceryItem> computedGroceryList;
    private Float totalCost;

    private String statusMessage;

    public void init() {
        foundRecipes = null;
        computedGroceryList = null;
        totalCost = null;
    }

    public void destroy() {
        init();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("Dashboard.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(RecepiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search() {

        statusMessage = "";
        
        computedGroceryList = null;
        totalCost = null;
        /*
        This constructor method creates movies and movieNames
        by using the JSON file returned from the RESTful web services at moviesRESTfulWebServices_url
         */
        JSONArray jsonSearchArray;
        foundRecipes = new ArrayList<Recepie>();
        List<String> searchStrings = new ArrayList<String>();

        if (!searchItem1.isEmpty()) {
            searchStrings.add(searchItem1);
        }
        if (!searchItem2.isEmpty()) {
            searchStrings.add(searchItem2);
        }
        if (!searchItem3.isEmpty()) {
            searchStrings.add(searchItem3);
        }
        if (!searchItem4.isEmpty()) {
            searchStrings.add(searchItem4);
        }
        if (!searchItem5.isEmpty()) {
            searchStrings.add(searchItem5);
        }

        /*
        ----------------------------------------------------------
        Create the movies list containing movie objects
        ----------------------------------------------------------
         */
        try {
            /*
            JSON data use the following notation:
            { }    represents a JavaScript object with its properties as Key:Value pairs
            [ ]    represents Array
            [{ }]  represents an Array of JavaScript objects
              :    separates Key from the Value
             */

            if (!searchStrings.isEmpty()) {
                int index = 0;
                for (String searchString : searchStrings) {

                    if (!searchString.isEmpty()) {
                        JSONObject jsonSearchObject = new JSONObject(readUrlContent(edamam_url1 + URLEncoder.encode(searchString, "UTF-8") + edamam_url2));
                        jsonSearchArray = new JSONArray(jsonSearchObject.getJSONArray("hits").toString());
                        if (jsonSearchArray.length() > 0) {
                            JSONObject recepieJsonObject = jsonSearchArray.optJSONObject(0).getJSONObject("recipe");
                            // System.out.println(recepieJsonObject);
                            int id = index;
                            String label = recepieJsonObject.optString("label");
                            String image = recepieJsonObject.optString("image");
                            String source = recepieJsonObject.optString("source");
                            String url = recepieJsonObject.optString("url");
                            String shareAs = recepieJsonObject.optString("shareAs");
                            String yield = recepieJsonObject.optString("yield");
                            Double calories = recepieJsonObject.optDouble("calories");

                            JSONArray dietLabelsJsonArray = recepieJsonObject.optJSONArray("dietLabels");
                            String dietLabels = getListOfStrings(dietLabelsJsonArray).toString().replace("[", "");
                            dietLabels = dietLabels.replace("]", "");

                            JSONArray healthLabelsJsonArray = recepieJsonObject.optJSONArray("healthLabels");
                            String healthLabels = getListOfStrings(healthLabelsJsonArray).toString().replace("[", "");;
                            healthLabels = healthLabels.replace("]", "");

                            JSONArray ingredientsJsonArray = recepieJsonObject.optJSONArray("ingredients");
                            List<Ingredient> ingredients = getListOfIngredients(ingredientsJsonArray);

                            Recepie r = new Recepie(id, label, image, source, url, shareAs, yield, dietLabels, healthLabels, ingredients, calories);

                            foundRecipes.add(r);
                        }
                    }
                    index++;
                }

            } else {
                statusMessage = "The Edamam and Walmart APIs are unreachable!";
                return;

            }
        } catch (Exception ex) {
            Logger.getLogger(RecepiesController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildGroceryList() {

        computedGroceryList = new ArrayList<GroceryItem>();

        if (!foundRecipes.isEmpty()) {
            for (Recepie r : foundRecipes) {
                List<Ingredient> li = r.getIngredients();
                for (Ingredient i : li) {
                    String[] words = i.getText().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                    words = removeDuplicates(words);
                    String result = "";
                    for (String word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        if (isStopword(word)) {
                            continue; //remove stopwords
                        }
                        result += (word + " ");
                    }
                    String walmartQueryTerm = result;

                    if (!walmartQueryTerm.isEmpty()) {
                        JSONObject jsonSearchObject;
                        try {
                            jsonSearchObject = new JSONObject(readUrlContent(walmart_url1 + URLEncoder.encode(walmartQueryTerm, "UTF-8") + walmart_url2));
                            if (jsonSearchObject != null) {
                                JSONArray jsonWalmartResultsArray = jsonSearchObject.optJSONArray("items");
                                if (jsonWalmartResultsArray != null) {
                                    JSONObject groceryItem = jsonWalmartResultsArray.optJSONObject(0);
                                    String groceryItemName = groceryItem.optString("name");
                                    String[] fields = groceryItem.optString("largeImage").split("\\?");
                                    String groceryItemImage = fields[0];
                                    Float groceryItemCost = (float) groceryItem.optDouble("salePrice");
                                    Float groceryItemWeight = i.getWeight();
                                    GroceryItem gi = new GroceryItem(groceryItemName, groceryItemImage, groceryItemCost, groceryItemWeight);
                                    computedGroceryList.add(gi);
                                }
                            }

                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(RecepiesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        totalCost = 0.0f;
        if (!computedGroceryList.isEmpty()) {
            List<GroceryItem> uniques = new ArrayList<GroceryItem>();
            for (GroceryItem element : computedGroceryList) {
                if (!uniques.contains(element)) {
                    totalCost = totalCost + element.getCost();
                    uniques.add(element);
                }
            }
            computedGroceryList = uniques;
        }
    }

    public static String[] removeDuplicates(String[] words) {
        Set<String> wordSet = new LinkedHashSet<String>();
        for (String word : words) {
            wordSet.add(word);
        }
        return wordSet.toArray(new String[wordSet.size()]);
    }

    /*
    ================
    Instance Methods
    ================
     */
    public static boolean isStopword(String word) {
        if (word.length() < 2) {
            return true;
        }
        if (word.charAt(0) >= '0' && word.charAt(0) <= '9') {
            return true; //remove numbers, "25th", etc
        }
        if (stopWordSet.contains(word)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the content of a given URL as String
     *
     * @param webServiceURL to retrieve the JSON data from
     * @return JSON data from the given URL as String
     * @throws Exception
     */
    public String readUrlContent(String webServiceURL) {
        /*
        reader is an object reference pointing to an object instantiated from the BufferedReader class.
        Currently, it is "null" pointing to nothing.
         */
        BufferedReader reader = null;

        try {
            // Create a URL object from the webServiceURL given
            URL url = new URL(webServiceURL);
            /*
            The BufferedReader class reads text from a character-input stream, buffering characters
            so as to provide for the efficient reading of characters, arrays, and lines.
             */
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Create a mutable sequence of characters and store its object reference into buffer
            StringBuilder buffer = new StringBuilder();

            // Create an array of characters of size 10240
            char[] chars = new char[10240];

            int numberOfCharactersRead;
            /*
            The read(chars) method of the reader object instantiated from the BufferedReader class
            reads 10240 characters as defined by "chars" into a portion of a buffered array.

            The read(chars) method attempts to read as many characters as possible by repeatedly
            invoking the read method of the underlying stream. This iterated read continues until
            one of the following conditions becomes true:

                (1) The specified number of characters have been read, thus returning the number of characters read.
                (2) The read method of the underlying stream returns -1, indicating end-of-file, or
                (3) The ready method of the underlying stream returns false, indicating that further input requests would block.

            If the first read on the underlying stream returns -1 to indicate end-of-file then the read(chars) method returns -1.
            Otherwise the read(chars) method returns the number of characters actually read.
             */
            while ((numberOfCharactersRead = reader.read(chars)) != -1) {
                buffer.append(chars, 0, numberOfCharactersRead);
            }

            // Return the String representation of the created buffer
            return buffer.toString();

        } catch (Exception e) {
            return "{}";
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException ex) {
                    Logger.getLogger(RecepiesController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Return the list of cast names of a movie as a list of strings for JSON
     * array of cast details received from the API
     *
     * @param anyJsonArray to retrieve the JSON data about the cast
     * @return a List of Strings
     */
    private List<String> getListOfStrings(JSONArray anyJsonArray) {

        //Initialize an empty list of strings and an index
        List<String> listOfStrings = new ArrayList<>();
        int index = 0;

        // Check if the JSON array is not empty
        if (anyJsonArray.length() > index) {

            while (anyJsonArray.length() > index) {

                String item = anyJsonArray.optString(index);
                listOfStrings.add(item);

                index++;
            }
        }

        return listOfStrings;
    }

    /**
     * Return the list of cast names of a movie as a list of strings for JSON
     * array of cast details received from the API
     *
     * @param ingredientJsonArray to retrieve the JSON data about the cast
     * @return a List of Ingredients
     */
    private List<Ingredient> getListOfIngredients(JSONArray ingredientJsonArray) {

        //Initialize an empty list of strings and an index
        List<Ingredient> listOfIngredients = new ArrayList<Ingredient>();
        int index = 0;

        // Check if the JSON array is not empty
        if (ingredientJsonArray.length() > index) {

            while (ingredientJsonArray.length() > index) {

                JSONObject item = ingredientJsonArray.optJSONObject(index);
                String ingredientText = item.optString("text");
                Float ingredientWeight = Float.parseFloat(item.optString("weight"));
                Ingredient i = new Ingredient(ingredientText, ingredientWeight);
                listOfIngredients.add(i);

                index++;
            }
        }

        return listOfIngredients;
    }

    public String getSearchItem1() {
        return searchItem1;
    }

    public void setSearchItem1(String searchItem1) {
        this.searchItem1 = searchItem1;
    }

    public String getSearchItem2() {
        return searchItem2;
    }

    public void setSearchItem2(String searchItem2) {
        this.searchItem2 = searchItem2;
    }

    public String getSearchItem3() {
        return searchItem3;
    }

    public void setSearchItem3(String searchItem3) {
        this.searchItem3 = searchItem3;
    }

    public String getSearchItem4() {
        return searchItem4;
    }

    public void setSearchItem4(String searchItem4) {
        this.searchItem4 = searchItem4;
    }

    public String getSearchItem5() {
        return searchItem5;
    }

    public void setSearchItem5(String searchItem5) {
        this.searchItem5 = searchItem5;
    }

    public List<Recepie> getFoundRecipes() {
        return foundRecipes;
    }

    public void setFoundRecipes(List<Recepie> foundRecipes) {
        this.foundRecipes = foundRecipes;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<GroceryItem> getComputedGroceryList() {
        return computedGroceryList;
    }

    public void setComputedGroceryList(List<GroceryItem> computedGroceryList) {
        this.computedGroceryList = computedGroceryList;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

}
