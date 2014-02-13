
/**
 * Created with IntelliJ IDEA.
 * User: relango
 * Date: 11/5/13
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */


// import of HttpBuilder related stuff
import groovyx.net.http.*
import java.util.concurrent.Executors
import java.util.concurrent.Callable

def dir= System.properties['user.home'];
File f = new File("${dir}/trb.csv");
def urlPrefix =   "http://111.118.182.204/TET_Paper2_result/TETPaper2_ResultPrint.aspx?RollNo=13TE442"
//def url = "http://111.118.182.204/TET_Paper2_result/TETPaper2_ResultPrint.aspx?RollNo=13TE44201983"


pool = Executors.newFixedThreadPool(20);

def runInThread = { c -> pool.submit(c as Callable) }
List futures = [];
(13242..99999).each{
    String rollNo = String.format("%05d", it);
   // rollNo = rollNo.padLeft((6 - it.toString().length()) , "0")
    futures << runInThread{doWork(f, urlPrefix + rollNo)}
}

futures.each {it.get()};
f.close();
pool.shutdown();


def doWork(File f, def url){
    println url
    def http = new HTTPBuilder(url)

    def html = http.get([:])

    def table = html."**".findAll {it.name() == 'TD' && it.@align == 'center';
    }

    def tds =  table[0].depthFirst().findAll {it.name() == 'TD'}

    if (tds.size() > 1) {
        f.append("${tds[1]} , ${tds[8]}\n")
    }
}