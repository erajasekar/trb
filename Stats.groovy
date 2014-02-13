
/**
 * Created with IntelliJ IDEA.
 * User: relango
 * Date: 11/5/13
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */

def dir= System.properties['user.home'];
File f = new File("${dir}/trb.csv");
List values = []
f.eachLine { if (it.length() > 0) {
   values << it.split(",")[1]?.toInteger()
}}

def stat = values.groupBy { if (it.toInteger() > 90 && it.toInteger() < 100) return "90-100" else if (it.toInteger() > 100) return ">100" else return "<90"};
stat.each {k,v->
    println "${k} == ${v.size()}"
}

println "Max => ${values.max()}";