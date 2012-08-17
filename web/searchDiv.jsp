
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id="res"  class="Search.ResultsBean"  scope="session"></jsp:useBean>
<%--<jsp:getProperty name="res" property="query"></jsp:getProperty>
<jsp:getProperty name="res" property="hits"></jsp:getProperty>
<jsp:getProperty name="res" property="path"></jsp:getProperty>--%>

     
<p> ${res.hits} Results for: <span class="searchExpression">${res.query}</span></p>


    <ul class="searchresult">
      <% int i;
      for(i=0;i<res.getHits();i++){%>
        <li>
            <a id="foundLink<%=i%>" href="<%=res.getPath()[i]%>" class="foundResult" name="foundLink<%=i%>"><%=res.getTitle()[i]%></a>

        <div class="shortdesclink">
           <%=res.getSummary()[i]%>
        </div>
      </li>

      <li style="list-style: none; display: inline">
        <div id="rightDiv">
          <div id="star">
            <ul id="star0" class="star">
                <li id="starCur0" class="curr" style=" width: <%=java.lang.Math.round(85*(res.getScores()[i]/res.getScores()[0]))%>px; "></li>
            </ul><br style="clear: both;" />
          </div>
        </div>
      </li>

      <% }%>
    </ul>
  
