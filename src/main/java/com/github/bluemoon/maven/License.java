package com.github.bluemoon.maven;

import java.io.Serializable;

/**
 * This class represents license of artifact.
 * 
 * @author Haruaki Tamada
 */
public class License implements Serializable{
    private static final long serialVersionUID = -1409392739556008675L;

    /**
     * Apache License version 2.0
     * see http://www.apache.org/licenses/LICENSE-2.0
     */
    public static final License APACHE_LICENSE_2_0 = new License(
        "The Apache License Version 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0"
    );

    /**
     * GNU General Public License version 2
     * see http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
     */
    public static final License GNU_GPL_2 = new License(
        "GNU General Public License version 2",
        "http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt"
    );

    /**
     * GNU General Public License version 3
     * see http://www.gnu.org/licenses/gpl-3.0.txt
     */
    public static final License GNU_GPL_3 = new License(
        "GNU General Public License version 3",
        "http://www.gnu.org/licenses/gpl-3.0.txt"
    );

    /**
     * Mozilla Public License version 1.1
     * see http://www.mozilla.org/MPL/MPL-1.1.txt
     */
    public static final License MPL_1_1 = new License(
        "Mozilla Public License version 1.1",
        "http://www.mozilla.org/MPL/MPL-1.1.txt"
    );

    private String name;
    private String url;

    /**
     * Basic constructor.
     */
    public License(String name, String url){
        this.name = name;
        this.url = url;
    }

    /**
     * returns the name of this license.
     */
    public String getName(){
        return name;
    }

    /**
     * returns the url of this license.
     */
    public String getUrl(){
        return url;
    }

    /**
     * returns true when given object is equals this object.
     */
    public boolean equals(Object o){
        if(o instanceof License){
            License license = (License)o;
            return getName().equals(license.getName())
                   && getUrl().equals(license.getUrl());
        }
        return false;
    }

    /**
     * returns hash code.
     */
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null)? 0: getName().hashCode());
        result = prime * result + ((getUrl() == null)? 0: getUrl().hashCode());
        return result;
    }
}
