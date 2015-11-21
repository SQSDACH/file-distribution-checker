<#assign r = data?eval>
File variants of '${r.name?string}'

<#list r.groups as group>
${group?index + 1}) #${group.files?size} times (md5: ${group.hash})
<#list group.files as file>
${file}
</#list>

</#list> 