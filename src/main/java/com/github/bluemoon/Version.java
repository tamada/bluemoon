package com.github.bluemoon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an artifact version.
 * 
 * @author Haruaki Tamada
 */
public class Version implements Serializable, Comparable<Version>{
    private static final long serialVersionUID = 7980518164461500648L;

    private String original;
    private String separator;
    private int[] numbers;
    private String rest;

    public Version(String versionString){
        this(versionString, "[-.]");
    }

    public Version(String versionString, String separatorRegex){
        original = versionString;
        this.separator = separatorRegex;
        parse(original, separatorRegex);
    }

    /**
     * This method returns true when this object is SNAPSHOT version.
     * When the version string contains ``snapshot,'' this object is SNAPSHOT.
     * 
     * @return returns true when this object is SNAPSHOT version.
     */
    public boolean isSnapshot(){
        return original.toLowerCase().endsWith("snapshot");
    }

    /**
     * This method returns true when this object is release candidate version.
     * When the version string matches regular expression ``rc[-_]?[0-9]*'', 
     * this object is release candidate, 
     * 
     * @return returns true when this object is release candidate version.
     */
    public boolean isReleaseCandidate(){
        return original.toLowerCase().matches(".*[-_]?rc[-_]?[0-9]*$");
    }

    /**
     * returns version string.
     * @return version string.
     */
    public String toString(){
        return original;
    }

    public int hashCode(){
        return original.hashCode() | separator.hashCode();
    }

    public boolean isAfterThan(Version v){
        return compareTo(v) > 0;
    }

    public boolean isBeforeThan(Version v){
        return compareTo(v) < 0;
    }

    public boolean equals(Object o){
        return o instanceof Version && compareTo((Version)o) == 0;
    }

    /**
     * <p>
     * Let <code>v1</code> and <code>v2</code> be objects of this class
     * with <code>1.1.0</code>, and <code>1.2.0</code>.
     * Then, following results are obtained.
     * </p>
     * <ul>
     *   <li>v1.compareTo(v2): negative integer</li>
     *   <li>v1.compareTo(v1): 0</li>
     *   <li>v2.compareTo(v1): positive integer</li>
     * </ul>
     * @see isBeforeThan
     * @see isAfterThan
     */
    public int compareTo(Version version){
        int value = compareNumbers(version);
        if(value == 0){
            // ex. this: 1.0, version: 1.0
            if(rest == null && version.rest == null){
                value = 0;
            }
            // ex. this: 1.0, version: 1.0-alpha
            else if(rest == null && version.rest != null){
                value = 1;
            }
            // ex. this: 1.0-alpha, version: 1.0
            else if(rest != null && version.rest == null){
                value = -1;
            }
            // ex. this: 1.0-alpha, version: 1.0-rc
            else{
                value = rest.compareTo(version.rest);
            }
        }
        return value;
    }

    /**
     * Comparing number part.
     */
    private int compareNumbers(Version version){
        // comparing numbers.
        for(int i = 0; i < numbers.length && i < version.numbers.length; i++){
            if(numbers[i] < version.numbers[i]){
                return -1;
            }
            else if(numbers[i] > version.numbers[i]){
                return 1;
            }
        }
        // comparing length of version number.
        // ex. 1.1, 1.1.1. 
        if(numbers.length < version.numbers.length){
            return -1;
        }
        else if(numbers.length > version.numbers.length){
            return 1;
        }
        return 0;
    }

    private void parse(String string, String separator){
        String[] values = string.split(separator);

        List<Integer> list = new ArrayList<Integer>();
        StringBuilder sb = new StringBuilder();
        boolean versioningFlag = true;
        for(int i = 0; i < values.length; i++){
            if(versioningFlag){
                try{
                    Integer num = new Integer(values[i]);
                    list.add(num);
                } catch(NumberFormatException e){
                    versioningFlag = false;
                    sb.append(values[i]);
                }
            }
            else{
                sb.append(values[i]);
            }
        }

        numbers = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            numbers[i] = list.get(i);
        }
        if(sb.length() > 0){
            rest = getNormalizedString(new String(sb));
        }
    }

    private String getNormalizedString(String restValue){
        restValue = restValue.toLowerCase();
        if(restValue.startsWith("alpha"))         restValue = "1a" + restValue.substring("alpha".length());
        else if(restValue.startsWith("beta"))     restValue = "1b" + restValue.substring("beta".length());
        else if(restValue.startsWith("gamma"))    restValue = "1c" + restValue.substring("gamma".length());
        else if(restValue.startsWith("delta"))    restValue = "1d" + restValue.substring("delta".length());
        else if(restValue.startsWith("epsilon"))  restValue = "1e" + restValue.substring("epsilon".length());
        else if(restValue.startsWith("zeta"))     restValue = "1f" + restValue.substring("zeta".length());
        else if(restValue.startsWith("eta"))      restValue = "1g" + restValue.substring("eta".length());
        else if(restValue.startsWith("theta"))    restValue = "1h" + restValue.substring("theta".length());
        else if(restValue.startsWith("iota"))     restValue = "1i" + restValue.substring("iota".length());
        else if(restValue.startsWith("kappa"))    restValue = "1j" + restValue.substring("kappa".length());
        else if(restValue.startsWith("lambda"))   restValue = "1k" + restValue.substring("lambda".length());
        else if(restValue.startsWith("mu"))       restValue = "1l" + restValue.substring("mu".length());
        else if(restValue.startsWith("nu"))       restValue = "1m" + restValue.substring("nu".length());
        else if(restValue.startsWith("xi"))       restValue = "1n" + restValue.substring("xi".length());
        else if(restValue.startsWith("o"))        restValue = "1o" + restValue.substring("o".length());
        else if(restValue.startsWith("pi"))       restValue = "1p" + restValue.substring("pi".length());
        else if(restValue.startsWith("rho"))      restValue = "1q" + restValue.substring("rho".length());
        else if(restValue.startsWith("sigma"))    restValue = "1r" + restValue.substring("sigma".length());
        else if(restValue.startsWith("tau"))      restValue = "1s" + restValue.substring("tau".length());
        else if(restValue.startsWith("upsilon"))  restValue = "1t" + restValue.substring("upsilon".length());
        else if(restValue.startsWith("phi"))      restValue = "1u" + restValue.substring("phi".length());
        else if(restValue.startsWith("chi"))      restValue = "1v" + restValue.substring("chi".length());
        else if(restValue.startsWith("psi"))      restValue = "1w" + restValue.substring("psi".length());
        else if(restValue.startsWith("omega"))    restValue = "1x" + restValue.substring("omega".length());
        else if(restValue.startsWith("snapshot")) restValue = "0a" + restValue.substring("snapshot".length());
        else if(restValue.startsWith("rc"))       restValue = "2rc" + restValue.substring("rc".length());

        return restValue;
    }
}
