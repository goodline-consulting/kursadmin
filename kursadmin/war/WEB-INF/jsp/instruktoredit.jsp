<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>InstruktörsInfo</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>InstruktörsInfo</h1>
 
<form name="insform" method="post" action="instruktoredit.htm" enctype="multipart/form-data">
	<div id="menu">
    <ul>
	   <li><a href="instruktorer.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       <li><a href="javascript:insform.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
       <li><a href="javascript:insform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
    </ul>
	</div><br><br><br>
  <table width="70%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" width="10%">Namn:</td>
        <td>
          <input type="text" size="60" name="namn" value="${modelMap.instruktor.namn}"/>
        </td>
        <td rowspan="5" align="right" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${!empty modelMap.instruktor.bild}">
          <img src="${modelMap.images}/${modelMap.instruktor.bild}" width="${modelMap.instruktor.bildx}" height="${modelMap.instruktor.bildy}"/>          
        </c:if>
        <c:if test="${empty modelMap.instruktor.bild}">
          <img src="${modelMap.images}/bildsaknas.jpg" width="160" height="124"/>          
        </c:if>  
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Adress:</td>
        <td colspan="2">
          <input type="text" size="60" name="adress" value="${modelMap.instruktor.adress}">
        </td>               
    </tr>    
    <tr>
      <td align="right" width="10%">Postnr:</td>
        <td colspan="2">
         <input type="text" size="5" name="postnr" value="${modelMap.instruktor.postnr}"/>
        </td>               
    </tr>
     <tr>
      <td align="right" width="10%">Postadress:</td>
        <td colspan="2">
          <input type="text" size="60" name="postadress" value="${modelMap.instruktor.postadress}"/>
        </td>               
    </tr>
    <tr>
      <td align="right" width="10%">Telefon:</td>
        <td colspan="2">
          <input type="text" size="30" name="telefon" value="${modelMap.instruktor.telefon}"/>
        </td>
    </tr>
    <tr>
      <td align="right" width="10%">Mobil:</td>
        <td colspan="2">
          <input type="text" size="30" name="mobil" value="${modelMap.instruktor.mobil}"/>
        </td>           
    </tr>
    <tr>
      <td align="right" width="10%">Email:</td>
        <td colspan="2">
          <input type="text" size="60" name="email" value="${modelMap.instruktor.email}"/>
        </td>
   </tr>
    <tr>
      <td align="right" valign="top" width="10%">Info:</td>
        <td colspan="2">
          <textarea rows="5" cols="80" name="info">${modelMap.instruktor.info}</textarea>
        </td>
    </tr>
    <tr><td align="right" valign="top" width="10%">Bild:</td>     
    <td colspan="2"><input type="file" size="60" name="bildfil" value="${modelMap.instruktor.bild}" accept="bilder/jpg">&nbsp;<a href="javascript:insform._action.value='reload';insform.submit();">Ladda bild</a></td>
	</tr>¨
	<tr>  
    	<td align="right" valign="top" width="10%">Bredd:</td>
    	<td colspan="3" valign="top"><input  type="text" size="5" name="bredd" value="${modelMap.instruktor.bildx}"/>
    	&nbsp;Höjd:
    	<input type="text" size="5" name="hojd" value="${modelMap.instruktor.bildy}"/></td>
    	</tr>
  </table>
  <input type="hidden" name="_action">
  <br>
</form>
</body>
</html>
