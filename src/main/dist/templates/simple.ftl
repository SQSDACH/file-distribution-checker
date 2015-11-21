<#assign r = data?eval>
<html>
<head>
  <meta charset="utf-8">
  <style>
    h1 { font-size: 1.2em; }
    h2 { font-size: 1em; }
  </style>
</head>
<body>
<h1>File variants of '${r.name?string}'</h1>

<#list r.groups as group>
<h2>${group?index + 1}) #${group.files?size} times (md5: ${group.hash})</h2>
<ul>
<#list group.files as file>
<li><a href="${file}">${file}</a></li>
</#list>
</ul>
</#list> 
</body>
</html>