/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

/**
 *
 * @author arun
 */
public class ResultsBean 
{
    private String query;
    private int hits;
    private Object[] path;
    private Object[] title;
    private Object[] summary;
    private float[] scores;

    public float[] getScores() {
        return scores;
    }

    public void setScores(float[] scores) {
        this.scores = scores;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Object[] getPath() {
        return path;
    }

    public void setPath(Object[] path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
        this.query=this.query.replaceAll("%20", " ");
    }

    public Object[] getSummary() {
        return summary;
    }

    public void setSummary(Object[] summary) {
        this.summary = summary;
    }

    public Object[] getTitle() {
        return title;
    }

    public void setTitle(Object[] title) {
        this.title = title;
    }

    
}
