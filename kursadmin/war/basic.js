//<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
function isNumber(f, msg)
{ 	
	if (f.value.length == 0)
		f.value = 0;	
	
	if (isNaN(f.value.replace(',','.'))) 
	{ 				 
		f.focus(); 
		f.select();
		alert(msg);  
		return false;
	}
	return true;	 	
}

function echeck(str) 
{
	var at="@"
	var dot="."
	var lat=str.indexOf(at)
	var lstr=str.length
	var ldot=str.indexOf(dot)
	if (str.indexOf(at)==-1)
	   return false;
	if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
	   return false;
	if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
	    return false;
	if (str.indexOf(at,(lat+1))!=-1)
	    return false;
	if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
	    return false;
	if (str.indexOf(dot,(lat+2))==-1)
	    return false;
	if (str.indexOf(" ")!=-1)
	    return false;
	return true;					
}


function goLite(FRM,BTN)
{  
   window.document.forms[FRM].elements[BTN].style.color = "#6666AA";
   window.document.forms[FRM].elements[BTN].style.backgroundColor = "lightblue";
   window.document.forms[FRM].elements[BTN].style.borderColor = "#9999DD";
}
function goLiteObs(FRM,BTN)
{  
   window.document.forms[FRM].elements[BTN].style.color = "#FFFFFF";
   window.document.forms[FRM].elements[BTN].style.backgroundColor = "lightblue";
   window.document.forms[FRM].elements[BTN].style.borderColor = "#9999DD";
}
function goDim(FRM,BTN)
{
   window.document.forms[FRM].elements[BTN].style.color = "#888888";
   window.document.forms[FRM].elements[BTN].style.backgroundColor = "#EEEEEE";
   window.document.forms[FRM].elements[BTN].style.borderColor = "#BBBBBB";
}
function goDimObs(FRM,BTN)
{
   window.document.forms[FRM].elements[BTN].style.color = "#FFFFFF";
   window.document.forms[FRM].elements[BTN].style.backgroundColor = "#F5A9A9";
   window.document.forms[FRM].elements[BTN].style.borderColor = "#BBBBBB";
}

function showPageElement(what)
{
    var obj = typeof what == 'object'
        ? what : document.getElementById(what);

    obj.style.display = 'block';
   // return false;
}

function hidePageElement(what)
{
    var obj = typeof what == 'object'
        ? what : document.getElementById(what);

    obj.style.display = 'none';
    // return false;
}

function openWindow(url, recid, width, height) 
{
	var load = window.open(url + '?recid=' + recid,'','scrollbars=no,menubar=no,height=' + height + ',width=' + width + ',resizable=yes,toolbar=no,location=no,status=yes');
}
function openWindow2(url, width, height) 
{
	var load = window.open(url, '', 'scrollbars=no,menubar=no,height=' + height + ',width=' + width + ',resizable=yes,toolbar=no,location=no,status=yes');
}

function submitenter(myfield,e)
{
	var keycode;
	if (window.event) keycode = window.event.keyCode;
	else if (e) 
		keycode = e.which;
	else 
		return true;

	if (keycode == 13)
    {
		myfield.form.submit();
		return false;
    }
	else
		return true;
}

function openvideo(moviename)
{
	document.getElementById('video').style.display='block';			
	document.getElementById('video').style.top= '272px';
	document.getElementById('video').style.position = "absolute";
	document.getElementById('video').style.left= '218px';	
	document.getElementById("videocontent").innerHTML = '<table width="300" border="0" cellspacing="0" cellpadding="0"><tr>' + 
														'<td colspan="2"><object width="300" height="250"><param name="movie"' + 
														'value="http://www.youtube.com/v/' + moviename + '"/><param name="wmode" value="transparent" />' +
														'<embed src="http://www.youtube.com/v/' + moviename + '" type="application/x-shockwave-flash"' +
														'wmode="transparent" width="300" height="250"></embed></object></td>    </tr>  </table>';
}
function closevideo()
{
	document.getElementById('video').style.display='none';
	document.getElementById("videocontent").innerHTML = "";
}

