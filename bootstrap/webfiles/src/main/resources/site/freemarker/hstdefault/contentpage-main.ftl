<#include "../include/imports.ftl">

<#-- @ftlvariable name="document" type="com.incentro.beans.ContentDocument" -->
<#if document??>
<article class="has-edit-button">
    <@hst.cmseditlink hippobean=document/>
  <h3>${document.title?html}</h3>
    <#if document.publicationDate??>
      <p>
          <@fmt.formatDate value=document.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/>
      </p>
    </#if>
    <#if document.introduction??>
      <p>
      ${document.introduction?html}
      </p>
    </#if>
    <@hst.html hippohtml=document.content/>
    <#if document.externalDocument??>
        <#list document.externalDocument as exdoc>
            <#assign jsonDoc = exdoc?eval>
          <table border="1">
            <tr>
              <td>${jsonDoc.id}</td>
              <td>${jsonDoc.title}</td>
            </tr>
            <tr>
              <td>${jsonDoc.description}</td>
              <td><img src="${jsonDoc.icon}"/></td>
            </tr>
          </table>
        </#list>
    <#else>
      <h2>no external docs found.</h2>
    </#if>
</article>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
<div>
  <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.png" />"> Click to edit Simple Content
</div>
</#if>