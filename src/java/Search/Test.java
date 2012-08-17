/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.demo.PositionalPorterStopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
/**
 *
 * @author arun
 */
@WebServlet(name = "Test", urlPatterns = {"/test"})
public class Test extends HttpServlet {
  
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8");
        
        boolean error = false;                  //used to control flow for error messages
       // String indexName = "../webapps/Test/web/index";       //local copy of the configuration variable
        String indexName = "/home/arun/apache-tomcat-7.0.29/webapps/Test/index"; 
        //String indexName = "/home/saasbook/apache-tomcat-7.0.22/webapps/try/index"; 
        IndexSearcher searcher = null;          //the searcher used to open/search the index
        Query query = null;                     //the Query created by the QueryParser
        TopDocs hits = null;                       //the search results
        int startindex = 0;                     //the first index displayed on this page
        int maxpage    = 50;                    //the maximum items displayed on this page
        String queryString = null;              //the query entered in the previous page
        String startVal    = null;              //string version of startindex
        String maxresults  = null;              //string version of maxpage
        int thispage = 0;                       //used for the for/next either maxpage or
        PrintWriter out=response.getWriter();                                        //hits.totalHits - startindex - whichever is
          try {
          IndexReader reader = IndexReader.open(FSDirectory.open(new File(indexName)));
          searcher = new IndexSearcher(reader);
          //out.print("done!");
          }                                       //less
          catch (Exception e) { 
               out.print("Fail!");
               out.print(e);
               error=true;
          }
          
          if(error==false){
             queryString=request.getParameter("query");
          
          
          if (queryString == null){
                    error=true;
          }
          Analyzer analyzer = new PositionalPorterStopAnalyzer();          //construct our usual analyzer
                try {
                        QueryParser qp = new QueryParser(Version.LUCENE_30, "contents", analyzer);
                        query = qp.parse(queryString); //parse the 
                }
                
                catch (Exception e) { 
                    if(error)
                        out.print("Empty query string!");
                    else{
                    out.printf("Error while parsing : "+queryString);
                    out.print("<br>Please try a different keyword");
                    error=true;
                    }
                }
          }
          
          if (error == false && searcher != null) {
          
              thispage = maxpage;                                   // default last element to maxpage
                hits = searcher.search(query, maxpage + startindex);  // run the query 
                if (hits.totalHits == 0) {                             // if we got no results tell the user
                    out.print("<p>No results for "+queryString+"</p>");
                    error=true;
                }
          }
                  if (error == false && searcher != null) { 
                      ScoreDoc sd[]=hits.scoreDocs;
                      ResultsBean res=new ResultsBean();
                      ArrayList<String>path=new ArrayList<String>();
                      ArrayList<String>title=new ArrayList<String>();
                      ArrayList<String>summary=new ArrayList<String>();
                      float scr[]=new float[hits.totalHits];
                      res.setQuery(queryString);
                      res.setHits(hits.totalHits);
                      for (int i = startindex; i < (hits.totalHits); i++) {  // for each element
                          Document doc = searcher.doc(hits.scoreDocs[i].doc);                    //get the next document 
                        String doctitle = doc.get("title");            //get its title
                        String url = doc.get("path");  
                        scr[i]=sd[i].score;
                        //get its path field
                        if (url != null) { // strip off ../webapps prefix if present
                            if(url.startsWith("../webapps/"))    
                                url = url.substring(10);
                            if(url.contains("web/"))
                                url=url.replace("web/","");
                        }
                         //res.setPath(url, i);
                        path.add(url);
                        if ((doctitle == null) || doctitle.equals("")) //use the path if it has no title
                                doctitle = url;
                        //res.setTitle(doctitle, i);
                        //res.setSummary(doc.get("summary"), i);
                        title.add(doctitle);
                        summary.add(doc.get("summary"));
                       
                        //session.setAttribute("test", "test");
                        
                      }
                       HttpSession session = request.getSession();
                       res.setPath(path.toArray());
                       res.setTitle(title.toArray());
                       res.setSummary(summary.toArray());
                       res.setScores(scr);
                       session.setAttribute("res",res);
                       response.sendRedirect("searchDiv.jsp");
                      
                  }              
       // processRequest(request, response);
    }

   
   
}
